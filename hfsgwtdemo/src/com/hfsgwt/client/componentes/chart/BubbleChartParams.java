package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class BubbleChartParams extends XYParamsDouble {

	public BubbleChartParams() {
		super();
	}

	public BubbleChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParamsDouble> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, series);
	}

}
