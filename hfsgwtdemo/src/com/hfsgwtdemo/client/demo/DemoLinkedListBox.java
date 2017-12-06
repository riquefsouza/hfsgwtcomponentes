package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.linkedlist.HFSLinkedItem;
import com.hfsgwt.client.componentes.linkedlist.HFSLinkedListBox;
import com.hfsgwt.client.componentes.linkedlist.HFSLinkedListBox.TipoLista;

public class DemoLinkedListBox extends Composite {

	private ArrayList<HFSLinkedItem> lItems;
	private Grid grid;
	private HFSLinkedListBox linkedListBox1;
	private HFSLinkedListBox linkedListBox2;

	public DemoLinkedListBox() {
		initComponents();
	}

	private void initComponents() {
		lItems = new ArrayList<HFSLinkedItem>();
		ArrayList<HFSItem> subItems = new ArrayList<HFSItem>();
		subItems.add(new HFSItem("11", "subitem11"));
		subItems.add(new HFSItem("12", "subitem12"));
		subItems.add(new HFSItem("13", "subitem13"));
		subItems.add(new HFSItem("14", "subitem14"));
		subItems.add(new HFSItem("15", "subitem15"));
		lItems.add(new HFSLinkedItem(new HFSItem("1", "item1"), subItems));

		ArrayList<HFSItem> subItems2 = new ArrayList<HFSItem>();
		subItems2.add(new HFSItem("21", "subitem21"));
		subItems2.add(new HFSItem("22", "subitem22"));
		subItems2.add(new HFSItem("23", "subitem23"));
		subItems2.add(new HFSItem("24", "subitem24"));
		subItems2.add(new HFSItem("25", "subitem25"));
		lItems.add(new HFSLinkedItem(new HFSItem("2", "item2"), subItems2));
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 1);
			grid.setCellSpacing(5);
			grid.setCellPadding(5);
			grid.setWidget(0, 0, getLinkedListBox1());
			grid.setWidget(1, 0, getLinkedListBox2());
		}
		return grid;
	}

	private HFSLinkedListBox getLinkedListBox1() {
		if (linkedListBox1 == null) {
			linkedListBox1 = new HFSLinkedListBox(PosicaoRotulo.ACIMA,
					TipoLista.LISTBOX, "Items", "SubItems", 150);
			linkedListBox1.setLinkedItems(lItems);
		}		
		return linkedListBox1;
	}

	private HFSLinkedListBox getLinkedListBox2() {
		if (linkedListBox2 == null) {
			linkedListBox2 = new HFSLinkedListBox(PosicaoRotulo.ACIMA,
					TipoLista.COMBOBOX, "Items", "SubItems", 150);
			linkedListBox2.setLinkedItems(lItems);
		}
		return linkedListBox2;
	}
}
