package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSLabelEncode64 extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private Label labCode64;

	public HFSLabelEncode64() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getLabCode64());
	}

	private Label getLabCode64() {
		if (labCode64 == null) {
			labCode64 = new Label();
			labCode64.setWordWrap(true);
			// labCode64.setWidth(largura+"px");
			labCode64.setTitle("Codifica em base64");
			labCode64.setText("Codifica em base64");
			labCode64.setStyleName("HFSLabelEncode64");
		}
		return labCode64;
	}

	private void codificacaoBase64(String texto, boolean encode64) {
		servico.getCodeBase64(texto, encode64, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "getCodeBase64");
			}

			public void onSuccess(String sCode64) {
				getLabCode64().setText(sCode64);
			}
		});
	}

	public void codificar(String texto) {
		codificacaoBase64(texto, true);
	}

	public void decodificar(String texto) {
		codificacaoBase64(texto, false);
	}
	
	public String getText() {
		return getLabCode64().getText();
	}
	
}
