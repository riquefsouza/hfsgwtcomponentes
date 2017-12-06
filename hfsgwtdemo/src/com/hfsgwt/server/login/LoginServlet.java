package com.hfsgwt.server.login;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 2861261785353824832L;

	public void init(ServletConfig cfg) {
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String perfil = (String) session.getAttribute("PERFIL_OPERADOR");
		String sIp = request.getRemoteAddr();
		String sId = request.getSession().getId();
		// String[] meuperfil = analize(perfil);
		UsuarioSessaoVO usuario = new UsuarioSessaoVO();
		usuario.setIdSessao(sId);
		usuario.setIp(sIp);
		usuario.setLogin(session.getAttribute("CODIGO_OPERADOR").toString());
		usuario.setNome(session.getAttribute("NOME_OPERADOR").toString());
//		usuario.setIdOrgao(RepositorioCivil.getInstancia().consultaOrgao(
//				session.getAttribute("CODIGO_OPERADOR").toString()));
//		usuario.setIdUnidade(RepositorioCivil.getInstancia().consultaUnidade(
//				session.getAttribute("CODIGO_OPERADOR").toString()));
		usuario.setInicioSessao(new java.util.Date());
		// registra a secao do usuario
		// SessionManager.getInstancia().putSessao(usuario.getIdSessao(),
		// usuario);
		request.setAttribute("perfil", perfil);
		session.setAttribute("perfil", perfil);
		session.setAttribute("usuarioSessaoVO", usuario);
		session.setAttribute("usuarioLogado", usuario.getNome());
//		session.setAttribute("unidadeUsuarioLogado", RepositorioCivil
//				.getInstancia().consultaDescUnidade(
//						session.getAttribute("CODIGO_OPERADOR").toString()));

		//return (map.findForward("granted"));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	}

	public void destroy() {
	}
}
