package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.server.endereco.objetosdao.PaisDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object PaisLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class PaisLista extends ListaValueObject {
	private ArrayList<EnderecoPais> lista;

	private static PaisLista instancia;

	private PaisLista() {
		lista = new ArrayList<EnderecoPais>();
	}

	public static PaisLista getInstancia() {
		if (instancia == null) {
			instancia = new PaisLista();
		}
		return instancia;
	}

	private ArrayList<EnderecoPais> getLista() {
		EnderecoPais elemento = null;
		try {
			EnderecoPais[] valores = PaisDAO.getInstancia().consultarTudo();
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

	public EnderecoPais[] getElementos() {
		return (EnderecoPais[]) lista.toArray(new EnderecoPais[lista.size()]);
	}
	
	public ArrayList<EnderecoPais> getListaElementos() {
		return lista;
	}

	public EnderecoPais getElemento(int codigo) {
		for (Iterator<EnderecoPais> iter = lista.iterator(); iter.hasNext();) {
			EnderecoPais elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(EnderecoPais obj) {
		for (Iterator<EnderecoPais> iter = lista.iterator(); iter.hasNext();) {
			EnderecoPais elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public EnderecoPais getElementoNaoNulo(int codigo) {
		EnderecoPais obj = getElemento(codigo);
		if (obj == null) {
			obj = new EnderecoPais();
		}
		return obj;
	}

}
