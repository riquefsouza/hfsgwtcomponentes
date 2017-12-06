package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.menuxml.MXEstrutura;
import com.hfsgwt.client.componentes.menuxml.MXItem;
import com.hfsgwt.client.componentes.menuxml.MXMenu;
import com.hfsgwt.client.componentes.menuxml.MXSistema;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSMenu extends Composite {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public interface CarregarHandler extends EventHandler {
		void onCarregar(final MXEstrutura mx);
	}
	
	private CarregarHandler carregarHandler;
	private MenuBar barraMenu;
	private MenuItem[][] subItem;	

	private String largura;
	private boolean habilitarAnimacao;
	private boolean abrirAutomaticamente;
	private String codSistema;

	public HFSMenu(String largura, boolean habilitarAnimacao,
			boolean abrirAutomaticamente, String codigoSistema) {
		this.largura = largura;
		this.habilitarAnimacao = habilitarAnimacao;
		this.abrirAutomaticamente = abrirAutomaticamente;
		this.codSistema = codigoSistema;
		initComponents();
		carregaMX(codigoSistema);
	}

	private void initComponents() {
		initWidget(getBarraMenu());
	}

	private MenuBar getBarraMenu() {
		if (barraMenu == null) {
			barraMenu = new MenuBar(false);
			barraMenu.setAnimationEnabled(this.habilitarAnimacao);
			barraMenu.setAutoOpen(this.abrirAutomaticamente);
			barraMenu.setWidth(largura);
		}
		return barraMenu;
	}

	private MenuItem getMenu(MenuBar subMenu, String rotulo) {
		MenuItem menuCadastros = new MenuItem(rotulo, false, subMenu);
		return menuCadastros;
	}

	private MenuItem getSubItem(int ordem, int subordem, String rotulo) {
		if (subItem[ordem][subordem] == null) {
			subItem[ordem][subordem] = new MenuItem(rotulo, false, (Command) null);
		}
		return subItem[ordem][subordem];
	}

	private void criarMenu(MXEstrutura mx) {
		MenuItem mi;
		MenuBar subMenuBar;
		MXSistema sistema;
		MXMenu menu;

		if (mx.getSistemas().length > 0) {
			sistema = mx.getSistemas()[0];

			if (sistema.getMenus().length > 0) {
				menu = sistema.getMenus()[0];

				if (menu.getItems().length > 0) {
					subItem = new MenuItem[sistema.getMenus().length][menu
							.getItems().length];
				}
			}
		}

		int ordem;
		int subordem;
		if (mx.getSistemas().length > 0) {
			sistema = mx.getSistemas()[0];

			ordem = 0;
			for (int j = 0; j < sistema.getMenus().length; j++) {
				menu = sistema.getMenus()[j];

				subMenuBar = new MenuBar(true);
				mi = getMenu(subMenuBar, menu.getLabel());

				subordem = 0;
				for (MXItem subItem : menu.getItems()) {
					subMenuBar.addItem(getSubItem(ordem, subordem, subItem
							.getLabel()));
					subordem++;
				}
				this.getBarraMenu().addItem(mi);
				ordem++;
			}
		}		
	}

	private void carregaMX(String codigoSistema) {
		final HFSLoading dlg = HFSLoading.mostrar();

		servico.carregaMX(codigoSistema, new AsyncCallback<MXEstrutura>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "carregaMX");
			}

			public void onSuccess(MXEstrutura mx) {
				criarMenu(mx);
				dlg.hide();

				if (carregarHandler != null) {
					carregarHandler.onCarregar(mx);
				}
			}
		});
	}

	public MenuItem getSubMenu(int ordem, int subordem){
		return subItem[ordem][subordem];
	}

	public MenuItem getSubMenu(MXEstrutura mx, String idMenu, String idSubMenu){
		int ordem = mx.getOrdemPeloCodigo(codSistema, idMenu);
		int subordem = mx.getSubOrdemPeloCodigo(codSistema, idMenu, idSubMenu);
		return subItem[ordem][subordem];
	}
	
	public void addCarregarHandler(CarregarHandler handler) {
		this.carregarHandler = handler;
	}

}
