package com.hfsgwtdemo.client.endereco;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EnderecoMunicipio implements IsSerializable {

	private int codigo;
	private String nome;
	private EnderecoUF UF;
	private String baseCartografica;

	public EnderecoMunicipio() {
		this.codigo = -1;
		this.nome = "";
		this.UF = new EnderecoUF();
		this.baseCartografica = "";
	}

	public EnderecoMunicipio(int codigo, String nome, EnderecoUF UF,
			String baseCartografica) {
		this.codigo = codigo;
		this.nome = nome;
		this.UF = UF;
		this.baseCartografica = baseCartografica;
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

	public String getBaseCartografica() {
		return baseCartografica;
	}

	public void setBaseCartografica(String baseCartografica) {
		this.baseCartografica = baseCartografica;
	}

	public EnderecoUF getUF() {
		return UF;
	}

	public void setUF(EnderecoUF uF) {
		UF = uF;
	}

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", UF: [" + UF
				+ "], baseCartografica: " + baseCartografica;
	}

}
