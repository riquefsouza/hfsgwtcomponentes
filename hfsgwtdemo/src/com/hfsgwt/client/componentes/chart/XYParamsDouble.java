package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class XYParamsDouble extends XYParamsBase {
	private List<XYSeriesParamsDouble> series;

	public XYParamsDouble() {
		super();
	}

	public XYParamsDouble(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParamsDouble> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical);
		this.series = series;
	}

	public List<XYSeriesParamsDouble> getSeries() {
		return series;
	}

	public void setSeries(List<XYSeriesParamsDouble> series) {
		this.series = series;
	}
}
