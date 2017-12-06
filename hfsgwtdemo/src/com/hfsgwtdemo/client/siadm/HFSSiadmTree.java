package com.hfsgwtdemo.client.siadm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hfsgwt.client.componentes.HFSLoading;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.tree.HFSTree;
import com.hfsgwt.client.componentes.tree.HFSTreeItem;
import com.hfsgwt.client.componentes.util.HFSUtil;
import com.hfsgwtdemo.client.DemoService;
import com.hfsgwtdemo.client.DemoServiceAsync;

public class HFSSiadmTree extends HFSTree {

	private final DemoServiceAsync servico = GWT.create(DemoService.class);

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public HFSSiadmTree(String largura, String altura,
			boolean habilitarAnimacao, boolean mostrarRaiz,
			boolean bordaDecorada) {
		super(largura, altura, habilitarAnimacao, mostrarRaiz, bordaDecorada,
				true);
		listarOrgaos(1);

		this
				.addArvoreHfsPorDemandaHandler(new HFSTree.ArvoreHfsPorDemandaHandler() {
					@Override
					public void onSelecao(HFSTreeItem itemSelecionado,
							boolean itemAberto, HFSTreeItem[] caminho) {
						if (!itemAberto) {
							String scaminho = "";
							for (int i = 0; i < caminho.length; i++) {
								scaminho += caminho[i].getTexto() + "|";
							}
							// Window.alert(scaminho + ": "
							// + itemSelecionado.getTexto());
						}
					}

					@Override
					public void onAbrir(HFSTreeItem item) {
						// TreeItem itemPai = item.getParentItem();
						if (item.getProfundidade() == 1)
							listarSetores(Integer.parseInt(item.getId()));
						else if (item.getProfundidade() == 2)
							listarUsuarios(Integer.parseInt(item.getId()));
					}
				});

	}

	private void listarOrgaos(int codigoPai) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Orgãos...");

		servico.listarOrgao(codigoPai, new AsyncCallback<SiadmOrgao[]>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "listarOrgao");
				dlg.hide();
			}

			public void onSuccess(SiadmOrgao[] orgaos) {
				if (orgaos.length > 0) {
					HFSTreeItem[] items = new HFSTreeItem[orgaos.length];
					for (int i = 0; i < orgaos.length; i++) {
						items[i] = new HFSTreeItem(img.siadmTreeOrgao(),
								Integer.toString(orgaos[i].getCodigo()),
								orgaos[i].getNome());
					}
					iniciarArvorePorDemanda("Raiz", items);
				}
				dlg.hide();
			}
		});
	}

	private void listarSetores(int codigoOrgao) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Setores...");

		servico.listarSetor(codigoOrgao, new AsyncCallback<SiadmSetor[]>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "listarSetor");
				dlg.hide();
			}

			public void onSuccess(SiadmSetor[] setores) {
				if (setores.length > 0) {
					HFSTreeItem[] items = new HFSTreeItem[setores.length];
					for (int i = 0; i < setores.length; i++) {
						items[i] = new HFSTreeItem(img.siadmTreeSetor(),
								Integer.toString(setores[i].getCodigo()),
								setores[i].getNome());
					}
					addItemsPorDemanda(items, 2);
				}
				dlg.hide();
			}
		});
	}

	private void listarUsuarios(int codigoSetor) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Usuários...");

		servico.listarUsuarioPeloSetor(codigoSetor,
				new AsyncCallback<SiadmUsuario[]>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"listarUsuario");
						dlg.hide();
					}

					public void onSuccess(SiadmUsuario[] usuarios) {
						if (usuarios.length > 0) {
							HFSTreeItem[] items = new HFSTreeItem[usuarios.length];
							for (int i = 0; i < usuarios.length; i++) {
								items[i] = new HFSTreeItem(img
										.siadmTreeUsuario(), usuarios[i]
										.getCodigo(), usuarios[i].getNome());
							}
							addItemsPorDemanda(items, 3);
						}
						dlg.hide();
					}
				});
	}

}
