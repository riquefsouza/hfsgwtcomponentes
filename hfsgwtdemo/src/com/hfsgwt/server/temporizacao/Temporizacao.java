package com.hfsgwt.server.temporizacao;

public class Temporizacao extends Thread {

	public static final int DURACAO = 60000; // em milisegundos

	public static final String ERRO_DURACAO = "O limite de 60 segundos expirou!";

	private int erroCodigo;

	private String erroDescricao;

	private int duracao;

	public Temporizacao() {
		this.erroCodigo = 0;
		this.erroDescricao = ERRO_DURACAO;
		this.duracao = DURACAO;
	}

	public int getErroCodigo() {
		return erroCodigo;
	}

	public void setErroCodigo(int erroCodigo) {
		this.erroCodigo = erroCodigo;
	}

	public String getErroDescricao() {
		return erroDescricao;
	}

	public void setErroDescricao(String erroDescricao) {
		this.erroDescricao = erroDescricao;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

}