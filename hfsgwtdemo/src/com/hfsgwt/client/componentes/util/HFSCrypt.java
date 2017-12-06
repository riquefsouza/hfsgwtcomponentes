package com.hfsgwt.client.componentes.util;

public class HFSCrypt {

	private static final int C1 = 52845;
	private static final int C2 = 22719;

	public static String criptografar(String texto, short chave){
		String ret = "";
		Character[] retorno = new Character[texto.length()];

		retorno[0] = texto.charAt(0);
		for (int i = 1; i < texto.length(); i++) {			
			retorno[i] = (char)((byte)texto.charAt(i) ^ (chave >> 8));
			chave = (short) (((byte)retorno[i].charValue() + chave) * C1 + C2); 
		}	
		
		for (Character item: retorno) {
			ret+=item.charValue();
		}
		return ret;
	}
	
	public static String descriptografar(String texto, short chave){
		String ret = "";
		Character[] retorno = new Character[texto.length()];
		
		retorno[0] = texto.charAt(0);
		for (int i = 1; i < texto.length(); i++) {
			retorno[i] = (char)((byte)texto.charAt(i) ^ (chave >> 8));
			chave = (short) (((byte)texto.charAt(i) + chave) * C1 + C2); 
		}

		for (Character item: retorno) {
			ret+=item;
		}
		return ret;
	}
}
