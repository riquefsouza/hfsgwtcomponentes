package com.hfsgwt.client.componentes.stackpanel;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSLoading;
import com.hfsgwt.client.componentes.menuxml.MXEstrutura;
import com.hfsgwt.client.componentes.menuxml.MXItem;
import com.hfsgwt.client.componentes.menuxml.MXMenu;
import com.hfsgwt.client.componentes.menuxml.MXSistema;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSStackPanel extends Composite {
	private StackPanel painel;
	private StackLayoutPanel layoutPainel;

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	public interface LinkHandler extends EventHandler {
		void onLinkClick(String id, String rotulo);
	}

	private ArrayList<HFSStackItem> items;
	private LinkHandler linkHandler;
	private String largura;
	private String altura;
	private boolean usarLayoutPanel;

	public HFSStackPanel(String largura, String altura,
			boolean usarLayoutPanel, ArrayList<HFSStackItem> items) {
		this.largura = largura;
		this.altura = altura;
		this.usarLayoutPanel = usarLayoutPanel;
		this.items = items;
		
		initComponents();
		criarStackPanel();
	}

	public HFSStackPanel(String largura, String altura,
			boolean usarLayoutPanel, String codigoSistema) {
		this.largura = largura;
		this.altura = altura;
		this.usarLayoutPanel = usarLayoutPanel;

		initComponents();
		carregaMX(codigoSistema);
	}

	private void initComponents() {
		initWidget(getPainel());
	}

	private StackPanel getPainel() {
		if (painel == null) {
			painel = new StackPanel();
			painel.setSize(largura, altura);
		}
		return painel;
	}

	public StackLayoutPanel getLayoutPainel() {
		if (layoutPainel == null) {
			layoutPainel = new StackLayoutPanel(Unit.EM);
			layoutPainel.setSize(largura, altura);
		}
		return layoutPainel;
	}

	private Anchor criarLink(String id, String texto) {
		Anchor link = new Anchor(texto);
		link.setName(id);
		if (usarLayoutPanel){
			link.setStyleName("HFSStackPanel-Link");
		}
		link.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (linkHandler != null) {
					Anchor link = (Anchor) event.getSource();
					linkHandler.onLinkClick(link.getName(), link.getText());					
				}
			}
		});
		return link;
	}

	public void addLinkHandler(LinkHandler handler) {
		this.linkHandler = handler;
	}

	private HTML getMenuHeader(String titulo) {
		//titulo="<div class='{HFSStackPanel-MenuHeader}'><div/>"+titulo+"</div>";
		HTML menuHeader = new HTML(titulo);
		return menuHeader;
	}

	private void criarStackPanel() {
		for (HFSStackItem item : this.items) {
			if (usarLayoutPanel) {
				FlowPanel stackPanel = new FlowPanel();					
				for (HFSItem link : item.getLinks()) {
					stackPanel.add(criarLink(link.getId(), link.getValor()));
				}
				stackPanel.setWidth("100%");
				
				ScrollPanel conteudoPanel = new ScrollPanel();
				conteudoPanel.setStyleName("HFSStackPanel-ConteudoPanel");
				conteudoPanel.setWidget(stackPanel);
				this.getLayoutPainel().add(conteudoPanel,
				getMenuHeader(item.getItem()), 2.0);
				
//				this.getLayoutPainel().add(stackPanel,
//						getMenuHeader(item.getItem()), 2.0);
			} else {
				VerticalPanel stackPanel = new VerticalPanel();
				for (HFSItem link : item.getLinks()) {
					stackPanel.add(criarLink(link.getId(), link.getValor()));
				}
				
				ScrollPanel conteudoPanel = new ScrollPanel();
				conteudoPanel.setStyleName("HFSStackPanel-ConteudoPanel");
				conteudoPanel.setWidget(stackPanel);
				this.getPainel().add(conteudoPanel, item.getItem(), false);
				
//				this.getPainel().add(stackPanel, item.getItem(), false);
			}
		}
	}

	private void criarStackPanel(MXEstrutura mx) {
		MXSistema sistema;
		MXMenu menu;

		for (int i = 0; i < mx.getSistemas().length; i++) {
			this.items = new ArrayList<HFSStackItem>();

			sistema = mx.getSistemas()[i];
			for (int j = 0; j < sistema.getMenus().length; j++) {
				menu = sistema.getMenus()[j];

				ArrayList<HFSItem> links = new ArrayList<HFSItem>();
				for (MXItem item : menu.getItems()) {
					links.add(new HFSItem(item.getCodigo(), item.getLabel()));
				}
				this.items.add(new HFSStackItem(menu.getLabel(), links));
			}
		}
		criarStackPanel();
		// mxEstrutura = mx;
	}

	private void carregaMX(String codigoSistema) {
		final HFSLoading dlg = HFSLoading.mostrar();

		servico.carregaMX(codigoSistema, new AsyncCallback<MXEstrutura>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "carregaMX");				
			}

			public void onSuccess(MXEstrutura mx) {				
				criarStackPanel(mx);
				dlg.hide();
			}
		});
	}

}
