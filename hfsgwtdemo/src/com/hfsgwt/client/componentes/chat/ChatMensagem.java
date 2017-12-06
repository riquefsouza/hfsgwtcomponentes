package com.hfsgwt.client.componentes.chat;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatMensagem implements IsSerializable {

	private String usuarioOrigem;

	private String usuarioDestino;

	private String assunto;

	private String mensagem;

	public ChatMensagem() {
		this.usuarioOrigem = "";
		this.usuarioDestino = "";
		this.assunto = "";
		this.mensagem = "";
	}

	public ChatMensagem(String usuarioOrigem, String usuarioDestino,
			String assunto, String mensagem) {
		this.usuarioOrigem = usuarioOrigem;
		this.usuarioDestino = usuarioDestino;
		this.assunto = assunto;
		this.mensagem = mensagem;

	}

	public String getUsuarioOrigem() {
		return usuarioOrigem;
	}

	public void setUsuarioOrigem(String usuarioOrigem) {
		this.usuarioOrigem = usuarioOrigem;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String toString() {
		return "Usuário Origem: " + this.usuarioOrigem + ", Usuário Destino: "
				+ this.usuarioDestino + ", Assunto:" + this.assunto
				+ ", Mensagem: " + this.mensagem;

	}
}
