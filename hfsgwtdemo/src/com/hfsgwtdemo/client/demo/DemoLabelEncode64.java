package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.hfsgwt.client.componentes.HFSLabelEncode64;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoLabelEncode64 extends Composite {
	private AbsolutePanel absolutePanel;
	private HFSTextBox edtCodificar;
	private Button btnCodificar;
	private HFSLabelEncode64 labCode64Codificar;
	private Button btnDecodificar;
	private HFSLabelEncode64 labelCode64Decodificar;

	public DemoLabelEncode64() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setSize("499px", "214px");
			absolutePanel.add(getEdtCodificar(), 10, 10);
			absolutePanel.add(getBtnCodificar(), 10, 56);
			absolutePanel.add(getLabCode64Codificar(), 10, 90);
			absolutePanel.add(getBtnDecodificar(), 10, 132);
			absolutePanel.add(getLabCode64Decodificar(), 10, 166);
		}
		return absolutePanel;
	}

	private HFSTextBox getEdtCodificar() {
		if (edtCodificar == null) {
			edtCodificar = new HFSTextBox(PosicaoRotulo.ACIMA, "Codificar",
					Formato.PADRAO, 30, 30, false);
			edtCodificar.setTexto("codificar este texto");
		}
		return edtCodificar;
	}

	private Button getBtnCodificar() {
		if (btnCodificar == null) {
			btnCodificar = new Button("codificar");
			btnCodificar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getLabCode64Codificar().codificar(
							getEdtCodificar().getTexto());
				}
			});
			btnCodificar.setSize("92px", "28px");
		}
		return btnCodificar;
	}

	private HFSLabelEncode64 getLabCode64Codificar() {
		if (labCode64Codificar == null) {
			labCode64Codificar = new HFSLabelEncode64();
			labCode64Codificar.setSize("470px", "32px");
		}
		return labCode64Codificar;
	}

	private Button getBtnDecodificar() {
		if (btnDecodificar == null) {
			btnDecodificar = new Button("decodificar");
			btnDecodificar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getLabCode64Decodificar().decodificar(
							labCode64Codificar.getText());
				}
			});
			btnDecodificar.setSize("92px", "28px");
		}
		return btnDecodificar;
	}

	private HFSLabelEncode64 getLabCode64Decodificar() {
		if (labelCode64Decodificar == null) {
			labelCode64Decodificar = new HFSLabelEncode64();
			labelCode64Decodificar.setSize("466px", "28px");
		}
		return labelCode64Decodificar;
	}
}
