package com.hfsgwtdemo.server.siadm.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;
import com.hfsgwtdemo.server.siadm.objetosdao.OrgaoDAO;

/**
 * Lista Value Object OrgaoLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class OrgaoLista extends ListaValueObject {
	private ArrayList<SiadmOrgao> lista;

	private static OrgaoLista instancia;

	private OrgaoLista() {
		lista = new ArrayList<SiadmOrgao>();
	}

	public static OrgaoLista getInstancia() {
		if (instancia == null) {
			instancia = new OrgaoLista();
		}
		return instancia;
	}

	private ArrayList<SiadmOrgao> getLista() {
		SiadmOrgao elemento = null;
		try {
			SiadmOrgao[] valores = OrgaoDAO.getInstancia().consultarTudo();
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

	public SiadmOrgao[] getElementos() {
		return (SiadmOrgao[]) lista.toArray(new SiadmOrgao[lista.size()]);
	}

	public SiadmOrgao getElemento(int codigo) {
		for (Iterator<SiadmOrgao> iter = lista.iterator(); iter.hasNext();) {
			SiadmOrgao elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(SiadmOrgao obj) {
		for (Iterator<SiadmOrgao> iter = lista.iterator(); iter.hasNext();) {
			SiadmOrgao elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public SiadmOrgao getElementoNaoNulo(int codigo) {
		SiadmOrgao obj = getElemento(codigo);
		if (obj == null) {
			obj = new SiadmOrgao();
		}
		return obj;
	}

	public SiadmOrgao[] getElementosCodigoPai(int codigoPai) {
		ArrayList<SiadmOrgao> resLista = new ArrayList<SiadmOrgao>();
		for (SiadmOrgao item : lista) {
			if (item.getCodigoOrgaoPai() == codigoPai) {
				resLista.add(item);
			}
		}
		return (SiadmOrgao[]) resLista.toArray(new SiadmOrgao[resLista.size()]);
	}

}
