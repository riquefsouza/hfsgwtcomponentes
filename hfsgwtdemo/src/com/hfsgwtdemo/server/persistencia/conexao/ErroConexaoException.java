package com.hfsgwtdemo.server.persistencia.conexao;

import org.apache.log4j.Logger;

/**
 * Classe de exceção para os possíveis erros da classe Conexao.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.ConexaoOracleFailover.conexao.ConexaoOracle
 */
public class ErroConexaoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * construtor que informa a mensagem de exceção e que também grava essa
	 * mensagem no arquivo de LOG.
	 * 
	 * @param msg
	 *            a mensagem de exceção
	 */
	public ErroConexaoException(Logger log, String msg) {
		super(msg);
		log.error(msg);
	}
}