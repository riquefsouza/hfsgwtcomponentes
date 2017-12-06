package com.hfsgwt.client.componentes;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class HFSPopupPanel extends PopupPanel {

	private String titulo;
	private HTML htmlCaption;

	public HFSPopupPanel(String titulo) {
		this.titulo = titulo;
	}

	protected HTML getTitulo() {
		if (htmlCaption == null) {
			htmlCaption = new HTML(
					"<table style=\"border: 1px solid rgb(0, 0, 0); text-align: left; width: 100%;\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\"><tr><td style=\"font-weight: bold; background-color: rgb(195, 217, 255);\">"
							+ this.titulo + "</td></tr></table>", true);
		}
		return htmlCaption;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
