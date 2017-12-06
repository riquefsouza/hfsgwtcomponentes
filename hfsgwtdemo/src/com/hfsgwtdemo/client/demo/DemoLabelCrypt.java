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
import com.hfsgwt.client.componentes.HFSLabelCrypt;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoLabelCrypt extends Composite {

	private AbsolutePanel absolutePanel;
	private HFSTextBox edtCodificar;
	private Button btnCriptografa;
	private Button btnDescriptografa;
	private HFSLabelCrypt labelCrypt;
//	private HFSLabelCrypt labelDecrypt;
	private HFSListBox listBox;
	private HFSLabelCrypt.GeradorChave gerador;

	public DemoLabelCrypt() {
		gerador = HFSLabelCrypt.GeradorChave.AES;
		initComponents();
		
		getLabelCrypt().iniciarCriptografia(gerador);		
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setSize("523px", "321px");
			absolutePanel.add(getListBox(), 10, 6);
			absolutePanel.add(getEdtCodificar(), 10, 54);
			absolutePanel.add(getBtnCriptografa(), 10, 116);
			absolutePanel.add(getBtnDescriptografa(), 150, 116);
			absolutePanel.add(getLabelCrypt(), 10, 150);
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

	private Button getBtnCriptografa() {
		if (btnCriptografa == null) {
			btnCriptografa = new Button("Encripta");
			btnCriptografa.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getLabelCrypt().encriptar(getEdtCodificar().getTexto());
				}
			});
			btnCriptografa.setSize("92px", "28px");
		}
		return btnCriptografa;
	}

	private Button getBtnDescriptografa() {
		if (btnDescriptografa == null) {
			btnDescriptografa = new Button("Decripta");
			btnDescriptografa.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {					
					getLabelCrypt().decriptar(getLabelCrypt().getText());
				}
			});
			btnDescriptografa.setSize("92px", "28px");
		}
		return btnDescriptografa;
	}
	
	private HFSLabelCrypt getLabelCrypt() {
		if (labelCrypt == null) {
			labelCrypt = new HFSLabelCrypt();
			labelCrypt.setSize("492px", "149px");
		}
		return labelCrypt;
	}

//	private HFSLabelCrypt getLabelDecrypt() {
//		if (labelDecrypt == null) {
//			labelDecrypt = new HFSLabelCrypt();
//			labelDecrypt.setSize("486px", "30px");
//		}
//		return labelDecrypt;
//	}

	private HFSListBox getListBox() {
		if (listBox == null) {
			ArrayList<HFSItem> items = new ArrayList<HFSItem>();
			int i = 1;
			for (HFSLabelCrypt.GeradorChave item : HFSLabelCrypt.GeradorChave.values()) {
				items.add(new HFSItem(i+"", item.name()));
				i++;
			}
			listBox = new HFSListBox(HFSListBox.PosicaoRotulo.ACIMA,
					TipoLista.COMBOBOX, "Tipo Crypt:", 100, false, false);
			listBox.setItems(items);
			listBox.getListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					gerador = HFSLabelCrypt.GeradorChave.valueOf(listBox
							.getItemSelecionado().getValor());
					getLabelCrypt().iniciarCriptografia(gerador);
				}
			});
		}
		return listBox;
	}
	
}

