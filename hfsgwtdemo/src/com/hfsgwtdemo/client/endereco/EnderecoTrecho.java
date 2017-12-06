package com.hfsgwtdemo.client.endereco;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EnderecoTrecho implements IsSerializable {

	private String bairro;
	private int codigoLogradouro;
	private String logradouro;
	private String numeracao;
	private String CEP;
	private int codigoTrecho;

	public EnderecoTrecho() {
		super();
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getCodigoLogradouro() {
		return codigoLogradouro;
	}

	public void setCodigoLogradouro(int codigoLogradouro) {
		this.codigoLogradouro = codigoLogradouro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeracao() {
		return numeracao;
	}

	public void setNumeracao(String numeracao) {
		this.numeracao = numeracao;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cep) {
		CEP = cep;
	}

	public int getCodigoTrecho() {
		return codigoTrecho;
	}

	public void setCodigoTrecho(int codigoTrecho) {
		this.codigoTrecho = codigoTrecho;
	}

}
