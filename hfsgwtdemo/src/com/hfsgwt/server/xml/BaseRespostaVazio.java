package com.hfsgwt.server.xml;

public abstract class BaseRespostaVazio {

	public static final String VAZIO = "[VAZIO]";

	public static String nvl(String valor) {
		if (valor != null)
			return (valor.trim().equals("") ? BaseRespostaVazio.VAZIO : valor);
		else
			return BaseRespostaVazio.VAZIO;
	}

}