package com.hfsgwt.client.componentes.stackpanel;

import java.util.ArrayList;

import com.hfsgwt.client.componentes.HFSItem;

public class HFSStackItem {

	private String item;

	private ArrayList<HFSItem> links;

	public HFSStackItem(String item, ArrayList<HFSItem> links) {
		this.item = item;
		this.links = links;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public ArrayList<HFSItem> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<HFSItem> links) {
		this.links = links;
	}

}
