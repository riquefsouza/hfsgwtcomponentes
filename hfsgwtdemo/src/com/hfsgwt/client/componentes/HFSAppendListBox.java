package com.hfsgwt.client.componentes;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSAppendListBox extends Composite {
	private CaptionPanel panelTitulo;
	private HFSListBox hfsListBox;
	private HFSTextBox hfsTextBox;
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private PushButton btnIncItem;
	private PushButton btnExcItem;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);
	
	private boolean habilitado;
	private boolean focado;

	public HFSAppendListBox() {
		this.habilitado = true;
		this.focado = true;
		
		initComponents();
	}

	private void initComponents() {
		initWidget(getPanelTitulo());
	}

	private CaptionPanel getPanelTitulo() {
		if (panelTitulo == null) {
			panelTitulo = new CaptionPanel();
			panelTitulo.setContentWidget(getVerticalPanel());
		}
		return panelTitulo;
	}

	public void setRotulo(String rotulo) {
		panelTitulo.setCaptionText(rotulo);
	}

	public String getRotulo() {
		return panelTitulo.getCaptionText();
	}

	private PushButton getBtnIncItem() {
		if (btnIncItem == null) {
			btnIncItem = new PushButton(new Image(img.appendIncluir()));
			btnIncItem.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnIncItemClick();
				}
			});
			btnIncItem.setTitle("Inclui o item.");
			btnIncItem.setSize("12px", "16px");
		}
		return btnIncItem;
	}
	
	private PushButton getBtnExcItem() {
		if (btnExcItem == null) {
			btnExcItem = new PushButton(new Image(img.appendExcluir()));
			btnExcItem.setEnabled(false);
			btnExcItem.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnExcItemClick();
				}
			});
			btnExcItem.setTitle("Exclui o item selecionado.");
			btnExcItem.setSize("12px", "16px");
		}
		return btnExcItem;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getHFSTextBox());
			horizontalPanel.setCellVerticalAlignment(getHFSTextBox(), HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.add(getBtnIncItem());
			horizontalPanel.add(getBtnExcItem());			
		}
		return horizontalPanel;
	}
	
	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getHFSListBox());
			verticalPanel.add(getHorizontalPanel());	
		}
		return verticalPanel;
	}
	
	private HFSListBox getHFSListBox() {
		if (hfsListBox == null) {
			hfsListBox = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.LISTBOX, "lista", 0, true, false);
			hfsListBox.setMostrarRotulo(false);
		}
		return hfsListBox;
	}
	
	private HFSTextBox getHFSTextBox(){
		if (hfsTextBox == null) {
			hfsTextBox = new HFSTextBox(PosicaoRotulo.ACIMA, "edit", Formato.PADRAO, 10, 0, false);
			hfsTextBox.setMostrarRotulo(false);
		}
		return hfsTextBox;
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		this.getHFSListBox().setHabilitado(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		this.getHFSListBox().setFocado(focado);
	}

	public boolean isFocado() {
		return this.focado;
	}
	
	public void setItems(ArrayList<HFSItem> items) {
		this.getHFSListBox().setItems(items);
	}

	public ArrayList<HFSItem> getItems() {
		return this.getHFSListBox().getItems();
	}

	public HFSItem getItemSelecionado() {
		return this.getHFSListBox().getItemSelecionado();
	}

	public void limpar() {
		this.getHFSListBox().limpar();
	}

	public int getIndiceSelecionado() {
		return this.getHFSListBox().getIndiceSelecionado();
	}

	public void setIndiceSelecionado(int indice) {
		this.getHFSListBox().setIndiceSelecionado(indice);
	}

	public void addItem(HFSItem item) {
		this.getHFSListBox().addItem(item);
	}

	public void removeItem(HFSItem item) {
		this.getHFSListBox().removeItem(item);
	}

	public void removeItem(int indice) {
		this.getHFSListBox().removeItem(indice);
	}
	
	public void removeSelecionados() {
		this.getHFSListBox().removeSelecionados();
	}	

	public void setTamanho(String largura, String altura){
		getHFSListBox().setTamanho(largura, altura);
		int nlargura = Integer.parseInt(largura.substring(0,largura.length()-2))-50;
		getHFSTextBox().setLargura(Integer.toString(nlargura)+"px");
		getVerticalPanel().setSize(largura, altura);
		//getHorizontalPanel().setHeight("16px");
		getPanelTitulo().setWidth(largura);
	}

	private void btnExcItemClick() {
		if (getHFSListBox().getItems().size() > 0) {
			getHFSListBox().removeSelecionados();			
		}
		btnExcItem.setEnabled(getHFSListBox().getItems().size() > 0);
	}

	private void btnIncItemClick() {
		if (getHFSTextBox().getTexto().length() > 0) {
			if (!getHFSListBox().existe(getHFSTextBox().getTexto())){
				String novoId = Integer.toString(getHFSListBox().getItems().size()+1); 
				getHFSListBox().addItem(new	HFSItem(novoId, getHFSTextBox().getTexto()));
				getHFSTextBox().setTexto("");
			} else {
				Window.alert("Item já cadastrado!");
				getHFSTextBox().setFocado(true);
			}
		}
		btnExcItem.setEnabled(getHFSListBox().getItems().size() > 0);
	}
	
}
