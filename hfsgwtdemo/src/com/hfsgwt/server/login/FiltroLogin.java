package com.hfsgwt.server.login;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FiltroLogin implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);

		String perfil = request.getParameter(Constantes.PERFIL);
		String perfilMasked = request.getParameter(Constantes.PERFIL_MASKED);

		if (perfil == null && perfilMasked == null) {
			if (session == null) {
				httpRequest.getRequestDispatcher("/index.html").forward(
						request, response);
				
			} else {
				UsuarioSessaoVO usuario = (UsuarioSessaoVO) session
						.getAttribute("usuarioSessaoVO");
				if (usuario == null) {
					httpRequest.getRequestDispatcher("/index.html").forward(
							request, response);
				} else {
					chain.doFilter(request, response);					
				}

			}
		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {
	}

}
