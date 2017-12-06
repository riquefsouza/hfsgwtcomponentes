package com.hfsgwtdemo.server.persistencia;

import java.util.ArrayList;

public final class PersistenciaRotinas {

	/**
	 * Repeti uma string com um separador uma determinada quantidade de vezes.
	 * 
	 * @param str
	 *            a string a ser repetida
	 * @param separador
	 *            o separador entre as strings
	 * @param qtd
	 *            a quantidade de strings repetidas
	 * @return a união das strings repetidas
	 */
	public static String repetirString(String str, String separador, int qtd) {
		String ret = "";
		for (int i = 0; i < qtd; i++) {
			if (i == 0) {
				ret = str;
			} else {
				ret += (separador + str);
			}
		}
		return ret;
	}

	/**
	 * Transforma um array de strings em um string com separador.
	 * 
	 * @param str
	 *            o array de strings
	 * @param separador
	 *            o separador entre as strings
	 * @return a união do array de strings
	 */
	public static String arrayToString(String[] str, String separador) {
		String ret = "";
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				ret = str[i];
			} else {
				ret += (separador + str[i]);
			}
		}
		return ret;
	}

	public static String arrayToString(String prefixo, String[] str,
			String separador) {
		String ret = "";
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				ret = prefixo + str[i];
			} else {
				ret += (separador + prefixo + str[i]);
			}
		}
		return ret;
	}

	/**
	 * Concatena dois arrays de strings num array de string Ãºnico.
	 * 
	 * @param elem1
	 *            o array de strings 1
	 * @param elem2
	 *            o array de strings 2
	 * @return a uniÃ£o dos dois arrays de strings
	 */
	public static String[] concatArrays(String[] elem1, String[] elem2) {
		ArrayList<String> lista = new ArrayList<String>();

		for (int i = 0; i < elem1.length; i++) {
			lista.add(elem1[i]);
		}
		for (int i = 0; i < elem2.length; i++) {
			lista.add(elem2[i]);
		}
		return (String[]) lista.toArray(new String[lista.size()]);
	}
	
	public static Throwable getCausaRaiz(Exception e) {
		Throwable root = e;
		while (root.getCause() != null) {
			root = root.getCause();
		}

		return root;
	}

	public static void imprimeRastreamentoPilha(Exception e) {
		Throwable t = getCausaRaiz(e);
		System.err.println("<----------------------------------------->");
		System.err.println("Excecao: "+ e);
		System.err.println("Throwable: "+ t);

		StackTraceElement[] trace = t.getStackTrace();
		StackTraceElement ste = trace[0];

		System.err.println("Metodo: "+ ste.getMethodName());
		System.err.println("Arquivo: " + ste.getFileName());
		System.err.println("Linha: "+ ste.getLineNumber());		
		//System.err.println("Rastreamento da Pilha:");
		//e.printStackTrace();
		System.err.println("<----------------------------------------->");
	}
	
}
