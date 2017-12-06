package com.hfsgwt.client.application;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.util.HFSUtil;

public final class HFSSobre extends DialogBox {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private VerticalPanel verticalPanel;
	private HorizontalPanel horizontalPanel;
	private Image image;
	private HTMLPanel infoPanel;
	private HFSStringGrid stringGrid;
	private ImageResource imagem;
	private String htmlInfo;
	private Button btnOk;

	private HFSSobre(ImageResource imagem, String htmlInfo) {
		this.imagem = imagem;
		this.htmlInfo = htmlInfo;

		initComponents();
	}

	private void initComponents() {
		setWidth("");
		setHTML("Sobre o sistema");
		setGlassEnabled(true);
		setWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(5);
			verticalPanel.setSize("500px", "100%");
			verticalPanel.add(getHorizontalPanel());
			verticalPanel.add(getStringGrid());
			verticalPanel.add(getBtnOk());
			verticalPanel.setCellHorizontalAlignment(getBtnOk(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return verticalPanel;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getImage());
			horizontalPanel.add(getInfoPanel());
			horizontalPanel.setCellHeight(getInfoPanel(), "100%");
			horizontalPanel.setCellWidth(getInfoPanel(), "100%");
			horizontalPanel.setCellVerticalAlignment(getInfoPanel(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.setCellHorizontalAlignment(getInfoPanel(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return horizontalPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image(imagem);
		}
		return image;
	}

	private HTMLPanel getInfoPanel() {
		if (infoPanel == null) {
			infoPanel = new HTMLPanel(htmlInfo);
		}
		return infoPanel;
	}

	private HFSStringGrid getStringGrid() {
		if (stringGrid == null) {
			stringGrid = new HFSStringGrid(new String[] { "Propriedade",
					"Valor" }, new Boolean[] { true, true }, 6);
			stringGrid.setSize("100%", "200px");
		}
		return stringGrid;
	}

	private Button getBtnOk() {
		if (btnOk == null) {
			btnOk = new Button("Ok");
			btnOk.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			btnOk.setWidth("100px");
		}
		return btnOk;
	}

	private void listarPropriedades() {
		servico
				.getPropriedadesSistema(new AsyncCallback<List<PropriedadeSistema>>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"getPropriedadesSistema");
					}

					public void onSuccess(List<PropriedadeSistema> lista) {
						getStringGrid().addLinhas(
								(new PropriedadeSistema()).getListaGrid(lista));
					}
				});
	}

	public static void mostrar(ImageResource imagem, String htmlInfo) {
		HFSSobre dlg = new HFSSobre(imagem, htmlInfo);
		dlg.listarPropriedades();
		dlg.show();
		dlg.center();
	}
}
