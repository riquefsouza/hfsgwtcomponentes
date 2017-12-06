package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSLabelCrypt extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public enum GeradorChave {
		AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4, Rijndael, TripleDES
	}

	private Label labCrypt;

	public HFSLabelCrypt() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getLabCrypt());
	}

	private Label getLabCrypt() {
		if (labCrypt == null) {
			labCrypt = new Label();
			labCrypt.setWordWrap(true);
			// labCrypt.setWidth(largura+"px");
			labCrypt.setTitle("Criptografa");
			labCrypt.setText("Criptografa");
			labCrypt.setStyleName("HFSLabelCrypt");
		}
		return labCrypt;
	}

	public void iniciarCriptografia(GeradorChave geradorChave) {
		servico.iniciarCriptografia(geradorChave.name(),
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"iniciarCriptografia");
					}

					public void onSuccess(String retorno) {
						getLabCrypt().setText(retorno);
					}
				});
	}

	public void encriptar(String texto) {
		servico.encriptar(texto, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "encriptar");
			}

			public void onSuccess(String retorno) {
				getLabCrypt().setText(retorno);
			}
		});
	}

	public void decriptar(String texto) {
		servico.decriptar(texto, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "decriptar");
			}

			public void onSuccess(String retorno) {
				getLabCrypt().setText(retorno);
			}
		});
	}

	public String getText() {
		return getLabCrypt().getText();
	}
}
