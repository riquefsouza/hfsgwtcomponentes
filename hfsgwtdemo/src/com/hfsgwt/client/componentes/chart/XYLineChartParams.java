package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class XYLineChartParams extends XYParams {

	public XYLineChartParams() {
		super();
	}

	public XYLineChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParams> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, dados);
	}

}
