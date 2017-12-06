package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class AreaChartParams extends CategoryParams {
	
	public AreaChartParams() {
		super();
	}

	public AreaChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloCategoriaAxis, String rotuloValorAxis,
			boolean orientacaoPlotVertical, List<CategoryDados> dados) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloCategoriaAxis, rotuloValorAxis, orientacaoPlotVertical,
				dados);
	}
}
