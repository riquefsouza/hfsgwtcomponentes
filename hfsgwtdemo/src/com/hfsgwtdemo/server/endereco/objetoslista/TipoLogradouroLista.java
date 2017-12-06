package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.endereco.objetos.TipoLogradouro;
import com.hfsgwtdemo.server.endereco.objetosdao.TipoLogradouroDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object TipoLogradouroLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class TipoLogradouroLista extends ListaValueObject {
	private ArrayList<TipoLogradouro> lista;

	private static TipoLogradouroLista instancia;

	private TipoLogradouroLista() {
		lista = new ArrayList<TipoLogradouro>();
	}

	public static TipoLogradouroLista getInstancia() {
		if (instancia == null) {
			instancia = new TipoLogradouroLista();
		}
		return instancia;
	}

	private ArrayList<TipoLogradouro> getLista() {
		TipoLogradouro elemento = null;
		try {
			TipoLogradouro[] valores = TipoLogradouroDAO.getInstancia()
					.consultarTudo();
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

	public TipoLogradouro[] getElementos() {
		return (TipoLogradouro[]) lista
				.toArray(new TipoLogradouro[lista.size()]);
	}

	public TipoLogradouro getElemento(int codigo) {
		for (Iterator<TipoLogradouro> iter = lista.iterator(); iter.hasNext();) {
			TipoLogradouro elemento = (TipoLogradouro) iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(TipoLogradouro obj) {
		for (Iterator<TipoLogradouro> iter = lista.iterator(); iter.hasNext();) {
			TipoLogradouro elemento = (TipoLogradouro) iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public TipoLogradouro getElementoNaoNulo(int codigo) {
		TipoLogradouro obj = getElemento(codigo);
		if (obj == null) {
			obj = new TipoLogradouro();
		}
		return obj;
	}

}
