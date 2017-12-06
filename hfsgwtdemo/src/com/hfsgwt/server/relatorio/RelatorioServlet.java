package com.hfsgwt.server.relatorio;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hfsgwt.server.util.Rotinas;

public abstract class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = -864178149348017174L;

	public enum TipoOpcao {
		PADRAO, ARQUIVO
	}
	
	private String caminhoJasper;
	private String diretorioArquivo;
	private String diretorioRelatorio;
	private TipoOpcao opcao;
	private String nome;
	private RotinasRelatorio.FormatoArquivo formatoArquivo;
	private boolean compactado;

	public void init(ServletConfig cfg) {
		this.caminhoJasper = Rotinas.dirRelatorios;
		this.diretorioArquivo = Rotinas.dirTemp;
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String sopcao = req.getParameter("opcao");
		this.nome = req.getParameter("nome");
		String formato = req.getParameter("formato");

		this.compactado = (new Boolean(req.getParameter("compactado")))
				.booleanValue();
		this.diretorioRelatorio = getCaminhoJasper() + "/" + getNome()
				+ ".jasper";

		if (sopcao.equals("PADRAO"))
			this.opcao = TipoOpcao.PADRAO;
		else if (sopcao.equals("ARQUIVO"))
			this.opcao = TipoOpcao.ARQUIVO;
		
		if (formato.equals("PDF"))
			this.formatoArquivo = RotinasRelatorio.FormatoArquivo.PDF;
		else if (formato.equals("HTML"))
			this.formatoArquivo = RotinasRelatorio.FormatoArquivo.HTML;
		else if (formato.equals("XLS"))
			this.formatoArquivo = RotinasRelatorio.FormatoArquivo.XLS;
		else if (formato.equals("RTF"))
			this.formatoArquivo = RotinasRelatorio.FormatoArquivo.RTF;
		else
			this.formatoArquivo = RotinasRelatorio.FormatoArquivo.PDF;
	}

	public String getCaminhoJasper() {
		return caminhoJasper;
	}

	public String getDiretorioArquivo() {
		return diretorioArquivo;
	}

	public TipoOpcao getOpcao() {
		return opcao;
	}

	public String getNome() {
		return nome;
	}

	public String getDiretorioRelatorio() {
		return diretorioRelatorio;
	}

	public boolean isCompactado() {
		return compactado;
	}

	public RotinasRelatorio.FormatoArquivo getFormatoArquivo() {
		return formatoArquivo;
	}
}
