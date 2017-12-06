package com.hfsgwtdemo.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hfsgwtdemo.server.endereco.RotinasEndereco;
import com.hfsgwtdemo.server.siadm.RotinasSiadm;

public class HfsgwtdemoCtxListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String caminho = sce.getServletContext().getRealPath("/");
		RotinasSiadm.getInstancia().iniciar(caminho);
		RotinasEndereco.getInstancia().iniciar(caminho);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		RotinasSiadm.getInstancia().finalizar();
		RotinasEndereco.getInstancia().finalizar();
	}
	
}
