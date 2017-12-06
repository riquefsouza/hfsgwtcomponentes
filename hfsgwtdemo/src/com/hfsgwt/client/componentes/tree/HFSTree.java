package com.hfsgwt.client.componentes.tree;

import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.HFSLoading;
import com.hfsgwt.client.componentes.menuxml.MXEstrutura;
import com.hfsgwt.client.componentes.menuxml.MXItem;
import com.hfsgwt.client.componentes.menuxml.MXMenu;
import com.hfsgwt.client.componentes.menuxml.MXSistema;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSTree extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public interface ArvoreSelecaoHandler extends EventHandler {
		void onSelecao(MXItem mxitem);
	}

	public interface ArvoreStrPorDemandaHandler extends EventHandler {
		void onSelecao(TreeItem itemSelecionado, boolean itemAberto,
				String[] caminho);

		void onAbrir(TreeItem itemAberto);
	}

	public interface ArvoreHfsPorDemandaHandler extends EventHandler {
		void onSelecao(HFSTreeItem itemSelecionado, boolean itemAberto,
				HFSTreeItem[] caminho);

		void onAbrir(HFSTreeItem item);
	}

	private ArvoreSelecaoHandler arvoreSelecaoHandler;
	private ArvoreStrPorDemandaHandler arvoreStrPorDemandaHandler;
	private ArvoreHfsPorDemandaHandler arvoreHfsPorDemandaHandler;

	private SimplePanel simplePanel;
	private DecoratorPanel decoratorPanel;
	private ScrollPanel scrollPanel;
	private Tree arvore;

	private String largura;
	private String altura;
	private boolean habilitarAnimacao;
	private String codigoSistema;
	private boolean mostrarRaiz;
	private boolean porDemanda;

	private MXEstrutura mxEstrutura;
	private TreeItem itemAberto;
	private boolean porHFSTreeItem;
	private HFSTreeItem hfsItemSelec;

	public HFSTree(String largura, String altura, boolean habilitarAnimacao,
			boolean mostrarRaiz, boolean bordaDecorada, String codigoSistema) {
		this.largura = largura;
		this.altura = altura;
		this.habilitarAnimacao = habilitarAnimacao;
		this.codigoSistema = codigoSistema;
		this.mostrarRaiz = mostrarRaiz;

		if (largura.trim().length() == 0)
			largura = "5px";
		if (altura.trim().length() == 0)
			altura = "5px";

		initComponents(bordaDecorada);
		if (codigoSistema.trim().length() > 0) {
			porDemanda = false;
			carregaMX(codigoSistema);
		} else
			porDemanda = true;
	}

	public HFSTree(String largura, String altura, boolean habilitarAnimacao,
			boolean mostrarRaiz, boolean bordaDecorada, String raiz,
			String[] items) {
		this(largura, altura, habilitarAnimacao, mostrarRaiz, bordaDecorada, "");
		this.porHFSTreeItem = false;
		iniciarArvorePorDemanda(raiz, items);
	}

	public HFSTree(String largura, String altura, boolean habilitarAnimacao,
			boolean mostrarRaiz, boolean bordaDecorada, String raiz,
			HFSTreeItem[] items) {
		this(largura, altura, habilitarAnimacao, mostrarRaiz, bordaDecorada, "");
		this.porHFSTreeItem = true;
		iniciarArvorePorDemanda(raiz, items);
	}

	public HFSTree(String largura, String altura, boolean habilitarAnimacao,
			boolean mostrarRaiz, boolean bordaDecorada, boolean porHFSTreeItem) {
		this(largura, altura, habilitarAnimacao, mostrarRaiz, bordaDecorada, "");
		this.porHFSTreeItem = porHFSTreeItem;
	}
	
	private void initComponents(boolean bordaDecorada) {
		if (bordaDecorada)
			initWidget(getDecoratorPanel());
		else
			initWidget(getSimplePanel());
	}

	private SimplePanel getSimplePanel() {
		if (simplePanel == null) {
			simplePanel = new SimplePanel();
			simplePanel.setWidget(getScrollPanel());
		}
		return simplePanel;
	}

	private DecoratorPanel getDecoratorPanel() {
		if (decoratorPanel == null) {
			decoratorPanel = new DecoratorPanel();
			decoratorPanel.setWidget(getScrollPanel());
		}
		return decoratorPanel;
	}

	private ScrollPanel getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new ScrollPanel();
			scrollPanel.setSize(this.largura, this.altura);
			scrollPanel.setWidget(getArvore());
		}
		return scrollPanel;
	}

	private Tree getArvore() {
		if (arvore == null) {
			arvore = new Tree();
			arvore.addSelectionHandler(new SelectionHandler<TreeItem>() {
				public void onSelection(SelectionEvent<TreeItem> event) {
					if (porDemanda) {
						if (!porHFSTreeItem) {
							if (arvoreStrPorDemandaHandler != null) {
								TreeItem item = event.getSelectedItem();
								Vector<String> vetor = new Vector<String>();
								while (item != null) {
									vetor.add(item.getText());
									item = item.getParentItem();
								}
								String[] vcaminho = (String[]) vetor
										.toArray(new String[vetor.size()]);
								arvoreStrPorDemandaHandler
										.onSelecao(event.getSelectedItem(),
												event.getSelectedItem()
														.getState(), vcaminho);
							}
						} else {
							if (arvoreHfsPorDemandaHandler != null) {
								if (hfsItemSelec != null) {
									hfsItemSelec.setSelecionado(false);
								}
								TreeItem item = event.getSelectedItem();
								hfsItemSelec = (HFSTreeItem) item.getWidget();
								hfsItemSelec.setSelecionado(true);

								Vector<HFSTreeItem> vetor = new Vector<HFSTreeItem>();
								while (item != null) {
									HFSTreeItem hfs = (HFSTreeItem) item
											.getWidget();
									vetor.add(hfs);
									item = item.getParentItem();
								}
								HFSTreeItem[] vcaminho = (HFSTreeItem[]) vetor
										.toArray(new HFSTreeItem[vetor.size()]);
								arvoreHfsPorDemandaHandler.onSelecao(
										hfsItemSelec, event.getSelectedItem()
												.getState(), vcaminho);
							}
						}
					} else {
						if (arvoreSelecaoHandler != null) {
							TreeItem item = event.getSelectedItem();
							TreeItem itemMenu = item.getParentItem();
							if (itemMenu != null) {
								MXItem mxitem = mxEstrutura.getItemPeloCodigo(
										codigoSistema, itemMenu.getText(), item
												.getText());
								arvoreSelecaoHandler.onSelecao(mxitem);
							}
						}
					}
				}
			});
			arvore.addOpenHandler(new OpenHandler<TreeItem>() {
				public void onOpen(OpenEvent<TreeItem> event) {
					if (porDemanda) {
						if (!porHFSTreeItem) {
							if (arvoreStrPorDemandaHandler != null) {
								itemAberto = event.getTarget();
								if (itemAberto.getChildCount() == 1) {
									arvoreStrPorDemandaHandler
											.onAbrir(itemAberto);
								}
							}
						} else {
							if (arvoreHfsPorDemandaHandler != null) {
								itemAberto = event.getTarget();
								if (itemAberto.getChildCount() == 1) {
									HFSTreeItem hfsItemAberto = (HFSTreeItem) itemAberto
											.getWidget();
									arvoreHfsPorDemandaHandler
											.onAbrir(hfsItemAberto);
								}
							}
						}
					}
				}
			});
			arvore.setAnimationEnabled(this.habilitarAnimacao);
			arvore.setSize("100%", "100%");
		}
		return arvore;
	}

	public void iniciarArvorePorDemanda(String raiz, String[] items) {
		TreeItem item;
		TreeItem itemRaiz = new TreeItem();
		if (mostrarRaiz)
			itemRaiz = this.getArvore().addTextItem(raiz);
		for (int i = 0; i < items.length; i++) {
			if (mostrarRaiz)
				item = itemRaiz.addTextItem(items[i]);
			else
				item = this.getArvore().addTextItem(items[i]);

			// adicionar items temporariamente para poder expandir os nodes
			item.addTextItem("");
		}
	}

	public void iniciarArvorePorDemanda(String raiz, HFSTreeItem[] items) {
		TreeItem item;
		TreeItem itemRaiz = new TreeItem();
		if (mostrarRaiz){			
			itemRaiz = this.getArvore().addTextItem(raiz);			
		}

		for (int i = 0; i < items.length; i++) {
			items[i].setProfundidade(1);
			if (mostrarRaiz) 				
				item = itemRaiz.addItem(items[i]);
			else
				item = this.getArvore().addItem(items[i]);

			// adicionar items temporariamente para poder expandir os nodes
			item.addTextItem("");
		}
	}

	public void addItemsPorDemanda(String[] items) {
		if (porDemanda) {
			if (items.length > 0) {
				TreeItem item = itemAberto;
				if (item != null) {
					// Fecha o item imediatamente
					item.setState(false, false);

					for (int i = 0; i < items.length; i++) {
						TreeItem filho = item.addTextItem(items[i]);
						filho.addTextItem("");
					}

					// Remove o item temporario quando acabamos de carregar
					item.getChild(0).remove();

					// Reabre o item
					item.setState(true, false);
				}
			}
		}
	}

	public void addItemsPorDemanda(HFSTreeItem[] items, int profundidade) {
		if (porDemanda) {
			if (items.length > 0) {
				TreeItem item = itemAberto;
				if (item != null) {
					// Fecha o item imediatamente
					item.setState(false, false);

					for (int i = 0; i < items.length; i++) {
						items[i].setProfundidade(profundidade);
						TreeItem filho = item.addItem(items[i]);
						filho.addTextItem("");
					}

					// Remove o item temporario quando acabamos de carregar
					item.getChild(0).remove();

					// Reabre o item
					item.setState(true, false);
				}
			}
		}
	}

	private void addSecao(TreeItem itemPai, String rotulo, MXItem[] subItems) {
		TreeItem secao = itemPai.addTextItem(rotulo);
		for (MXItem subItem : subItems) {
			secao.addTextItem(subItem.getLabel());
		}
	}

	private void addSecao(TreeItem itemPai, MXItem[] subItems) {
		for (MXItem subItem : subItems) {
			itemPai.addTextItem(subItem.getLabel());
		}
	}

	private void criarArvore(MXEstrutura mx) {
		TreeItem arvoreItem;
		MXSistema sistema;
		MXMenu menu;
		for (int i = 0; i < mx.getSistemas().length; i++) {
			sistema = mx.getSistemas()[i];
			if (mostrarRaiz) {
				arvoreItem = this.arvore.addTextItem(sistema.getLabel());

				for (int j = 0; j < sistema.getMenus().length; j++) {
					menu = sistema.getMenus()[j];
					addSecao(arvoreItem, menu.getLabel(), menu.getItems());
				}
			} else {
				for (int j = 0; j < sistema.getMenus().length; j++) {
					menu = sistema.getMenus()[j];
					arvoreItem = this.arvore.addTextItem(menu.getLabel());
					addSecao(arvoreItem, menu.getItems());
				}
			}
		}
		mxEstrutura = mx;
	}

	private void carregaMX(String codigoSistema) {
		final HFSLoading dlg = HFSLoading.mostrar();

		servico.carregaMX(codigoSistema, new AsyncCallback<MXEstrutura>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "carregaMX");
			}

			public void onSuccess(MXEstrutura mx) {
				criarArvore(mx);
				dlg.hide();
			}
		});
	}

	public void addArvoreSelecaoHandler(ArvoreSelecaoHandler handler) {
		this.arvoreSelecaoHandler = handler;
	}

	public void addArvoreStrPorDemandaHandler(ArvoreStrPorDemandaHandler handler) {
		this.arvoreStrPorDemandaHandler = handler;
	}

	public void addArvoreHfsPorDemandaHandler(ArvoreHfsPorDemandaHandler handler) {
		this.arvoreHfsPorDemandaHandler = handler;
	}
}
