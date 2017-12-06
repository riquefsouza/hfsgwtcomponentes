package com.hfsgwtdemo.server.persistencia;

import org.apache.log4j.Logger;


/**
 * Classe de exceção para os possíveis erros de qualquer acesso a dados.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.ConexaoOracleFailover.conexao.ConexaoOracle
 */
public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;

	private int codigo;

	public DAOException(Logger log, int codigo, String msg) {
		super(codigo + " - " + msg);
		this.codigo = codigo;
		log.error(codigo + " - " + msg);
	}

	public DAOException(Logger log, String msg) {
		super(msg);
		log.error(msg);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}