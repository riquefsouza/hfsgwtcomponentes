package com.hfsgwt.client.componentes.multi;

import com.hfsgwt.client.componentes.HFSItem;

public class HFSMultiItem extends HFSItem {

	private boolean checado;
	private boolean habilitado;
	private int linha;
	private int coluna;

	public HFSMultiItem(String id, String valor, boolean checado,
			boolean habilitado) {
		super(id, valor);
		this.checado = checado;
		this.habilitado = habilitado;
	}

	public boolean isChecado() {
		return checado;
	}

	public void setChecado(boolean checado) {
		this.checado = checado;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public String toString() {
		return super.toString() + ", linha=" + this.linha + ", coluna="
				+ this.coluna + ", checado=" + this.checado + ", habilitado="
				+ this.habilitado;
	}

}
