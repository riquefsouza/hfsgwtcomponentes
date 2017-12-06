package com.hfsgwt.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hfsgwt.server.util.Rotinas;

public class HfsgwtCtxListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// System.out.println("Contexto iniciado: "+sce);
		Rotinas.dirMenuXML = sce.getServletContext().getRealPath("menuxml");
		Rotinas.dirRelatorios = sce.getServletContext().getRealPath(
				"relatorios");
		Rotinas.dirTemp = sce.getServletContext().getRealPath("temp");
		Rotinas.dirUpload = sce.getServletContext().getRealPath("upload");
		Rotinas.dirFTP = sce.getServletContext().getRealPath("ftp");
		Rotinas.dirScanner = sce.getServletContext().getRealPath("scanner");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// System.out.println("Contexto destruído: "+sce);
	}

}
