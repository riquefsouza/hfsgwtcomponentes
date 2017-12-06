package com.hfsgwt.client.componentes.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatUsuario implements IsSerializable {

	private String nome;

	private String usuario;

	private String senha;

	public ChatUsuario() {
		super();
	}

	public ChatUsuario(String nome, String usuario) {
		this.nome = nome;
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
