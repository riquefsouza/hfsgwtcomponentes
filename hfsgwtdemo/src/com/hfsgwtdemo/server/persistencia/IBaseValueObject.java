package com.hfsgwtdemo.server.persistencia;

import java.io.Serializable;

/**
 * Interface que serve de base para o padrÃ£o Value Object (VO).
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 01/10/2007
 */
public interface IBaseValueObject extends Serializable {

	/**
	 * Limpa todos os atributos da classe.
	 */
	public abstract void limparDados();

	/**
	 * Faz a comparaÃ§Ã£o dos atributos com um outro objeto do mesmo tipo.
	 * 
	 * @param obj
	 *            objeto do mesmo tipo de classe
	 * @return se os atributos dos dois objetos sÃ£o iguais
	 */
	public abstract boolean equals(Object obj);

	/**
	 * Retorna a representaÃ§Ã£o em texto dos nomes e valores dos atributos da
	 * classe.
	 * 
	 * @return os atributos da classe como um texto com pares 'nome atributo:
	 *         valor atributo'
	 */
	public abstract String toString();
}