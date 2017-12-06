package com.hfsgwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hfsgwt.client.application.PropriedadeSistema;
import com.hfsgwt.client.componentes.barcode.BarCodeParams;
import com.hfsgwt.client.componentes.chart.AreaChartParams;
import com.hfsgwt.client.componentes.chart.BarChartParams;
import com.hfsgwt.client.componentes.chart.BoxAndWhiskerChartParams;
import com.hfsgwt.client.componentes.chart.BubbleChartParams;
import com.hfsgwt.client.componentes.chart.CandlestickChartParams;
import com.hfsgwt.client.componentes.chart.GanttChartParams;
import com.hfsgwt.client.componentes.chart.HighLowChartParams;
import com.hfsgwt.client.componentes.chart.HistogramChartParams;
import com.hfsgwt.client.componentes.chart.LineChartParams;
import com.hfsgwt.client.componentes.chart.PieChartParams;
import com.hfsgwt.client.componentes.chart.ScatterChartParams;
import com.hfsgwt.client.componentes.chart.StackedAreaChartParams;
import com.hfsgwt.client.componentes.chart.StackedBarChartParams;
import com.hfsgwt.client.componentes.chart.TimeSeriesChartParams;
import com.hfsgwt.client.componentes.chart.XYAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYBarChartParams;
import com.hfsgwt.client.componentes.chart.XYLineChartParams;
import com.hfsgwt.client.componentes.chart.XYStepAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYStepChartParams;
import com.hfsgwt.client.componentes.chat.ChatMensagem;
import com.hfsgwt.client.componentes.chat.ChatUsuario;
import com.hfsgwt.client.componentes.email.EmailConteudo;
import com.hfsgwt.client.componentes.ftp.FTPArquivo;
import com.hfsgwt.client.componentes.ftp.FTPEstrutura;
import com.hfsgwt.client.componentes.menuxml.MXEstrutura;

public interface HfsgwtServiceAsync {
	void getNumeroExtenso(double numero, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getCodeBase64(String texto, boolean encode64,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void enviarEmail(EmailConteudo email, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void getBarCodeHTML(BarCodeParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarPieChart(PieChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarXYLineChart(XYLineChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarLineChart(LineChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarXYAreaChart(XYAreaChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarXYBarChart(XYBarChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarHistogramChart(HistogramChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarBubbleChart(BubbleChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarXYStepChart(XYStepChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarXYStepAreaChart(XYStepAreaChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarBarChart(BarChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarStackedBarChart(StackedBarChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarTimeSeriesChart(TimeSeriesChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarAreaChart(AreaChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void carregaMX(String codigoSistema, AsyncCallback<MXEstrutura> callback)
			throws IllegalArgumentException;

	void conectaChat(ChatUsuario usuario, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void desconectaChat(AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void enviarMensagemChat(String usuarioDestino, String mensagem,
			AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void getMensagemChat(AsyncCallback<ChatMensagem> callback)
			throws IllegalArgumentException;

	void getUsuariosChat(AsyncCallback<ChatUsuario[]> callback)
			throws IllegalArgumentException;

	void conectaFTP(FTPEstrutura ftpe, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void desconectaFTP(AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void enviarArquivoFTP(FTPEstrutura ftpe, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void receberArquivoFTP(FTPEstrutura ftpe, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void listarArquivosFTP(FTPEstrutura ftpe, boolean somenteDiretorio,
			AsyncCallback<FTPArquivo[]> callback)
			throws IllegalArgumentException;

	void validarLogin(String hostname, String login, String senha,
			AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void alterarSenha(String hostname, String login, String senha,
			String novaSenha, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;

	void getHash(String tipo, String senha, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void validaForcaSenha(String senha, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void iniciarCriptografia(String geradorChave, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void encriptar(String texto, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void decriptar(String texto, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getPropriedadesSistema(AsyncCallback<List<PropriedadeSistema>> callback)
			throws IllegalArgumentException;

	void criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarCandlestickChart(CandlestickChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarGanttChart(GanttChartParams params, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void criarHighLowChart(HighLowChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarScatterChart(ScatterChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;

	void criarStackedAreaChart(StackedAreaChartParams params,
			AsyncCallback<String> callback) throws IllegalArgumentException;
}
