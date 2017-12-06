package com.hfsgwt.client.componentes.tabpanel;

import com.google.gwt.user.client.ui.Widget;

public class HFSTabItem {

	private Widget painel;

	private String rotulo;

	private boolean comoHTML;

	public HFSTabItem() {
		super();
	}

	public HFSTabItem(String rotulo, Widget painel, boolean comoHTML) {
		this.rotulo = rotulo;
		this.painel = painel;
		this.comoHTML = comoHTML;
	}

	public Widget getPainel() {
		return painel;
	}

	public void setPainel(Widget painel) {
		this.painel = painel;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public boolean isComoHTML() {
		return comoHTML;
	}

	public void setComoHTML(boolean comoHTML) {
		this.comoHTML = comoHTML;
	}
}
