package com.hfsgwt.client.componentes.linkedlist;

import java.util.ArrayList;

import com.hfsgwt.client.componentes.HFSItem;

public class HFSLinkedItem {

	private HFSItem item;
	
	private ArrayList<HFSItem> subItems;
	
	public HFSLinkedItem(HFSItem item, ArrayList<HFSItem> subItems){
		this.item = item;
		this.subItems = subItems;
	}

	public HFSItem getItem() {
		return item;
	}

	public void setItem(HFSItem item) {
		this.item = item;
	}

	public ArrayList<HFSItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(ArrayList<HFSItem> subItems) {
		this.subItems = subItems;
	}

}
