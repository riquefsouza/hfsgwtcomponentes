package com.hfsgwtdemo.server;

import java.util.ArrayList;
import java.util.List;

import com.hfsgwtdemo.client.Pessoa;

public class PessoaDAO {
	// private static Logger log = Logger.getLogger(PessoaDAO.class);
	private static PessoaDAO instancia;

	private ArrayList<Pessoa> lista;
	
	private PessoaDAO() {
		lista = new ArrayList<Pessoa>();
		lista.add(new Pessoa(1, "Luiz Henrique o ninja"));
		lista.add(new Pessoa(2, "Carlos o Bila"));
		lista.add(new Pessoa(3, "Rafael o Pinto"));
		lista.add(new Pessoa(4, "Calado o magro"));
		lista.add(new Pessoa(5, "Flavio o fofo"));
		lista.add(new Pessoa(6, "Raul o mudo"));
		lista.add(new Pessoa(7, "Ailton a gazela"));
		lista.add(new Pessoa(8, "Roberto o microempresario"));
		lista.add(new Pessoa(9, "Filipe o faltante"));
		lista.add(new Pessoa(10, "Neto o jogador"));
		lista.add(new Pessoa(11, "Allan o chefe"));
		lista.add(new Pessoa(12, "Rogerio o desaparecido"));
	}

	public static PessoaDAO getInstancia() {
		if (instancia == null) {
			instancia = new PessoaDAO();
		}
		return instancia;
	}
	
	public Pessoa getPessoa(int codigo) {
		return lista.get(codigo-1);
	}

	public List<Pessoa> listarPessoas(int min, int max) {
		if (min==0 && max==0)
			return lista;
		else {
			List<Pessoa> alista = lista.subList(min, max+1);
			List<Pessoa> nlista = new ArrayList<Pessoa>();
			for (Pessoa pessoa : alista) {
				nlista.add(pessoa);	
			}			 
			return nlista;
		}
	}
}
