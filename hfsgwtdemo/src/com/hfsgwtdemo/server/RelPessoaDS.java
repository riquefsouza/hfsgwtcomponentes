package com.hfsgwtdemo.server;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.hfsgwtdemo.client.Pessoa;

public class RelPessoaDS implements JRDataSource {

	private Pessoa objeto;

	private Iterator<Pessoa> iterator;

	public RelPessoaDS(List<Pessoa> lista) {
		this.iterator = lista.iterator();
	}

	@Override
	public Object getFieldValue(JRField campo) throws JRException {
		Object valor = null;

		if ("codPessoa".equals(campo.getName())) {
			valor = Integer.toString(objeto.getCodigo());
		} else if ("nomePessoa".equals(campo.getName())) {
			valor = objeto.getNome();
		}

		return valor;
	}

	@Override
	public boolean next() throws JRException {
		objeto = iterator.hasNext() ? iterator.next() : null;
		return (objeto != null);
	}

}
