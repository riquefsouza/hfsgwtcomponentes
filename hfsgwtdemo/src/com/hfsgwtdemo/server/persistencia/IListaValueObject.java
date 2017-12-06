package com.hfsgwtdemo.server.persistencia;

import org.apache.log4j.Logger;

/**
 * Interface que serve de base para as listas dos Value Objects (VO).
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 31/12/2009
 */
public interface IListaValueObject {

	/**
	 * Carrega os valores do objeto dentro de uma lista em mem�ria.
	 */
	public abstract void carregar(Logger log);
}