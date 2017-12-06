package com.hfsgwt.server.temporizacao;

public interface ITemporizacao {

	public boolean getCondicao();

	public int getDuracao();

	public void setDuracao(int duracao);

	public void temporizar();
}