package com.hfsgwtdemo.server.persistencia.transacao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionParams implements Serializable {
	private static final long serialVersionUID = -6648019945693484609L;

	public static final int SINGLE_TRANSACTION = 0;

	public static final int DISTRIBUTED_TRANSACTION = 1;

	private String urlBanco;

	private String usuarioBanco;

	private String senhaBanco;

	private int tipoTransacao;

	private boolean emTransacao;

	private String sessionID;

//	private ArrayList xidLista;
//
//	OracleXAConnection xaConexao;
//
//	OracleXAResource xaResource;

	Connection conexaoTransacao;

	PreparedStatement pstmtTransacao;

	public TransactionParams() {
		this.limparDados();
	}

	public void limparDados() {
		this.tipoTransacao = SINGLE_TRANSACTION;
		this.emTransacao = false;
		this.sessionID = "";
//		this.xidLista = new ArrayList();
	}

	public boolean isEmTransacao() {
		return emTransacao;
	}

	public void setEmTransacao(boolean emTransacao) {
		this.emTransacao = emTransacao;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public int getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(int tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Connection getConexaoTransacao() {
		return conexaoTransacao;
	}

	public void setConexaoTransacao(Connection conexaoTransacao) {
		this.conexaoTransacao = conexaoTransacao;
	}

//	public OracleXid getXid() {
//		return (OracleXid) xidLista.get(xidLista.size() - 1);
//	}
//
//	public void setXid(OracleXid xid) {
//		this.xidLista.add(xid);
//	}
//	
//	public OracleXAConnection getXaConexao() {
//		return xaConexao;
//	}
//
//	public void setXaConexao(OracleXAConnection xaConexao) {
//		this.xaConexao = xaConexao;
//	}
//
//	public OracleXAResource getXaResource() {
//		return xaResource;
//	}
//
//	public void setXaResource(OracleXAResource xaResource) {
//		this.xaResource = xaResource;
//	}
//
//	public ArrayList getXidLista() {
//		return xidLista;
//	}
	
	public PreparedStatement getPstmtTransacao() {
		return pstmtTransacao;
	}

	public void setPstmtTransacao(PreparedStatement pstmtTransacao) {
		this.pstmtTransacao = pstmtTransacao;
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

}