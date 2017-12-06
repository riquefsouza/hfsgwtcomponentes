package com.hfsgwtdemo.server.siadm.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.siadm.SiadmUsuario;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;
import com.hfsgwtdemo.server.siadm.objetosdao.UsuarioDAO;

/**
 * Lista Value Object UsuarioLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class UsuarioLista extends ListaValueObject {
	private ArrayList<SiadmUsuario> lista;

	private static UsuarioLista instancia;

	private UsuarioLista() {
		lista = new ArrayList<SiadmUsuario>();
	}

	public static UsuarioLista getInstancia() {
		if (instancia == null) {
			instancia = new UsuarioLista();
		}
		return instancia;
	}

	private ArrayList<SiadmUsuario> getLista() {
		SiadmUsuario elemento = null;
		try {
			SiadmUsuario[] valores = UsuarioDAO.getInstancia().consultarTudo();
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

	public SiadmUsuario[] getElementos() {
		return (SiadmUsuario[]) lista.toArray(new SiadmUsuario[lista.size()]);
	}

	public SiadmUsuario getElemento(String codigo) {
		for (Iterator<SiadmUsuario> iter = lista.iterator(); iter.hasNext();) {
			SiadmUsuario elemento = iter.next();
			if (elemento.getCodigo().equals(codigo)) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(String codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(SiadmUsuario obj) {
		for (Iterator<SiadmUsuario> iter = lista.iterator(); iter.hasNext();) {
			SiadmUsuario elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public SiadmUsuario getElementoNaoNulo(String codigo) {
		SiadmUsuario obj = getElemento(codigo);
		if (obj == null) {
			obj = new SiadmUsuario();
		}
		return obj;
	}

	public SiadmUsuario[] getElementosCodigoOrgaoLotacao(int codigoOrgao) {
		ArrayList<SiadmUsuario> resLista = new ArrayList<SiadmUsuario>();
		for (SiadmUsuario item : lista) {
			if (item.getOrgaoLotacao().getCodigo() == codigoOrgao) {
				resLista.add(item);
			}
		}
		return (SiadmUsuario[]) resLista.toArray(new SiadmUsuario[resLista
				.size()]);
	}

	public SiadmUsuario[] getElementosCodigoOrgaoOrigem(int codigoOrgao) {
		ArrayList<SiadmUsuario> resLista = new ArrayList<SiadmUsuario>();
		for (SiadmUsuario item : lista) {
			if (item.getOrgaoOrigem().getCodigo() == codigoOrgao) {
				resLista.add(item);
			}
		}
		return (SiadmUsuario[]) resLista.toArray(new SiadmUsuario[resLista
				.size()]);
	}

	public SiadmUsuario[] getElementosCodigoSetor(int codigoSetor) {
		ArrayList<SiadmUsuario> resLista = new ArrayList<SiadmUsuario>();
		for (SiadmUsuario item : lista) {
			if (item.getSetor().getCodigo() == codigoSetor) {
				resLista.add(item);
			}
		}
		return (SiadmUsuario[]) resLista.toArray(new SiadmUsuario[resLista
				.size()]);
	}
}
