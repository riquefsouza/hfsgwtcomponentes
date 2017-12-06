package com.hfsgwt.server.mapa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwt.server.util.StringList;

public class MapaServlet extends HttpServlet {
	// private static Logger log = Logger.getLogger(MapaServlet.class);
	private static final long serialVersionUID = 1631721088906184120L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		StringList sl = Rotinas.lerTextoDentroJar2(this, "mapa.html");

		byte[] conteudo = sl.getText().getBytes();

		res.setContentType("text/html");
		res.setContentLength(conteudo.length);

		// Escreve no browser
		ServletOutputStream ouputStream = res.getOutputStream();
		ouputStream.write(conteudo, 0, conteudo.length);
		ouputStream.flush();
		ouputStream.close();
	}

}
