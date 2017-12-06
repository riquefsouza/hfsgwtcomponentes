package com.hfsgwt.client.componentes.tree;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class HFSTreeItem extends Composite {
	private HorizontalPanel horizontalPanel;
	private Image image;
	private Label labTexto;
	private int profundidade;
	private String id;
	private String texto;
	private ImageResource imagem;
	private boolean mostrarImagem;
	private boolean selecionado;

	public HFSTreeItem(ImageResource imagem, String id, String texto) {
		this.imagem = imagem;
		this.id = id;
		this.texto = texto;
		this.mostrarImagem = true;
		initComponents();
	}

	public HFSTreeItem(String id, String texto) {
		this.id = id;
		this.texto = texto;
		this.mostrarImagem = false;
		initComponents();
	}

	private void initComponents() {
		initWidget(getHorizontalPanel());
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			if (this.mostrarImagem) {
				horizontalPanel.add(getImage());
				horizontalPanel.setCellVerticalAlignment(getImage(),
						HasVerticalAlignment.ALIGN_MIDDLE);
			}
			horizontalPanel.add(getLabTexto());
		}
		return horizontalPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image(this.imagem);
		}
		return image;
	}

	private Label getLabTexto() {
		if (labTexto == null) {
			labTexto = new Label(this.texto);
		}
		return labTexto;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
		if (selecionado)
			horizontalPanel.setStyleName("HFSTreeItem-selecionado");
		else
			horizontalPanel.setStyleName("");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}

}
