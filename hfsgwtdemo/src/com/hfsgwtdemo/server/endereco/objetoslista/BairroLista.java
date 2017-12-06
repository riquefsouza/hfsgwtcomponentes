package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.server.endereco.objetosdao.BairroDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object BairroLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class BairroLista extends ListaValueObject {
	private ArrayList<EnderecoBairro> lista;

	private static BairroLista instancia;

	private BairroLista() {
		lista = new ArrayList<EnderecoBairro>();
	}

	public static BairroLista getInstancia() {
		if (instancia == null) {
			instancia = new BairroLista();
		}
		return instancia;
	}

	private ArrayList<EnderecoBairro> getLista() {
		EnderecoBairro elemento = null;
		try {
			EnderecoBairro[] valores = BairroDAO.getInstancia().consultarTudo();
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

	public EnderecoBairro[] getElementos() {
		return (EnderecoBairro[]) lista
				.toArray(new EnderecoBairro[lista.size()]);
	}

	public EnderecoBairro getElemento(int codigo) {
		for (Iterator<EnderecoBairro> iter = lista.iterator(); iter.hasNext();) {
			EnderecoBairro elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(EnderecoBairro obj) {
		for (Iterator<EnderecoBairro> iter = lista.iterator(); iter.hasNext();) {
			EnderecoBairro elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public EnderecoBairro getElementoNaoNulo(int codigo) {
		EnderecoBairro obj = getElemento(codigo);
		if (obj == null) {
			obj = new EnderecoBairro();
		}
		return obj;
	}

}
