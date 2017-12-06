package com.hfsgwt.client.application;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSLogin;
import com.hfsgwt.client.componentes.HFSMenu;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.stackpanel.HFSStackPanel;
import com.hfsgwt.client.componentes.tree.HFSTree;

public class HFSApplication {

	public enum TipoMenu {
		STACKPANEL, TREE, MENU
	}

	public interface AppClickHandler extends EventHandler {
		void onSobreClick();
		void onDesconectarClick();
	}
	
	private AppClickHandler appClickHandler;
	private TipoMenu tipoMenu;
	private DockLayoutPanel dockLayoutPanel;
	private HTML htmlTitulo;
	private Anchor linkSobre;
	private Anchor linkDesconectar;
	private HorizontalPanel sobrePanel;
	private Image imageLogo;
	private HorizontalPanel tituloPanel;
	private HorizontalPanel nortePanel;
	private VerticalPanel verticalPanel;
	private HFSStackPanel stackPanel;
	private SplitLayoutPanel splitLayoutPanel;
	private ScrollPanel conteudoPanel;
	private HFSTree arvore;
	private HFSMenu menu;

	private String tituloSistema;
	private String subTituloSistema;
	private HorizontalPanel horizontalPanel;
	private HTML htmlSubTitulo;
	private String codigoSistema;

	private RootLayoutPanel rootPanel;
	private DockPanel dockPanel;
	private Image imageInicial;
	private HFSListBox listBox;
	private boolean usarLogin;

	public void onModuleLoad(final TipoMenu tipoMenu, ImageResource imageInicial, boolean usarLogin,
			boolean usarServidorLogin, ImageResource imageLogo, final String codigoSistema,
			final String tituloSistema, final String subTituloSistema) {
		rootPanel = RootLayoutPanel.get();
		// RootPanel rootPanel = RootPanel.get();
		this.tipoMenu = tipoMenu;
		if (imageInicial!=null)
			this.imageInicial = new Image(imageInicial);
		this.usarLogin = usarLogin;
		if (imageLogo!=null)
			this.imageLogo = new Image(imageLogo);

		if (usarLogin) {
			HFSLogin login = new HFSLogin(usarServidorLogin);
			login.addLoginHandler(new HFSLogin.LoginHandler() {
				@Override
				public boolean validarLogin(String login, String senha) {
					if (login.equals("02685748474") && senha.equals("B5385CA"))
						return true;
					else
						return false;
				}

				@Override
				public boolean alterarSenha(String login, String senha,
						String novaSenha) {
					if (login.equals("02685748474") && senha.equals("B5385CA"))
						return true;
					else
						return false;
				}

				@Override
				public void onLogin(boolean loginRealizado) {
					if (loginRealizado) {
						rootPanel.clear();						
						iniciarAplicacao(rootPanel, codigoSistema,
								tituloSistema, subTituloSistema);
					} else
						Window.alert("login sem sucesso!");
				}

				@Override
				public void onMaximoTentativas(int maximoTentativas) {
					Window.alert("Login sem sucesso em "+maximoTentativas+" tentativas, você será desconectado!");					
				}
			});

			rootPanel.add(getDockPanel(login));
		} else
			iniciarAplicacao(rootPanel, codigoSistema, tituloSistema,
					subTituloSistema);
	}

	private DockPanel getDockPanel(HFSLogin login) {
		if (dockPanel == null) {
			dockPanel = new DockPanel();
			dockPanel.setSize("100%", "100%");

			if (imageInicial!=null) {
				dockPanel.add(imageInicial, DockPanel.NORTH);
				dockPanel.setCellVerticalAlignment(imageInicial,
						HasVerticalAlignment.ALIGN_MIDDLE);
				dockPanel.setCellHorizontalAlignment(imageInicial,
						HasHorizontalAlignment.ALIGN_CENTER);
			}
			
			dockPanel.add(getListBox(), DockPanel.NORTH);
			dockPanel.setCellVerticalAlignment(getListBox(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			dockPanel.setCellHorizontalAlignment(getListBox(),
					HasHorizontalAlignment.ALIGN_CENTER);
			
			dockPanel.add(login, DockPanel.CENTER);
			dockPanel.setCellVerticalAlignment(login,
					HasVerticalAlignment.ALIGN_MIDDLE);
			dockPanel.setCellHorizontalAlignment(login,
					HasHorizontalAlignment.ALIGN_CENTER);
			
		}
		return dockPanel;
	}

	private HFSListBox getListBox() {
		if (listBox == null) {
			ArrayList<HFSItem> listbox_items = new ArrayList<HFSItem>();
			listbox_items.add(new HFSItem("1", TipoMenu.STACKPANEL.name()));
			listbox_items.add(new HFSItem("2", TipoMenu.TREE.name()));
			listbox_items.add(new HFSItem("3", TipoMenu.MENU.name()));
			
			listBox = new HFSListBox(PosicaoRotulo.ESQUERDA, TipoLista.COMBOBOX,
					"Escolha o tipo de Layout: ", 150, false, false);
			listBox.setItems(listbox_items);
		}
		return listBox;
	}
	
	private void iniciarAplicacao(RootLayoutPanel rootPanel,
			String codigoSistema, String tituloSistema, String subTituloSistema) {
		
		if (usarLogin){
			tipoMenu = TipoMenu.valueOf(getListBox().getItemSelecionado().getValor());
		}
		
		this.codigoSistema = codigoSistema;

		if (tituloSistema.trim().equals(""))
			this.tituloSistema = "Título do Sistema";
		else
			this.tituloSistema = tituloSistema;

		if (subTituloSistema.trim().equals(""))
			this.subTituloSistema = "Sub Título do Sistema";
		else
			this.subTituloSistema = subTituloSistema;

		Window.enableScrolling(false);
		Window.setMargin("0px");

		Element topElem = getDockLayoutPanel().getWidgetContainerElement(
				getNortePanel());
		topElem.getStyle().setZIndex(2);
		topElem.getStyle().setOverflow(Overflow.VISIBLE);

		rootPanel.add(getDockLayoutPanel());
	}

	private DockLayoutPanel getDockLayoutPanel() {
		if (dockLayoutPanel == null) {
			dockLayoutPanel = new DockLayoutPanel(Unit.EM);
			if (Navigator.getAppName().equals("Microsoft Internet Explorer"))
				dockLayoutPanel.addNorth(getNortePanel(), 6.3);
			else //Navigator.getAppName().equals("Netscape")
				dockLayoutPanel.addNorth(getNortePanel(), 6.7);

			if (tipoMenu == TipoMenu.MENU) {
				dockLayoutPanel.addNorth(getMenu(), 2.0);
				dockLayoutPanel.add(getConteudoPanel());
			} else
				dockLayoutPanel.add(getSplitLayoutPanel());
		}
		return dockLayoutPanel;
	}

	private HTML getHtmlTitulo() {
		if (htmlTitulo == null) {
			htmlTitulo = new HTML("<big><big><big><b>" + this.tituloSistema
					+ "</b></big></big></big>", true);
			htmlTitulo
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return htmlTitulo;
	}

	private Anchor getLinkSobre() {
		if (linkSobre == null) {
			linkSobre = new Anchor("Sobre o sistema");
			linkSobre.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (appClickHandler!=null){
						appClickHandler.onSobreClick();
					}					
				}
			});
		}
		return linkSobre;
	}

	private Anchor getLinkDesconectar() {
		if (linkDesconectar == null) {
			linkDesconectar = new Anchor("Desconectar");
			linkDesconectar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (appClickHandler!=null){
						appClickHandler.onDesconectarClick();
					}					
				}
			});
		}
		return linkDesconectar;
	}

	private HorizontalPanel getSobrePanel() {
		if (sobrePanel == null) {
			sobrePanel = new HorizontalPanel();
			sobrePanel
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
			sobrePanel.setSpacing(5);
			sobrePanel.add(getLinkSobre());
			sobrePanel.add(getLinkDesconectar());
		}
		return sobrePanel;
	}

	private HorizontalPanel getTituloPanel() {
		if (tituloPanel == null) {
			tituloPanel = new HorizontalPanel();
			tituloPanel.setWidth("100%");
			tituloPanel.add(getHtmlTitulo());
		}
		return tituloPanel;
	}

	private HorizontalPanel getNortePanel() {
		if (nortePanel == null) {
			nortePanel = new HorizontalPanel();
			nortePanel.setStyleName("HFSApplication-NortePanel");
			nortePanel.setWidth("100%");
			if (imageLogo!=null){
				nortePanel.add(imageLogo);
				nortePanel.setCellVerticalAlignment(imageLogo,
						HasVerticalAlignment.ALIGN_MIDDLE);
			}
			nortePanel.add(getVerticalPanel());
			nortePanel.setCellWidth(getVerticalPanel(), "100%");
		}
		return nortePanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setWidth("100%");
			verticalPanel.add(getTituloPanel());
			verticalPanel.add(getHorizontalPanel());
			verticalPanel.add(getSobrePanel());
			verticalPanel.setCellHorizontalAlignment(getSobrePanel(),
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
		return verticalPanel;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setWidth("100%");
			horizontalPanel.add(getHtmlSubTitulo());
		}
		return horizontalPanel;
	}

	private HTML getHtmlSubTitulo() {
		if (htmlSubTitulo == null) {
			htmlSubTitulo = new HTML("<big><big><b>" + this.subTituloSistema
					+ "</b></big></big>", true);
			htmlSubTitulo
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return htmlSubTitulo;
	}

	public HFSStackPanel getStackPanel() {
		if (stackPanel == null) {
			stackPanel = new HFSStackPanel("100%", "100%", true, codigoSistema);
		}
		return stackPanel;
	}

	public HFSTree getTree() {
		if (arvore == null) {
			arvore = new HFSTree("100%", "100%", true, false,
					false, codigoSistema);
		}
		return arvore;
	}

	public HFSMenu getMenu() {
		if (menu == null) {
			menu = new HFSMenu("100%", true, true, codigoSistema);
		}
		return menu;
	}

	private SplitLayoutPanel getSplitLayoutPanel() {
		if (splitLayoutPanel == null) {
			splitLayoutPanel = new SplitLayoutPanel();
			if (tipoMenu == TipoMenu.STACKPANEL)
				splitLayoutPanel.addWest(getStackPanel().getLayoutPainel(),
						200.0);
			else if (tipoMenu == TipoMenu.TREE)
				splitLayoutPanel.addWest(getTree(), 200.0);
			splitLayoutPanel.add(getConteudoPanel());
		}
		return splitLayoutPanel;
	}

	private ScrollPanel getConteudoPanel() {
		if (conteudoPanel == null) {
			conteudoPanel = new ScrollPanel();
			conteudoPanel.setStyleName("HFSApplication-ConteudoPanel");
		}
		return conteudoPanel;
	}
	
	public void setTela(Widget w) {
		this.getConteudoPanel().setWidget(w);
	}
	
	public int getLarguraConteudo(){
		return getConteudoPanel().getOffsetWidth();
	}

	public int getAlturaConteudo(){
		return getConteudoPanel().getOffsetHeight();
	}

	public String getTituloSistema() {
		return tituloSistema;
	}

	public void setTituloSistema(String tituloSistema) {
		this.tituloSistema = tituloSistema;
		this.getHtmlTitulo().setHTML(
				"<big><big><big><b>" + this.tituloSistema
						+ "</b></big></big></big>");
	}

	public String getSubTituloSistema() {
		return subTituloSistema;
	}

	public void setSubTituloSistema(String subTituloSistema) {
		this.subTituloSistema = subTituloSistema;
		this.getHtmlSubTitulo().setHTML(
				"<big><big><b>" + this.subTituloSistema + "</b></big></big>");
	}
	
	public void addAppClickHandler(AppClickHandler handler) {
		this.appClickHandler = handler;
	}
 
}
