package com.hfsgwtdemo.client.demo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.multi.HFSMultiCheckBox;
import com.hfsgwt.client.componentes.multi.HFSMultiItem;

import java.util.ArrayList;
import com.google.gwt.user.client.ui.ListBox;

public class DemoMultiCheckBox extends Composite {
	private Grid grid;
	private HFSMultiCheckBox multiCheckBox;
	private ArrayList<HFSMultiItem> mitems;
	private ListBox listBox;

	public DemoMultiCheckBox() {
		mitems = new ArrayList<HFSMultiItem>();
		mitems.add(new HFSMultiItem("0", "item1", false, true));
		mitems.add(new HFSMultiItem("1", "item2", true, true));
		mitems.add(new HFSMultiItem("2", "item3", false, true));
		mitems.add(new HFSMultiItem("3", "item4", true, true));
		mitems.add(new HFSMultiItem("4", "item5", false, true));
		mitems.add(new HFSMultiItem("5", "item6", true, true));
		mitems.add(new HFSMultiItem("6", "item7", true, true));

		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 1);
			grid.setWidget(0, 0, getMultiCheckBox());
			grid.setWidget(1, 0, getListBox());
		}
		return grid;
	}

	private HFSMultiCheckBox getMultiCheckBox() {
		if (multiCheckBox == null) {
			multiCheckBox = new HFSMultiCheckBox();
			multiCheckBox.setRotulo("Check algum item");
			multiCheckBox.setItems(3, mitems);
			multiCheckBox.addMultiClickHandler(new HFSMultiCheckBox.MultiClickHandler() {
				@Override
				public void onClick(HFSMultiItem item) {
					//getListBox().clear();
					getListBox().addItem(item.getValor() + " - " + item.isChecado());
				}
			});
		}
		return multiCheckBox;
	}

	private ListBox getListBox() {
		if (listBox == null) {
			listBox = new ListBox();
			listBox.setWidth("200px");
			listBox.setVisibleItemCount(5);
		}
		return listBox;
	}
}
