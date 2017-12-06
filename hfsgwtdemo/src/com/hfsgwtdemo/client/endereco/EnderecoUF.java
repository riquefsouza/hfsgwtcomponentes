package com.hfsgwtdemo.client.endereco;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EnderecoUF implements IsSerializable {

	private int codigo;
	private String sigla;
	private String nome;
	private EnderecoPais pais;

	public EnderecoUF() {
		this.codigo = -1;
		this.sigla = "";
		this.nome = "";
		this.pais = new EnderecoPais();
	}

	public EnderecoUF(int codigo, String sigla, String nome, EnderecoPais pais) {
		this.codigo = codigo;
		this.sigla = sigla;
		this.nome = nome;
		this.pais = pais;
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

	public EnderecoPais getPais() {
		return pais;
	}

	public void setPais(EnderecoPais pais) {
		this.pais = pais;
	}

	public String toString() {
		return "codigo: " + codigo + ", sigla: " + sigla + ", nome: " + nome
				+ ", pais: [" + pais + "]";
	}

}
