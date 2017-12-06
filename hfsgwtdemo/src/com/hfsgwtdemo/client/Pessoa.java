package com.hfsgwtdemo.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hfsgwt.client.componentes.grid.HFSGridColumn;
import com.hfsgwt.client.componentes.grid.IHFSColumn;

public class Pessoa implements IsSerializable, IHFSColumn {

	private int codigo;
	private String nome;

	public Pessoa() {
		this.codigo = -1;
		this.nome = "";
	}

	public Pessoa(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Comparator<Pessoa> getComparator(int coluna) {
		if (coluna == 0)
			return new Comparator<Pessoa>() {
				@Override
				public int compare(Pessoa o1, Pessoa o2) {
					return (new Integer(o1.getCodigo())).compareTo(o2
							.getCodigo());
				}
			};
		else if (coluna == 1)
			return new Comparator<Pessoa>() {
				@Override
				public int compare(Pessoa o1, Pessoa o2) {
					return o1.getNome().compareTo(o2.getNome());
				}
			};
		else
			return null;
	}

	@Override
	public String getColuna(int coluna) {
		if (coluna == 0)
			return Integer.toString(this.codigo);
		else if (coluna == 1)
			return this.nome;
		else
			return null;
	}

	public List<List<String>> getListaGrid(List<Pessoa> lista) {
		List<List<String>> linhas = new ArrayList<List<String>>();
		List<String> item;
		for (Pessoa obj : lista) {
			item = new ArrayList<String>();
			item.add(Integer.toString(obj.getCodigo()));
			item.add(obj.getNome());
			linhas.add(item);
		}
		return linhas;
	}

	public static List<IHFSColumn> getLista(List<Pessoa> lista, int colunaOrdena,
			boolean ordemCrescente) {
		if (colunaOrdena >= 0) {
			Collections.sort(lista, (new Pessoa()).getComparator(colunaOrdena));
			if (!ordemCrescente)
				Collections.reverse(lista);
		}
		List<IHFSColumn> retorno = new ArrayList<IHFSColumn>(lista);

		return retorno;
	}

	public static HFSGridColumn[] getColunas() {
		HFSGridColumn[] colunas = new HFSGridColumn[2];
		colunas[0] = new HFSGridColumn("Código", 0, true, true); //60
		colunas[1] = new HFSGridColumn("Nome", 0, true, true); //150
		return colunas;
	}
}
