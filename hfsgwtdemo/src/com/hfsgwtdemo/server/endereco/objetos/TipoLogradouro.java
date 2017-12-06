package com.hfsgwtdemo.server.endereco.objetos;

public class TipoLogradouro {
	private int codigo;
	private String nome;

	public TipoLogradouro() {
		this.codigo = -1;
		this.nome = "";
	}

	public TipoLogradouro(int codigo, String nome) {
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
