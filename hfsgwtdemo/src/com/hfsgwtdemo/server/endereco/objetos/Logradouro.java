package com.hfsgwtdemo.server.endereco.objetos;

import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;

public class Logradouro {
	private int codigo;
	private String nome;
	private TipoLogradouro tipoLogradouro;
	private TituloLogradouro tituloLogradouro;
	private String apelido;
	private String situacao;
	private TipoPreposicao tipoPreposicao;
	private String KM;
	private String fonetico;
	private String apelidoFonetico;
	private EnderecoMunicipio municipio;

	public Logradouro() {
		this.codigo = -1;
		this.nome = "";
		this.tipoLogradouro = new TipoLogradouro();
		this.tituloLogradouro = new TituloLogradouro();
		this.apelido = "";
		this.situacao = "";
		this.tipoPreposicao = new TipoPreposicao();
		this.KM = "";
		this.fonetico = "";
		this.apelidoFonetico = "";
		this.municipio = new EnderecoMunicipio();
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

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public void setTipoLogradouro(TipoLogradouro tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

	public TituloLogradouro getTituloLogradouro() {
		return tituloLogradouro;
	}

	public void setTituloLogradouro(TituloLogradouro tituloLogradouro) {
		this.tituloLogradouro = tituloLogradouro;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public TipoPreposicao getTipoPreposicao() {
		return tipoPreposicao;
	}

	public void setTipoPreposicao(TipoPreposicao tipoPreposicao) {
		this.tipoPreposicao = tipoPreposicao;
	}

	public String getKM() {
		return KM;
	}

	public void setKM(String kM) {
		KM = kM;
	}

	public String getFonetico() {
		return fonetico;
	}

	public void setFonetico(String fonetico) {
		this.fonetico = fonetico;
	}

	public String getApelidoFonetico() {
		return apelidoFonetico;
	}

	public void setApelidoFonetico(String apelidoFonetico) {
		this.apelidoFonetico = apelidoFonetico;
	}

	public EnderecoMunicipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(EnderecoMunicipio municipio) {
		this.municipio = municipio;
	}

}
