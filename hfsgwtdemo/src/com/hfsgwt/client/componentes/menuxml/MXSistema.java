package com.hfsgwt.client.componentes.menuxml;

public class MXSistema extends MXItem {
	private MXMenu[] menus;

	public MXSistema() {
		super();
	}

	public MXSistema(int ordem, String codigo, String label, MXMenu[] menus) {
		this.setOrdem(ordem);
		this.setCodigo(codigo);
		this.setLabel(label);
		this.menus = menus;
	}

	public MXMenu[] getMenus() {
		return menus;
	}

	public void setMenus(MXMenu[] menus) {
		this.menus = menus;
	}
}
