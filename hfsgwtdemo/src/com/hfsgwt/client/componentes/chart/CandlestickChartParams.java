package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class CandlestickChartParams extends OHLCParams {

	private List<OHLCSeriesParams> series;

	public CandlestickChartParams() {
		super();
	}

	public CandlestickChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloTempoAxis, String rotuloValorAxis, String chave,
			List<OHLCSeriesParams> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloTempoAxis, rotuloValorAxis, chave);
		this.series = series;
	}

	public List<OHLCSeriesParams> getSeries() {
		return series;
	}

	public void setSeries(List<OHLCSeriesParams> series) {
		this.series = series;
	}

}
