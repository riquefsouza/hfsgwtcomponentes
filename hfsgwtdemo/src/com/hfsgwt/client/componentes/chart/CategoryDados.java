package com.hfsgwt.client.componentes.chart;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDados implements IsSerializable {
	private Number valor;
	private String chaveLinha;
	private String chaveColuna;

	public CategoryDados() {
		super();
	}

	public CategoryDados(Number valor, String chaveLinha, String chaveColuna) {
		this.valor = valor;
		this.chaveLinha = chaveLinha;
		this.chaveColuna = chaveColuna;
	}

	public Number getValor() {
		return valor;
	}

	public void setValor(Number valor) {
		this.valor = valor;
	}

	public String getChaveLinha() {
		return chaveLinha;
	}

	public void setChaveLinha(String chaveLinha) {
		this.chaveLinha = chaveLinha;
	}

	public String getChaveColuna() {
		return chaveColuna;
	}

	public void setChaveColuna(String chaveColuna) {
		this.chaveColuna = chaveColuna;
	}
}
