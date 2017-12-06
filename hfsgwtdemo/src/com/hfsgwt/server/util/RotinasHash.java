package com.hfsgwt.server.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

public final class RotinasHash {
	private static Logger log = Logger.getLogger(RotinasHash.class);
	private static RotinasHash instancia;

	public enum TipoDigest {
		MD2, MD5, SHA1, SHA256, SHA384, SHA512
	}

	private RotinasHash() {
		super();
	}

	public static RotinasHash getInstancia() {
		if (instancia == null) {
			instancia = new RotinasHash();
		}
		return instancia;
	}

	private String getTipoDigest(TipoDigest tipo) {
		String retorno = "";
		switch (tipo) {
		case MD2:
			retorno = "MD2";
			break;
		case MD5:
			retorno = "MD5";
			break;
		case SHA1:
			retorno = "SHA-1";
			break;
		case SHA256:
			retorno = "SHA-256";
			break;
		case SHA384:
			retorno = "SHA-384";
			break;
		case SHA512:
			retorno = "SHA-512";
			break;
		}
		return retorno;
	}

	// MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
	public String getHash(String tipo, String senha) throws ServicoException {
		try {
			String sen = "";
			MessageDigest md = null;
			md = MessageDigest.getInstance(tipo);
			BigInteger hash = new BigInteger(1, md.digest(senha
					.getBytes("UTF-8")));
			sen = hash.toString(16);
			return sen;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			throw new ServicoException(log, e.getMessage());
		}
		return "";
	}

	public String[] listarAlgoritmosHash() {
		return RotinasCriptografia.getInstancia().getServicoCriptografia(
				"MessageDigest");
	}

	public String getHash(TipoDigest tipo, String senha)
			throws ServicoException {
		return getHash(getTipoDigest(tipo), senha);
	}

}
