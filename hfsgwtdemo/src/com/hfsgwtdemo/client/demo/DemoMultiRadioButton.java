package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.hfsgwt.client.componentes.multi.HFSMultiItem;
import com.hfsgwt.client.componentes.multi.HFSMultiRadioButton;

public class DemoMultiRadioButton extends Composite {
	private Grid grid;
	private HFSMultiRadioButton multiRadioButton;
	private ArrayList<HFSMultiItem> mitems;
	private ListBox listBox;

	public DemoMultiRadioButton() {
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
			grid.setWidget(0, 0, getMultiRadioButton());
			grid.setWidget(1, 0, getListBox());
		}
		return grid;
	}

	private HFSMultiRadioButton getMultiRadioButton() {
		if (multiRadioButton == null) {
			multiRadioButton = new HFSMultiRadioButton();
			multiRadioButton.setRotulo("Check algum item");
			multiRadioButton.setItems(3, mitems);
			multiRadioButton.addMultiClickHandler(new HFSMultiRadioButton.MultiClickHandler() {
				@Override
				public void onClick(HFSMultiItem item) {
					//getListBox().clear();
					getListBox().addItem(item.getValor() + " - " + item.isChecado());
				}
			});
		}
		return multiRadioButton;
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
