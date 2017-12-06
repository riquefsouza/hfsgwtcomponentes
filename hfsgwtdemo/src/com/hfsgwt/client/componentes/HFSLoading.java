package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSLoading extends PopupPanel {
	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	private VerticalPanel verticalPanel;
	private Image image;
	private HTML html;
	private String texto;

	public HFSLoading() {
		super(false);
		this.texto = "Carregando...";
		initComponents();
	}

	public HFSLoading(String texto) {
		super(false);
		this.texto = texto;
		initComponents();
	}

	private void initComponents() {
		setGlassEnabled(true);
		// setPopupPosition(10, 10);
		setWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(5);
			verticalPanel.add(getImage());
			verticalPanel.setCellHorizontalAlignment(getImage(),
					HasHorizontalAlignment.ALIGN_CENTER);
			verticalPanel.add(getHtml());
		}
		return verticalPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image(img.carregandoGrande());
		}
		return image;
	}

	private HTML getHtml() {
		if (html == null) {
			html = new HTML("<b>" + this.texto + "</b>", true);
		}
		return html;
	}

	public static HFSLoading mostrar() {
		HFSLoading dlg = new HFSLoading();
		dlg.center();
		dlg.show();
		return dlg;
	}

	public static HFSLoading mostrar(String texto) {
		HFSLoading dlg = new HFSLoading(texto);
		dlg.center();
		dlg.show();
		return dlg;
	}
}
