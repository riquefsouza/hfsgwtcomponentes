package com.hfsgwtdemo.client.siadm;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SiadmTipoOrgao implements IsSerializable {

	private int codigo;
	private String nome;

	public SiadmTipoOrgao() {
		this.codigo = -1;
		this.nome = "";
	}

	public SiadmTipoOrgao(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
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

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome;
	}
	
}
