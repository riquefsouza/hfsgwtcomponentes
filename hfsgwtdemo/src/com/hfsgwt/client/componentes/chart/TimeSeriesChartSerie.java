package com.hfsgwt.client.componentes.chart;

import java.util.List;

public class TimeSeriesChartSerie extends SeriesParams {

	public TimeSeriesChartSerie(){
		super();
	}
	
	public TimeSeriesChartSerie(String nomeSerie,
			List<SeriesDados> dados) {
		super(nomeSerie, dados);
	}
}