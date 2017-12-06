package com.hfsgwt.server.temporizacao;

import org.apache.log4j.Logger;

/**
 * Classe de exceção para os campos que são considerados inválidos.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 19/11/2008
 *  
 */
public class TemporizacaoException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * construtor que informa a mensagem de exceção e que também grava essa
	 * mensagem no arquivo de LOG.
	 * 
	 * @param msg
	 *            a mensagem de exceção
	 */
	public TemporizacaoException(Logger log, String msg) {
		super(msg);
		log.error(msg);
	}
}