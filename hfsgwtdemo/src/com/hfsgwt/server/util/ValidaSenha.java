package com.hfsgwt.server.util;

/* 
 * Name: 
 * 		PasswordCheck.java
 * Author: 
 * 		Jim Sloey - jsloey@justwild.us
 * Requirements:
 * 		Java 1.4 or greater
 * Usage:
 *		Bundled usage: java -jar PasswordCheck.jar <password>
 *		Unbundled usage: java PasswordCheck <password>
 * History:
 * 		Created May 19, 2006 by Jim Sloey
 * Derived from: 
 * 		Steve Moitozo's passwdmeter
 * 		See http://www.geekwisdom.com/dyn/passwdmeter
 * License:
 * 		Open Software License 2.1 or Academic Free License 2.1 
 * 		See http://www.opensource.org
 * Description:
 * 		Need a simple way to check the strength of a password?
 * 		To check in the HTML on the front end try Steve Moitozo's 
 * 		Javascript example at http://www.geekwisdom.com/dyn/passwdmeter
 * Source URL:
 * 		http://justwild.us/examples/password/
 * Adaptado por:
 * 		Henrique F. de Souza
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidaSenha {

	public enum ForcaSenha {
		NENHUMA, MUITO_FRACA, FRACA, MEDIOCRE, FORTE, MUITO_FORTE
	}

	// variaveis de regra
	private static int PASSWORD_MIXED_CASE;
	private static int PASSWORD_MIN_LENGTH;
	private static int PASSWORD_MAX_LENGTH;
	private static int PASSWORD_NUMERIC;
	private static int PASSWORD_SPECIAL;
	private static int PASSWORD_STRENGTH;

	private int forca;
	private ForcaSenha forcaQualificada;
	private List<String> logStatus;

	public ValidaSenha() {
		super();
		this.forca = 0;
		this.forcaQualificada = ForcaSenha.NENHUMA;
		this.logStatus = new ArrayList<String>();

		PASSWORD_MIXED_CASE = 1;
		PASSWORD_MIN_LENGTH = 8;
		PASSWORD_MAX_LENGTH = 30;
		PASSWORD_NUMERIC = 1;
		PASSWORD_SPECIAL = 1;
		PASSWORD_STRENGTH = 30;
	}

	public ValidaSenha(boolean usarMaiusculaMinuscula, int tamanhoMinimo,
			int tamanhoMaximo, boolean usarCaracterNumerico,
			boolean usarCaracterEspecial, ForcaSenha forcaSenha) {
		this();

		PASSWORD_MIXED_CASE = (usarMaiusculaMinuscula ? 1 : 0);
		PASSWORD_MIN_LENGTH = tamanhoMinimo;
		PASSWORD_MAX_LENGTH = tamanhoMaximo;
		PASSWORD_NUMERIC = (usarCaracterNumerico ? 1 : 0);
		PASSWORD_SPECIAL = (usarCaracterEspecial ? 1 : 0);
		switch (forcaSenha) {
		case NENHUMA:
			PASSWORD_STRENGTH = 0;
			break;
		case MUITO_FRACA:
			PASSWORD_STRENGTH = 15;
			break;
		case FRACA:
			PASSWORD_STRENGTH = 20;
			break;
		case MEDIOCRE:
			PASSWORD_STRENGTH = 30;
			break;
		case FORTE:
			PASSWORD_STRENGTH = 40;
			break;
		case MUITO_FORTE:
			PASSWORD_STRENGTH = 50;
			break;
		}
	}

	public boolean validaForcaSenha(String senha) {
		int upper = 0, lower = 0, numbers = 0, special = 0, length = 0;
		int intScore = 0;
		// String strVerdict = "nenhum", strLog = "";
		Pattern p;
		Matcher m;
		if (senha == null)
			return false;
		// TAMANHO DA SENHA
		length = senha.length();
		if (length < 5) // tamanho 4 ou menor
		{
			intScore = (intScore + 3);
			logStatus.add("3 pontos para o tamanho (" + length + ")");
		} else if (length > 4 && senha.length() < 8) // tamanho entre 5 e 7
		{
			intScore = (intScore + 6);
			logStatus.add("6 pontos para o tamanho (" + length + ")");
		} else if (length > 7 && senha.length() < 16) // tamanho entre 8 e 15
		{
			intScore = (intScore + 12);
			logStatus.add("12 pontos para o tamanho (" + length + ")");
		} else if (length > 15) // tamanho 16 ou mais
		{
			intScore = (intScore + 18);
			logStatus.add("18 pontos para o tamanho (" + length + ")");
		}
		// LETRAS
		p = Pattern.compile(".??[a-z]");
		m = p.matcher(senha);
		while (m.find()) // [verifica] ao menos uma letra minuscula
		{
			lower += 1;
		}
		if (lower > 0) {
			intScore = (intScore + 1);
			logStatus.add("1 ponto para caracter minúsculo");
		}
		p = Pattern.compile(".??[A-Z]");
		m = p.matcher(senha);
		while (m.find()) // [verifica] ao menos uma letra maiuscula
		{
			upper += 1;
		}
		if (upper > 0) {
			intScore = (intScore + 5);
			logStatus.add("5 pontos para caracter maiúsculo");
		}
		// NUMBERS
		p = Pattern.compile(".??[0-9]");
		m = p.matcher(senha);
		while (m.find()) // [verifica] ao menos uma letra numerica
		{
			numbers += 1;
		}
		if (numbers > 0) {
			intScore = (intScore + 5);
			logStatus.add("5 pontos para um número");
			if (numbers > 1) {
				intScore = (intScore + 2);
				logStatus.add("2 pontos para ao menos dois números");
				if (numbers > 2) {
					intScore = (intScore + 3);
					logStatus.add("3 pontos para ao menos três números");
				}
			}
		}
		// SPECIAL CHAR
		p = Pattern.compile(".??[:,!,@,#,$,%,^,&,*,?,_,~]");
		m = p.matcher(senha);
		while (m.find()) // [verifica] ao menos uma caracter especial
		{
			special += 1;
		}
		if (special > 0) {
			intScore = (intScore + 5);
			logStatus.add("5 pontos para um caracter especial");
			if (special > 1) {
				intScore += (intScore + 5);
				logStatus
						.add("5 pontos para ao menos dois caracteres especiais");
			}
		}
		// COMBOS
		if (upper > 0 && lower > 0) // [verifica] entre maiuscula e minuscula
		{
			intScore = (intScore + 2);
			logStatus.add("2 combo pontos para letras maiúsculas e minúsculas");
		}
		if ((upper > 0 || lower > 0) && numbers > 0) // [verifica] entre letras
		// e numeros
		{
			intScore = (intScore + 2);
			logStatus.add("2 combo pontos para letras e números");
		}
		// [verifica] letras, numeros e caracteres especiais
		if ((upper > 0 || lower > 0) && numbers > 0 && special > 0) {
			intScore = (intScore + 2);
			logStatus
					.add("2 combo pontos para letras, números e caracteres especiais");
		}
		// [verifica] letras minusculas e maiusculas, numeros e caracteres
		// especiais
		if (upper > 0 && lower > 0 && numbers > 0 && special > 0) {
			intScore = (intScore + 2);
			logStatus
					.add("2 combo pontos letras minúsculas e maiúsculas, números e caracteres especiais");
		}
		if (intScore <= 0) {
			forcaQualificada = ForcaSenha.NENHUMA;
		} else if (intScore > 0 && intScore < 16) {
			forcaQualificada = ForcaSenha.MUITO_FRACA;
		} else if (intScore > 15 && intScore < 25) {
			forcaQualificada = ForcaSenha.FRACA;
		} else if (intScore > 24 && intScore < 35) {
			forcaQualificada = ForcaSenha.MEDIOCRE;
		} else if (intScore > 34 && intScore < 45) {
			forcaQualificada = ForcaSenha.FORTE;
		} else {
			forcaQualificada = ForcaSenha.MUITO_FORTE;
		}
		this.forca = intScore;
		// System.out.println(strVerdict + " - " + intScore + "\n" + strLog);
		// Verifica se está de acordo com a politica de senha
		try {
			if (length < PASSWORD_MIN_LENGTH)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		try {
			if (length > PASSWORD_MAX_LENGTH)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		try {
			if (numbers < PASSWORD_NUMERIC)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		try {
			int mix = PASSWORD_MIXED_CASE;
			if (upper < mix || lower < mix)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		try {
			if (intScore < PASSWORD_STRENGTH)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		try {
			if (special < PASSWORD_SPECIAL)
				return false;
		} catch (Exception e) {
			;
		} // nao definido
		return true;
	}

	public int getForca() {
		return forca;
	}

	public ForcaSenha getForcaQualificada() {
		return forcaQualificada;
	}

	public List<String> getLogStatus() {
		return logStatus;
	}
	
}
