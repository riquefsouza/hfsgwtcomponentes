package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.hfsgwt.client.componentes.HFSLabelTextNumber;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoLabelTextNumber extends Composite {
	private AbsolutePanel absolutePanel;
	private HFSTextBox edtCodificar;
	private Button btnExtenso;
	private HFSLabelTextNumber labelTextNumber;

	public DemoLabelTextNumber() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setSize("523px", "321px");
			absolutePanel.add(getEdtCodificar(), 10, 10);
			absolutePanel.add(getBtnExtenso(), 10, 56);
			absolutePanel.add(getLabelTextNumber(), 10, 90);
		}
		return absolutePanel;
	}

	private HFSTextBox getEdtCodificar() {
		if (edtCodificar == null) {
			edtCodificar = new HFSTextBox(PosicaoRotulo.ACIMA, "Número",
					Formato.DECIMAL, 10, 10, false);
			edtCodificar.setTexto("1698,34");
		}
		return edtCodificar;
	}

	private Button getBtnExtenso() {
		if (btnExtenso == null) {
			btnExtenso = new Button("Por Extenso");
			btnExtenso.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getLabelTextNumber().setNumero(
							getEdtCodificar().getDecimal());
				}
			});
			btnExtenso.setSize("92px", "28px");
		}
		return btnExtenso;
	}

	private HFSLabelTextNumber getLabelTextNumber() {
		if (labelTextNumber == null) {
			labelTextNumber = new HFSLabelTextNumber();
			labelTextNumber.setSize("404px", "208px");
		}
		return labelTextNumber;
	}
}
