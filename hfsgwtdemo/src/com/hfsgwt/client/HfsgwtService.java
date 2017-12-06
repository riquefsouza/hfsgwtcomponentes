package com.hfsgwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

@RemoteServiceRelativePath("hfsgwtservico")
public interface HfsgwtService extends RemoteService {

	public String getNumeroExtenso(double numero)
			throws IllegalArgumentException;

	public String getCodeBase64(String texto, boolean encode64)
			throws IllegalArgumentException;

	public Boolean enviarEmail(EmailConteudo email)
			throws IllegalArgumentException;

	public String getBarCodeHTML(BarCodeParams params)
			throws IllegalArgumentException;

	public String criarPieChart(PieChartParams params)
			throws IllegalArgumentException;

	public String criarXYLineChart(XYLineChartParams params)
			throws IllegalArgumentException;

	public String criarLineChart(LineChartParams params)
			throws IllegalArgumentException;

	public String criarXYAreaChart(XYAreaChartParams params)
			throws IllegalArgumentException;

	public String criarXYBarChart(XYBarChartParams params)
			throws IllegalArgumentException;

	public String criarHistogramChart(HistogramChartParams params)
			throws IllegalArgumentException;

	public String criarBubbleChart(BubbleChartParams params)
			throws IllegalArgumentException;

	public String criarXYStepChart(XYStepChartParams params)
			throws IllegalArgumentException;

	public String criarXYStepAreaChart(XYStepAreaChartParams params)
			throws IllegalArgumentException;

	public String criarBarChart(BarChartParams params)
			throws IllegalArgumentException;

	public String criarStackedBarChart(StackedBarChartParams params)
			throws IllegalArgumentException;

	public String criarTimeSeriesChart(TimeSeriesChartParams params)
			throws IllegalArgumentException;

	public String criarAreaChart(AreaChartParams params)
			throws IllegalArgumentException;

	public String criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params)
			throws IllegalArgumentException;

	public String criarStackedAreaChart(StackedAreaChartParams params)
			throws IllegalArgumentException;

	public String criarScatterChart(ScatterChartParams params)
			throws IllegalArgumentException;

	public String criarHighLowChart(HighLowChartParams params)
			throws IllegalArgumentException;

	public String criarCandlestickChart(CandlestickChartParams params)
			throws IllegalArgumentException;

	public String criarGanttChart(GanttChartParams params)
			throws IllegalArgumentException;

	public MXEstrutura carregaMX(String codigoSistema)
			throws IllegalArgumentException;

	public Boolean conectaChat(ChatUsuario usuario)
			throws IllegalArgumentException;

	public Boolean desconectaChat() throws IllegalArgumentException;

	public Boolean enviarMensagemChat(String usuarioDestino, String mensagem)
			throws IllegalArgumentException;

	public ChatMensagem getMensagemChat() throws IllegalArgumentException;

	public ChatUsuario[] getUsuariosChat() throws IllegalArgumentException;

	public Boolean conectaFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException;

	public Boolean desconectaFTP() throws IllegalArgumentException;

	public Boolean enviarArquivoFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException;

	public Boolean receberArquivoFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException;

	public FTPArquivo[] listarArquivosFTP(FTPEstrutura ftpe,
			boolean somenteDiretorio) throws IllegalArgumentException;

	public Boolean validarLogin(String hostname, String login, String senha)
			throws IllegalArgumentException;

	public Boolean alterarSenha(String hostname, String login, String senha,
			String novaSenha) throws IllegalArgumentException;

	public String getHash(String tipo, String senha)
			throws IllegalArgumentException;

	public String validaForcaSenha(String senha)
			throws IllegalArgumentException;

	public String iniciarCriptografia(String geradorChave)
			throws IllegalArgumentException;

	public String encriptar(String texto) throws IllegalArgumentException;

	public String decriptar(String texto) throws IllegalArgumentException;

	public List<PropriedadeSistema> getPropriedadesSistema()
			throws IllegalArgumentException;
}
