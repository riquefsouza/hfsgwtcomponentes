package com.hfsgwt.client.componentes.chart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSChart extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private enum TipoGrafico {
		LINHA, XY_LINHA, BARRA, PIZZA, SERIES_NO_TEMPO, AREA, BARRA_EMPILHADA, XY_AREA, XY_PASSO, XY_PASSO_AREA, XY_BARRA, HISTOGRAMA, BOLHA,
		BOX_AND_WHISKER, AREA_EMPILHADA, SCATTER, HIGHLOW, CANDELABRO, GANTT
	}

	private String largura;
	private String altura;
	private Image image;

	private TipoGrafico tipoGrafico;
	private BarChartParams paramsBarChart;
	private XYLineChartParams paramsXYLineChart;
	private LineChartParams paramsLineChart;
	private PieChartParams paramsPieChart;
	private TimeSeriesChartParams paramsTimeSeriesChart;
	private AreaChartParams paramsAreaChart;
	private StackedBarChartParams paramsStackedBarChart;
	private XYAreaChartParams paramsXYAreaChart;
	private XYStepChartParams paramsXYStepChart;
	private XYStepAreaChartParams paramsXYStepAreaChart;
	private XYBarChartParams paramsXYBarChart;
	private HistogramChartParams paramsHistogramChart;
	private BubbleChartParams paramsBubbleChart;
	private BoxAndWhiskerChartParams paramsBoxAndWhiskerChart;
	private StackedAreaChartParams paramsStackedAreaChart;
	private ScatterChartParams paramsScatterChart;
	private HighLowChartParams paramsHighLowChart;
	private CandlestickChartParams paramsCandlestickChart;
	private GanttChartParams paramsGanttChart;
	
	
	public HFSChart(BarChartParams params) {
		this.paramsBarChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.BARRA;
		initComponents();
	}

	public HFSChart(XYLineChartParams params) {
		this.paramsXYLineChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.XY_LINHA;
		initComponents();
	}

	public HFSChart(LineChartParams params) {
		this.paramsLineChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.LINHA;
		initComponents();
	}

	public HFSChart(PieChartParams params) {
		this.paramsPieChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.PIZZA;
		initComponents();
	}

	public HFSChart(TimeSeriesChartParams params) {
		this.paramsTimeSeriesChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.SERIES_NO_TEMPO;
		initComponents();
	}

	public HFSChart(AreaChartParams params) {
		this.paramsAreaChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.AREA;
		initComponents();
	}
	
	public HFSChart(StackedBarChartParams params) {
		this.paramsStackedBarChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.BARRA_EMPILHADA;
		initComponents();
	}

	public HFSChart(XYAreaChartParams params) {
		this.paramsXYAreaChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.XY_AREA;
		initComponents();
	}
	
	public HFSChart(XYStepChartParams params) {
		this.paramsXYStepChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.XY_PASSO;
		initComponents();
	}

	public HFSChart(XYStepAreaChartParams params) {
		this.paramsXYStepAreaChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.XY_PASSO_AREA;
		initComponents();
	}
	
	public HFSChart(XYBarChartParams params) {
		this.paramsXYBarChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.XY_BARRA;
		initComponents();
	}
	
	public HFSChart(HistogramChartParams params) {
		this.paramsHistogramChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.HISTOGRAMA;
		initComponents();
	}

	public HFSChart(BubbleChartParams params) {
		this.paramsBubbleChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.BOLHA;
		initComponents();
	}

	public HFSChart(BoxAndWhiskerChartParams params) {
		this.paramsBoxAndWhiskerChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.BOX_AND_WHISKER;
		initComponents();
	}

	public HFSChart(StackedAreaChartParams params) {
		this.paramsStackedAreaChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.AREA_EMPILHADA;
		initComponents();
	}

	public HFSChart(ScatterChartParams params) {
		this.paramsScatterChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.SCATTER;
		initComponents();
	}

	public HFSChart(HighLowChartParams params) {
		this.paramsHighLowChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.HIGHLOW;
		initComponents();
	}

	public HFSChart(CandlestickChartParams params) {
		this.paramsCandlestickChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.CANDELABRO;
		initComponents();
	}

	public HFSChart(GanttChartParams params) {
		this.paramsGanttChart = params;
		this.largura = Integer.toString(params.getLargura());
		this.altura = Integer.toString(params.getAltura());
		this.tipoGrafico = TipoGrafico.GANTT;
		initComponents();
	}

	private void initComponents() {
		initWidget(getImage());
	}

	private Image getImage() {
		if (image == null) {
			image = new Image();
			image.setSize(this.largura + "px", this.altura + "px");
		}
		return image;
	}

	public void mostrarGrafico() {
		switch (tipoGrafico) {
		case AREA:
			criarAreaChart(paramsAreaChart);
			break;
		case XY_LINHA:
			criarXYLineChart(paramsXYLineChart);
			break;
		case LINHA:
			criarLineChart(paramsLineChart);
			break;
		case XY_AREA:
			criarXYAreaChart(paramsXYAreaChart);
			break;
		case XY_PASSO:
			criarXYStepChart(paramsXYStepChart);
			break;
		case XY_PASSO_AREA:
			criarXYStepAreaChart(paramsXYStepAreaChart);
			break;
		case BARRA:
			criarBarChart(paramsBarChart);
			break;
		case BARRA_EMPILHADA:
			criarStackedBarChart(paramsStackedBarChart);
			break;
		case PIZZA:
			criarPieChart(paramsPieChart);
			break;
		case SERIES_NO_TEMPO:
			criarTimeSeriesChart(paramsTimeSeriesChart);
			break;
		case XY_BARRA:
			criarXYBarChart(paramsXYBarChart);
			break;
		case HISTOGRAMA:
			criarHistogramChart(paramsHistogramChart);
			break;
		case BOLHA:
			criarBubbleChart(paramsBubbleChart);
			break;			
		case BOX_AND_WHISKER:
			criarBoxAndWhiskerChart(paramsBoxAndWhiskerChart);
			break;
		case AREA_EMPILHADA:
			criarStackedAreaChart(paramsStackedAreaChart);
			break;
		case SCATTER:
			criarScatterChart(paramsScatterChart);
			break;
		case HIGHLOW:
			criarHighLowChart(paramsHighLowChart);
			break;
		case CANDELABRO:
			criarCandlestickChart(paramsCandlestickChart);
			break;
		case GANTT:
			criarGanttChart(paramsGanttChart);
			break;			
		}
	}

	private String getURL(String arquivo) {
		return GWT.getHostPageBaseURL() + "grafico?tipo="
				+ tipoGrafico.toString() + "&arquivo=" + arquivo;
	}

	private void criarBarChart(BarChartParams params) {
		servico.criarBarChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarBarChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarXYLineChart(XYLineChartParams params) {
		servico.criarXYLineChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarXYLineChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarLineChart(LineChartParams params) {
		servico.criarLineChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarLineChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarPieChart(PieChartParams params) {
		servico.criarPieChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarPieChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarTimeSeriesChart(TimeSeriesChartParams params) {
		servico.criarTimeSeriesChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(),
						"criarTimeSeriesChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}
	
	private void criarAreaChart(AreaChartParams params) {
		servico.criarAreaChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarAreaChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}
	
	private void criarStackedBarChart(StackedBarChartParams params) {
		servico.criarStackedBarChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarStackedBarChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarXYAreaChart(XYAreaChartParams params) {
		servico.criarXYAreaChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarXYAreaChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}
	
	private void criarXYStepChart(XYStepChartParams params) {
		servico.criarXYStepChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarXYStepChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarXYStepAreaChart(XYStepAreaChartParams params) {
		servico.criarXYStepAreaChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarXYStepAreaChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarXYBarChart(XYBarChartParams params) {
		servico.criarXYBarChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarXYBarChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarHistogramChart(HistogramChartParams params) {
		servico.criarHistogramChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarHistogramChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarBubbleChart(BubbleChartParams params) {
		servico.criarBubbleChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarBubbleChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params) {
		servico.criarBoxAndWhiskerChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarBoxAndWhiskerChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarStackedAreaChart(StackedAreaChartParams params) {
		servico.criarStackedAreaChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarStackedAreaChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarScatterChart(ScatterChartParams params) {
		servico.criarScatterChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarScatterChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarHighLowChart(HighLowChartParams params) {
		servico.criarHighLowChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarHighLowChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarCandlestickChart(CandlestickChartParams params) {
		servico.criarCandlestickChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarCandlestickChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}

	private void criarGanttChart(GanttChartParams params) {
		servico.criarGanttChart(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "criarGanttChart");
			}

			public void onSuccess(String arquivo) {
				getImage().setUrl(getURL(arquivo));
			}
		});
	}
	
}
