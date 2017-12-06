package com.hfsgwtdemo.client.siadm;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SiadmSetor implements IsSerializable {
	private int codigo;
	private String nome;
	private SiadmOrgao orgao;

	public SiadmSetor() {
		this.codigo = -1;
		this.nome = "";
		this.orgao = new SiadmOrgao();
	}

	public SiadmSetor(int codigo, String nome, SiadmOrgao orgao) {
		this.codigo = codigo;
		this.nome = nome;
		this.orgao = orgao;
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

	public SiadmOrgao getOrgao() {
		return orgao;
	}

	public void setOrgao(SiadmOrgao orgao) {
		this.orgao = orgao;
	}

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", orgao: [" + orgao
				+ "]";
	}

}
