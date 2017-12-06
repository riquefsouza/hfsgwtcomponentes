package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSLabelTextNumber extends Composite {
	private Label labNumeroExtenso;

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public HFSLabelTextNumber() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getLabNumeroExtenso());
	}

	private Label getLabNumeroExtenso() {
		if (labNumeroExtenso == null) {
			labNumeroExtenso = new Label();
			labNumeroExtenso.setWordWrap(true);
			labNumeroExtenso.setTitle("Número por Extenso");
			labNumeroExtenso.setText("Número por Extenso");
			labNumeroExtenso.setStyleName("HFSLabelTextNumber");
		}
		return labNumeroExtenso;
	}

	private void numeroPorExtenso(double numero) {
		servico.getNumeroExtenso(numero, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "getNumeroExtenso");
			}

			public void onSuccess(String sNumeroExtenso) {
				getLabNumeroExtenso().setText(sNumeroExtenso);
			}
		});
	}

	public void setNumero(double numero) {
		numeroPorExtenso(numero);
	}

	public String getText() {
		return getLabNumeroExtenso().getText();
	}
}
