package com.hfsgwt.client.componentes.util;

public class HFSValida {

	public static boolean validaCPF(String cpfNum, boolean comFormatacao) {
		int tamMax;

		if (comFormatacao)
			tamMax = 14;
		else
			tamMax = 11; 					
		
		if (cpfNum.trim().length() == tamMax) {
			if (comFormatacao) {
				cpfNum = HFSUtil.desformataCPF(cpfNum);
			}
			
			int[] cpf = new int[cpfNum.length()]; // define o valor com o
			// tamanho da
			// string
			int resultP = 0;
			int resultS = 0;

			// converte a string para um array de integer
			for (int i = 0; i < cpf.length; i++) {
				cpf[i] = Integer.parseInt(cpfNum.substring(i, i + 1));
			}

			// calcula o primeiro número(DIV) do cpf
			for (int i = 0; i < 9; i++) {
				resultP += cpf[i] * (i + 1);
			}
			int divP = resultP % 11;

			// se o resultado for diferente ao 10º digito do cpf retorna falso
			if (divP != cpf[9]) {
				return false;
			} else {
				// calcula o segundo número(DIV) do cpf
				for (int i = 0; i < 10; i++) {
					resultS += cpf[i] * (i);
				}
				int divS = resultS % 11;

				// se o resultado for diferente ao 11º digito do cpf retorna
				// falso
				if (divS != cpf[10]) {
					return false;
				}
			}
			// se tudo estiver ok retorna verdadeiro
			return true;
		} else
			return false;
	}

	// exemplo 03.847.655/0001-98
	public static boolean validaCNPJ(String cnpjNum, boolean comFormatacao) {
		int tamMax;

		if (comFormatacao)
			tamMax = 18;
		else
			tamMax = 14; 					
		
		
		if (cnpjNum.trim().length() == tamMax) {
			if (comFormatacao) {
				cnpjNum = HFSUtil.desformataCNPJ(cnpjNum);
			}
			
			int[] cnpj = new int[cnpjNum.length()];
			int resultP = 0;
			int resultS = 0;
			int divP = 0;
			int divS = 0;

			// converte string para um array de integer
			for (int i = 0; i < cnpjNum.length(); i++) {
				cnpj[i] = Integer.parseInt(cnpjNum.substring(i, i + 1));
			}

			int j = 6;
			// calcula o primeiro div
			for (int i = 0; i < 12; i++) {
				resultP += cnpj[i] * j;

				j++;
				if (j > 9) {
					j = 2;
				}
			}
			divP = resultP % 11;

			if (divP != cnpj[12]) {
				return false;
			} else {
				j = 5;
				// calcula o segundo div
				for (int i = 0; i < 13; i++) {
					resultS += cnpj[i] * j;

					j++;
					if (j > 9) {
						j = 2;
					}
				}
				divS = resultS % 11;

				if (divS != cnpj[13])
					return false;
			}
			return true;
		} else
			return false;
	}

	public static boolean validaAnoBisexto(String sData) {
		int dia = Integer.parseInt(HFSUtil.Copy(sData, 1, 2));
		int mes = Integer.parseInt(HFSUtil.Copy(sData, 4, 2));
		int ano = Integer.parseInt(HFSUtil.Copy(sData, 7, 4));
		if (mes == 2) {
			if ((ano % 4) == 0) // é bisexto
				return (dia <= 29);
			else
				return (dia <= 28);
		} else
			return true;
	}

	
	/*----------------------------------------------------
	 * Início das validações usandos expressões regulares
	 -----------------------------------------------------*/

	/**
	 * Verifica se uma string está representa um formato de 99:99 válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaHoraHMSimples(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d{2}:\\d{2}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de hora até 24 :99
	 * válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaHoraH24M(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^([0-1]\\d|2[0-3]):[0-5]\\d$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de hora até 12 :99
	 * válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaHoraH12M(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^(0[1-9]|1[0-2]):[0-5]\\d$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de 99:99:99 válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaHorasMMSS(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d+:[0-5]\\d:[0-5]\\d$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de 99:99:99.999 válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaHorasMMSSmili(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d+:[0-5]\\d:[0-5]\\.\\d{3}\\d$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de 99/99/9999 válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDataSimples(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{1,4}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de data média válida.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDataMedia(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^[0-3]?\\d\\/[01]?\\d\\/(\\d{2}|\\d{4})$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de data avançada
	 * válida.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDataAvancada(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^(0?[1-9]|[12]\\d|3[01])\\/(0?[1-9]|1[0-2])\\/(19|20)?\\d{2}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de data completa
	 * válida.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDataCompleta(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^((0?[1-9]|[12]\\d)\\/(0?[1-9]|1[0-2])|30\\/(0?[13-9]|1[0-2])|31\\/(0?[13578]|1[02]))\\/(19|20)?\\d{2}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de data de dia/mês/ano
	 * válida.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDataDMA(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^((0[1-9]|[12]\\d)\\/(0[1-9]|1[0-2])|30\\/(0[13-9]|1[0-2])|31\\/(0[13578]|1[02]))\\/\\d{4}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de somente números
	 * válidos.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDigitos(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d+$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato brasileiro de números
	 * decimais válidos.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDecimalPt(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^[+-]?((\\d+|\\d{1,3}(\\.\\d{3})+)(\\,\\d*)?|\\,\\d+)$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato americano de números
	 * decimais válidos.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaDecimalEn(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^[+-]?((\\d+|\\d{1,3}(\\,\\d{3})+)(\\.\\d*)?|\\.\\d+)$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato moeda válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaMoeda(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d{1,3}(\\.\\d{3})*\\,\\d{2}$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de e-mail livre válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaEmailLivre(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^[\\w!#$%&'*+\\/=?^`{|}~-]+(\\.[\\w!#$%&'*+\\/=?^`{|}~-]+)*@(([\\w-]+\\.)+[A-Za-z]{2,6}|\\[\\d{1,3}(\\.\\d{1,3}){3}\\])$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de e-mail compacto
	 * válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaEmailCompacto(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^[\\w-]+(\\.[\\w-]+)*@(([\\w-]{2,63}\\.)+[A-Za-z]{2,6}|\\[\\d{1,3}(\\.\\d{1,3}){3}\\])$");
		}
		return ret;
	}

	/**
	 * Verifica se uma string está representa um formato de e-mail restrito
	 * válido.
	 * 
	 * @param texto
	 *            a string
	 * @return um booleano indicado se a string é válida ou não
	 */
	public static boolean validaEmailRestrito(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^[\\w-]+(\\.[\\w-]+)*@(([A-Za-z\\d][A-Za-z\\d-]{0,61}[A-Za-z\\d]\\.)+[A-Za-z]{2,6}|\\[\\d{1,3}(\\.\\d{1,3}){3}\\])$");
		}
		return ret;
	}

	public static boolean validaDataHora(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto
					.matches("^\\d{1,2}\\/\\d{1,2}\\/\\d{1,4} ([0-1]\\d|2[0-3]):[0-5]\\d$");
		}
		return ret;
	}

	public static boolean validaCEP(String texto) {
		boolean ret = false;
		if (texto != null && !texto.equals("")) {
			ret = texto.matches("^\\d{5}-\\d{3}$");
		}
		return ret;
	}
}
