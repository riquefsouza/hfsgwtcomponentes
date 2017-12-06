package com.hfsgwt.server.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.hfsgwt.client.application.PropriedadeSistema;
import com.hfsgwt.server.ServicoException;

public final class Rotinas {

	public static String dirMenuXML;
	public static String dirRelatorios;
	public static String dirTemp;
	public static String dirUpload;
	public static String dirFTP;
	public static String dirScanner;

	private static Logger log = Logger.getLogger(Rotinas.class);

	public static boolean arquivoExiste(String arquivo) {
		File arq = new File(arquivo);
		return arq.exists();
	}

	/**
	 * Realiza a leitura de um aquivo de propriedades.
	 * 
	 * @param arquivo
	 *            o nome do arquivo de propriedades
	 * @return um objeto properties com as propriedades carregadas do arquivo
	 */
	public static Properties lerArqPropriedades(String arquivo) {
		Properties propriedades = new Properties();
		try {
			propriedades.load(new FileInputStream(arquivo));
		} catch (IOException e) {
			System.err.println("Erro de leitura do arquivo de propriedades!, "
					+ e.getMessage());
		}
		return propriedades;
	}

	public static ArrayList<String> lerInputStream(InputStream entrada)
			throws IOException {
		ArrayList<String> lista = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
		String str = "";
		while ((str = in.readLine()) != null) {
			lista.add(str);
		}
		in.close();
		return lista;
	}

	public static StringList lerInputStream2(InputStream entrada)
			throws IOException {
		StringList lista = new StringList();
		BufferedReader in = new BufferedReader(new InputStreamReader(entrada));
		String str = "";
		while ((str = in.readLine()) != null) {
			lista.add(str);
		}
		in.close();
		return lista;
	}

	/**
	 * Realiza a leitura de um aquivo qualquer.
	 * 
	 * @param arquivo
	 *            o nome do arquivo de propriedades
	 * @return um objeto properties com as propriedades carregadas do arquivo
	 */
	public static ArrayList<String> lerArquivo(String arquivo)
			throws IOException {
		ArrayList<String> lista = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader(arquivo));
		String str;
		while ((str = in.readLine()) != null) {
			lista.add(str);
		}
		in.close();
		return lista;
	}

	public static StringList lerArquivo2(String arquivo) throws IOException {
		StringList lista = new StringList();
		BufferedReader in = new BufferedReader(new FileReader(arquivo));
		String str;
		while ((str = in.readLine()) != null) {
			lista.add(str);
		}
		in.close();
		return lista;
	}

	public static void salvaArquivo(String arquivo, ArrayList<String> lista,
			boolean bAppend) throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(arquivo, bAppend));

		for (int i = 0; i < lista.size(); i++) {
			out.write(lista.get(i));
			out.flush();
		}
		out.close();
	}

	public static void salvaArquivo(String arquivo, byte[] dados,
			boolean bAppend) throws IOException {
		FileOutputStream fos = new FileOutputStream(arquivo, true);
		InputStream bs = ByteArrayToStream(dados);
		// byte[] buf = new byte[16384];
		byte[] buf = new byte[1024];
		int bytes;
		while ((bytes = bs.read(buf)) != -1) {
			fos.write(buf, 0, bytes);
		}
		fos.close();
		bs.close();
	}

	public static void salvaArquivo2(String arquivo, StringList lista,
			boolean bAppend) throws IOException {
		BufferedWriter out = new BufferedWriter(
				new FileWriter(arquivo, bAppend));

		for (int i = 0; i < lista.size(); i++) {
			out.write(lista.get(i));
			out.flush();
		}
		out.close();
	}

	public static InputStream ByteArrayToStream(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	public static byte[] StreamToByteArray(InputStream sin, int tamanho)
			throws IOException {
		byte[] dados = new byte[tamanho];
		int c, count = 0;
		while ((c = sin.read(dados, count, dados.length - count)) != -1) {
			count += c;
		}
		sin.close();

		return dados;
	}

	public static byte[] getBytesDoArquivo(File arquivo) throws IOException {
		InputStream is = new FileInputStream(arquivo);

		long length = arquivo.length();

		if (length > Integer.MAX_VALUE) {
			is.close();
			throw new IOException("O arquivo " + arquivo.getName()
					+ " é muito grande");
		}

		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Não posso ler completamente o arquivo: "
					+ arquivo.getName());
		}

		is.close();
		return bytes;
	}

	public static Byte[] converteByteArray(byte[] bytes) {
		Byte[] vetor = new Byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			vetor[i] = new Byte(bytes[i]);
		}
		return vetor;
	}

	public static InputStream lerImagem(String arquivo) {
		InputStream imagem = null;
		try {
			imagem = new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			log.error("Erro de leitura do arquivo de imagem!, ", e);
		}
		return imagem;
	}

	/**
	 * Pega uma substring de uma string, rotina de compatibilidade com a mesma
	 * rotina no Delphi.
	 * 
	 * @param texto
	 *            a string
	 * @param pos
	 *            a posição inicial a ser cortada da string
	 * @param tam
	 *            o tamanho máximo de partes a serem cortadas da string
	 * @return uma parte da string
	 */
	public static String Copy(String texto, int pos, int tam) {
		if ((pos + tam - 1) > texto.length())
			tam = texto.length();
		else
			tam = pos + tam - 1;
		return texto.substring(pos - 1, tam);
	}

	/**
	 * Converte uma string que representa um inteiro para um número com um valor
	 * padrão caso ocorra algum erro, rotina de compatibilidade com a mesma
	 * rotina no Delphi.
	 * 
	 * @param str
	 *            a string
	 * @param valorPadrao
	 *            o inteiro com um valor padrão caso ocorra algum erro
	 * @return uma parte da string
	 */
	public static int StrToIntDef(String str, int valorPadrao) {
		int ret = valorPadrao;
		try {
			if (str != null) {
				if (str.trim().length() > 0)
					ret = Integer.parseInt(str);
			}
		} catch (Exception e) {
			ret = valorPadrao;
		}
		return ret;
	}

	public static double StrToFloatDef(String str, double valorPadrao) {
		double ret;
		try {
			ret = Double.parseDouble(str);
		} catch (Exception e) {
			ret = valorPadrao;
		}
		return ret;
	}

	/**
	 * Faz a divisão de dois número inteiros, rotina de compatibilidade com a
	 * mesma rotina no Delphi.
	 * 
	 * @param num1
	 *            o dividendo
	 * @param num2
	 *            o divisor
	 * @return o resultado da divisão entre números inteiros
	 */
	public static int Div(int num1, int num2) {
		return Math.round(num1 / num2);
	}

	/**
	 * Testa se uma string é nula e retorna um valor não nulo.
	 * 
	 * @param res
	 *            a string
	 * @return o valor não nulo da string
	 */
	public static String testaNull(String res) {
		return (res == null ? "" : res.trim());
	}

	/**
	 * Testa se uma string é nula e retorna um valor não nulo em forma de um
	 * único caracter.
	 * 
	 * @param res
	 *            a string
	 * @return o valor não nulo do caracter
	 */
	public static char testaNullChar(String res) {
		return (res == null ? ' ' : res.charAt(0));
	}

	/**
	 * Testa se uma data é nula e retorna um valor não nulo da data.
	 * 
	 * @param res
	 *            a data
	 * @return o valor não nulo da data
	 */
	public static java.util.Date testaNull(java.util.Date res) {
		return (res == null ? (new java.util.Date()) : res);
	}

	/**
	 * Testa se uma string é nula e retorna um valor não nulo da string.
	 * 
	 * @param res
	 *            a string
	 * @param branco
	 *            um possível valor não nulo
	 * @return o valor não nulo da string
	 */
	public static String testaNull(String res, String branco) {
		return (res == null ? branco : res.trim());
	}

	/**
	 * Testa se um booleano e true ou false e retorna 'S' ou 'N'
	 * 
	 * @param opcao
	 *            o booleano
	 * @return o valor se 'S' ou 'N'
	 */
	public static String simNao(boolean opcao) {
		return opcao ? "S" : "N";
	}

	/**
	 * Testa se uma string é 'S' ou 'N' e retorna true ou false
	 * 
	 * @param opcao
	 *            a string
	 * @return o valor se true ou false
	 */
	public static boolean simNao(String opcao) {
		return opcao.trim().equals("S") ? true : false;
	}

	/**
	 * Formata um objeto Date usando um formato a ser definido.
	 * 
	 * @param formato
	 *            o possível formato da data
	 * @param dt
	 *            um objeto Date
	 * @return o valor do objeto Date formatado
	 */
	public static String formataDate(String formato, java.util.Date dt) {
		String ds = "";
		if (dt != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			ds = sdf.format(dt);
		}
		return ds;
	}

	/**
	 * Formata um objeto Date usando o formato 'dd/MM/yyyy HH:mm:ss'.
	 * 
	 * @param dt
	 *            um objeto Date
	 * @return o valor do objeto Date formatado
	 */
	public static String formataDate(java.util.Date dt) {
		return formataDate("dd/MM/yyyy HH:mm:ss", dt);
	}

	/**
	 * Transforma uma string num objeto Date de acordo com um formato
	 * previamente definido.
	 * 
	 * @param sData
	 *            uma string da data
	 * @param formatoEntrada
	 *            o possível formato da string de representação da data
	 * @return o valor do objeto Date
	 */
	public static java.util.Date StringToDate(String sData,
			String formatoEntrada) {
		Date data = null;
		if (sData.trim().length() > 0) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(formatoEntrada);
				data = sdf.parse(sData);
			} catch (Exception ex) {
				log.error("Erro na rotina StringToDate: ", ex);
			}
		}
		return data;
	}

	/**
	 * Concatena determinada quantidades de caracteres numa string.
	 * 
	 * @param ch
	 *            o caracter
	 * @param qtd
	 *            a quantidade de caracteres a serem concatenados
	 * @return a string com os caracteres concatenados
	 */
	public static String StringOfChar(char ch, int qtd) {
		String res = "";
		for (int i = 0; i < qtd; i++) {
			res += ch;
		}
		return res;
	}

	/**
	 * Captura uma string dentro de uma string que seja a união de várias
	 * strings separadas por um separador.
	 * 
	 * @param dado
	 *            a string
	 * @param ocorrencia
	 *            a posicao da string dentre as outras strings
	 * @param separador
	 *            o separador das posições das string
	 * @return o caracter como uma string
	 */
	public static String Captura(String dado, int ocorrencia, String separador) {
		String[] retorno = dado.split("\\" + separador);
		if (retorno.length >= ocorrencia && ocorrencia > 0) {
			return retorno[ocorrencia - 1];
		} else {
			return "";
		}
	}

	/**
	 * Realiza uma condição.
	 * 
	 * @param Condicao
	 *            a condição
	 * @param RetornoVerdade
	 *            retorna essa string se a condição for verdadeira
	 * @param RetornoFalso
	 *            retorna essa string se a condição for falsa
	 * @return o resultado da condição ou o retorno verdadeiro ou o retorno
	 *         falso
	 */
	public static String Se2(boolean Condicao, String RetornoVerdade,
			String RetornoFalso) {

		if (Condicao)
			return RetornoVerdade;
		else
			return RetornoFalso;
	}

	/**
	 * Verifica se uma string é vazia e sem espaços entre ela.
	 * 
	 * @param pObjeto
	 *            a string
	 * @return um booleano indicado se a string é vazia ou não
	 */
	public static boolean SeVazio(String pObjeto) {
		return pObjeto.trim().equals("");
	}

	public static StringList SplitWord(String s, String delimiters) {
		StringList sSplitWord;
		int sLength, sStart, sEnd;

		sSplitWord = new StringList();
		sStart = 1;

		while (sStart <= s.length()) {

			while ((Rotinas.Pos(Rotinas.Copy(s, sStart, 1), delimiters) != 0)
					&& (sStart <= s.length()))
				sStart++;

			sEnd = sStart;

			while ((Rotinas.Pos(Rotinas.Copy(s, sEnd, 1), delimiters) == 0)
					&& (sEnd <= s.length()))
				sEnd++;

			sLength = sEnd - sStart;

			if (sLength != 0)
				sSplitWord.add(Rotinas.Copy(s, sStart, sLength));

			sStart = sEnd;
		}

		return sSplitWord;
	}

	public static boolean charIn(char letra, char[] vletras) {
		boolean res = false;
		for (int i = 0; i < vletras.length; i++) {
			if (vletras[i] == letra) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**
	 * Retira algumas abreviações ou títulos de nome de pessoa.
	 * 
	 * @param nome
	 *            o nome da pessoa com as abreviações ou títulos
	 * @return o nome da pessoa sem as abreviações ou títulos
	 */
	public static String LimpaNome(String N) {
		StringList LP;
		int i;
		String S;

		if (N.trim().equals(""))
			return "";

		LP = new StringList();

		S = N.toUpperCase();

		for (i = 1; i <= S.length(); i++) {
			if (charIn(S.charAt(i - 1), new char[] { 'Á', 'Ã', 'À', 'Â' }))
				S = Rotinas.Copy(S, 1, i - 1) + "A"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (charIn(S.charAt(i - 1), new char[] { 'É', 'È', 'Ê' }))
				S = Rotinas.Copy(S, 1, i - 1) + "E"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (charIn(S.charAt(i - 1), new char[] { 'Í', 'Í', 'Ì' }))
				S = Rotinas.Copy(S, 1, i - 1) + "I"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (charIn(S.charAt(i - 1), new char[] { 'Ó', 'Õ', 'Ó', 'Ô' }))
				S = Rotinas.Copy(S, 1, i - 1) + "O"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (charIn(S.charAt(i - 1), new char[] { 'Ú', 'Ú', 'Û', 'Ü' }))
				S = Rotinas.Copy(S, 1, i - 1) + "U"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == 'Ç')
				S = Rotinas.Copy(S, 1, i - 1) + "C"
						+ Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == '.')
				S = Rotinas.Copy(S, 1, i - 1) + " "
						+ Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == '-')
				S = Rotinas.Copy(S, 1, i - 1) + " "
						+ Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == '\'')
				S = Rotinas.Copy(S, 1, i - 1) + Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == ';')
				S = Rotinas.Copy(S, 1, i - 1) + Rotinas.Copy(S, i + 1, 255);
			else if (S.charAt(i - 1) == ',')
				S = Rotinas.Copy(S, 1, i - 1) + Rotinas.Copy(S, i + 1, 255);
		}

		LP = Rotinas.SplitWord(S, " ?");

		if (LP.get(0).length() >= 1) {

			for (i = LP.size() - 1; i >= 0; i--) {
				if (LP.get(i).length() == 1)
					LP.remove(i);
			}

			if (LP.size() >= 1) {

				for (i = LP.size() - 1; i >= 0; i--) {
					if (LP.get(i).equals("DA") || LP.get(i).equals("DE")
							|| LP.get(i).equals("DI") || LP.get(i).equals("DO")
							|| LP.get(i).equals("DU") || LP.get(i).equals("DR")
							|| LP.get(i).equals("DAS")
							|| LP.get(i).equals("DOS")
							|| LP.get(i).equals("DAL")
							|| LP.get(i).equals("DEL")
							|| LP.get(i).equals("DER")
							|| LP.get(i).equals("LA") || LP.get(i).equals("LE")
							|| LP.get(i).equals("LO")
							|| LP.get(i).equals("LAS")
							|| LP.get(i).equals("LES")
							|| LP.get(i).equals("LOS")
							|| LP.get(i).equals("VAN")
							|| LP.get(i).equals("VON")
							|| LP.get(i).equals("EL"))
						LP.remove(i);
				}
			}

			if (LP.size() >= 1) {

				if (LP.get(0).equals("CAP") || LP.get(0).equals("CAPITAO")
						|| LP.get(0).equals("CEL")
						|| LP.get(0).equals("CORONEL")
						|| LP.get(0).equals("GAL") || LP.get(0).equals("GEN")
						|| LP.get(0).equals("GENERAL")
						|| LP.get(0).equals("MAJ") || LP.get(0).equals("MAJOR")
						|| LP.get(0).equals("SARG")
						|| LP.get(0).equals("SARGENTO")
						|| LP.get(0).equals("TEN")
						|| LP.get(0).equals("TENENTE")
						|| LP.get(0).equals("BEL")
						|| LP.get(0).equals("BACHAR")
						|| LP.get(0).equals("BACHAREL")
						|| LP.get(0).equals("DR") || LP.get(0).equals("DOUTOR")
						|| LP.get(0).equals("DRA")
						|| LP.get(0).equals("DOUTORA")
						|| LP.get(0).equals("ENG")
						|| LP.get(0).equals("ENGENHEIRO")
						|| LP.get(0).equals("ENGENHEIRA")
						|| LP.get(0).equals("MED")
						|| LP.get(0).equals("MEDICO")
						|| LP.get(0).equals("PROF")
						|| LP.get(0).equals("PROFESSOR")
						|| LP.get(0).equals("PE") || LP.get(0).equals("PADRE")
						|| LP.get(0).equals("VIUVA") || LP.get(0).equals("VVA")) {
					if (LP.size() >= 3)
						LP.remove(0);
				}
			}

			if (LP.size() >= 1) {

				if (LP.get(LP.size() - 1).equals("FILHO")
						|| LP.get(LP.size() - 1).equals("FILHA")
						|| LP.get(LP.size() - 1).equals("FA")
						|| LP.get(LP.size() - 1).equals("FO")
						|| LP.get(LP.size() - 1).equals("FILHOS")
						|| LP.get(LP.size() - 1).equals("FILHAS")
						|| LP.get(LP.size() - 1).equals("NETO")
						|| LP.get(LP.size() - 1).equals("NETA")
						|| LP.get(LP.size() - 1).equals("NETTO")
						|| LP.get(LP.size() - 1).equals("NETTA")
						|| LP.get(LP.size() - 1).equals("BISNETO")
						|| LP.get(LP.size() - 1).equals("BISNETA")
						|| LP.get(LP.size() - 1).equals("BISNETTO")
						|| LP.get(LP.size() - 1).equals("BISNETTA")
						|| LP.get(LP.size() - 1).equals("BISNET")
						|| LP.get(LP.size() - 1).equals("PRIMO")
						|| LP.get(LP.size() - 1).equals("PRIMA")
						|| LP.get(LP.size() - 1).equals("SOB")
						|| LP.get(LP.size() - 1).equals("SOBRIN")
						|| LP.get(LP.size() - 1).equals("SOBRINHO")
						|| LP.get(LP.size() - 1).equals("SOBRINHA")
						|| LP.get(LP.size() - 1).equals("IRMAO")
						|| LP.get(LP.size() - 1).equals("IRMAOS")
						|| LP.get(LP.size() - 1).equals("SEGUND")
						|| LP.get(LP.size() - 1).equals("SEGUNDO")
						|| LP.get(LP.size() - 1).equals("TERCEIRO")
						|| LP.get(LP.size() - 1).equals("TERCEIRA")
						|| LP.get(LP.size() - 1).equals("JUNIOR")
						|| LP.get(LP.size() - 1).equals("JR")) {
					if (LP.size() >= 3)
						LP.remove(LP.size() - 1);
				}
			}

			S = "";
			for (i = 0; i <= LP.size() - 1; i++) {
				if (S.equals(""))
					S = LP.get(i);
				else
					S = S + " " + LP.get(i);
			}
		}

		return S;

	}

	/**
	 * Trunca o valor de um número em ponto flutuante para um valor inteiro,
	 * rotina de compatibilidade com a mesma rotina no Delphi.
	 * 
	 * @param valor
	 *            o valor do número em ponto flutuante
	 * @return o número truncado para um valor inteiro
	 */
	public static int Trunc(double valor) {
		return (int) Math.floor(valor);
	}

	/**
	 * Formata um número de ponto flutuante para uma string, rotina de
	 * compatibilidade com a mesma rotina no Delphi.
	 * 
	 * @param formato
	 *            o formato desejado
	 * @param valor
	 *            o valor do número em ponto flutuante
	 * @return uma string do número formatado
	 */
	public static String FormatFloat(String formato, double valor) {
		DecimalFormat fmt = new DecimalFormat(formato);
		return fmt.format(valor);
	}

	/**
	 * Formata um número inteiro para uma string, rotina de compatibilidade com
	 * a mesma rotina no Delphi.
	 * 
	 * @param formato
	 *            o formato desejado
	 * @param valor
	 *            o valor do número inteiro
	 * @return uma string do número formatado
	 */
	public static String FormatLong(String formato, long valor) {
		DecimalFormat fmt = new DecimalFormat(formato);
		return fmt.format(valor);
	}

	/**
	 * Transforma um número de dias em ano, mês e dia.
	 * 
	 * @param pDias
	 *            o número em dias
	 * @return uma string de data formatada
	 */
	public static String CalculaAnoMesDia(double pDias) {
		int nRst, nAno, nMes, nDia;

		nAno = Div(Trunc(pDias), 360);
		nRst = Trunc(pDias) % 360;
		nMes = Div(nRst, 30);
		nDia = nRst % 30;
		return FormatFloat("00", nAno) + "ano(s);" + FormatFloat("00", nMes)
				+ "mês(es);" + FormatFloat("00", nDia) + "dia(s).";
	}

	/**
	 * Retorna a posição de uma substring dentro de uma string, rotina de
	 * compatibilidade com a mesma rotina no Delphi.
	 * 
	 * @param substr
	 *            a substring
	 * @param str
	 *            a string
	 * @return a posição da substring dentro da string
	 */
	public static int Pos(String substr, String str) {
		return str.indexOf(substr) + 1;
	}

	/**
	 * Substitui partes de uma string por outras partes, rotina de
	 * compatibilidade com a mesma rotina no Delphi.
	 * 
	 * @param str
	 *            a string
	 * @param OldPattern
	 *            a parte antiga
	 * @param NewPattern
	 *            a nova parte
	 * @return a string já com as partes substituídas
	 */
	public static String StringReplace(String str, String OldPattern,
			String NewPattern) {
		return str.replaceAll(OldPattern, NewPattern);
	}

	/**
	 * Retorna a diferença de duas datas em dias.
	 * 
	 * @param dtTermino
	 *            o objeto Date final
	 * @param dtInicio
	 *            o objeto Date inicial
	 * @return a diferença em dias
	 */
	public static long diferencaEmDias(java.util.Date dtTermino,
			java.util.Date dtInicio) {

		Calendar calDtTermino = Calendar.getInstance();
		calDtTermino.setTime(dtTermino);

		Calendar calDtInicio = Calendar.getInstance();
		calDtInicio.setTime(dtInicio);

		long m1 = calDtTermino.getTimeInMillis();
		long m2 = calDtInicio.getTimeInMillis();
		return (long) ((m1 - m2) / (24 * 60 * 60 * 1000));
	}

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
	 * Concatena dois arrays de strings num array de string único.
	 * 
	 * @param elem1
	 *            o array de strings 1
	 * @param elem2
	 *            o array de strings 2
	 * @return a união dos dois arrays de strings
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

	/**
	 * Converte um objeto Date para um objeto do tipo java.sql.Timestamp.
	 * 
	 * @param data
	 *            o objeto Date
	 * @return o objeto Date convertido num java.sql.Timestamp
	 */
	public static Timestamp DateToTimestamp(Date data) {
		Timestamp ts = null;
		if (data != null) {
			ts = new Timestamp(data.getTime());
		}
		return ts;
	}

	/**
	 * Remove todos os acentos contidos num texto
	 * 
	 * @param str
	 *            o texto
	 * @return o texto sem os acentos
	 */
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

	/**
	 * Remove as preposições contidas num texto
	 * 
	 * @param str
	 *            o texto
	 * @return o texto sem as preposições
	 */
	public static String removePreposicoes(String str) {
		String arr[] = { "DE", "DES", "DOS", "DO", "DA", "DAS", "DI", "O", "E",
				"A", "OS", "AS" };
		StringTokenizer st = new StringTokenizer(str);
		String token;
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			for (int i = 0; i < arr.length; i++) {
				if (token.equalsIgnoreCase(arr[i])) {
					token = "";
					break;
				}
			}
			if (!token.equals("")) {
				sb.append(token).append(' ');
			}
		}
		return sb.toString().trim();
	}

	/**
	 * Pega um recurso relativo a classe do objeto
	 * 
	 * @param obj
	 *            o objeto onde o recurso se encontra no mesmo diretório
	 * @param str
	 *            o nome do recurso
	 * @return o recurso como um stream
	 */
	public static InputStream getTextoDentroJar(Object obj, String str) {
		return obj.getClass().getResourceAsStream(str);
	}

	/**
	 * Realizar a leitura de um recurso relativo a classe do objeto
	 * 
	 * @param obj
	 *            o objeto onde o recurso se encontra no mesmo diretório
	 * @param str
	 *            o nome do recurso
	 */
	public static ArrayList<String> lerTextoDentroJar(Object obj, String str) {
		String thisLine;
		ArrayList<String> sl = new ArrayList<String>();
		try {
			InputStream is = getTextoDentroJar(obj, str);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((thisLine = br.readLine()) != null) {
				sl.add(thisLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sl;
	}

	public static StringList lerTextoDentroJar2(Object obj, String str) {
		String thisLine;
		StringList sl = new StringList();
		try {
			InputStream is = getTextoDentroJar(obj, str);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((thisLine = br.readLine()) != null) {
				sl.add(thisLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sl;
	}

	/**
	 * Parses uma stream XML ou arquivo XML e retorna um documento DOM. Se
	 * validating é true, o conteúdo é validado contra o DTD especifica no
	 * arquivo.
	 * 
	 * @param lerArquivo
	 *            se vai carregar pelo arquivo XML ou pela stream XML
	 * @param arquivoXML
	 *            nome do arquivo XML
	 * @param streamXML
	 *            nome da stream XML
	 * @param validarDTD
	 *            se vai validar contra o DTD especificado no arquivo
	 */
	private static Document carregarXml(boolean lerArquivo, String arquivoXML,
			InputStream streamXML, boolean validarDTD) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(validarDTD);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			Document doc = null;
			if (lerArquivo) {
				doc = factory.newDocumentBuilder().parse(new File(arquivoXML));
			} else {
				doc = factory.newDocumentBuilder().parse(streamXML);
			}
			return doc;
		} catch (SAXException e) {
			log.error("[Rotinas.carregarXml] Erro de SAX: ", e);
		} catch (ParserConfigurationException e) {
			log.error("[Rotinas.carregarXml] Erro de ParserConfiguration: ", e);
		} catch (IOException e) {
			log.error("[Rotinas.carregarXml] Erro de entrada/saída: ", e);
		}
		return null;
	}

	/**
	 * Parses um arquivo XML e retorna um documento DOM. Se validating é true, o
	 * conteúdo é validado contra o DTD especifica no arquivo.
	 * 
	 * @param arquivoXML
	 *            nome do arquivo XML
	 * @param validarDTD
	 *            se vai validar contra o DTD especificado no arquivo
	 */
	public static Document carregarXml(String arquivoXML, boolean validarDTD) {
		return carregarXml(true, arquivoXML, null, validarDTD);
	}

	/**
	 * Parses uma stream XML e retorna um documento DOM. Se validating é true, o
	 * conteúdo é validado contra o DTD especifica no arquivo.
	 * 
	 * @param streamXML
	 *            nome da stream XML
	 * @param validarDTD
	 *            se vai validar contra o DTD especificado no arquivo
	 */
	public static Document carregarXml(InputStream streamXML, boolean validarDTD) {
		return carregarXml(false, "", streamXML, validarDTD);
	}

	/**
	 * Grava mais de um BLOB contido num ResultSet para arquivos
	 * 
	 * @param rs
	 *            o ResultSet
	 * @param sArquivos
	 *            os nomes dos arquivos de saída
	 */
	public static void gravarArquivoBLOB(ResultSet rs, String[] sArquivos)
			throws SQLException {
		int qtd = sArquivos.length;
		File[] arq = new File[qtd];

		for (int i = 0; i < qtd; i++) {
			arq[i] = new File(sArquivos[i]);
			FileOutputStream fs = null;
			InputStream bs = null;
			try {
				if (arq[i].exists()) {
					log.info("[Rotinas.gravarBLOB] arquivo existe: "
							+ arq[i].getCanonicalPath());
				} else {
					System.out
							.println("[Rotinas.gravarBLOB] gravando arquivo: "
									+ arq[i].getCanonicalPath());
					fs = new FileOutputStream(arq[i]);
					bs = rs.getBinaryStream(i + 1);
					byte[] buf = new byte[16384];
					int bytes;
					while ((bytes = bs.read(buf)) != -1) {
						fs.write(buf, 0, bytes);
					}
				}
			} catch (IOException ex) {
				log.error("[Rotinas.gravarBLOB] Erro de entrada/saída: ", ex);
			} finally {
				if (fs != null)
					try {
						fs.close();
					} catch (Exception ex) {
						log
								.error(
										"[Rotinas.gravarBLOB] Erro ao fechar FileOutputStream: ",
										ex);
					}
				if (bs != null)
					try {
						bs.close();
					} catch (Exception ex) {
						log
								.error(
										"[Rotinas.gravarBLOB] Erro ao fechar InputStream: ",
										ex);
					}
			}

		}
	}

	/**
	 * Monta o SQL de um PreparedStatement com o seus parâmetros.
	 * 
	 * @param sql
	 *            a consulta sql
	 * @param params
	 *            array contendo os valores dos parâmetros
	 * @param tipos
	 *            array contendo os tipos dos parâmetros
	 * @throws Exception
	 */
	public static String montarParamsSqlPreparado(String sql, Object[] params,
			int[] tipos) throws Exception {
		String sret = sql, slocalsql = sql;
		int ntipo, ncampos = 0;
		int nqtd = params.length;

		if (nqtd != tipos.length) {
			throw new Exception(
					"A quantidade de parâmetros está diferente da quantidade de tipos!");
		}

		for (int i = 0; i < sql.length(); i++) {
			if (sql.charAt(i) == '?') {
				ncampos++;
			}
		}

		if (nqtd != ncampos) {
			throw new Exception(
					"A quantidade de parâmetros está diferente da quantidade de possíveis parâmetros (?) do SQL!");
		}

		for (int nparam = 0; nparam < nqtd; nparam++) {
			ntipo = tipos[nparam];

			switch (ntipo) {
			case Types.CHAR:
			case Types.VARCHAR:
				sret = slocalsql.replaceFirst("[?]", "'"
						+ params[nparam].toString() + "'");
				break;
			case Types.DATE:
				sret = slocalsql.replaceFirst("[?]", "TO_DATE('"
						+ Rotinas.formataDate((Date) params[nparam])
						+ "','DD/MM/YYYY')");
				break;
			case Types.TIME:
				sret = slocalsql.replaceFirst("[?]", "TO_DATE('"
						+ Rotinas.formataDate((Date) params[nparam])
						+ "','HH24:MI:SS')");
				break;
			case Types.TIMESTAMP:
				sret = slocalsql.replaceFirst("[?]", "TO_DATE('"
						+ Rotinas.formataDate((Date) params[nparam])
						+ "','DD/MM/YYYY HH24:MI:SS')");
				break;
			default:
				sret = slocalsql.replaceFirst("[?]", params[nparam].toString());
				break;
			}
			slocalsql = sret;
		}

		return sret;
	}

	/**
	 * Duplica todos os apóstrofos de uma substring dentro de uma string, rotina
	 * de compatibilidade com a mesma rotina no Delphi.
	 * 
	 * @param str
	 *            a string
	 * @return a string com todos os apóstrofos duplicados
	 */
	public static String QuotedStr(String str) {
		return "'" + str.replaceAll("[']", "''") + "'";
	}

	public static String TiraEspacoDuplo(String nome) {
		int cont, ContEspaco;
		String x, Texto = "";

		if (nome.trim().length() > 0) {
			ContEspaco = 0;
			for (cont = 1; cont <= nome.length(); cont++) {
				x = Copy(nome, cont, 1);
				if (x.equals(" "))
					ContEspaco++;
				else
					ContEspaco = 0;
				if (ContEspaco < 2)
					Texto = Texto + x;
			}
			return Texto.trim();
		} else
			return nome.trim();

	}

	public static String validaIdade(Date idade, boolean bMens1) {
		int lAnoIdade, lAnoAtual;
		String sMsg = "";

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(idade);
		lAnoIdade = cal1.get(Calendar.YEAR);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		lAnoAtual = cal2.get(Calendar.YEAR);

		if (bMens1) {
			if (((lAnoAtual - lAnoIdade) > 120)
					&& ((lAnoAtual - lAnoIdade) < 150)) {
				sMsg = "A Data de Nascimento informada indica que o requerente teria hoje "
						+ (lAnoAtual - lAnoIdade)
						+ " anos de idade.\nConfirma a \"DATA NASCIMENTO\" ?";
			}
		} else {
			if ((lAnoAtual - lAnoIdade) > 149) {
				sMsg = "A Data de Nascimento informada indica que o requerente teria hoje "
						+ (lAnoAtual - lAnoIdade)
						+ " anos de idade.\nFavor corrigir a \"DATA NASCIMENTO\"!.";
			}
		}
		return sMsg;
	}

	public static long StrToTime(String str) {
		return Rotinas.StringToDate(str, "HH:mm:ss").getTime();
	}

	public static long StrToDate(String str) {
		return Rotinas.StringToDate(str, "dd/MM/yyyy").getTime();
	}

	public static String TimeToStr(long data) {
		Timestamp ts = new Timestamp(data);
		return Rotinas.formataDate("HH:mm:ss", ts);
	}

	public static String DateToStr(long data) {
		Timestamp ts = new Timestamp(data);
		return Rotinas.formataDate("dd/MM/yyyy", ts);
	}

	public static int StrToInt(String str) {
		return Integer.parseInt(str);
	}

	public static StringList pegarConteudoURL(String sUrl, int qtdMaxLinhas) {
		URL url;
		StringList sSaida = new StringList();
		String sLinha = "";
		try {
			url = new URL(sUrl);
			URLConnection conexao = url.openConnection();
			conexao.connect();

			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					conexao.getInputStream()));
			int ncont = 1;
			while ((sLinha = entrada.readLine()) != null
					&& ncont <= qtdMaxLinhas) {
				sSaida.add(sLinha);
				ncont++;
			}
		} catch (MalformedURLException e) {
			log.error("Erro ao pegar conteúdo da URL [" + sUrl + "]: ", e);
		} catch (IOException e) {
			log.error("Erro ao pegar conteúdo da URL [" + sUrl + "]: ", e);
		}
		return sSaida;
	}

	public static String getNomeImpressoraPadrao() {
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		return service.getName();
	}

	public static String getArquivoRandom(String diretorio, String extensao) {
		String arquivo = diretorio + "/"
				+ RandomStringUtils.randomAlphanumeric(32).toUpperCase() + "."
				+ extensao;
		return arquivo;
	}

	public static String getArquivoRandom(String extensao) {
		String arquivo = RandomStringUtils.randomAlphanumeric(32).toUpperCase()
				+ "." + extensao;
		return arquivo;
	}

	public static String getEnderecoIP() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return "HostName: " + addr.getHostName() + ", IP: "
					+ addr.getHostAddress();
		} catch (UnknownHostException e) {
			return e.getMessage();
		}
	}

	public static byte[] toBytes(char[] inputChars) {
		byte[] out = new byte[inputChars.length];
		for (int i = 0; i < inputChars.length; i++) {
			out[i] = (byte) inputChars[i];
		}
		return out;
	}

	public static char[] toChars(byte[] in) {
		char[] out = new char[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = (char) in[i];
		}
		return out;
	}

	public static List<PropriedadeSistema> getPropriedadesSistema() {
		List<PropriedadeSistema> lista = new ArrayList<PropriedadeSistema>();
		Properties props = System.getProperties();
		PropriedadeSistema propSis = null;
		Enumeration<?> enum1 = props.propertyNames();
		for (; enum1.hasMoreElements();) {
			String propName = (String) enum1.nextElement();
			String propValue = props.getProperty(propName);
			propSis = new PropriedadeSistema(propName, propValue);
			lista.add(propSis);
		}
		Collections.sort(lista);
		return lista;
	}

	public static byte[] getDadosViaURL(URL host, String caminho)
			throws ServicoException {
		try {
			String hostname = host.toString();
			String url = hostname + caminho;
			URL cl = new URL(url);
			URLConnection conn = cl.openConnection();
			// conn.setDoInput(true);
			// conn.setUseCaches(false);
			// conn.connect();

			InputStream in = conn.getInputStream();
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in));
			return buffer.readLine().getBytes();
		} catch (MalformedURLException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public static void enviarDadosViaURL(URL host, String caminho,
			byte[] conteudo) throws ServicoException {
		try {
			String hostname = host.toString();
			String url = hostname + caminho;
			URL cl = new URL(url);
			URLConnection conn = cl.openConnection();
			conn.setDoOutput(true);
			// conn.setUseCaches(false);
			// conn.connect();

			OutputStreamWriter saida = new OutputStreamWriter(conn
					.getOutputStream());
			char[] dados = new char[conteudo.length];
			for (int i = 0; i < conteudo.length; i++) {
				dados[i] = (char) conteudo[i];
			}
			saida.write(dados, 0, dados.length);
			saida.flush();
			saida.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));

			// String decodedString;
			// while ((decodedString = in.readLine()) != null) {
			// System.out.println(decodedString);
			// }
			in.close();

		} catch (MalformedURLException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
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
		System.err.println("Excecao: " + e);
		System.err.println("Throwable: " + t);

		StackTraceElement[] trace = t.getStackTrace();
		StackTraceElement ste = trace[0];

		System.err.println("Metodo: " + ste.getMethodName());
		System.err.println("Arquivo: " + ste.getFileName());
		System.err.println("Linha: " + ste.getLineNumber());
		// System.err.println("Rastreamento da Pilha:");
		// e.printStackTrace();
		System.err.println("<----------------------------------------->");
	}

	public static byte[] testaNull(byte[] res) {
		return (res == null ? new byte[] { -1 } : res);
	}

	public static String getUrlToString(String sUrl) throws IOException {
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[128];
		int i = -1;
		String stemp = "";

		if (sUrl != null && sUrl.length() > 0) {
			URL url = new URL(sUrl);
			InputStream is = url.openStream();

			while ((i = is.read(b)) > 0) {
				for (int j = 0; j < i; j++) {
					stemp = stemp + (char) b[j];
				}
				sb.append(stemp);
				stemp = "";
			}
		}
		return sb.toString();
	}

	public static String geraNumeroAleatorioSeguro()
			throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		// Get 1024 random bits
		// String stemp = "";
		// byte[] bytes = new byte[1024 / 8];
		// sr.nextBytes(bytes);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 32; i++) {
			sb.append(Long.toString(Math.abs(sr.nextLong()) % 32, 32));
		}
		return sb.toString();

		// StringBuffer sb = new StringBuffer();
		// Random rand = new Random();
		// for (int i = 0; i < 32; i++) {
		// sb.append(Integer.toString(Math.abs(rand.nextInt()) % 32, 32));
		// }
		// return sb.toString();

	}

	public static String duracaoTempo(Date horaPartida) {
		String hora, min, seg;
		Date horaChegada = new Date();
		long duracaoMili = horaChegada.getTime() - horaPartida.getTime();

		long duracaoSegundos = duracaoMili / (1000);
		long duracaoMinutos = duracaoMili / (60 * 1000);
		long duracaoHoras = duracaoMili / (60 * 60 * 1000);
		// long duracaoDias = duracaoMili/(24*60*60*1000);

		if (duracaoHoras < 10)
			hora = "0" + duracaoHoras;
		else
			hora = "" + duracaoHoras;

		if (duracaoMinutos < 10)
			min = "0" + duracaoMinutos;
		else
			min = "" + duracaoMinutos;

		if (duracaoSegundos < 10)
			seg = "0" + duracaoSegundos;
		else
			seg = "" + duracaoSegundos;

		return hora + "h:" + min + "m:" + seg + "s:" + duracaoMili + "ms";
	}

	public static Object nvlBigDecimal(int valor) {
		return valor == -1 ? null : new BigDecimal(valor);
	}

	public static Object nvlBigDecimal(long valor) {
		return valor == -1 ? null : new BigDecimal(valor);
	}

	public static Object nvlBigDecimal(double valor) {
		return valor == -1 ? null : new BigDecimal(valor);
	}

	public static Object nvlString(String valor) {
		if (valor == null)
			return null;
		else
			return valor.length() == 0 ? null : new String(valor);
	}

	public static Object nvlStringLike(String valor) {
		if (valor == null)
			return null;
		else
			return valor.length() == 0 ? null : new String(valor + "%");
	}

	public static Object nvlStringLike2(String valor) {
		if (valor == null)
			return null;
		else
			return valor.length() == 0 ? null : new String("%" + valor + "%");
	}

	public static int limitaIntRandom(int min, int max) {
		int valor = RandomUtils.nextInt(max);
		while (valor < min) {
			valor = RandomUtils.nextInt(max);
		}
		return valor;
	}

	public static Integer[] DiferenteIntRandom(int maxInt, int min, int max) {
		Set<Integer> unico = new HashSet<Integer>();
		while (unico.size() < maxInt) {
			unico.add(limitaIntRandom(min, max));
		}
		return (Integer[]) unico.toArray(new Integer[unico.size()]);
	}

	public static byte[] getBytesFromFile(String arquivo) throws IOException {
		File file = new File(arquivo);
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			is.close();
			throw new IOException(
					"Não foi possível completar a leitura do arquivo: "
							+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public static CLOB getCLOB(Connection conn, String item)
			throws SQLException {
		CLOB tempClob = null;

		if (item == null)
			return null;
		if (item.length() == 0)
			return null;

		try {
			// create a new temporary CLOB
			tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);

			// Open the temporary CLOB in readwrite mode to enable writing
			tempClob.open(CLOB.MODE_READWRITE);

			// Get the output stream to write
			Writer tempClobWriter = tempClob.setCharacterStream(0L);

			// Write the data into the temporary CLOB
			tempClobWriter.write(item);

			// Flush and close the stream
			tempClobWriter.flush();
			tempClobWriter.close();

			// Close the temporary CLOB
			tempClob.close();

		} catch (SQLException exp) {
			// Free CLOB object
			tempClob.freeTemporary();
		} catch (IOException e) {
			// Free CLOB object
			tempClob.freeTemporary();
			log.error("Erro de entrada/saída: ", e);
		}
		return tempClob;
	}

	public static BLOB getBLOB(Connection conn, byte[] item)
			throws SQLException {
		BLOB tempBlob = null;

		if (item == null)
			return null;
		if (item.length == 0)
			return null;

		try {
			// create a new temporary CLOB
			tempBlob = BLOB.createTemporary(conn, true, BLOB.DURATION_SESSION);

			// Open the temporary CLOB in readwrite mode to enable writing
			tempBlob.open(BLOB.MODE_READWRITE);

			// Get the output stream to write
			OutputStream tempBlobOS = tempBlob.setBinaryStream(0L);

			// Write the data into the temporary CLOB
			tempBlobOS.write(item);

			// Flush and close the stream
			tempBlobOS.flush();
			tempBlobOS.close();

			// Close the temporary CLOB
			tempBlob.close();

		} catch (SQLException exp) {
			// Free CLOB object
			tempBlob.freeTemporary();
		} catch (IOException e) {
			// Free CLOB object
			tempBlob.freeTemporary();
			log.error("Erro de entrada/saída: ", e);
		}
		return tempBlob;

	}

	public static void byteArrayToFile(byte[] conteudo, String arquivo) {
		BufferedOutputStream bos = null;
		try {
			FileOutputStream fos = new FileOutputStream(new File(arquivo));
			bos = new BufferedOutputStream(fos);
			bos.write(conteudo);
		} catch (FileNotFoundException fnfe) {
			log.error("Arquivo não encontrado: " + arquivo, fnfe);
		} catch (IOException ioe) {
			log.error("Erro de entrada/saída: ", ioe);
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
