package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class XYBarChartParams extends XYParamsDouble {
	private boolean mostrarTempoXAxis;

	public XYBarChartParams() {
		super();
	}

	public XYBarChartParams(int largura, int altura, String titulo,
			boolean mostrarLegenda, boolean usarTooltips, boolean gerarURLs,
			String rotuloXAxis, String rotuloYAxis,
			boolean orientacaoPlotVertical, List<XYSeriesParamsDouble> series,
			boolean mostrarTempoXAxis) {
		super(largura, altura, titulo, mostrarLegenda, usarTooltips, gerarURLs,
				rotuloXAxis, rotuloYAxis, orientacaoPlotVertical, series);
		this.mostrarTempoXAxis = mostrarTempoXAxis;
	}

	public boolean isMostrarTempoXAxis() {
		return mostrarTempoXAxis;
	}

	public void setMostrarTempoXAxis(boolean mostrarTempoXAxis) {
		this.mostrarTempoXAxis = mostrarTempoXAxis;
	}

}
