package com.hfsgwt.server.chart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.hfsgwt.client.componentes.chart.AreaChartParams;
import com.hfsgwt.client.componentes.chart.BarChartParams;
import com.hfsgwt.client.componentes.chart.BoxAndWhiskerChartDados;
import com.hfsgwt.client.componentes.chart.BoxAndWhiskerChartParams;
import com.hfsgwt.client.componentes.chart.BubbleChartParams;
import com.hfsgwt.client.componentes.chart.CandlestickChartParams;
import com.hfsgwt.client.componentes.chart.CategoryDados;
import com.hfsgwt.client.componentes.chart.GanttChartDados;
import com.hfsgwt.client.componentes.chart.GanttChartParams;
import com.hfsgwt.client.componentes.chart.HighLowChartParams;
import com.hfsgwt.client.componentes.chart.HistogramChartParams;
import com.hfsgwt.client.componentes.chart.LineChartParams;
import com.hfsgwt.client.componentes.chart.OHLCSeriesDados;
import com.hfsgwt.client.componentes.chart.OHLCSeriesParams;
import com.hfsgwt.client.componentes.chart.PieChartDados;
import com.hfsgwt.client.componentes.chart.PieChartParams;
import com.hfsgwt.client.componentes.chart.ScatterChartParams;
import com.hfsgwt.client.componentes.chart.SeriesDados;
import com.hfsgwt.client.componentes.chart.StackedAreaChartParams;
import com.hfsgwt.client.componentes.chart.StackedBarChartParams;
import com.hfsgwt.client.componentes.chart.TimeSeriesChartParams;
import com.hfsgwt.client.componentes.chart.TimeSeriesChartSerie;
import com.hfsgwt.client.componentes.chart.XYAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYBarChartParams;
import com.hfsgwt.client.componentes.chart.XYDados;
import com.hfsgwt.client.componentes.chart.XYLineChartParams;
import com.hfsgwt.client.componentes.chart.XYSeriesParams;
import com.hfsgwt.client.componentes.chart.XYSeriesParamsDouble;
import com.hfsgwt.client.componentes.chart.XYStepAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYStepChartParams;
import com.hfsgwt.server.util.Rotinas;

public final class RotinasChart {
	private static Logger log = Logger.getLogger(RotinasChart.class);
	private static RotinasChart instancia;

	private RotinasChart() {
		super();
	}

	public static RotinasChart getInstancia() {
		if (instancia == null) {
			instancia = new RotinasChart();
		}
		return instancia;
	}

	public double[][] getRandomDados(int maxLinhas, int maxCols,
			int fatorMultiplicacao) {
		double[][] dados = new double[maxLinhas][maxCols];
		for (int linha = 0; linha < maxLinhas; linha++) {
			for (int col = 0; col < maxCols; col++) {
				dados[linha][col] = (RandomUtils.nextDouble() * fatorMultiplicacao);
			}
		}
		return dados;
	}

	public void gerarGrafico(HttpServletResponse res, String arquivoChartJPG) {
		try {
			byte[] jpg = null;
			if (arquivoChartJPG != null) {
				File arq = new File(arquivoChartJPG);
				jpg = Rotinas.getBytesDoArquivo(arq);

				// Diz que vai enviar um arquivo do tipo JPG
				res.setContentType("image/jpeg");

				// Diz o tamanho dos dados
				res.setContentLength(jpg.length);

				// Escreve no browser
				ServletOutputStream ouputStream = res.getOutputStream();
				ouputStream.write(jpg, 0, jpg.length);
				ouputStream.flush();
				ouputStream.close();

				// excluir arquivo temporario
				arq.delete();
			}
		} catch (IOException e) {
			log.error("Erro de entrada/saída ao gerar gráfico(chart), ", e);
		}
	}

	public void criarPieChart(PieChartParams params, String arquivoChartJPG) {
		// Create a simple pie chart
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (PieChartDados dado : params.getDados()) {
			pieDataset.setValue(dado.getRotulo(), dado.getDado());
		}
		JFreeChart chart;
		if (params.isUsarPie3D()) {
			chart = ChartFactory.createPieChart3D(params.getTitulo(), // Title
					pieDataset, // Dataset
					params.isMostrarLegenda(), // Show legend
					params.isUsarTooltips(), // Use tooltips
					params.isGerarURLs() // Configure chart to generate URLs?
					);
		} else {
			chart = ChartFactory.createPieChart(params.getTitulo(), // Title
					pieDataset, // Dataset
					params.isMostrarLegenda(), // Show legend
					params.isUsarTooltips(), // Use tooltips
					params.isGerarURLs() // Configure chart to generate URLs?
					);
		}
		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (Exception e) {
			log.error("Erro ao criar PieChart", e);
		}
	}

	public void PieChartExemplo() {
		ArrayList<PieChartDados> dados = new ArrayList<PieChartDados>();
		dados.add(new PieChartDados("A", new Integer(75)));
		dados.add(new PieChartDados("B", new Integer(10)));
		dados.add(new PieChartDados("C", new Integer(10)));
		dados.add(new PieChartDados("D", new Integer(5)));
		PieChartParams params = new PieChartParams(500, 300,
				"Distribuição HFS", true, true, false, false, dados);
		criarPieChart(params, "C:\\PieChartExample.jpg");

		params = new PieChartParams(500, 300, "Distribuição HFS", true, true,
				false, true, dados);
		criarPieChart(params, "C:\\PieChart3DExample.jpg");
	}

	public void criarXYLineChart(XYLineChartParams params,
			String arquivoChartJPG) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYLineChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		// It is possible to modify the graph to show each of the 5 data points:
		// if (bMarkDataPoints) {
		// XYItemRenderer rend = chart.getXYPlot().getRenderer();
		// StandardXYItemRenderer rr = (StandardXYItemRenderer) rend;
		// rr.setPlotLines(true);
		//
		// try {
		// ChartUtilities.saveChartAsJPEG(new File(
		// "C:\\XYChartExample_MarkDataPoints.jpg"), chart, 500, 300);
		// } catch (IOException e) {
		// System.err.println("Problem occurred creating chart.");
		// }
		// } else {
		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar XYChart");
		}
		// }
	}

	public void XYLineChartExample() {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYLineChartParams params = new XYLineChartParams(500, 300,
				"Gráfico XY Linha", true, true, false, "x-eixo", "y-eixo",
				true, series);
		criarXYLineChart(params, "C:\\XYLineChartExample.jpg");
	}

	public void criarBarChart(BarChartParams params, String arquivoChartJPG) {
		// Create a simple Bar chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(), dado
					.getChaveColuna());
		}
		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createBarChart3D(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		} else {
			chart = ChartFactory
					.createBarChart(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		}
		if (params.getCorParams() != null) {
			// Set the background colour of the chart
			chart.setBackgroundPaint(new Color(params.getCorParams()
					.getCorFundoChart().getRed(), params.getCorParams()
					.getCorFundoChart().getGreen(), params.getCorParams()
					.getCorFundoChart().getBlue()));
			// Adjust the colour of the title
			chart.getTitle().setPaint(
					new Color(params.getCorParams().getCorTituloChart()
							.getRed(), params.getCorParams()
							.getCorTituloChart().getGreen(), params
							.getCorParams().getCorTituloChart().getBlue()));
			// Get the Plot object for a bar graph
			CategoryPlot p = chart.getCategoryPlot();
			// Modify the plot background
			p.setBackgroundPaint(new Color(params.getCorParams()
					.getCorFundoPlot().getRed(), params.getCorParams()
					.getCorFundoPlot().getGreen(), params.getCorParams()
					.getCorFundoPlot().getBlue()));
			// Modify the colour of the plot gridlines
			p.setRangeGridlinePaint(new Color(params.getCorParams()
					.getCorLinhasGridPlot().getRed(), params.getCorParams()
					.getCorLinhasGridPlot().getGreen(), params.getCorParams()
					.getCorLinhasGridPlot().getBlue()));
		}

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar BarChart");
		}
	}

	public void BarChartExample() {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		BarChartParams params = new BarChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", true, dados, false);

		criarBarChart(params, "C:\\BarChartExample.jpg");

		BarChartParams params2 = new BarChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Valor ($)", true, dados, true);

		criarBarChart(params2, "C:\\BarChartDuaLinhasExample.jpg");
	}

	public void criarTimeSeriesChart(TimeSeriesChartParams params,
			String arquivoChartJPG) {
		// Create a time series chart
		TimeSeries pop;
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for (TimeSeriesChartSerie serie : params.getSeries()) {
			pop = new TimeSeries(serie.getNomeSerie());

			for (SeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				params.getTitulo(), // Title
				params.getRotuloTempoAxis(), params.getRotuloValorAxis(),
				dataset, // Dataset
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar TimeSeriesChart");
		}
	}

	public void TimeSeriesExample() {
		ArrayList<SeriesDados> dados1 = new ArrayList<SeriesDados>();
		dados1.add(new SeriesDados(10, 1, 2004, 100));
		dados1.add(new SeriesDados(10, 2, 2004, 150));
		dados1.add(new SeriesDados(10, 3, 2004, 250));
		dados1.add(new SeriesDados(10, 4, 2004, 275));
		dados1.add(new SeriesDados(10, 5, 2004, 325));
		dados1.add(new SeriesDados(10, 6, 2004, 425));

		ArrayList<SeriesDados> dados2 = new ArrayList<SeriesDados>();
		dados2.add(new SeriesDados(20, 1, 2004, 200));
		dados2.add(new SeriesDados(20, 2, 2004, 250));
		dados2.add(new SeriesDados(20, 3, 2004, 450));
		dados2.add(new SeriesDados(20, 4, 2004, 475));
		dados2.add(new SeriesDados(20, 5, 2004, 125));

		ArrayList<TimeSeriesChartSerie> series = new ArrayList<TimeSeriesChartSerie>();
		series.add(new TimeSeriesChartSerie("População1", dados1));
		series.add(new TimeSeriesChartSerie("População2", dados2));

		TimeSeriesChartParams params = new TimeSeriesChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", series);

		criarTimeSeriesChart(params, "C:\\TimeSeriesExample.jpg");
	}

	public void criarAreaChart(AreaChartParams params, String arquivoChartJPG) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(), dado
					.getChaveColuna());
		}

		JFreeChart chart = ChartFactory.createAreaChart(params.getTitulo(),
				params.getRotuloCategoriaAxis(), params.getRotuloValorAxis(),
				dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar AreaChart");
		}
	}

	public void AreaChartExample() {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		AreaChartParams params = new AreaChartParams(500, 300,
				"Comparação entre vendedor", false, true, false, "Vendedor",
				"Lucro", true, dados);

		criarAreaChart(params, "C:\\AreaChartExample.jpg");
	}

	public void criarStackedBarChart(StackedBarChartParams params,
			String arquivoChartJPG) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(), dado
					.getChaveColuna());
		}

		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createStackedBarChart3D(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		} else {
			chart = ChartFactory
					.createStackedBarChart(
							params.getTitulo(), // Title
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset, // Dataset
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), // Plot
							// Orientation
							params.isMostrarLegenda(), // Show Legend
							params.isUsarTooltips(), // Use tooltips
							params.isGerarURLs() // Configure chart to generate
					// URLs?
					);
		}

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar StackedBarChart");
		}
	}

	public void StackedBarChartExample() {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		StackedBarChartParams params = new StackedBarChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", true, dados, false);

		criarStackedBarChart(params, "C:\\StackedBarChartExample.jpg");

		StackedBarChartParams params2 = new StackedBarChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Valor ($)", true, dados, true);

		criarStackedBarChart(params2, "C:\\StackedBarChart3DExample.jpg");
	}

	public void criarXYAreaChart(XYAreaChartParams params,
			String arquivoChartJPG) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYAreaChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar XYAreaChart");
		}
	}

	public void XYAreaChartExample() {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYAreaChartParams params = new XYAreaChartParams(500, 300,
				"Gráfico XY Área", true, true, false, "x-eixo", "y-eixo", true,
				series);
		criarXYAreaChart(params, "C:\\XYAreaChartExample.jpg");
	}

	public void criarXYStepChart(XYStepChartParams params,
			String arquivoChartJPG) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYStepChart(params.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar XYStepChart");
		}
	}

	public void XYStepChartExample() {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYStepChartParams params = new XYStepChartParams(500, 300,
				"Gráfico XY Step", true, true, false, "x-eixo", "y-eixo", true,
				series);
		criarXYStepChart(params, "C:\\XYStepChartExample.jpg");
	}

	public void criarXYStepAreaChart(XYStepAreaChartParams params,
			String arquivoChartJPG) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createXYStepAreaChart(params
				.getTitulo(), // Title
				params.getRotuloXAxis(), // x-axis Label
				params.getRotuloYAxis(), // y-axis Label
				dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar XYStepAreaChart");
		}
	}

	public void XYStepAreaChartExample() {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		XYStepAreaChartParams params = new XYStepAreaChartParams(500, 300,
				"Gráfico XY Step Área", true, true, false, "x-eixo", "y-eixo",
				true, series);
		criarXYStepAreaChart(params, "C:\\XYStepAreaChartExample.jpg");
	}

	public void criarLineChart(LineChartParams params, String arquivoChartJPG) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(), dado
					.getChaveColuna());
		}
		JFreeChart chart;
		if (params.isUsarBarra3D()) {
			chart = ChartFactory
					.createLineChart3D(
							params.getTitulo(),
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset,
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), params
									.isMostrarLegenda(), params
									.isUsarTooltips(), params.isGerarURLs());
		} else {
			chart = ChartFactory
					.createLineChart(
							params.getTitulo(),
							params.getRotuloCategoriaAxis(),
							params.getRotuloValorAxis(),
							dataset,
							(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
									: PlotOrientation.HORIZONTAL), params
									.isMostrarLegenda(), params
									.isUsarTooltips(), params.isGerarURLs());

		}

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar LineChart");
		}
	}

	public void LineChartExample() {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		LineChartParams params = new LineChartParams(500, 300,
				"Comparação entre vendedor", false, true, false, "Vendedor",
				"Lucro", true, dados, false);

		criarLineChart(params, "C:\\LineChartExample.jpg");

		params = new LineChartParams(500, 300, "Comparação entre vendedor",
				false, true, false, "Vendedor", "Lucro", true, dados, true);

		criarLineChart(params, "C:\\LineChart3DExample.jpg");
	}

	public void criarBubbleChart(BubbleChartParams params,
			String arquivoChartJPG) {
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}

		JFreeChart chart = ChartFactory.createBubbleChart(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar BubbleChart");
		}
	}

	public void BubbleChartExample() {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", getRandomDados(3, 20,
				100)));
		series.add(new XYSeriesParamsDouble("População2", getRandomDados(3, 20,
				100)));

		BubbleChartParams params = new BubbleChartParams(500, 300,
				"Gráfico XY Bolha", true, true, false, "x-eixo", "y-eixo",
				true, series);

		criarBubbleChart(params, "C:\\BubbleChartExample.jpg");
	}

	public void criarHistogramChart(HistogramChartParams params,
			String arquivoChartJPG) {
		DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}
		JFreeChart chart = ChartFactory.createHistogram(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar HistogramChart");
		}
	}

	public void HistogramChartExample() {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", getRandomDados(3, 20,
				100)));
		series.add(new XYSeriesParamsDouble("População2", getRandomDados(3, 20,
				100)));

		HistogramChartParams params = new HistogramChartParams(500, 300,
				"Gráfico XY Histograma", true, true, false, "x-eixo", "y-eixo",
				true, series);

		criarHistogramChart(params, "C:\\HistogramChartExample.jpg");
	}

	public void criarXYBarChart(XYBarChartParams params, String arquivoChartJPG) {
		DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
		for (XYSeriesParamsDouble serie : params.getSeries()) {
			dataset.addSeries(serie.getNomeSerie(), serie.getDados());
		}
		JFreeChart chart = ChartFactory.createXYBarChart(params.getTitulo(),
				params.getRotuloXAxis(), params.isMostrarTempoXAxis(), params
						.getRotuloYAxis(), dataset, // Dataset
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), // Plot Orientation
				params.isMostrarLegenda(), // Show Legend
				params.isUsarTooltips(), // Use tooltips
				params.isGerarURLs() // Configure chart to generate URLs?
				);

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar XYBarChart");
		}
	}

	public void XYBarChartExample() {
		ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
		series.add(new XYSeriesParamsDouble("População1", getRandomDados(3, 20,
				100)));
		series.add(new XYSeriesParamsDouble("População2", getRandomDados(3, 20,
				100)));

		XYBarChartParams params = new XYBarChartParams(500, 300,
				"Gráfico XY Barra", true, true, false, "x-eixo", "y-eixo",
				true, series, false);

		criarXYBarChart(params, "C:\\XYBarChartExample.jpg");

		params = new XYBarChartParams(500, 300, "Gráfico XY Barra", true, true,
				false, "x-eixo", "y-eixo", true, series, true);
		criarXYBarChart(params, "C:\\XYBarChartExampleTempo.jpg");
	}

	public void criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params,
			String arquivoChartJPG) {
		DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
		BoxAndWhiskerItem bwItem;
		for (BoxAndWhiskerChartDados item : params.getDados()) {
			bwItem = new BoxAndWhiskerItem(item.getMedia(), item.getMediana(),
					item.getQ1(), item.getQ3(), item.getMinValorRegular(), item
							.getMaxValorRegular(), item.getMinAnexo(), item
							.getMaxAnexo(), item.getAnexos());
			dataset
					.add(bwItem, params.getChaveLinha(), params
							.getChaveColuna());
		}
		// (title, categoryAxisLabel, valueAxisLabel, dataset, legend)
		// (title, timeAxisLabel, valueAxisLabel, dataset, legend)

		JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(params
				.getTitulo(), params.getRotuloCategoriaAxis(), params
				.getRotuloValorAxis(), dataset, params.isMostrarLegenda());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void BoxAndWhiskerChartExample() {
		List<BoxAndWhiskerChartDados> dados = new ArrayList<BoxAndWhiskerChartDados>();
		List<Double> anexos = new ArrayList<Double>();
		anexos.add(new Double(34));
		anexos.add(new Double(24));
		anexos.add(new Double(14));
		anexos.add(new Double(6));
		anexos.add(new Double(50));
		dados.add(new BoxAndWhiskerChartDados(100, 25, 10, 8, 5, 30, 6, 80,
				anexos));

		BoxAndWhiskerChartParams params = new BoxAndWhiskerChartParams(500,
				300, "Comparação entre vendedor", true, "Vendedor", "Lucro",
				"ChaveLinha", "ChaveColuna", dados);

		criarBoxAndWhiskerChart(params, "C:\\BoxAndWhiskerChartExample.jpg");
	}

	public void criarStackedAreaChart(StackedAreaChartParams params,
			String arquivoChartJPG) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (CategoryDados dado : params.getDados()) {
			dataset.setValue(dado.getValor(), dado.getChaveLinha(), dado
					.getChaveColuna());
		}
		JFreeChart chart = ChartFactory.createStackedAreaChart(params
				.getTitulo(), params.getRotuloCategoriaAxis(), params
				.getRotuloValorAxis(), dataset, (params
				.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
				: PlotOrientation.HORIZONTAL), params.isMostrarLegenda(),
				params.isUsarTooltips(), params.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar StackedAreaChart");
		}
	}

	public void StackedAreaChartExample() {
		ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
		dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
		dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
		dados.add(new CategoryDados(7, "Lucro1", "Raul"));
		dados.add(new CategoryDados(10, "Lucro2", "Raul"));
		dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
		dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
		dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
		dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
		dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
		dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

		StackedAreaChartParams params = new StackedAreaChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", true, dados);

		criarStackedAreaChart(params, "C:\\StackedAreaChartExample.jpg");
	}

	public void criarScatterChart(ScatterChartParams params,
			String arquivoChartJPG) {
		XYSeries series;
		XYSeriesCollection dataset = new XYSeriesCollection();
		for (XYSeriesParams serie : params.getSeries()) {
			series = new XYSeries(serie.getNomeSerie());
			for (XYDados dado : serie.getDados()) {
				series.add(dado.getSerieX(), dado.getSerieY());
			}
			dataset.addSeries(series);
		}
		JFreeChart chart = ChartFactory.createScatterPlot(params.getTitulo(),
				params.getRotuloXAxis(), params.getRotuloYAxis(), dataset,
				(params.isOrientacaoPlotVertical() ? PlotOrientation.VERTICAL
						: PlotOrientation.HORIZONTAL), params
						.isMostrarLegenda(), params.isUsarTooltips(), params
						.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar ScatterChart");
		}
	}

	public void ScatterChartExample() {
		ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
		dados1.add(new XYDados(1, 1));
		dados1.add(new XYDados(1, 2));
		dados1.add(new XYDados(2, 1));
		dados1.add(new XYDados(3, 9));
		dados1.add(new XYDados(4, 10));

		ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
		dados2.add(new XYDados(3, 6));
		dados2.add(new XYDados(8, 9));
		dados2.add(new XYDados(7, 10));
		dados2.add(new XYDados(8, 1));
		dados2.add(new XYDados(9, 4));

		ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
		series.add(new XYSeriesParams("População1", dados1));
		series.add(new XYSeriesParams("População2", dados2));

		ScatterChartParams params = new ScatterChartParams(500, 300,
				"Gráfico Scatter", true, true, false, "x-eixo", "y-eixo",
				true, series);
		criarScatterChart(params, "C:\\ScatterChartExample.jpg");
	}

	public void criarHighLowChart(HighLowChartParams params,
			String arquivoChartJPG) {
		OHLCSeries pop;
		OHLCSeriesCollection dataset = new OHLCSeriesCollection();
		for (OHLCSeriesParams serie : params.getSeries()) {
			pop = new OHLCSeries(serie.getNomeSerie());
			for (OHLCSeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor().doubleValue(), dado.getValor2(), dado
								.getValor3(), dado.getValor4());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory.createHighLowChart(params.getTitulo(),
				params.getRotuloTempoAxis(), params.getRotuloValorAxis(),
				dataset, params.isMostrarLegenda());

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar HighLowChart");
		}
	}

	public void HighLowChartExample() {
		ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
		dados1.add(new OHLCSeriesDados(10, 1, 2000, 100, 200, 150, 300));
		dados1.add(new OHLCSeriesDados(10, 2, 2000, 150, 50, 200, 140));
		dados1.add(new OHLCSeriesDados(10, 3, 2000, 250, 100, 80, 93));
		dados1.add(new OHLCSeriesDados(10, 4, 2000, 275, 230, 280, 168));
		dados1.add(new OHLCSeriesDados(10, 5, 2000, 325, 245, 123, 52));
		dados1.add(new OHLCSeriesDados(10, 6, 2000, 425, 45, 98, 195));

		ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
		dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
		dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
		dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
		dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
		dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

		ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
		series.add(new OHLCSeriesParams("População1", dados1));
		series.add(new OHLCSeriesParams("População2", dados2));

		HighLowChartParams params = new HighLowChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", "Chave", series);

		criarHighLowChart(params, "C:\\HighLowChartExample.jpg");
	}

	public void criarCandlestickChart(CandlestickChartParams params,
			String arquivoChartJPG) {
		OHLCSeries pop;
		OHLCSeriesCollection dataset = new OHLCSeriesCollection();
		for (OHLCSeriesParams serie : params.getSeries()) {
			pop = new OHLCSeries(serie.getNomeSerie());
			for (OHLCSeriesDados dado : serie.getDados()) {
				pop.add(new Day(dado.getDia(), dado.getMes(), dado.getAno()),
						dado.getValor().doubleValue(), dado.getValor2(), dado
								.getValor3(), dado.getValor4());
			}
			dataset.addSeries(pop);
		}

		JFreeChart chart = ChartFactory.createCandlestickChart(params
				.getTitulo(), params.getRotuloTempoAxis(), params
				.getRotuloValorAxis(), dataset, params.isMostrarLegenda());

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd/MM/yyyy"));

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar CandlestickChart");
		}
	}

	public void CandlestickChartExample() {
		ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
		dados1.add(new OHLCSeriesDados(10, 1, 2004, 100, 200, 150, 300));
		dados1.add(new OHLCSeriesDados(10, 2, 2004, 150, 50, 200, 140));
		dados1.add(new OHLCSeriesDados(10, 3, 2004, 250, 100, 80, 93));
		dados1.add(new OHLCSeriesDados(10, 4, 2004, 275, 230, 280, 168));
		dados1.add(new OHLCSeriesDados(10, 5, 2004, 325, 245, 123, 52));
		dados1.add(new OHLCSeriesDados(10, 6, 2004, 425, 45, 98, 195));

		ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
		dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
		dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
		dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
		dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
		dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

		ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
		series.add(new OHLCSeriesParams("População1", dados1));
		series.add(new OHLCSeriesParams("População2", dados2));

		CandlestickChartParams params = new CandlestickChartParams(500, 300,
				"População da Cidade do Recife", true, true, false, "Data",
				"População", "Chave", series);

		criarCandlestickChart(params, "C:\\CandlestickChartExample.jpg");
	}

	public void criarGanttChart(GanttChartParams params, String arquivoChartJPG) {
		String[] nomeSeries = new String[params.getDados().length];
		Number[][] partidas = new Number[params.getDados().length][];
		Number[][] chegadas = new Number[params.getDados().length][];
		GanttChartDados item;
		for (int i = 0; i < params.getDados().length; i++) {
			item = params.getDados()[i];
			nomeSeries[i] = item.getNomeSerie();

			partidas[i] = new Number[item.getPartidas().length];
			for (int j = 0; j < item.getPartidas().length; j++) {
				partidas[i][j] = item.getPartidas()[j];
			}

			chegadas[i] = new Number[item.getChegadas().length];
			for (int j = 0; j < item.getChegadas().length; j++) {
				chegadas[i][j] = item.getChegadas()[j];
			}
		}
		DefaultIntervalCategoryDataset dataset = new DefaultIntervalCategoryDataset(
				nomeSeries, partidas, chegadas);

		JFreeChart chart = ChartFactory.createGanttChart(params.getTitulo(),
				params.getRotuloCategoriaAxis(), params.getRotuloDataAxis(),
				dataset, params.isMostrarLegenda(), params.isUsarTooltips(),
				params.isGerarURLs());

		try {
			ChartUtilities.saveChartAsJPEG(new File(arquivoChartJPG), chart,
					params.getLargura(), params.getAltura());
		} catch (IOException e) {
			log.error("Erro ao criar GanttChart");
		}
	}

	public void GanttChartExample() {

		Number[] partidas1 = new Number[6];
		partidas1[0] = 100;
		partidas1[1] = 150;
		partidas1[2] = 250;
		partidas1[3] = 275;
		partidas1[4] = 325;
		partidas1[5] = 425;

		Number[] chegadas1 = new Number[6];
		chegadas1[0] = 200;
		chegadas1[1] = 250;
		chegadas1[2] = 450;
		chegadas1[3] = 475;
		chegadas1[4] = 125;
		chegadas1[5] = 305;

		Number[] chegadas2 = new Number[6];
		chegadas2[0] = 100;
		chegadas2[1] = 150;
		chegadas2[2] = 250;
		chegadas2[3] = 275;
		chegadas2[4] = 325;
		chegadas2[5] = 425;

		Number[] partidas2 = new Number[6];
		partidas2[0] = 200;
		partidas2[1] = 250;
		partidas2[2] = 450;
		partidas2[3] = 475;
		partidas2[4] = 125;
		partidas2[5] = 305;

		GanttChartDados[] series = new GanttChartDados[2];
		series[0] = new GanttChartDados("População1", partidas1, chegadas1);
		series[1] = new GanttChartDados("População2", partidas2, chegadas2);

		GanttChartParams params = new GanttChartParams(500, 300,
				"Comparação entre vendedor", true, true, false, "Vendedor",
				"Lucro", series);

		criarGanttChart(params, "C:\\GanttChartExample.jpg");
	}
	
}
