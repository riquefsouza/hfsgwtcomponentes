package com.hfsgwtdemo.client.siadm;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SiadmUsuario implements IsSerializable {
	private String codigo;
	private String nome;
	private SiadmOrgao orgaoOrigem;
	private SiadmOrgao orgaoLotacao;
	private SiadmSetor setor;

	public SiadmUsuario() {
		this.codigo = "";
		this.nome = "";
		this.orgaoOrigem = new SiadmOrgao();
		this.orgaoLotacao = new SiadmOrgao();
		this.setor = new SiadmSetor();
	}

	public SiadmUsuario(String codigo, String nome, SiadmOrgao orgaoOrigem,
			SiadmOrgao orgaoLotacao, SiadmSetor setor) {
		this.codigo = codigo;
		this.nome = nome;
		this.orgaoOrigem = orgaoOrigem;
		this.orgaoLotacao = orgaoLotacao;
		this.setor = setor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SiadmOrgao getOrgaoOrigem() {
		return orgaoOrigem;
	}

	public void setOrgaoOrigem(SiadmOrgao orgaoOrigem) {
		this.orgaoOrigem = orgaoOrigem;
	}

	public SiadmOrgao getOrgaoLotacao() {
		return orgaoLotacao;
	}

	public void setOrgaoLotacao(SiadmOrgao orgaoLotacao) {
		this.orgaoLotacao = orgaoLotacao;
	}

	public SiadmSetor getSetor() {
		return setor;
	}

	public void setSetor(SiadmSetor setor) {
		this.setor = setor;
	}

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", orgaoLotacao: ["
				+ orgaoLotacao + "], orgaoOrigem: [" + orgaoOrigem
				+ "], setor: [" + setor + "]";
	}

}
