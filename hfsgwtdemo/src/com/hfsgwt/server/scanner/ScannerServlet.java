package com.hfsgwt.server.scanner;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.hfsgwt.server.util.Rotinas;

public class ScannerServlet extends HttpServlet {
	private static final long serialVersionUID = 1973478811017153129L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		//System.out.println("ScannerServlet-doGet");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		int tamanho = req.getContentLength();
		byte[] dados = new byte[tamanho];

		ServletInputStream sin = req.getInputStream();
		dados = Rotinas.StreamToByteArray(sin, tamanho);
		
		byte[] conteudo = Base64.decodeBase64(dados);
		System.out.println("Tamanho Dados ScannerServlet: "+conteudo.length);
		//byte[] conteudo = RotinasZIP.getInstancia().descomprimirByteArray(dados);

		String dir = Rotinas.dirScanner;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		Rotinas.salvaArquivo(arq, conteudo, true);		
	}

}
