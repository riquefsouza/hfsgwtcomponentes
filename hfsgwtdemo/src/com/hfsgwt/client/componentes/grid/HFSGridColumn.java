package com.hfsgwt.client.componentes.grid;


public class HFSGridColumn {
	private String titulo;
	private int largura;
	private boolean larguraAutomatica;
	private boolean permitirOrdenacao;

	public HFSGridColumn() {
		this.titulo = "Coluna";
		this.largura = 100;
		this.larguraAutomatica = false;
		this.permitirOrdenacao = true;
	}

	public HFSGridColumn(String titulo, int largura, 
			boolean larguraAutomatica, boolean permitirOrdenacao) {
		this.titulo = titulo;
		this.largura = largura;
		this.larguraAutomatica = larguraAutomatica;
		this.permitirOrdenacao = permitirOrdenacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public boolean isLarguraAutomatica() {
		return larguraAutomatica;
	}

	public void setLarguraAutomatica(boolean larguraAutomatica) {
		this.larguraAutomatica = larguraAutomatica;
	}

	public boolean isPermitirOrdenacao() {
		return permitirOrdenacao;
	}

	public void setPermitirOrdenacao(boolean permitirOrdenacao) {
		this.permitirOrdenacao = permitirOrdenacao;
	}

}
