package com.hfsgwtdemo.client.demo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.HFSNavigator;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoVisivel;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoTamanho;

public class DemoNavigator extends Composite {
	private Grid grid;
	private HFSNavigator navigator;
	private HFSNavigator navigator_1;

	public DemoNavigator() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 1);
			grid.setCellSpacing(2);
			grid.setCellPadding(2);
			grid.setWidget(0, 0, getNavigator());
			grid.setWidget(1, 0, getNavigator_1());
		}
		return grid;
	}

	private HFSNavigator getNavigator() {
		if (navigator == null) {
			navigator = new HFSNavigator(BotaoVisivel.PADRAO,
					BotaoTamanho.GRANDE, false);
		}
		return navigator;
	}

	private HFSNavigator getNavigator_1() {
		if (navigator_1 == null) {
			navigator_1 = new HFSNavigator(BotaoVisivel.PADRAO,
					BotaoTamanho.PEQUENO, false);
		}
		return navigator_1;
	}
}
