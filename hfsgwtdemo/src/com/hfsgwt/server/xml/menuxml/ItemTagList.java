package com.hfsgwt.server.xml.menuxml;

import com.hfsgwt.server.xml.IBaseTagList;

public class ItemTagList implements IBaseTagList {
	private static final long serialVersionUID = 1L;

	private int ordem;

	private String codigo;

	private String label;
	
	private String link;

	public ItemTagList() {
		this.ordem = -1;
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
