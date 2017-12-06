package com.hfsgwt.client.componentes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSAppendGrid extends Composite {
	private CaptionPanel panelTitulo;
	private HFSStringGrid grid;
	private HFSTextBox hfsTextBox;
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private PushButton btnIncItem;
	private PushButton btnExcItem;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public interface BtnClickHandler extends EventHandler {
		void onBtnIncluirClick();
	}
	
	private BtnClickHandler btnClickHandler;
//	private boolean habilitado;
//	private boolean focado;
	private String[] colunas;

	public HFSAppendGrid(String[] colunas) {
//		this.habilitado = true;
//		this.focado = true;
		this.colunas = colunas;
		
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
					if (btnClickHandler != null) {
						btnClickHandler.onBtnIncluirClick();
					}
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
			verticalPanel.add(getGrid());
			verticalPanel.add(getHorizontalPanel());	
		}
		return verticalPanel;
	}
	
	private HFSStringGrid getGrid() {
		if (grid == null) {
			grid = new HFSStringGrid(colunas, new Boolean[] { false, false }, 6);
		}
		return grid;
	}
	
	private HFSTextBox getHFSTextBox(){
		if (hfsTextBox == null) {
			hfsTextBox = new HFSTextBox(PosicaoRotulo.ACIMA, "edit", Formato.PADRAO, 10, 0, false);
			hfsTextBox.setMostrarRotulo(false);
		}
		return hfsTextBox;
	}
	
//	public void setHabilitado(boolean habilitado) {
//		this.habilitado = habilitado;
//		this.getGrid().setHabilitado(habilitado);
//	}
//
//	public boolean isHabilitado() {
//		return this.habilitado;
//	}
//
//	public void setFocado(boolean focado) {
//		this.focado = focado;
//		this.getGrid().setFocado(focado);
//	}
//
//	public boolean isFocado() {
//		return this.focado;
//	}

	public void setTamanho(String largura, String altura){
		int nlargura = Integer.parseInt(largura.substring(0,largura.length()-2))-50;
		int naltura = Integer.parseInt(altura.substring(0,altura.length()-2))-30;
		getHFSTextBox().setLargura(Integer.toString(nlargura)+"px");

		getGrid().setTamanho(largura, Integer.toString(naltura)+"px");

		getVerticalPanel().setSize(largura, altura);
		//getHorizontalPanel().setHeight("16px");
		getPanelTitulo().setWidth(largura);
	}

	private void btnExcItemClick() {
		if (getGrid().getLinhas().size() > 0) {
			getGrid().removeLinhaSelecionada();			
		}
		btnExcItem.setEnabled(getGrid().getLinhas().size() > 0);
	}

	public void btnIncItemClick(HFSItem itemChave) {
		if (getHFSTextBox().getTexto().length() > 0) {
			List<String> linha = new ArrayList<String>();
			linha.add(itemChave.getValor());
			linha.add(getHFSTextBox().getTexto());

			if (!getGrid().existe(linha)){
				getGrid().addLinha(linha);
				getHFSTextBox().setTexto("");
			} else {
				Window.alert("Item já cadastrado!");
				getHFSTextBox().setFocado(true);
			}
		}
		btnExcItem.setEnabled(getGrid().getLinhas().size() > 0);
	}
	
	public void addBtnClickHandler(BtnClickHandler handler) {
		this.btnClickHandler = handler;
	}
	
}
