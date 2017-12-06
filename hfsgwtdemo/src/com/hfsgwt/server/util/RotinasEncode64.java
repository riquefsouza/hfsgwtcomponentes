package com.hfsgwt.server.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public final class RotinasEncode64 {

	public RotinasEncode64() {
		super();
	}

	/**
	 * Converte array de byte para string codificada em base64.
	 * 
	 * @param buffer
	 *            o array de bytes a ser convertido
	 * @param codificacao
	 *            o tipo da codificação, ex: 'UTF-8', 'ISO-8859-1'
	 * @return a string transformada em Encode64
	 */
	public static String encode64(byte[] buffer, String codificacao) {
		String resultado = null;
		if (buffer != null) {
			try {
				resultado = URLEncoder.encode((new BASE64Encoder())
						.encode(buffer), codificacao);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
		return resultado;
	}

	/**
	 * Converte string codificada em base64 para array de byte.
	 * 
	 * @param buffer
	 *            a string a ser convertida
	 * @return a string transformada em array de byte
	 */
	public static byte[] decode64(String buffer) {
		byte[] resultado = null;
		try {
			resultado = (new BASE64Decoder()).decodeBuffer(buffer);
		} catch (IOException e) {
			return null;
		}
		return resultado;
	}

	public static byte[] decode64(InputStream buffer) {
		byte[] resultado = null;
		try {
			resultado = (new BASE64Decoder()).decodeBuffer(buffer);
		} catch (IOException e) {
			return null;
		}
		return resultado;
	}
	
	public static byte[] arquivoParaByteArray(String arquivo) {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// InputStream is = this.getClass().getResourceAsStream(arquivo);
			InputStream is = new FileInputStream(arquivo);

			while (is.read(buffer) != -1) {
				out.write(buffer);
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

}
