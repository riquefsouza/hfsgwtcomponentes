package com.hfsgwt.client.componentes.menuxml;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MXEstrutura implements IsSerializable {
	private MXSistema[] sistemas;

	public MXEstrutura() {
		super();
	}

	public MXEstrutura(MXSistema[] sistemas) {
		this.sistemas = sistemas;
	}

	public MXSistema[] getSistemas() {
		return sistemas;
	}

	public void setSistemas(MXSistema[] sistemas) {
		this.sistemas = sistemas;
	}

	public String getLinkItemPeloCodigo(String codigoSistema,
			String codigoMenu, String codigoItem) {
		MXSistema sismx;
		MXMenu menumx;
		MXItem itemmx;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (menumx.getCodigo().equals(codigoMenu)) {
						for (int k = 0; k < menumx.getItems().length; k++) {
							itemmx = menumx.getItems()[k];
							if (itemmx.getCodigo().equals(codigoItem)) {
								return itemmx.getLink();
							}
						}
					}
				}
			}
		}
		return "";
	}

	public MXItem getItemPeloLabel(String labelSistema, String labelMenu,
			String labelItem) {
		MXSistema sismx;
		MXMenu menumx;
		MXItem itemmx = null;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getLabel().equals(labelSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (menumx.getLabel().equals(labelMenu)) {
						for (int k = 0; k < menumx.getItems().length; k++) {
							itemmx = menumx.getItems()[k];
							if (itemmx.getLabel().equals(labelItem)) {
								return itemmx;
							}
						}
					}
				}
			}
		}
		return itemmx;
	}

	public MXItem getItemPeloCodigo(String codigoSistema, String labelMenu,
			String labelItem) {
		MXSistema sismx;
		MXMenu menumx;
		MXItem itemmx = null;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (menumx.getLabel().equals(labelMenu)) {
						for (int k = 0; k < menumx.getItems().length; k++) {
							itemmx = menumx.getItems()[k];
							if (itemmx.getLabel().equals(labelItem)) {
								return itemmx;
							}
						}
					}
				}
			}
		}
		return itemmx;
	}

	public MXItem getItemPelaOrdem(String codigoSistema, int ordem, int subordem) {
		MXSistema sismx;
		MXMenu menumx;
		MXItem itemmx = null;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (j == ordem) {
						for (int k = 0; k < menumx.getItems().length; k++) {
							itemmx = menumx.getItems()[k];
							if (k == subordem) {
								return itemmx;
							}
						}
					}
				}
			}
		}
		return itemmx;
	}

	public int getOrdemPeloCodigo(String codigoSistema, String codigoMenu) {
		MXSistema sismx;
		MXMenu menumx;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (menumx.getCodigo().equals(codigoMenu)) {
						return j; 
					}
				}
			}
		}		
		return -1;
	}

	public int getSubOrdemPeloCodigo(String codigoSistema, String codigoMenu,
			String codigoItem) {
		MXSistema sismx;
		MXMenu menumx;
		MXItem itemmx;
		for (int i = 0; i < this.sistemas.length; i++) {
			sismx = this.sistemas[i];
			if (sismx.getCodigo().equals(codigoSistema)) {
				for (int j = 0; j < sismx.getMenus().length; j++) {
					menumx = sismx.getMenus()[j];
					if (menumx.getCodigo().equals(codigoMenu)) {
						for (int k = 0; k < menumx.getItems().length; k++) {
							itemmx = menumx.getItems()[k];
							if (itemmx.getCodigo().equals(codigoItem)) {
								return k;
							}
						}
					}
				}
			}
		}		
		return -1;
	}
}
