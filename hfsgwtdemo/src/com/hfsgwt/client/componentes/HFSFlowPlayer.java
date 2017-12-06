package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

public class HFSFlowPlayer extends Composite {

	private int largura;
	private int altura;
	private Frame frame;

	public HFSFlowPlayer(int largura, int altura) {
		if (largura < 300)
			this.largura = 300;
		else
			this.largura = largura;
		if (altura < 200)
			this.altura = 200;
		else
			this.altura = altura;
		initComponents();
	}

	private void initComponents() {
		initWidget(getFrame());
	}

	private Frame getFrame() {
		if (frame == null) {
			frame = new Frame();
			frame.setStyleName("HFSFlowPlayer");
			frame.setSize(this.largura + "px", this.altura + "px");
		}
		return frame;
	}

	public void mostrarPlayer(String urlVideo) {
		String url = GWT.getModuleBaseURL() + "flowplayer.html?largura="
				+ (this.largura - 40) + "px&altura=" + (this.altura - 40)
				+ "px&video=" + urlVideo;
		this.getFrame().setUrl(url);
	}
}
