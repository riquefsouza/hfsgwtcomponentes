package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.endereco.objetos.TituloLogradouro;
import com.hfsgwtdemo.server.endereco.objetosdao.TituloLogradouroDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object TituloLogradouroLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class TituloLogradouroLista extends ListaValueObject {
	private ArrayList<TituloLogradouro> lista;

	private static TituloLogradouroLista instancia;

	private TituloLogradouroLista() {
		lista = new ArrayList<TituloLogradouro>();
	}

	public static TituloLogradouroLista getInstancia() {
		if (instancia == null) {
			instancia = new TituloLogradouroLista();
		}
		return instancia;
	}

	private ArrayList<TituloLogradouro> getLista() {
		TituloLogradouro elemento = null;
		try {
			TituloLogradouro[] valores = TituloLogradouroDAO.getInstancia().consultarTudo();
			if (valores != null) {
				lista.clear();
				for (int i = 0; i < valores.length; i++) {
					elemento = valores[i];
					lista.add(elemento);
				}
			}
		} catch (DAOException e) {
		}
		return lista;
	}

	public void carregar(Logger log) {
		super.carregar(log);
		lista = this.getLista();
	}

	public TituloLogradouro[] getElementos() {
		return (TituloLogradouro[]) lista.toArray(new TituloLogradouro[lista.size()]);
	}

	public TituloLogradouro getElemento(int codigo) {
		for (Iterator<TituloLogradouro> iter = lista.iterator(); iter.hasNext();) {
			TituloLogradouro elemento = (TituloLogradouro) iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(TituloLogradouro obj) {
		for (Iterator<TituloLogradouro> iter = lista.iterator(); iter.hasNext();) {
			TituloLogradouro elemento = (TituloLogradouro) iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}
	
	public TituloLogradouro getElementoNaoNulo(int codigo) {
		TituloLogradouro obj = getElemento(codigo);
		if (obj == null) {
			obj = new TituloLogradouro();
		}
		return obj;
	}
	
}
