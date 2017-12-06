package com.hfsgwt.server.captcha;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;

public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = -8031668099331839551L;
	private static Logger log = Logger.getLogger(CaptchaServlet.class);

	// protected void service(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Boolean isResponseCorrect = Boolean.FALSE;
		String captchaId = request.getSession().getId();
		String resposta = request.getParameter("texto");
		try {
			isResponseCorrect = CaptchaServiceSingleton.getInstance()
					.validateResponseForID(captchaId, resposta);
			
			if (isResponseCorrect){
				resposta = "A resposta ["+resposta+"] está correta!";
			} else 
				resposta = "A resposta ["+resposta+"] NÃO está correta!";
			
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.print(resposta);
			ouputStream.flush();
			ouputStream.close();
			
		} catch (CaptchaServiceException e) {
			log.error("Erro ao realizar validação Captcha, " + e.getMessage());
		}
	}
}
