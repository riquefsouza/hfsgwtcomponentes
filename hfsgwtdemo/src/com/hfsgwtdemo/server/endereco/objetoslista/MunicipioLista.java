package com.hfsgwtdemo.server.endereco.objetoslista;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.server.endereco.objetosdao.MunicipioDAO;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.ListaValueObject;

/**
 * Lista Value Object MunicipioLista.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.ListaValueObject
 */
public class MunicipioLista extends ListaValueObject {
	private ArrayList<EnderecoMunicipio> lista;

	private static MunicipioLista instancia;

	private MunicipioLista() {
		lista = new ArrayList<EnderecoMunicipio>();
	}

	public static MunicipioLista getInstancia() {
		if (instancia == null) {
			instancia = new MunicipioLista();
		}
		return instancia;
	}

	private ArrayList<EnderecoMunicipio> getLista() {
		EnderecoMunicipio elemento = null;
		try {
			EnderecoMunicipio[] valores = MunicipioDAO.getInstancia()
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

	public EnderecoMunicipio[] getElementos() {
		return (EnderecoMunicipio[]) lista.toArray(new EnderecoMunicipio[lista
				.size()]);
	}

	public EnderecoMunicipio getElemento(int codigo) {
		for (Iterator<EnderecoMunicipio> iter = lista.iterator(); iter
				.hasNext();) {
			EnderecoMunicipio elemento = iter.next();
			if (elemento.getCodigo() == codigo) {
				return elemento;
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		return (getElemento(codigo) != null);
	}

	public boolean existe(EnderecoMunicipio obj) {
		for (Iterator<EnderecoMunicipio> iter = lista.iterator(); iter
				.hasNext();) {
			EnderecoMunicipio elemento = iter.next();
			if (elemento.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	public EnderecoMunicipio getElementoNaoNulo(int codigo) {
		EnderecoMunicipio obj = getElemento(codigo);
		if (obj == null) {
			obj = new EnderecoMunicipio();
		}
		return obj;
	}

}
