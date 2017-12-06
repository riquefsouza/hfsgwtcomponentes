package com.hfsgwt.client.componentes.ftp;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FTPEstrutura implements IsSerializable {

	private String servidor;
	private String usuario;
	private String senha;
	private String diretorio; // diretorio do FTP
	private String arquivo;
	private String arquivoExtensao;

	public FTPEstrutura() {
		this.servidor = "";
		this.usuario = "";
		this.senha = "";
		this.diretorio = "";
		this.arquivo = "";
	}

	public FTPEstrutura(String servidor, String usuario, String senha,
			String diretorio) {
		this.servidor = servidor;
		this.usuario = usuario;
		this.senha = senha;
		this.diretorio = diretorio;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
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

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getArquivoExtensao() {
		return arquivoExtensao;
	}

	public void setArquivoExtensao(String arquivoExtensao) {
		this.arquivoExtensao = arquivoExtensao;
	}
}
