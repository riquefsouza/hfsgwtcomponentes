package com.hfsgwt.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwt.server.util.RotinasFTP;

public class UploadServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(UploadServlet.class);
	private static final long serialVersionUID = -1328984045964403221L;

	private String diretorio;

	private boolean habilitarFTP;

	private String servidorFTP;

	private String usuarioFTP;

	private String senhaFTP;

	private String diretorioFTP;

	public void init(ServletConfig cfg) {
		// System.out.println("UploadServlet criado!"
		// + cfg.getServletContext().getContextPath());
		diretorio = Rotinas.dirUpload;
		habilitarFTP = (new Boolean(cfg.getInitParameter("HabilitarFTP"))).booleanValue();
		servidorFTP = cfg.getInitParameter("ServidorFTP");
		usuarioFTP = cfg.getInitParameter("UsuarioFTP");
		senhaFTP = cfg.getInitParameter("SenhaFTP");
		diretorioFTP = cfg.getInitParameter("DiretorioFTP");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println("UploadServlet doGET!");
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// System.out.println("UploadServlet doPOST!");

		// DiskFileItemFactory factory = new DiskFileItemFactory();
		// // maximum size that will be stored in memory
		// factory.setSizeThreshold(4096);
		// // the location for saving data that is larger than
		// getSizeThreshold()
		// factory.setRepository(new File("/tmp"));
		//
		// ServletFileUpload upload = new ServletFileUpload(factory);
		// // maximum size before a FileUploadException will be thrown
		// upload.setSizeMax(1000000);
		//
		// List fileItems;
		// try {
		// fileItems = upload.parseRequest(req);
		// // assume we know there are two files. The first file is a small
		// // text file, the second is unknown and is written to a file on
		// // the server
		// Iterator i = fileItems.iterator();
		// String comment = ((FileItem) i.next()).getString();
		// FileItem fi = (FileItem) i.next();
		// // filename on the client
		// String fileName = fi.getName();
		// // save comment and filename to database
		// // ...
		// // write the file
		// fi.write(new File("/www/uploads/", fileName));
		// } catch (FileUploadException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

//		formPanel.setAction("arquivoupload?ftp=true&nome=henrique");		
//		String ftp = req.getParameter("ftp");
//		String nome = req.getParameter("nome");

		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List<FileItem> files = upload.parseRequest(req);

				// Iterator<FileItem> i = files.iterator();
				// String texto = ((FileItem) i.next()).getString();
				// System.out.println(texto);

				for (FileItem arquivo : files) {
					String nomeArquivo = arquivo.getName();

					try {
						arquivo.write(new File(diretorio, nomeArquivo));
					} catch (Exception e) {
						log.error("Erro ao salvar o arquivo " + diretorio
								+ nomeArquivo + ", " + e.getMessage());
					}

					// byte[] buffer = file.get();
					// String texto = arquivo.getString();
					// System.out.println(texto);
					
					enviarParaFTP(nomeArquivo);
				}
			} catch (FileUploadException e) {
				log.error("Erro ao realizar o upload de arquivo, "
						+ e.getMessage());
			}
		}

	}

	private void enviarParaFTP(String nomeArquivo) {
		if (habilitarFTP) {
			try {				
				FTPClient ftp = RotinasFTP.getInstancia().ConectaServidorFTP(servidorFTP,
						usuarioFTP, senhaFTP, diretorioFTP);
				
				if (RotinasFTP.getInstancia().Conectado(ftp)){
					RotinasFTP.getInstancia().enviarArquivo(ftp, diretorio+"\\", nomeArquivo);					
					RotinasFTP.getInstancia().DesconectaServidorFTP(ftp);
					File file = new File(diretorio, nomeArquivo);
					file.delete();
				}
			} catch (ServicoException e) {
			}
		}
	}

	public void destroy() {
		// System.out.println("UploadServlet destruído!");
	}
}
