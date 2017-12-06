package com.hfsgwt.client.componentes.ftp;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FTPArquivo implements IsSerializable {

	public enum Tipo {
		ARQUIVO, DIRETORIO, LINKSIMBOLICO
	}
	
	private String nome;
	private long tamanho;
	private Tipo tipo;
	private Date data;

	public FTPArquivo() {
		this.nome = "";
		this.tamanho = 0;
		this.tipo = Tipo.ARQUIVO;
		this.data = new Date();		
	}

	public FTPArquivo(String nome, long tamanho, Tipo tipo, Date data) {
		this.nome = nome;
		this.tamanho = tamanho;
		this.tipo = tipo;
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
