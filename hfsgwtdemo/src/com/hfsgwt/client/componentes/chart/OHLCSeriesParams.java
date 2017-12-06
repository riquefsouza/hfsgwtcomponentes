package com.hfsgwt.client.componentes.chart;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class OHLCSeriesParams implements IsSerializable {
	private String nomeSerie;
	private List<OHLCSeriesDados> dados;

	public OHLCSeriesParams() {
		super();
	}

	public OHLCSeriesParams(String nomeSerie, List<OHLCSeriesDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<OHLCSeriesDados> getDados() {
		return dados;
	}

	public void setDados(List<OHLCSeriesDados> dados) {
		this.dados = dados;
	}
}
