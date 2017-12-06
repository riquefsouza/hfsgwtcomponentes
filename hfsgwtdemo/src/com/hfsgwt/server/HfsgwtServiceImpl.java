package com.hfsgwt.server;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTPClient;
import org.jivesoftware.smack.XMPPConnection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hfsgwt.client.HfsgwtService;
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
import com.hfsgwt.client.componentes.util.HFSCrypt;
import com.hfsgwt.server.barcode.RotinasBarCode;
import com.hfsgwt.server.chart.RotinasChart;
import com.hfsgwt.server.chat.RotinasChat;
import com.hfsgwt.server.login.RotinasLogin;
import com.hfsgwt.server.util.NumeroExtenso;
import com.hfsgwt.server.util.Rotinas;
import com.hfsgwt.server.util.RotinasCriptografia;
import com.hfsgwt.server.util.RotinasEmail;
import com.hfsgwt.server.util.RotinasFTP;
import com.hfsgwt.server.util.RotinasHash;
import com.hfsgwt.server.util.RotinasMX;
import com.hfsgwt.server.util.ValidaSenha;
import com.hfsgwt.server.util.RotinasCriptografia.GeradorChave;

@SuppressWarnings("serial")
public class HfsgwtServiceImpl extends RemoteServiceServlet implements
		HfsgwtService {

	FTPClient ftp;
	XMPPConnection chat;

	@Override
	public String getNumeroExtenso(double numero)
			throws IllegalArgumentException {
		NumeroExtenso ne = new NumeroExtenso(numero, " real", " reais");
		return ne.toString();
	}

	@Override
	public String getCodeBase64(String texto, boolean encode64)
			throws IllegalArgumentException {
		String sCode64;
		if (encode64)			
			//sCode64 = RotinasEncode64.encode64(texto.getBytes(), "UTF-8");
			sCode64 = new String(Base64.encodeBase64(texto.getBytes()));
		else
			//sCode64 = new String(RotinasEncode64.decode64(texto));
			sCode64 = new String(Base64.decodeBase64(texto.getBytes()));

		return sCode64;
	}

	@Override
	public Boolean enviarEmail(EmailConteudo email)
			throws IllegalArgumentException {
		try {
			RotinasEmail.getInstancia().enviarEmail(email);
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public String getBarCodeHTML(BarCodeParams params)
			throws IllegalArgumentException {
		return RotinasBarCode.getInstancia().getBarCodeHTML(params);
	}

	@Override
	public String criarBarChart(BarChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarBarChart(params, arq);
		return arq;
	}

	@Override
	public String criarPieChart(PieChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarPieChart(params, arq);
		return arq;
	}

	@Override
	public String criarTimeSeriesChart(TimeSeriesChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarTimeSeriesChart(params, arq);
		return arq;
	}

	@Override
	public String criarXYLineChart(XYLineChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarXYLineChart(params, arq);
		return arq;
	}

	@Override
	public String criarXYBarChart(XYBarChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarXYBarChart(params, arq);
		return arq;
	}

	@Override
	public String criarHistogramChart(HistogramChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarHistogramChart(params, arq);
		return arq;
	}

	@Override
	public String criarBubbleChart(BubbleChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarBubbleChart(params, arq);
		return arq;
	}
	
	@Override
	public String criarLineChart(LineChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarLineChart(params, arq);
		return arq;
	}
	
	@Override
	public String criarXYAreaChart(XYAreaChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarXYAreaChart(params, arq);
		return arq;
	}
	
	@Override
	public String criarXYStepChart(XYStepChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarXYStepChart(params, arq);
		return arq;
	}

	@Override
	public String criarXYStepAreaChart(XYStepAreaChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarXYStepAreaChart(params, arq);
		return arq;
	}

	@Override
	public String criarAreaChart(AreaChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarAreaChart(params, arq);
		return arq;
	}

	@Override
	public String criarStackedBarChart(StackedBarChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarStackedBarChart(params, arq);
		return arq;
	}

	@Override
	public String criarBoxAndWhiskerChart(BoxAndWhiskerChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarBoxAndWhiskerChart(params, arq);
		return arq;
	}

	@Override
	public String criarCandlestickChart(CandlestickChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarCandlestickChart(params, arq);
		return arq;
	}

	@Override
	public String criarGanttChart(GanttChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarGanttChart(params, arq);
		return arq;
	}

	@Override
	public String criarHighLowChart(HighLowChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarHighLowChart(params, arq);
		return arq;
	}

	@Override
	public String criarScatterChart(ScatterChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarScatterChart(params, arq);
		return arq;
	}

	@Override
	public String criarStackedAreaChart(StackedAreaChartParams params)
			throws IllegalArgumentException {
		String dir = Rotinas.dirTemp;
		String arq = Rotinas.getArquivoRandom(dir, "jpg");
		RotinasChart.getInstancia().criarStackedAreaChart(params, arq);
		return arq;
	}
	
	@Override
	public MXEstrutura carregaMX(String codigoSistema)
			throws IllegalArgumentException {
		String arqMenuXML = Rotinas.dirMenuXML + "/menu.xml";
		return RotinasMX.getInstancia().carregaMX(arqMenuXML, codigoSistema);
	}

	@Override
	public Boolean conectaChat(ChatUsuario usuario)
			throws IllegalArgumentException {
		try {
			chat = RotinasChat.getInstancia().conectaGoogleTalk(usuario);
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public Boolean desconectaChat() throws IllegalArgumentException {
		RotinasChat.getInstancia().desconecta(chat);
		return true;
	}

	@Override
	public Boolean enviarMensagemChat(String usuarioDestino, String mensagem)
			throws IllegalArgumentException {
		try {
			RotinasChat.getInstancia().enviarMensagem(chat, usuarioDestino,
					mensagem);
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public ChatMensagem getMensagemChat() throws IllegalArgumentException {
		return RotinasChat.getInstancia().getMensagem();
	}

	@Override
	public ChatUsuario[] getUsuariosChat() throws IllegalArgumentException {
		return RotinasChat.getInstancia().getUsuarios(chat);
	}

	@Override
	public Boolean conectaFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException {
		try {
			ftp = RotinasFTP.getInstancia().ConectaServidorFTP(
					ftpe.getServidor(), ftpe.getUsuario(), ftpe.getSenha(),
					ftpe.getDiretorio());
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public Boolean desconectaFTP() throws IllegalArgumentException {
		try {
			RotinasFTP.getInstancia().DesconectaServidorFTP(ftp);
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public Boolean enviarArquivoFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException {
		try {
			String dir = Rotinas.dirFTP;
			if (RotinasFTP.getInstancia().Conectado(ftp)) {
				RotinasFTP.getInstancia().enviarArquivo(ftp, dir + "\\",
						ftpe.getArquivo());
				// File file = new File(dir, ftpe.getArquivo());
				// file.delete();
			}
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public Boolean receberArquivoFTP(FTPEstrutura ftpe)
			throws IllegalArgumentException {
		try {
			String dir = Rotinas.dirFTP;
			if (RotinasFTP.getInstancia().Conectado(ftp)) {
				RotinasFTP.getInstancia().receberArquivo(ftp, dir + "\\",
						ftpe.getArquivo());
			}
			return true;
		} catch (ServicoException e) {
			return false;
		}
	}

	@Override
	public FTPArquivo[] listarArquivosFTP(FTPEstrutura ftpe,
			boolean somenteDiretorio) throws IllegalArgumentException {
		FTPArquivo[] retorno = new FTPArquivo[] {};
		try {
			if (RotinasFTP.getInstancia().Conectado(ftp)) {
				retorno = RotinasFTP.getInstancia().listaArquivosFTP(ftp,
						ftpe.getDiretorio(), somenteDiretorio);
			}
			return retorno;
		} catch (ServicoException e) {
			return null;
		}
	}

	@Override
	public Boolean validarLogin(String hostname, String login, String senha)
			throws IllegalArgumentException {
		try {
			return RotinasLogin.getInstancia().validarLogin(hostname, login,
					senha);
		} catch (ServicoException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public Boolean alterarSenha(String hostname, String login, String senha,
			String novaSenha) throws IllegalArgumentException {
		try {
			return RotinasLogin.getInstancia().alterarSenha(hostname, login,
					senha, novaSenha);
		} catch (ServicoException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String getHash(String tipo, String senha)
			throws IllegalArgumentException {
		try {
			return RotinasHash.getInstancia().getHash(tipo, senha);
		} catch (ServicoException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String validaForcaSenha(String senha)
			throws IllegalArgumentException {
		ValidaSenha validaSenha = new ValidaSenha();
		String temp = HFSCrypt.descriptografar(senha, (short) 12345);
		validaSenha.validaForcaSenha(temp);
		return validaSenha.getForcaQualificada().name();
	}

	@Override
	public String iniciarCriptografia(String geradorChave)
			throws IllegalArgumentException {
		try {
			RotinasCriptografia.getInstancia().iniciar(
					GeradorChave.valueOf(geradorChave));
			return "";
		} catch (ServicoException e) {
			return e.getMessage();
		}		
	}

	@Override
	public String encriptar(String texto) throws IllegalArgumentException {
		try {
			return RotinasCriptografia.getInstancia().encriptar(texto);
		} catch (ServicoException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String decriptar(String texto) throws IllegalArgumentException {
		try {
			return RotinasCriptografia.getInstancia().decriptar(texto);
		} catch (ServicoException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public List<PropriedadeSistema> getPropriedadesSistema()
			throws IllegalArgumentException {
		return Rotinas.getPropriedadesSistema();
	}

}
