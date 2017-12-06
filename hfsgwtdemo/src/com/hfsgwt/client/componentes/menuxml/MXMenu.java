package com.hfsgwt.client.componentes.menuxml;

public class MXMenu extends MXItem {
	private MXItem[] items;

	public MXMenu() {
		super();
	}

	public MXMenu(int ordem, String codigo, String label, MXItem[] items) {
		this.setOrdem(ordem);
		this.setCodigo(codigo);
		this.setLabel(label);
		this.items = items;
	}
	
	public MXItem[] getItems() {
		return items;
	}

	public void setItems(MXItem[] items) {
		this.items = items;
	}
		
}
