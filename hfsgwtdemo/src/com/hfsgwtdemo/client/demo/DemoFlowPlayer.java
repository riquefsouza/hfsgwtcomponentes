package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.hfsgwt.client.componentes.HFSFlowPlayer;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoFlowPlayer extends Composite {
	private Grid grid;
	private HFSTextBox textBox;
	private Button btnTocar;
	private HFSFlowPlayer flowPlayer;

	public DemoFlowPlayer() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(3, 1);
			grid.setWidget(0, 0, getTextBox());
			grid.setWidget(1, 0, getBtnTocar());
			grid.getCellFormatter().setHorizontalAlignment(1, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.setWidget(2, 0, getFlowPlayer());
		}
		return grid;
	}

	private HFSTextBox getTextBox() {
		if (textBox == null) {
			textBox = new HFSTextBox(PosicaoRotulo.ACIMA, "Vídeo:",
					Formato.PADRAO, 100, 70, false);
			textBox
					.setTexto("http://e1h13.simplecdn.net/flowplayer/flowplayer.flv");
		}
		return textBox;
	}

	private Button getBtnTocar() {
		if (btnTocar == null) {
			btnTocar = new Button("Tocar Vídeo");
			btnTocar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (getTextBox().getTexto().trim().length() > 0)
						flowPlayer.mostrarPlayer(getTextBox().getTexto());
				}
			});
		}
		return btnTocar;
	}

	private HFSFlowPlayer getFlowPlayer() {
		if (flowPlayer == null) {
			flowPlayer = new HFSFlowPlayer(520, 330);
		}
		return flowPlayer;
	}
}
