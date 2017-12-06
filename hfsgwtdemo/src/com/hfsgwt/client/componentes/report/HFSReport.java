package com.hfsgwt.client.componentes.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

public class HFSReport extends Composite {

	public enum OpcaoGeracao {
		PADRAO, ARQUIVO
	}

	public enum FormatoArquivo {
		PDF, HTML, XLS, RTF
	}

	private String largura;
	private String altura;
	private String url;
	private Frame frame;

	public HFSReport(String largura, String altura, String nomeRelatorio,
			OpcaoGeracao opcao, FormatoArquivo formato, boolean compactado) {
		this.largura = largura;
		this.altura = altura;
		this.url = GWT.getHostPageBaseURL() + "relatorio?opcao=" + opcao
				+ "&nome=" + nomeRelatorio + "&formato=" + formato
				+ "&compactado=" + compactado;

		initComponents();
	}

	private void initComponents() {
		initWidget(getFrame());
	}

	private Frame getFrame() {
		if (frame == null) {
			frame = new Frame();
			frame.setSize(this.largura, this.altura);
		}
		return frame;
	}

	public void mostrarRelatorio() {
		this.getFrame().setUrl(this.url);
		//Window.open(this.url, "Relatorio", "");
	}

	public String getUrl() {
		return url;
	}

}
