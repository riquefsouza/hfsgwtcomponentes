package com.hfsgwt.server.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;
import com.hfsgwt.server.util.RotinasCRC32;

public final class RotinasLogin {
	private static Logger log = Logger.getLogger(RotinasLogin.class);
	private static RotinasLogin instancia;

	private String perfil;

	private RotinasLogin() {
		super();
	}

	public static RotinasLogin getInstancia() {
		if (instancia == null) {
			instancia = new RotinasLogin();
		}
		return instancia;
	}

	public boolean validarLogin(String hostname, String login, String senha)
			throws ServicoException {
		try {
			// URL host = this.getCodeBase();
			// String hostname = host.toString();
			// String url =
			// "http://172.30.2.92:8080/login/loginAppletServlet?user="+ user
			// +"&pass="+pass;
			String url = hostname + "loginAppletServlet?user=" + login
					+ "&pass=" + senha;
			URL cl = new URL(url);
			URLConnection conn = cl.openConnection();
			conn.setUseCaches(false);
			InputStream in = conn.getInputStream();
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in));
			this.perfil = getPerfil(buffer.readLine());
			return true;
		} catch (IOException e) {
			throw new ServicoException(log, "Erro ao validar o login!, "
					+ e.getMessage());
		}
	}

	public boolean alterarSenha(String hostname, String login, String senha,
			String novaSenha) throws ServicoException {
		try {
			// URL host = this.getCodeBase();
			// String hostname = host.toString();
			// String url =
			// "http://172.30.2.92:8080/login/loginAppletServlet?user="+ user
			// +"&pass="+pass;
			String url = hostname + "servletAlterarSenha?user=" + login
					+ "&pass=" + senha + "&newPass=" + novaSenha;
			URL cl = new URL(url);
			URLConnection conn = cl.openConnection();
			conn.setUseCaches(false);
			InputStream in = conn.getInputStream();
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in));
			String retorno = buffer.readLine();

			if (retorno.indexOf("OK") != -1)
				return true;
			else
				return false;
		} catch (IOException e) {
			throw new ServicoException(log, "Erro ao alterar senha!, "
					+ e.getMessage());
		}
	}

	private String getPerfil(String retorno) {
		if (!retorno.equals("CODIGO INVALIDO!")) {
			int size = retorno.length();
			if (retorno.indexOf("res:") != -1) {
				retorno = retorno.substring(4, size);
			} else if (retorno.indexOf("exc:") != -1) {
				int badpos = retorno.indexOf("-");
				retorno = retorno.substring((badpos + 1), size);
			}
		}
		return retorno;
	}

	public String getDisgestoLogin() {
		if (this.perfil != null) {
			return (this.getCriptoPerfil(this.perfil));
		}
		return ("NULO");
	}

	private String getCriptoPerfil(String retorno) {
		if (retorno != null) {

			char letrasErroneas[] = { 'á', 'â', 'ã', 'à', 'Á', 'Â', 'Ã', 'À',
					'é', 'ê', 'è', 'É', 'Ê', 'È', 'ó', 'ò', 'ô', 'õ', 'Ó', 'Ò',
					'Ô', 'Õ', 'ú', 'ù', 'û', 'Ú', 'Ù', 'Û', 'Ç', 'ç', 'í', 'ì',
					'î', 'Ì', 'Í', 'Î', 'ª' };
			char letrasMask[] = { 'a', 'a', 'a', 'a', 'A', 'A', 'A', 'A', 'E',
					'E', 'E', 'E', 'E', 'E', 'o', 'o', 'o', 'o', 'O', 'O', 'O',
					'O', 'O', 'O', 'u', 'u', 'u', 'u', 'C', 'c', 'i', 'i', 'i',
					'I', 'I', 'I', ' ' };

			for (int e = 0; e < letrasErroneas.length; e++) {
				retorno = retorno.replace(letrasErroneas[e], letrasMask[e]);
			}

			return (RotinasCRC32.CryptCRC32(retorno));
		}
		return "NULO";
	}

}
