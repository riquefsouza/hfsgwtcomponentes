package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class StackedBarChartParams extends CategoryParams {
	private boolean usarBarra3D;

	public StackedBarChartParams() {
		super();
	}

	public StackedBarChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloCategoriaAxis, String rotuloValorAxis,
			boolean orientacaoPlotVertical, List<CategoryDados> dados,
			boolean usarBarra3D) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloCategoriaAxis, rotuloValorAxis, orientacaoPlotVertical,
				dados);
		this.usarBarra3D = usarBarra3D;
	}

	public boolean isUsarBarra3D() {
		return usarBarra3D;
	}

	public void setUsarBarra3D(boolean usarBarra3D) {
		this.usarBarra3D = usarBarra3D;
	}

}
