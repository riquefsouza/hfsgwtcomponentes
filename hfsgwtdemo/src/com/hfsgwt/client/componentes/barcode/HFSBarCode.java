package com.hfsgwt.client.componentes.barcode;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSBarCode extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private HTML panel;

	public HFSBarCode() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getPanel());
	}

	private HTML getPanel() {
		if (panel == null) {
			panel = new HTML("Carregando Código de Barras");
		}
		return panel;
	}

	public void mostrarBarCode(BarCodeParams params) {
		servico.getBarCodeHTML(params, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(),
						"getBarCodeHTML");						
			}

			public void onSuccess(String resultado) {
				getPanel().setHTML(resultado);
			}
		});
	}

}
