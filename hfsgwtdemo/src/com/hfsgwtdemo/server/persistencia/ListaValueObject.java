package com.hfsgwtdemo.server.persistencia;

import org.apache.log4j.Logger;


/**
 * Classe que serve de base para as listas dos Value Objects (VO).
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 */
public abstract class ListaValueObject implements IListaValueObject {

	/**
	 * Carrega os valores do objeto dentro de uma lista em mem�ria.
	 */
	public void carregar(Logger log) {
		if (PersistenciaParams.getInstancia().isMostrarCarregandoClasse()) {
			log.info(
					"Carregando classe [" + this.getClass().getName() + "]");
		}
	}

}
