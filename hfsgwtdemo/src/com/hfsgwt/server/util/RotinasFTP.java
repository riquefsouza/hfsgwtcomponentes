package com.hfsgwt.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.hfsgwt.client.componentes.ftp.FTPArquivo;
import com.hfsgwt.server.ServicoException;

public final class RotinasFTP {
	private static Logger log = Logger.getLogger(RotinasFTP.class);
	private static RotinasFTP instancia;

	// private FTPClient ftp;

	public static final int TIPO_ARQUIVO = 0;

	public static final int TIPO_DIRETORIO = 1;

	public static final int TIPO_LINKSIMBOLICO = 2;

	private RotinasFTP() {
		super();
		// ftp = new FTPClient();
	}

	public static RotinasFTP getInstancia() {
		if (instancia == null) {
			instancia = new RotinasFTP();
		}
		return instancia;
	}

	public boolean Conectado(FTPClient ftp) {
		return ftp.isConnected();
	}

	public FTPClient ConectaServidorFTP(String servidorFTP, String usuario,
			String senha, String diretorio) throws ServicoException {
		FTPClient ftp = new FTPClient();
		try {
			if (!ftp.isConnected()) {
				log.info("Conectando com o Servidor FTP");
				ftp.connect(servidorFTP);
				ftp.login(usuario, senha);
				ftp.changeWorkingDirectory(diretorio);
				return ftp;
			}
		} catch (SocketException e) {
			throw new ServicoException(log,
					"Erro ao conectar no servidor FTP!, " + e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro ao conectar no servidor FTP!, " + e.getMessage());
		}
		return null;
	}

	public void DesconectaServidorFTP(FTPClient ftp) throws ServicoException {
		try {
			if (ftp.isConnected()) {
				log.info("Desconectando do Servidor FTP");
				ftp.logout();
				ftp.disconnect();
			}
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro ao desconectar do servidor FTP!, " + e.getMessage());
		}
	}

	public FTPFile[] listaArquivos(FTPClient ftp, String sExtensao)
			throws ServicoException {
		FTPFile[] arquivos = null;
		if (ftp.isConnected()) {
			try {
				// String[] arq = ftp.listNames("*.pdf");
				arquivos = ftp.listFiles(sExtensao);
			} catch (IOException e) {
				throw new ServicoException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return arquivos;
	}

	public FTPArquivo[] listaArquivosFTP(FTPClient ftp, String caminho,
			boolean somenteDiretorio) throws ServicoException {
		ArrayList<FTPArquivo> lista = new ArrayList<FTPArquivo>();
		FTPFile[] arquivos = null;
		if (ftp.isConnected()) {
			try {
				arquivos = ftp.listFiles(caminho);
				for (int i = 0; i < arquivos.length; i++) {

					if (somenteDiretorio) {
						if (arquivos[i].getType() == TIPO_DIRETORIO) {
							lista.add(preencheFTPArquivo(arquivos[i]));
						}
					} else {
						lista.add(preencheFTPArquivo(arquivos[i]));
					}
				}
			} catch (IOException e) {
				throw new ServicoException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return (FTPArquivo[]) lista.toArray(new FTPArquivo[lista.size()]);
	}

	private FTPArquivo preencheFTPArquivo(FTPFile arq) {
		FTPArquivo ftparq = new FTPArquivo();
		ftparq.setNome(arq.getName());
		ftparq.setTamanho(arq.getSize());
		switch (arq.getType()) {
		case TIPO_ARQUIVO:
			ftparq.setTipo(FTPArquivo.Tipo.ARQUIVO);
			break;
		case TIPO_DIRETORIO:
			ftparq.setTipo(FTPArquivo.Tipo.DIRETORIO);
			break;
		case TIPO_LINKSIMBOLICO:
			ftparq.setTipo(FTPArquivo.Tipo.LINKSIMBOLICO);
			break;
		}
		ftparq.setData(arq.getTimestamp().getTime());
		return ftparq;
	}

	public ArrayList<String> listaNomes(FTPClient ftp, String sExtensao)
			throws ServicoException {
		ArrayList<String> arquivos = new ArrayList<String>();
		if (ftp.isConnected()) {
			try {
				String[] nomes = ftp.listNames(sExtensao);
				for (int ncont = 0; ncont < nomes.length; ncont++) {
					arquivos.add(nomes[ncont]);
				}
			} catch (IOException e) {
				throw new ServicoException(log,
						"Erro ao listar arquivos do servidor FTP!, "
								+ e.getMessage());
			}
		}
		return arquivos;
	}

	public boolean enviarArquivo(FTPClient ftp, String sDir, String sArquivo)
			throws ServicoException {
		FileInputStream fis;
		File arq;
		if (ftp.isConnected()) {
			try {
				arq = new File(sDir + sArquivo);
				if (!arq.exists()) {
					throw new ServicoException(log, "Arquivo não encontrado ("
							+ sDir + sArquivo + ") no diretorio!");
				}

				fis = new FileInputStream(sDir + sArquivo);

				if (ftp.storeFile(sArquivo, fis)) {
					fis.close();
					return true;
				} else
					return false;
			} catch (IOException e) {
				throw new ServicoException(log, "Erro ao enviar arquivo ("
						+ sDir + sArquivo + ") para o FTP!, " + e.getMessage());
			}
		}
		return false;
	}

	public boolean receberArquivo(FTPClient ftp, String sDir, String sArquivo)
			throws ServicoException {
		FileOutputStream fos;
		if (ftp.isConnected()) {
			try {
				fos = new FileOutputStream(sDir + sArquivo);
				if (ftp.retrieveFile(sArquivo, fos)){
					fos.close();
					return true;
				} else
					return false;
			} catch (IOException e) {
				throw new ServicoException(log, "Erro ao receber arquivo ("
						+ sArquivo + ") do FTP!, " + e.getMessage());
			}
		}
		return false;
	}

	public boolean excluirArquivo(FTPClient ftp, String sArquivo)
			throws ServicoException {
		if (ftp.isConnected()) {
			try {
				ftp.deleteFile(sArquivo);
				return true;
			} catch (IOException e) {
				throw new ServicoException(log, "Erro ao excluir arquivo ("
						+ sArquivo + ") do FTP!, " + e.getMessage());
			}
		}
		return false;
	}
}