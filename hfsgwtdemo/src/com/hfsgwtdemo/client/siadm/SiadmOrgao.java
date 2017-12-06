package com.hfsgwtdemo.client.siadm;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SiadmOrgao implements IsSerializable {
	private int codigo;
	private String nome;
	private SiadmTipoOrgao tipo;
	private int codigoOrgaoPai;

	public SiadmOrgao() {
		this.codigo = -1;
		this.nome = "";
		this.tipo = new SiadmTipoOrgao();
		this.codigoOrgaoPai = -1;
	}

	public SiadmOrgao(int codigo, String nome, SiadmTipoOrgao tipo, int codigoOrgaoPai) {
		this.codigo = codigo;
		this.nome = nome;
		this.tipo = tipo;
		this.codigoOrgaoPai = codigoOrgaoPai;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SiadmTipoOrgao getTipo() {
		return tipo;
	}

	public void setTipo(SiadmTipoOrgao tipo) {
		this.tipo = tipo;
	}

	public int getCodigoOrgaoPai() {
		return codigoOrgaoPai;
	}

	public void setCodigoOrgaoPai(int codigoOrgaoPai) {
		this.codigoOrgaoPai = codigoOrgaoPai;
	}

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", tipo: [" + tipo 
		+ "], orgaoPai: [" + codigoOrgaoPai + "]";
	}

}
