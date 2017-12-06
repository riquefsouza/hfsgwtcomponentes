package com.hfsgwt.client.componentes.tabpanel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

public class HFSTabPanel extends Composite {
	private DecoratedTabPanel tabPanel;

	private String largura;
	private String altura;
	private HFSTabItem[] items;
	boolean habilitaAnimacao;

	public HFSTabPanel(String largura, String altura,
			HFSTabItem[] items, boolean habilitaAnimacao) {
		this.largura = largura;
		this.altura = altura;
		this.items = items;
		this.habilitaAnimacao = habilitaAnimacao;

		initComponents();
	}

	private void initComponents() {
		initWidget(getTabPanel());
	}

	private DecoratedTabPanel getTabPanel() {
		if (tabPanel == null) {
			tabPanel = new DecoratedTabPanel();
			tabPanel.setAnimationEnabled(this.habilitaAnimacao);
			tabPanel.setSize(this.largura, this.altura);

			for (HFSTabItem item : this.items) {
				tabPanel.add(item.getPainel(), item.getRotulo(), item
						.isComoHTML());
			}
			if (this.items.length > 0) {
				tabPanel.selectTab(0);
			}
		}
		return tabPanel;
	}

	public void selecionaAba(int indice) {
		this.getTabPanel().selectTab(indice);
	}
}
