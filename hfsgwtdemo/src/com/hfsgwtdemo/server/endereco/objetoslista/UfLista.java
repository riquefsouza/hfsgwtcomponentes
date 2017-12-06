package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.server.endereco.objetosdao.UfDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object UFLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class UfLista extends ListaValueObject {
	private ArrayList<EnderecoUF> lista;

	private static UfLista instancia;

	private UfLista() {
		lista = new ArrayList<EnderecoUF>();
	}

	public static UfLista getInstancia() {
		if (instancia == null) {
			instancia = new UfLista();
		}
		return instancia;
	}

	private ArrayList<EnderecoUF> getLista() {
		EnderecoUF elemento = null;
		try {
			EnderecoUF[] valores = UfDAO.getInstancia().consultarTudo();
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

	public EnderecoUF[] getElementos() {
		return (EnderecoUF[]) lista.toArray(new EnderecoUF[lista.size()]);
	}

	public EnderecoUF getElemento(int codigo) {
		for (Iterator<EnderecoUF> iter = lista.iterator(); iter.hasNext();) {
			EnderecoUF elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(EnderecoUF obj) {
		for (Iterator<EnderecoUF> iter = lista.iterator(); iter.hasNext();) {
			EnderecoUF elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public EnderecoUF getElementoNaoNulo(int codigo) {
		EnderecoUF obj = getElemento(codigo);
		if (obj == null) {
			obj = new EnderecoUF();
		}
		return obj;
	}

}
