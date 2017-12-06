package com.hfsgwt.server.login;

public class UsuarioSessaoVO {

	private String login;
	private String pwd;
	private String idSessao;
	private String ip;
	private String nome;
	private String telefone;
	private String email;
	private int idUnidade;
	private int idOrgao;
	private java.util.Date inicioSessao;

	public UsuarioSessaoVO() {
		this("", "", "", "");
	}

	public UsuarioSessaoVO(String login, String pwd, String idSessao, String ip) {
		this.login = login;
		this.pwd = pwd;
		this.idSessao = idSessao;
		this.ip = ip;
	}

	public String getIdSessao() {
		return idSessao;
	}

	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public java.util.Date getInicioSessao() {
		return inicioSessao;
	}

	public void setInicioSessao(java.util.Date inicioSessao) {
		this.inicioSessao = inicioSessao;
	}

	public int getIdOrgao() {
		return idOrgao;
	}

	public void setIdOrgao(int idOrgao) {
		this.idOrgao = idOrgao;
	}

	public int getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(int idUnidade) {
		this.idUnidade = idUnidade;
	}
}