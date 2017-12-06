package com.hfsgwt.server.chart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChartServlet extends HttpServlet {
	private static final long serialVersionUID = 770489670094293924L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String sArquivo = req.getParameter("arquivo");
		RotinasChart.getInstancia().gerarGrafico(res, sArquivo);
	}

}
