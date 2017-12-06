package com.hfsgwt.client.componentes;

import java.util.List;

public interface IDBNavigator {
	public void salvar();
	public void excluir();
	public List<List<String>> pesquisar(String descricao);
}
