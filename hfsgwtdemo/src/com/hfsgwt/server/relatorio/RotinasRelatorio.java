package com.hfsgwt.server.relatorio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;
import com.hfsgwt.server.util.Rotinas;
import com.hfsgwt.server.util.RotinasZIP;
import com.hfsgwt.server.util.VisitarDiretorio;

public final class RotinasRelatorio {
	private static Logger log = Logger.getLogger(RotinasRelatorio.class);
	private static RotinasRelatorio instancia;

	public enum FormatoArquivo {
		PDF, HTML, XLS, RTF
	}

	private RotinasRelatorio() {
		super();
	}

	public static RotinasRelatorio getInstancia() {
		if (instancia == null) {
			instancia = new RotinasRelatorio();
		}
		return instancia;
	}

	public void relatorioPDF(HttpServletResponse res, String arquivoPDF) {
		try {
			byte[] pdf = null;
			if (arquivoPDF != null) {
				pdf = Rotinas.getBytesDoArquivo(new File(arquivoPDF));

				// Diz que vai enviar um arquivo do tipo PDF
				res.setContentType("application/pdf");

				// Diz o tamanho dos dados
				res.setContentLength(pdf.length);

				// Escreve no browser
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(pdf, 0, pdf.length);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (IOException e) {
			log.error("Erro de entrada/saída ao gerar relatório, "
					+ e.getMessage());
		}
	}

	/**
	 * Realiza a conversão de um relatório jasper para PDF.
	 * 
	 * @param response
	 *            o HttpServletResponse do servlet
	 * @param caminhoRelatorio
	 *            o caminho onde se encontra o relatorio
	 * @param paramsRelatorio
	 *            o parâmetros contido no Jasper relatório
	 * @param fonteDados
	 *            o JRDataSource contendo os dados do relatório
	 */
	public void relatorioPDF(HttpServletResponse res, String jasperRelatorio,
			HashMap<String, String> paramsRelatorio, JRDataSource fonteDados) {
		try {
			byte[] pdf = null;

			pdf = JasperRunManager.runReportToPdf(jasperRelatorio,
					paramsRelatorio, fonteDados);

			// Diz que vai enviar um arquivo do tipo PDF
			res.setContentType("application/pdf");

			// Diz o tamanho dos dados
			res.setContentLength(pdf.length);

			// Escreve no browser
			ServletOutputStream ouputStream;

			ouputStream = res.getOutputStream();
			ouputStream.write(pdf, 0, pdf.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			log.error("Erro de entrada/saída ao gerar relatório, "
					+ e.getMessage());
		} catch (JRException e) {
			log.error("Erro ao gerar relatório, " + e.getMessage());
		}
	}

	public void gerarRelatorioParaArquivo(FormatoArquivo tipo, File arquivo,
			String jasperRelatorio, HashMap<String, String> paramsRelatorio,
			JRDataSource fonteDados) {
		try {
			// InputStream arquivoJasper = Rotinas.getTextoDentroJar(
			// RotinasRelatorio.class, jasperRelatorio);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperRelatorio, paramsRelatorio, fonteDados);
			if (tipo == FormatoArquivo.PDF) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, arquivo
						.getPath());
			} else if (tipo == FormatoArquivo.HTML) {
				JasperExportManager.exportReportToHtmlFile(jasperPrint, arquivo
						.getPath());
			} else if (tipo == FormatoArquivo.XLS) {
				FileOutputStream arqRel = new FileOutputStream(arquivo);
				JExcelApiExporter exporterXLS = new JExcelApiExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				exporterXLS
						.setParameter(
								JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
						arqRel);
				exporterXLS.exportReport();
			} else if (tipo == FormatoArquivo.RTF) {
				FileOutputStream arqRel = new FileOutputStream(arquivo);
				JRRtfExporter exporterRTF = new JRRtfExporter();
				exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM,
						arqRel);
				exporterRTF.exportReport();
			}
		} catch (IOException e) {
			log.error("Erro de entrada/saída ao gerar relatório para arquivo, "
					+ e.getMessage());
		} catch (JRException e) {
			log
					.error("Erro ao gerar relatório para arquivo, "
							+ e.getMessage());
		}
	}

	public void relatorioPorFormato(HttpServletResponse res,
			String jasperRelatorio, HashMap<String, String> paramsRelatorio,
			JRDataSource fonteDados, FormatoArquivo tipo, String diretorio,
			boolean compactado, boolean escreverNoBrowser)
			throws ServicoException {
		try {
			byte[] conteudo = null;

			switch (tipo) {
			case PDF:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "pdf",
						"application/pdf", escreverNoBrowser);
				break;
			case HTML:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "html",
						"text/html", escreverNoBrowser);
				break;
			case XLS:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "xls",
						"application/vnd.ms-excel", escreverNoBrowser);
				break;
			case RTF:
				conteudo = geraConteudo(res, jasperRelatorio, paramsRelatorio,
						fonteDados, tipo, diretorio, compactado, "rtf",
						"application/rtf", escreverNoBrowser);
				break;
			}

			if (escreverNoBrowser) {
				res.setContentLength(conteudo.length);
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(conteudo, 0, conteudo.length);
				ouputStream.flush();
				ouputStream.close();
			}
		} catch (IOException e) {
			log.error("Erro de entrada/saída ao gerar relatório, "
					+ e.getMessage());
		}
	}

	private byte[] geraConteudo(HttpServletResponse res,
			String jasperRelatorio, HashMap<String, String> paramsRelatorio,
			JRDataSource fonteDados, FormatoArquivo formato, String diretorio,
			boolean compactado, String extensao, String tipo,
			boolean excluirArquivo) throws IOException, ServicoException {
		byte[] conteudo;
		String sArquivo;
		File arquivo;
		sArquivo = Rotinas.getArquivoRandom(extensao);
		arquivo = new File(diretorio, sArquivo);

		if (!compactado) {
			gerarRelatorioParaArquivo(formato, arquivo, jasperRelatorio,
					paramsRelatorio, fonteDados);
			conteudo = Rotinas.getBytesDoArquivo(arquivo);
			res.setContentType(tipo);
		} else {
			gerarRelatorioParaArquivo(formato, arquivo, jasperRelatorio,
					paramsRelatorio, fonteDados);

			String arquivoZIP = diretorio + "/" + sArquivo + ".zip";
			if (formato == FormatoArquivo.HTML) {
				String dirResto = diretorio + "/" + sArquivo + "_files";

				if (VisitarDiretorio.getInstancia().ListarDiretorio(dirResto)){
					ArrayList<File> lista = VisitarDiretorio.getInstancia().getListaFile();
					String[] arquivos = new String[lista.size()+1];
					String[] dirs = new String[lista.size()+1];
					
					dirs[0] = "";
					arquivos[0] = sArquivo;
					
				    for (int j = 0; j < lista.size(); j++) {
				    	dirs[j+1] = lista.get(j).getParentFile().getName();
				    	arquivos[j+1] = lista.get(j).getName();
					}
					RotinasZIP.getInstancia().criarZIP(arquivoZIP, diretorio, dirs, arquivos);
					
					for (File file:lista) {
						file.delete();
					}
					File subdir = new File(dirResto);
					subdir.delete();
				}
				
			} else
				RotinasZIP.getInstancia().criarZIP(arquivoZIP, diretorio,
						new String[] { sArquivo });
			
			arquivo.delete();

			arquivo = new File(arquivoZIP);
			conteudo = Rotinas.getBytesDoArquivo(arquivo);
			res.setContentType("application/zip");
		}
		if (excluirArquivo) {
			arquivo.delete();
		}

		return conteudo;
	}

}
