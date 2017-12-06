package com.hfsgwtdemo.client.endereco;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EnderecoPais implements IsSerializable {

	private int codigo;
	private String sigla;
	private String nome;
	private String nacionalidade;

	public EnderecoPais() {
		this.codigo = -1;
		this.sigla = "";
		this.nome = "";
		this.nacionalidade = "";
	}

	public EnderecoPais(int codigo, String sigla, String nome,
			String nacionalidade) {
		this.codigo = codigo;
		this.sigla = sigla;
		this.nome = nome;
		this.nacionalidade = nacionalidade;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String toString() {
		return "codigo: " + codigo + ", sigla: " + sigla + ", nome: " + nome
				+ ", nacionalidade: " + nacionalidade;
	}
}
