package com.hfsgwtdemo.server.siadm.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.siadm.SiadmTipoOrgao;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;
import com.hfsgwtdemo.server.siadm.objetosdao.TipoOrgaoDAO;

/**
 * Lista Value Object TipoOrgaoLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class TipoOrgaoLista extends ListaValueObject {
	private ArrayList<SiadmTipoOrgao> lista;

	private static TipoOrgaoLista instancia;

	private TipoOrgaoLista() {
		lista = new ArrayList<SiadmTipoOrgao>();
	}

	public static TipoOrgaoLista getInstancia() {
		if (instancia == null) {
			instancia = new TipoOrgaoLista();
		}
		return instancia;
	}

	private ArrayList<SiadmTipoOrgao> getLista() {
		SiadmTipoOrgao elemento = null;
		try {
			SiadmTipoOrgao[] valores = TipoOrgaoDAO.getInstancia()
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

	public SiadmTipoOrgao[] getElementos() {
		return (SiadmTipoOrgao[]) lista
				.toArray(new SiadmTipoOrgao[lista.size()]);
	}

	public SiadmTipoOrgao getElemento(int codigo) {
		for (Iterator<SiadmTipoOrgao> iter = lista.iterator(); iter.hasNext();) {
			SiadmTipoOrgao elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(SiadmTipoOrgao obj) {
		for (Iterator<SiadmTipoOrgao> iter = lista.iterator(); iter.hasNext();) {
			SiadmTipoOrgao elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public SiadmTipoOrgao getElementoNaoNulo(int codigo) {
		SiadmTipoOrgao obj = getElemento(codigo);
		if (obj == null) {
			obj = new SiadmTipoOrgao();
		}
		return obj;
	}

}
