package com.hfsgwt.client.componentes;

import com.hfsgwt.client.componentes.util.HFSEquals;

public class HFSItem {

	private String id;
	private String valor;

	public HFSItem() {
		this.id = "";
		this.valor = "";
	}

	public HFSItem(String id, String valor) {
		this.id = id;
		this.valor = valor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String toString() {
		return "id=" + this.id + ", valor=" + this.valor;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HFSItem))
			return false;
		HFSItem objeto = (HFSItem) obj;
		return HFSEquals.areEqual(this.id, objeto.id)
				&& HFSEquals.areEqual(this.valor, objeto.valor);
	}
}
