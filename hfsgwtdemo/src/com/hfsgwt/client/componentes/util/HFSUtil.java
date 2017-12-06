package com.hfsgwt.client.componentes.util;

import com.google.gwt.user.client.Window;

public class HFSUtil {

	public static void mostrarFalha(Throwable caught, Class<?> componente,
			String metodo) {
		Window.alert(HFSConst.SERVIDOR_ERRO + componente.getName() + "["
				+ metodo + "], Erro: " + caught.getMessage());
	}

	public static String pontoParaVirgula(String texto) {
		String resultado = texto.replace(',', '.');

		char[] vtexto = resultado.toCharArray();
		for (int i = (vtexto.length - 1); i >= 0; i--) {
			if (vtexto[i] == '.') {
				vtexto[i] = ',';
				break;
			}
		}
		return new String(vtexto);
	}

	public static String removeAcento(String str) {
		String resultado = "";
		if (str.trim().length() > 0) {
			String acento = "´`¨^~";
			String com_acento = "áàäâãÁÀÄÂÃéèëêÉÈËÊíìïîÍÌÏÎóòöôõÓÒÖÔÕúùüûÚÙÜÛ";
			String sem_acento = "aaaaaAAAAAeeeeEEEEiiiiIIIIoooooOOOOOuuuuUUUU";
			char ch[] = str.toCharArray();
			int index;
			for (int i = 0; i < ch.length; i++) {
				if ((index = com_acento.indexOf(ch[i])) > -1) {
					ch[i] = sem_acento.charAt(index);
				}
			}

			String temp = String.valueOf(ch);
			for (int i = 0; i < ch.length; i++) {
				if (acento.indexOf(ch[i]) == -1) {
					resultado += temp.charAt(i);
				}
			}
		}
		return resultado;
	}

	public static String Copy(String texto, int pos, int tam) {
		if ((pos + tam - 1) > texto.length())
			tam = texto.length();
		else
			tam = pos + tam - 1;
		return texto.substring(pos - 1, tam);
	}

	public static String formataCPF(String cpf) {
		if (cpf != null) {
			if (cpf.length() == 11) {
				return Copy(cpf, 1, 3) + "." + Copy(cpf, 4, 3) + "."
						+ Copy(cpf, 7, 3) + "-" + Copy(cpf, 10, 2);
			}
		}
		return "";
	}

	public static String desformataCPF(String cpf) {
		if (cpf != null) {
			if (cpf.length() == 14) {
				return Copy(cpf, 1, 3) + Copy(cpf, 5, 3) + Copy(cpf, 9, 3)
						+ Copy(cpf, 13, 2);
			}
		}
		return "";
	}

	public static String formataCNPJ(String cnpj) {
		if (cnpj != null) {
			if (cnpj.length() == 14) {
				return Copy(cnpj, 1, 2) + "." + Copy(cnpj, 3, 3) + "."
						+ Copy(cnpj, 6, 3) + "/" + Copy(cnpj, 9, 4) + "-"
						+ Copy(cnpj, 13, 2);
			}
		}
		return "";
	}

	public static String desformataCNPJ(String cnpj) {
		if (cnpj != null) {
			if (cnpj.length() == 18) {
				return Copy(cnpj, 1, 2) + Copy(cnpj, 4, 3) + Copy(cnpj, 8, 3)
						+ Copy(cnpj, 12, 4) + Copy(cnpj, 17, 2);
			}
		}
		return "";
	}

	public static String formataCEP(String cep) {
		if (cep != null) {
			if (cep.length() == 8) {
				return Copy(cep, 1, 5) + "-" + Copy(cep, 6, 3);
			}
		}
		return "";
	}

	public static String desformataCEP(String cep) {
		if (cep != null) {
			if (cep.length() == 9) {
				return Copy(cep, 1, 5) + Copy(cep, 7, 3);
			}
		}
		return "";
	}

	public static String removeEspacoDuplo(String nome) {
		int cont, ContEspaco = 0;
		String x, texto = "";

		for (cont = 0; cont < nome.length(); cont++) {
			x = nome.substring(cont, cont+1);
			if (x.equals(" ")) {
				ContEspaco++;
			} else {
				ContEspaco = 0;
			}
			
			if (ContEspaco < 2) {
				texto += x;
			}
		}
		return texto.trim();
	}
}
