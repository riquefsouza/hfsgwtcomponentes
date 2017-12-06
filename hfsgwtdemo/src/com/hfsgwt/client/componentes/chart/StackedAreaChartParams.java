package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class StackedAreaChartParams extends CategoryParams {

	public StackedAreaChartParams() {
		super();
	}

	public StackedAreaChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloCategoriaAxis, String rotuloValorAxis,
			boolean orientacaoPlotVertical, List<CategoryDados> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloCategoriaAxis, rotuloValorAxis, orientacaoPlotVertical,
				dados);
	}

}
