package com.hfsgwtdemo.server.siadm.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;
import com.hfsgwtdemo.server.siadm.objetosdao.SetorDAO;

/**
 * Lista Value Object SetorLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class SetorLista extends ListaValueObject {
	private ArrayList<SiadmSetor> lista;

	private static SetorLista instancia;

	private SetorLista() {
		lista = new ArrayList<SiadmSetor>();
	}

	public static SetorLista getInstancia() {
		if (instancia == null) {
			instancia = new SetorLista();
		}
		return instancia;
	}

	private ArrayList<SiadmSetor> getLista() {
		SiadmSetor elemento = null;
		try {
			SiadmSetor[] valores = SetorDAO.getInstancia()
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

	public SiadmSetor[] getElementos() {
		return (SiadmSetor[]) lista
				.toArray(new SiadmSetor[lista.size()]);
	}

	public SiadmSetor getElemento(int codigo) {
		for (Iterator<SiadmSetor> iter = lista.iterator(); iter.hasNext();) {
			SiadmSetor elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(SiadmSetor obj) {
		for (Iterator<SiadmSetor> iter = lista.iterator(); iter.hasNext();) {
			SiadmSetor elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public SiadmSetor getElementoNaoNulo(int codigo) {
		SiadmSetor obj = getElemento(codigo);
		if (obj == null) {
			obj = new SiadmSetor();
		}
		return obj;
	}

	public SiadmSetor[] getElementosCodigoOrgao(int codigoOrgao) {
		ArrayList<SiadmSetor> resLista = new ArrayList<SiadmSetor>();
		for (SiadmSetor item : lista) {
			if (item.getOrgao().getCodigo() == codigoOrgao) {
				resLista.add(item);
			}
		}
		return (SiadmSetor[]) resLista.toArray(new SiadmSetor[resLista.size()]);
	}
}
