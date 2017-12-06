package com.hfsgwt.server.xml.menuxml;

import com.hfsgwt.server.xml.BaseXmlObject;

public class MenuXmlObject extends BaseXmlObject {
	private SistemaTagList[] sistemas;

	public MenuXmlObject() {
		this.setApelidos(new String[] { "menuxml", "sistema", "menu", "item" });
		this.setClasses(new Class[] { MenuXmlObject.class,
				SistemaTagList.class, MenuTagList.class, ItemTagList.class });
		this.setClassesAtributo(new Class[] { SistemaTagList.class,
				SistemaTagList.class, SistemaTagList.class, MenuTagList.class,
				MenuTagList.class, MenuTagList.class, ItemTagList.class,
				ItemTagList.class, ItemTagList.class, ItemTagList.class, });
		this
				.setCamposAtributo(new String[] { "ordem", "codigo", "label",
						"ordem", "codigo", "label", "ordem", "codigo", "label",
						"link" });
		this
				.setApelidosAtributo(new String[] { "ordem", "codigo", "label",
						"ordem", "codigo", "label", "ordem", "codigo", "label",
						"link" });
	}

	public SistemaTagList[] getSistemas() {
		return sistemas;
	}

	public void setObjetosdao(SistemaTagList[] sistemas) {
		this.sistemas = sistemas;
	}

	public String getLabelMenu(String codigoSistema, String codigoMenu) {
		SistemaTagList sistl;
		MenuTagList menutl;
		for (int i = 0; i < this.sistemas.length; i++) {
			sistl = this.sistemas[i];
			if (sistl.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sistl.getMenus().length; j++) {
					menutl = sistl.getMenus()[j];
					if (menutl.getCodigo().equals(codigoMenu)) {
						return menutl.getLabel();
					}
				}
			}
		}
		return "";
	}

	public String getLinkItem(String codigoSistema, String codigoMenu,
			String codigoItem) {
		SistemaTagList sistl;
		MenuTagList menutl;
		ItemTagList itemtl;
		for (int i = 0; i < this.sistemas.length; i++) {
			sistl = this.sistemas[i];
			if (sistl.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sistl.getMenus().length; j++) {
					menutl = sistl.getMenus()[j];
					if (menutl.getCodigo().equals(codigoMenu)) {
						for (int k = 0; k < menutl.getItems().length; k++) {
							itemtl = menutl.getItems()[k];
							if (itemtl.getCodigo().equals(codigoItem)) {
								return itemtl.getLink();
							}
						}
					}
				}
			}
		}
		return "";
	}

}
