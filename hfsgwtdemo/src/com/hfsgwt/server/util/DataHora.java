package com.hfsgwt.server.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe que representa uma estrutura de data e hora completas.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 01/10/2007
 */
public class DataHora {

	private int dia;

	private int mes;

	private int ano;

	private int hora12;

	private int hora24;

	private int minuto;

	private int segundo;

	private int miliSegundo;

	private String aMPM;

	private String era;

	private int DiaDaSemana;

	private String DiaDaSemanaStr;

	/**
	 * @return retorna o aMPM.
	 */
	public String getAMPM() {
		return aMPM;
	}

	/**
	 * @param ampm
	 *            The aMPM to set.
	 */
	public void setAMPM(String ampm) {
		aMPM = ampm;
	}

	/**
	 * @return retorna o ano.
	 */
	public int getAno() {
		return ano;
	}

	/**
	 * @param ano
	 *            O valor de ano para atribuir.
	 */
	public void setAno(int ano) {
		this.ano = ano;
	}

	/**
	 * @return retorna o dia.
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            O valor de dia para atribuir.
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	/**
	 * @return retorna o diaDaSemana.
	 */
	public int getDiaDaSemana() {
		return DiaDaSemana;
	}

	/**
	 * @param diaDaSemana
	 *            O valor de diaDaSemana para atribuir.
	 */
	public void setDiaDaSemana(int diaDaSemana) {
		DiaDaSemana = diaDaSemana;
	}

	/**
	 * @return retorna o diaDaSemanaStr.
	 */
	public String getDiaDaSemanaStr() {
		return DiaDaSemanaStr;
	}

	/**
	 * @param diaDaSemanaStr
	 *            O valor de diaDaSemanaStr para atribuir.
	 */
	public void setDiaDaSemanaStr(String diaDaSemanaStr) {
		DiaDaSemanaStr = diaDaSemanaStr;
	}

	/**
	 * @return retorna o era.
	 */
	public String getEra() {
		return era;
	}

	/**
	 * @param era
	 *            O valor de era para atribuir.
	 */
	public void setEra(String era) {
		this.era = era;
	}

	/**
	 * @return retorna o hora12.
	 */
	public int getHora12() {
		return hora12;
	}

	/**
	 * @param hora12
	 *            O valor de hora12 para atribuir.
	 */
	public void setHora12(int hora12) {
		this.hora12 = hora12;
	}

	/**
	 * @return retorna o hora24.
	 */
	public int getHora24() {
		return hora24;
	}

	/**
	 * @param hora24
	 *            O valor de hora24 para atribuir.
	 */
	public void setHora24(int hora24) {
		this.hora24 = hora24;
	}

	/**
	 * @return retorna o mes.
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            O valor de mes para atribuir.
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}

	/**
	 * @return retorna o milisegundo.
	 */
	public int getMiliSegundo() {
		return miliSegundo;
	}

	/**
	 * @param miliSegundo
	 *            O valor de milisegundo para atribuir.
	 */
	public void setMiliSegundo(int miliSegundo) {
		this.miliSegundo = miliSegundo;
	}

	/**
	 * @return retorna o minuto.
	 */
	public int getMinuto() {
		return minuto;
	}

	/**
	 * @param minuto
	 *            O valor de minuto para atribuir.
	 */
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	/**
	 * @return retorna o segundo.
	 */
	public int getSegundo() {
		return segundo;
	}

	/**
	 * @param segundo
	 *            O valor de segundo para atribuir.
	 */
	public void setSegundo(int segundo) {
		this.segundo = segundo;
	}

	/**
	 * @return a representação em texto da data e da hora.
	 */
	public String toString() {
		return Rotinas.FormatLong("00", dia) + "/"
				+ Rotinas.FormatLong("00", mes) + "/"
				+ Rotinas.FormatLong("0000", ano) + " "
				+ Rotinas.FormatLong("00", hora24) + ":"
				+ Rotinas.FormatLong("00", minuto) + ":"
				+ Rotinas.FormatLong("00", segundo) + ":"
				+ Rotinas.FormatLong("000", miliSegundo) + " " + aMPM
				+ ", era: " + era + ", Dia da Semana: " + DiaDaSemanaStr;

	}

	/**
	 * Limpa todos os atributos da classe.
	 */
	public void limparDados() {
		dia = 0;
		mes = 0;
		ano = 0;
		hora12 = 0;
		hora24 = 0;
		minuto = 0;
		segundo = 0;
		miliSegundo = 0;
		aMPM = "";
		era = "";
		DiaDaSemana = 0;
		DiaDaSemanaStr = "";
	}

	/**
	 * Faz a comparação dos atributos com um outro objeto do mesmo tipo.
	 * 
	 * @param obj
	 *            objeto do mesmo tipo de classe
	 * @return se os atributos dos dois objetos são iguais
	 */
	public boolean equals(DataHora obj) {
		DataHora dh = (DataHora) obj;
		if (dh.getDia() == this.getDia() && dh.getMes() == this.getMes()
				&& dh.getAno() == this.getAno()
				&& dh.getHora12() == this.getHora12()
				&& dh.getHora24() == this.getHora24()
				&& dh.getMinuto() == this.getMinuto()
				&& dh.getSegundo() == this.getSegundo()
				&& dh.getMiliSegundo() == this.getMiliSegundo()
				&& dh.getAMPM().equals(this.getAMPM())
				&& dh.getEra().equals(this.getEra())
				&& dh.getDiaDaSemana() == this.getDiaDaSemana()
				&& dh.getDiaDaSemanaStr().equals(this.getDiaDaSemanaStr()))
			return true;
		else
			return false;

	}

	/**
	 * Decodifica um objeto Date para transforma em um objeto DataHora.
	 * 
	 * @param data
	 *            o objeto Date
	 * @return um objeto DataHora
	 */
	public DataHora decodeDataHora(java.util.Date data) {
		String[] diasem = new String[] { "Domingo", "Segundo", "Terça",
				"Quarta", "Quinta", "Sexta", "Sábado" };
		DataHora dh = new DataHora();
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);

		dh.setHora12(cal.get(Calendar.HOUR)); // 0..11
		dh.setHora24(cal.get(Calendar.HOUR_OF_DAY)); // 0..23
		dh.setMinuto(cal.get(Calendar.MINUTE)); // 0..59
		dh.setSegundo(cal.get(Calendar.SECOND)); // 0..59
		dh.setMiliSegundo(cal.get(Calendar.MILLISECOND)); // 0..999
		if (cal.get(Calendar.AM_PM) == 0)
			dh.setAMPM("AM");
		else if (cal.get(Calendar.AM_PM) == 1)
			dh.setAMPM("PM");

		if (cal.get(Calendar.ERA) == 0)
			dh.setEra("BC");
		else if (cal.get(Calendar.ERA) == 1)
			dh.setEra("AD");

		dh.setAno(cal.get(Calendar.YEAR));
		dh.setMes(cal.get(Calendar.MONTH));
		dh.setDia(cal.get(Calendar.DAY_OF_MONTH));
		dh.setDiaDaSemana(cal.get(Calendar.DAY_OF_WEEK)); // 1=Sunday, 2=Monday,
		dh.setDiaDaSemanaStr(diasem[cal.get(Calendar.DAY_OF_WEEK) - 1]);

		return dh;
	}

	/**
	 * Codifica um objeto DataHora para transforma em um objeto Date.
	 * 
	 * @param dh
	 *            o objeto DataHora
	 * @return um objeto Date
	 */
	public Date encodeDataHora(DataHora dh) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, dh.getAno());
		cal.set(Calendar.MONTH, dh.getMes());
		cal.set(Calendar.DAY_OF_MONTH, dh.getDia());
		cal.set(Calendar.HOUR_OF_DAY, dh.getHora24());
		cal.set(Calendar.HOUR, dh.getHora12());
		cal.set(Calendar.MINUTE, dh.getMinuto());
		cal.set(Calendar.SECOND, dh.getSegundo());
		cal.set(Calendar.MILLISECOND, dh.getMiliSegundo());
		if (dh.getAMPM().equals("AM")) {
			cal.set(Calendar.AM_PM, 0);
		}
		if (dh.getAMPM().equals("PM")) {
			cal.set(Calendar.AM_PM, 1);
		}
		if (dh.getEra().equals("BC")) {
			cal.set(Calendar.ERA, 0);
		}
		if (dh.getEra().equals("AD")) {
			cal.set(Calendar.ERA, 1);
		}
		cal.set(Calendar.DAY_OF_WEEK, dh.getDiaDaSemana());

		return cal.getTime();
	}

}