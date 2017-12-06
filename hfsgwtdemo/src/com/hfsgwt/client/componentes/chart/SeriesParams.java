package com.hfsgwt.client.componentes.chart;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SeriesParams implements IsSerializable {
	private String nomeSerie;
	private List<SeriesDados> dados;

	public SeriesParams(){
		super();
	}
	
	public SeriesParams(String nomeSerie,
			List<SeriesDados> dados) {
		this.nomeSerie = nomeSerie;
		this.dados = dados;
	}

	public String getNomeSerie() {
		return nomeSerie;
	}

	public void setNomeSerie(String nomeSerie) {
		this.nomeSerie = nomeSerie;
	}

	public List<SeriesDados> getDados() {
		return dados;
	}

	public void setDados(List<SeriesDados> dados) {
		this.dados = dados;
	}
}
