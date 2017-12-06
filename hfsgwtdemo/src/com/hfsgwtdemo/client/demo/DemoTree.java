package com.hfsgwtdemo.client.demo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TreeItem;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.menuxml.MXItem;
import com.hfsgwt.client.componentes.tree.HFSTree;
import com.hfsgwt.client.componentes.tree.HFSTreeItem;

public class DemoTree extends Composite {
	private Grid grid;
	private HFSTree arvore1;
	private HFSTree arvore2;
	private HFSTree arvore3;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public DemoTree() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(1, 3);
			grid.setWidget(0, 0, getArvore1());
			grid.setWidget(0, 1, getArvore2());
			grid.setWidget(0, 2, getArvore3());
		}
		return grid;
	}

	private HFSTree getArvore1() {
		if (arvore1 == null) {
			arvore1 = new HFSTree("300px", "300px", true, false, true,
					"HFS_GWT_COMPONENTES_DEMO");
			arvore1.addArvoreSelecaoHandler(new HFSTree.ArvoreSelecaoHandler() {
				@Override
				public void onSelecao(MXItem mxitem) {
					if (mxitem.getCodigo().equals("HFSCalculator")) {
						Window.alert(mxitem.getLink());
					}
				}
			});

		}
		return arvore1;
	}

	private HFSTree getArvore2() {
		if (arvore2 == null) {
			String[] items = new String[5];
			items[0] = "item001";
			items[1] = "item002";
			items[2] = "item003";
			items[3] = "item004";
			items[4] = "item005";
			arvore2 = new HFSTree("300px", "300px", true, false, true, "raiz",
					items);
			arvore2
					.addArvoreStrPorDemandaHandler(new HFSTree.ArvoreStrPorDemandaHandler() {
						@Override
						public void onSelecao(TreeItem itemSelecionado,
								boolean itemAberto, String[] caminho) {
							if (!itemAberto) {
								String scaminho = "";
								for (int i = 0; i < caminho.length; i++) {
									scaminho += caminho[i] + "|";
								}
								Window.alert(scaminho + ": "
										+ itemSelecionado.getText());
							}
						}

						@Override
						public void onAbrir(TreeItem item) {
							// TreeItem itemPai = item.getParentItem();
							String[] items = new String[5];
							items[0] = "subitem001";
							items[1] = "subitem002";
							items[2] = "subitem003";
							items[3] = "subitem004";
							items[4] = "subitem005";
							arvore2.addItemsPorDemanda(items);
						}
					});
		}
		return arvore2;
	}

	private HFSTree getArvore3() {
		if (arvore3 == null) {
			HFSTreeItem[] items = new HFSTreeItem[5];
			items[0] = new HFSTreeItem(img.siadmTreeOrgao(), "1", "item001");
			items[1] = new HFSTreeItem(img.siadmTreeOrgao(), "2", "item002");
			items[2] = new HFSTreeItem(img.siadmTreeOrgao(), "3", "item003");
			items[3] = new HFSTreeItem(img.siadmTreeOrgao(), "4", "item004");
			items[4] = new HFSTreeItem(img.siadmTreeOrgao(), "5", "item005");
			arvore3 = new HFSTree("300px", "300px", true, false, true, "raiz",
					items);
			arvore3
					.addArvoreHfsPorDemandaHandler(new HFSTree.ArvoreHfsPorDemandaHandler() {
						@Override
						public void onSelecao(HFSTreeItem itemSelecionado,
								boolean itemAberto, HFSTreeItem[] caminho) {
							if (!itemAberto) {
								String scaminho = "";
								for (int i = 0; i < caminho.length; i++) {
									scaminho += caminho[i].getTexto() + "|";
								}
								Window.alert(scaminho + ": "
										+ itemSelecionado.getTexto());
							}
						}

						@Override
						public void onAbrir(HFSTreeItem item) {
							// TreeItem itemPai = item.getParentItem();
							HFSTreeItem[] items = new HFSTreeItem[5];
							items[0] = new HFSTreeItem(img.siadmTreeSetor(),
									"1", "subitem001");
							items[1] = new HFSTreeItem(img.siadmTreeSetor(),
									"2", "subitem002");
							items[2] = new HFSTreeItem(img.siadmTreeSetor(),
									"3", "subitem003");
							items[3] = new HFSTreeItem(img.siadmTreeSetor(),
									"4", "subitem004");
							items[4] = new HFSTreeItem(img.siadmTreeSetor(),
									"5", "subitem005");
							arvore3.addItemsPorDemanda(items, 2);
						}
					});
		}
		return arvore3;
	}

}
