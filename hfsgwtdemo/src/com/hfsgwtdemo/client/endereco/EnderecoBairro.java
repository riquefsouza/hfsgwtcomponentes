package com.hfsgwtdemo.client.endereco;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EnderecoBairro implements IsSerializable {

	private int codigo;
	private String nome;
	private EnderecoMunicipio municipio;

	public EnderecoBairro() {
		this.codigo = -1;
		this.nome = "";
		this.municipio = new EnderecoMunicipio();
	}

	public EnderecoBairro(int codigo, String nome, EnderecoMunicipio municipio) {
		this.codigo = codigo;
		this.nome = nome;
		this.municipio = municipio;
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

	public EnderecoMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(EnderecoMunicipio municipio) {
		this.municipio = municipio;
	}

	public String toString() {
		return "codigo: " + codigo + ", nome: " + nome + ", municipio: ["
				+ municipio + "]";
	}

}
