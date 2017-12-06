package com.hfsgwt.client.componentes.menuxml;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MXItem implements IsSerializable {

	private int ordem;

	private String codigo;

	private String label;

	private String link;

	public MXItem() {
		super();
	}

	public MXItem(int ordem, String codigo, String label, String link) {
		this.ordem = ordem;
		this.codigo = codigo;
		this.label = label;
		this.link = link;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
