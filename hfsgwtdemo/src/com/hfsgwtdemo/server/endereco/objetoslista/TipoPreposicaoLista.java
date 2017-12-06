package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.endereco.objetos.TipoPreposicao;
import com.hfsgwtdemo.server.endereco.objetosdao.TipoPreposicaoDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object TipoPreposicaoLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class TipoPreposicaoLista extends ListaValueObject {
	private ArrayList<TipoPreposicao> lista;

	private static TipoPreposicaoLista instancia;

	private TipoPreposicaoLista() {
		lista = new ArrayList<TipoPreposicao>();
	}

	public static TipoPreposicaoLista getInstancia() {
		if (instancia == null) {
			instancia = new TipoPreposicaoLista();
		}
		return instancia;
	}

	private ArrayList<TipoPreposicao> getLista() {
		TipoPreposicao elemento = null;
		try {
			TipoPreposicao[] valores = TipoPreposicaoDAO.getInstancia()
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

	public TipoPreposicao[] getElementos() {
		return (TipoPreposicao[]) lista
				.toArray(new TipoPreposicao[lista.size()]);
	}

	public TipoPreposicao getElemento(int codigo) {
		for (Iterator<TipoPreposicao> iter = lista.iterator(); iter.hasNext();) {
			TipoPreposicao elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(TipoPreposicao obj) {
		for (Iterator<TipoPreposicao> iter = lista.iterator(); iter.hasNext();) {
			TipoPreposicao elemento = (TipoPreposicao) iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public TipoPreposicao getElementoNaoNulo(int codigo) {
		TipoPreposicao obj = getElemento(codigo);
		if (obj == null) {
			obj = new TipoPreposicao();
		}
		return obj;
	}
}
