package com.hfsgwt.client.componentes.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.HTML;

public class HFSColumn extends HTML {

	public interface ColClickHandler extends EventHandler {
		boolean onClick(HFSColumn coluna);
	}

	private ColClickHandler colClickHandler;
	private int indice;
	private String titulo;
	private boolean ordemCrescente;

	public HFSColumn(int indice, String titulo) {
		this.indice = indice;
		this.titulo = titulo;
		this.ordemCrescente = false;
		initComponents();
	}

	private String getHTML(boolean asc, boolean desc) {
		if (asc)
			return "<b>" + titulo + "</b> <img src='" + GWT.getModuleName()
					+ "/HFSGrid-Crescente.png'>";
		else if (desc)
			return "<b>" + titulo + "</b> <img src='" + GWT.getModuleName()
					+ "/HFSGrid-Decrescente.png'>";
		else
			return "<b>" + titulo + "</b>";
	}

	private void initComponents() {
		addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setOrdem(!ordemCrescente);
			}
		});
		setHTML(getHTML(false, false));
		setWordWrap(false);
	}

	public void setOrdem(boolean ordemCrescente) {
		this.ordemCrescente = ordemCrescente;
		if (colClickHandler != null) {
			if (colClickHandler.onClick(this)) {
				if (ordemCrescente) {
					setHTML(getHTML(true, false));
				} else {
					setHTML(getHTML(false, true));
				}
			}
		}
	}

	public void setImagemSemOrdem() {
		setHTML(getHTML(false, false));
	}

	public boolean getOrdemCrescente() {
		return ordemCrescente;
	}

	public void addColClickHandler(ColClickHandler handler) {
		this.colClickHandler = handler;
	}

	public String getTitulo() {
		return titulo;
	}

	public int getIndice() {
		return indice;
	}

}
