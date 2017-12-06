package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class XYStepAreaChartParams extends XYParams {

	public XYStepAreaChartParams() {
		super();
	}

	public XYStepAreaChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParams> series) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, series);
	}

}
