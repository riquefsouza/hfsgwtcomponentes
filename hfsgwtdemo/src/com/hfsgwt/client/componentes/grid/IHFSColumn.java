package com.hfsgwt.client.componentes.grid;

import java.util.Comparator;

public interface IHFSColumn {

	public Comparator<?> getComparator(int coluna);

	public String getColuna(int coluna);
}
