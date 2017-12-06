package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;

public class DemoListBox extends Composite {
	private Grid grid;
	private HFSListBox listBox1;
	private ArrayList<HFSItem> listbox_items;
	private HFSListBox listBox2;
	private Label label1;
	private Label label2;

	public DemoListBox() {
		listbox_items = new ArrayList<HFSItem>();
		listbox_items.add(new HFSItem("1", "lista item 1"));
		listbox_items.add(new HFSItem("2", "lista item 2"));
		listbox_items.add(new HFSItem("3", "lista item 3"));
		listbox_items.add(new HFSItem("4", "lista item 4"));
		listbox_items.add(new HFSItem("5", "lista item 5"));

		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 2);
			grid.setCellSpacing(2);
			grid.setCellPadding(2);
			grid.setWidget(0, 0, getListBox1());
			grid.setWidget(0, 1, getListBox2());
			grid.setWidget(1, 0, getLabel1());
			grid.setWidget(1, 1, getLabel2());
		}
		return grid;
	}

	private HFSListBox getListBox1() {
		if (listBox1 == null) {
			listBox1 = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.LISTBOX,
					"ListBox", 150, false, false);
			listBox1.setItems(listbox_items);
			getLabel1().setText(listBox1.getItemSelecionado().getId());
			
			listBox1.getListBox().addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {					
					getLabel1().setText("código: "+listBox1.getItemSelecionado().getId());
				}
			});
//			listBox1.getListBox().addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					getLabel1().setText(listBox1.getItemSelecionado().getId());
//				}
//			});
		}
		return listBox1;
	}

	private HFSListBox getListBox2() {
		if (listBox2 == null) {
			listBox2 = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.COMBOBOX,
					"ComboBox", 150, false, false);
			listBox2.setItems(listbox_items);
			getLabel2().setText(listBox2.getItemSelecionado().getId());
//			listBox2.getListBox().addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					getLabel2().setText(listBox2.getItemSelecionado().getId());					
//				}
//			});
			listBox2.getListBox().addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					getLabel2().setText("código: "+listBox2.getItemSelecionado().getId());
				}
			});
		}
		return listBox2;
	}
	private Label getLabel1() {
		if (label1 == null) {
			label1 = new Label("codigo1");
		}
		return label1;
	}
	private Label getLabel2() {
		if (label2 == null) {
			label2 = new Label("codigo2");
		}
		return label2;
	}
}
