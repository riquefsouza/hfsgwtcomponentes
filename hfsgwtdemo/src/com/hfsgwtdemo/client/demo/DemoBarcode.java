package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.barcode.BarCodeParams;
import com.hfsgwt.client.componentes.barcode.HFSBarCode;

public class DemoBarcode extends Composite {
	private Grid grid;
	private ListBox comboBox;
	private HFSTextBox textBox;
	private Button btnGerar;
	private HFSBarCode barCode;
	private BarCodeParams params;

	public DemoBarcode() {
		params = new BarCodeParams();
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(4, 1);
			grid.setCellSpacing(5);
			grid.setCellPadding(5);
			grid.setWidget(0, 0, getComboBox());
			grid.setWidget(1, 0, getTextBox());
			grid.setWidget(2, 0, getBtnGerar());
			grid.setWidget(3, 0, getBarCode());
		}
		return grid;
	}

	private ListBox getComboBox() {
		if (comboBox == null) {
			comboBox = new ListBox();
			comboBox.addItem("CODABAR");
			comboBox.addItem("CODE39");
			comboBox.addItem("POSTNET");
			comboBox.addItem("INTL2OF5");
			comboBox.addItem("EAN_128");
			comboBox.addItem("ROYAL_MAIL_CBC");
			comboBox.addItem("EAN_13");
			comboBox.addItem("DATAMATRIX");
			comboBox.addItem("CODE128");
			comboBox.addItem("EAN128");
			comboBox.addItem("PDF417");
			comboBox.addItem("UPC_A");
			comboBox.addItem("UPC_E");
			comboBox.addItem("USPS4CB");
			comboBox.addItem("EAN_8");
		}
		return comboBox;
	}

	private HFSTextBox getTextBox() {
		if (textBox == null) {
			textBox = new HFSTextBox(PosicaoRotulo.ACIMA, "Numero",
					Formato.NUMERO, 10, 10, false);
			textBox.setNumero(123456L);
		}
		return textBox;
	}

	private Button getBtnGerar() {
		if (btnGerar == null) {
			btnGerar = new Button("Gerar Código de Barras");
			btnGerar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					params.setFormatoImagem(BarCodeParams.FormatoImagem.JPEG);					
					String tipo = getComboBox().getValue(getComboBox().getSelectedIndex());
					params.setTipoBarCode(BarCodeParams.TipoBarCode.valueOf(tipo));					
					params.setMensagem(getTextBox().getTexto());
					barCode.mostrarBarCode(params);
				}
			});
			btnGerar.setWidth("170px");
		}
		return btnGerar;
	}

	private HFSBarCode getBarCode() {
		if (barCode == null) {
			barCode = new HFSBarCode();
		}
		return barCode;
	}
}
