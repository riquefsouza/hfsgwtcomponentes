package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSLabelHash;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoLabelHash extends Composite {

	private AbsolutePanel absolutePanel;
	private HFSTextBox edtCodificar;
	private Button btnHash;
	private HFSLabelHash labelHash;
	private HFSListBox listBox;
	private HFSLabelHash.TipoDigest tipo;

	public DemoLabelHash() {
		tipo = HFSLabelHash.TipoDigest.MD2;
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setSize("523px", "321px");
			absolutePanel.add(getListBox(), 10, 6);
			absolutePanel.add(getEdtCodificar(), 10, 58);
			absolutePanel.add(getBtnHash(), 10, 106);
			absolutePanel.add(getLabelHash(), 10, 140);
		}
		return absolutePanel;
	}

	private HFSTextBox getEdtCodificar() {
		if (edtCodificar == null) {
			edtCodificar = new HFSTextBox(PosicaoRotulo.ACIMA, "Texto",
					Formato.PADRAO, 50, 50, false);
			edtCodificar.setTexto("texto");
		}
		return edtCodificar;
	}

	private Button getBtnHash() {
		if (btnHash == null) {
			btnHash = new Button("Gerar Hash");
			btnHash.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getLabelHash().getHash(tipo, getEdtCodificar().getTexto());
				}
			});
			btnHash.setSize("92px", "28px");
		}
		return btnHash;
	}

	private HFSLabelHash getLabelHash() {
		if (labelHash == null) {
			labelHash = new HFSLabelHash();
			labelHash.setSize("485px", "146px");
		}
		return labelHash;
	}

	private HFSListBox getListBox() {
		if (listBox == null) {
			ArrayList<HFSItem> items = new ArrayList<HFSItem>();
			
			int i = 1;
			for (HFSLabelHash.TipoDigest item : HFSLabelHash.TipoDigest.values()) {
				items.add(new HFSItem(i+"", item.name()));
				i++;
			}
			listBox = new HFSListBox(HFSListBox.PosicaoRotulo.ACIMA,
					TipoLista.COMBOBOX, "Tipo Hash:", 100, false, false);
			listBox.setItems(items);
			listBox.getListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					tipo = HFSLabelHash.TipoDigest.valueOf(listBox
							.getItemSelecionado().getValor());
				}
			});
		}
		return listBox;
	}
}
