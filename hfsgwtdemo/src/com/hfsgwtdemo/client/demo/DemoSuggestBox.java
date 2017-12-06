package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.suggest.HFSSuggestBox;

public class DemoSuggestBox extends Composite {
	private Grid grid;
	private HFSSuggestBox suggestBox1;
	private HFSSuggestBox suggestBox2;

	public DemoSuggestBox() {

		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 1);
			grid.setCellSpacing(5);
			grid.setWidget(0, 0, getSuggestBox1());
			grid.setWidget(1, 0, getSuggestBox2());
		}
		return grid;
	}

	private HFSSuggestBox getSuggestBox1() {
		if (suggestBox1 == null) {
			suggestBox1 = new HFSSuggestBox(
					HFSSuggestBox.PosicaoRotulo.ACIMA,
					"Caixa de sugestão por String:",200,false,10,
					new String[] { "Henrique Figueiredo", "Raul Raposo",
							"Márcio Calado", "Rafael Caneca", "Luiz Henrique",
							"Carlos lenhador", "Ailton mala", "Roberto Aquino" });
		}
		return suggestBox1;
	}

	private HFSSuggestBox getSuggestBox2() {
		if (suggestBox2 == null) {
			suggestBox2 = new HFSSuggestBox(HFSSuggestBox.PosicaoRotulo.ACIMA,
					"Caixa de sugestão por Demanda:", 200, false, 10);
			suggestBox2.addCarregarHandler(new HFSSuggestBox.CarregarHandler() {
				@Override
				public List<HFSItem> onCarregarAoIniciar() {
					List<HFSItem> lista = new ArrayList<HFSItem>();
					lista.add(new HFSItem("0", "Henrique Figueiredo"));
					lista.add(new HFSItem("1", "Raul Raposo"));
					lista.add(new HFSItem("2", "Márcio Calado"));
					lista.add(new HFSItem("3", "Rafael Caneca"));
					lista.add(new HFSItem("4", "Luiz Henrique"));
					lista.add(new HFSItem("5", "Carlos lenhador"));
					lista.add(new HFSItem("6", "Ailton mala"));
					lista.add(new HFSItem("7", "Roberto Aquino"));
					return lista;
				}
				@Override
				public List<HFSItem> onCarregarAoDigitar(String consulta) {
					List<HFSItem> lista = new ArrayList<HFSItem>();
					lista.add(new HFSItem("0", "Henrique Figueiredo"));
					lista.add(new HFSItem("1", "Raul Raposo"));
					lista.add(new HFSItem("2", "Márcio Calado"));
					lista.add(new HFSItem("3", "Rafael Caneca"));
					lista.add(new HFSItem("4", "Luiz Henrique"));
					lista.add(new HFSItem("5", "Carlos lenhador"));
					lista.add(new HFSItem("6", "Ailton mala"));
					lista.add(new HFSItem("7", "Roberto Aquino"));
					return lista;
				}
			});
		}
		return suggestBox2;
	}
}
