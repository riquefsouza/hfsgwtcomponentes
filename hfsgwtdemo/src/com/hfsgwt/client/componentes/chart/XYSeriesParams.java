package com.hfsgwt.client.componentes.chart;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class XYSeriesParams implements IsSerializable {
	private String nomeSerie;
	private List<XYDados> dados;

	public XYSeriesParams() {
		super();
	}

	public XYSeriesParams(String nomeSerie, List<XYDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<XYDados> getDados() {
		return dados;
	}

	public void setDados(List<XYDados> dados) {
		this.dados = dados;
	}
	
}
