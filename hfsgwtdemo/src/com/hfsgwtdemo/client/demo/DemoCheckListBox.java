package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.multi.HFSCheckListBox;
import com.hfsgwt.client.componentes.multi.HFSMultiItem;

public class DemoCheckListBox extends Composite {
	private Grid grid;
	private HFSCheckListBox checkList;
	private ArrayList<HFSMultiItem> mitems;
	private ListBox listBox;

	public DemoCheckListBox() {
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
			grid.setWidget(0, 0, getCheckList());
			grid.setWidget(1, 0, getListBox());
		}
		return grid;
	}

	private HFSCheckListBox getCheckList() {
		if (checkList == null) {
			checkList = new HFSCheckListBox(PosicaoRotulo.ESQUERDA, "Check algum item", true);
			checkList.setTamanho("150px", "100px");
			checkList.setItems(mitems);
			checkList.addMultiClickHandler(new HFSCheckListBox.MultiClickHandler() {
				@Override
				public void onClick(HFSMultiItem item) {
					getListBox().addItem(item.getValor() + " - " + item.isChecado());
				}
			});
		}
		return checkList;
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
