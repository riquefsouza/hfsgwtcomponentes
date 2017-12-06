package com.hfsgwtdemo.client.demo;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.hfsgwt.client.componentes.HFSAppendGrid;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSAppendGrid.BtnClickHandler;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;

public class DemoAppendGrid extends Composite {
	private AbsolutePanel absolutePanel;
	private HFSListBox listBox;
	private HFSAppendGrid appendGrid;

	public DemoAppendGrid() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setHeight("328px");
			absolutePanel.add(getListBox(), 6, 6);
			absolutePanel.add(getAppendGrid(), 6, 43);
		}
		return absolutePanel;
	}

	private HFSListBox getListBox() {
		if (listBox == null) {
			listBox = new HFSListBox(PosicaoRotulo.ESQUERDA,
					TipoLista.COMBOBOX, "Local do Corpo:", 200, false, false);
			listBox.addItem(new HFSItem("0","NÃO INFORMADO"));
			listBox.addItem(new HFSItem("3","FACE DIREITA"));
			listBox.addItem(new HFSItem("4","FACE ESQUERDA"));
			listBox.addItem(new HFSItem("5","NUCA"));
			listBox.addItem(new HFSItem("6","OLHO DIREITO"));
			listBox.addItem(new HFSItem("7","OLHO ESQUERDO"));
			listBox.addItem(new HFSItem("8","ORELHA DIREITA"));
			listBox.addItem(new HFSItem("9","ORELHA ESQUERDA"));
			listBox.addItem(new HFSItem("10","QUEIXO"));
		}
		return listBox;
	}

	private HFSAppendGrid getAppendGrid() {
		if (appendGrid == null) {
			appendGrid = new HFSAppendGrid(new String[]{"Local Corpo", "Sinal"});
			appendGrid.setRotulo("Sinais");
			appendGrid.setTamanho("250px", "250px");
			appendGrid.addBtnClickHandler(new BtnClickHandler() {				
				@Override
				public void onBtnIncluirClick() {					
					appendGrid.btnIncItemClick(getListBox().getItemSelecionado());
				}
			});
		}
		return appendGrid;
	}
}
