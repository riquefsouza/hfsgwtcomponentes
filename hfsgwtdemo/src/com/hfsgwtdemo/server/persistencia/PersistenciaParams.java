package com.hfsgwtdemo.server.persistencia;

public final class PersistenciaParams {

	private static PersistenciaParams instancia;

	private String nomeCache;

	private String urlBanco;

	private String usuarioBanco;

	private String senhaBanco;

	private String sistemaDescricao;

	private String oracleHome;

	private boolean mostrarSQL;

	private boolean mostrarCarregandoClasse;

	private boolean habilitarFailOver;

	private PersistenciaParams() {
		this.nomeCache = "";
		this.urlBanco = "";
		this.usuarioBanco = "";
		this.senhaBanco = "";
		this.sistemaDescricao = "";
		this.oracleHome = "";
		this.mostrarSQL = false;
		this.mostrarCarregandoClasse = false;
		this.habilitarFailOver = false;
	}

	public static PersistenciaParams getInstancia() {
		if (instancia == null) {
			instancia = new PersistenciaParams();
		}
		return instancia;
	}

	public String getNomeCache() {
		return nomeCache;
	}

	public void setNomeCache(String nomeCache) {
		this.nomeCache = nomeCache;
	}

	public String getSistemaDescricao() {
		return sistemaDescricao;
	}

	public void setSistemaDescricao(String sistemaDescricao) {
		this.sistemaDescricao = sistemaDescricao;
	}

	public boolean isMostrarSQL() {
		return mostrarSQL;
	}

	public void setMostrarSQL(boolean mostrarSQL) {
		this.mostrarSQL = mostrarSQL;
	}

	public boolean isMostrarCarregandoClasse() {
		return mostrarCarregandoClasse;
	}

	public void setMostrarCarregandoClasse(boolean mostrarCarregandoClasse) {
		this.mostrarCarregandoClasse = mostrarCarregandoClasse;
	}

	public String getSenhaBanco() {
		return senhaBanco;
	}

	public void setSenhaBanco(String senhaBanco) {
		this.senhaBanco = senhaBanco;
	}

	public String getUrlBanco() {
		return urlBanco;
	}

	public void setUrlBanco(String urlBanco) {
		this.urlBanco = urlBanco;
	}

	public String getUsuarioBanco() {
		return usuarioBanco;
	}

	public void setUsuarioBanco(String usuarioBanco) {
		this.usuarioBanco = usuarioBanco;
	}

	public String getOracleHome() {
		return oracleHome;
	}

	public void setOracleHome(String oracleHome) {
		this.oracleHome = oracleHome;
	}

	public boolean isHabilitarFailOver() {
		return habilitarFailOver;
	}

	public void setHabilitarFailOver(boolean habilitarFailOver) {
		this.habilitarFailOver = habilitarFailOver;
	}

}