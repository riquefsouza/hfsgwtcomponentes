package com.hfsgwt.client.componentes;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HFSDualList extends Composite {
	private HorizontalPanel horizontalPanel;
	private VerticalPanel botoesPanel;
	private CaptionPanel panelDestino;
	private ListBox lbDestino;
	private CaptionPanel panelOrigem;
	private ListBox lbOrigem;
	private Button btnIncluirTodos;
	private Button btnIncluir;
	private Button btnRemover;
	private Button btnRemoverTodos;

	private ArrayList<HFSItem> itemsOrigem, itemsDestino;

	public HFSDualList(int largura, int altura) {
		itemsOrigem = new ArrayList<HFSItem>();
		itemsDestino = new ArrayList<HFSItem>();

		initComponents();
		this.setTamanho(largura, altura);
	}

	private void initComponents() {
		initWidget(getHorizontalPanel());
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setSpacing(5);
			horizontalPanel.setSize("295px", "127px");
			horizontalPanel.add(getPanelOrigem());
			horizontalPanel.setCellHorizontalAlignment(getPanelOrigem(),
					HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.setCellVerticalAlignment(getPanelOrigem(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.add(getBotoesPanel());
			horizontalPanel.setCellVerticalAlignment(getBotoesPanel(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.setCellHorizontalAlignment(getBotoesPanel(),
					HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.add(getPanelDestino());
			horizontalPanel.setCellVerticalAlignment(getPanelDestino(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.setCellHorizontalAlignment(getPanelDestino(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return horizontalPanel;
	}

	private VerticalPanel getBotoesPanel() {
		if (botoesPanel == null) {
			botoesPanel = new VerticalPanel();
			botoesPanel.setSpacing(5);
			botoesPanel
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			botoesPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			//botoesPanel.setSize("10", "100%");
			botoesPanel.add(getBtnIncluirTodos());
			botoesPanel.setCellHeight(getBtnIncluirTodos(), "25");
			botoesPanel.setCellWidth(getBtnIncluirTodos(), "30");
			botoesPanel.add(getBtnIncluir());
			botoesPanel.setCellHeight(getBtnIncluir(), "25");
			botoesPanel.setCellWidth(getBtnIncluir(), "30");
			botoesPanel.add(getBtnRemover());
			botoesPanel.setCellHeight(getBtnRemover(), "25");
			botoesPanel.setCellWidth(getBtnRemover(), "30");
			botoesPanel.add(getBtnRemoverTodos());
			botoesPanel.setCellHeight(getBtnRemoverTodos(), "25");
			botoesPanel.setCellWidth(getBtnRemoverTodos(), "30");
		}
		return botoesPanel;
	}

	private CaptionPanel getPanelDestino() {
		if (panelDestino == null) {
			panelDestino = new CaptionPanel();
			//panelDestino.setSize("110px", "120px");
			panelDestino.setContentWidget(getLbDestino());
		}
		return panelDestino;
	}

	private ListBox getLbDestino() {
		if (lbDestino == null) {
			lbDestino = new ListBox();
			lbDestino.setMultipleSelect(true);
			lbDestino.setSize("110px", "110px");
			lbDestino.setVisibleItemCount(20);
			lbDestino.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					getLbDestino().setStyleName("HFSListBox-focado");
				}
			});
			lbDestino.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					getLbDestino().setStyleName("");
				}
			});			
		}
		return lbDestino;
	}

	private CaptionPanel getPanelOrigem() {
		if (panelOrigem == null) {
			panelOrigem = new CaptionPanel();
			//panelOrigem.setSize("110px", "120px");
			panelOrigem.setContentWidget(getLbOrigem());
		}
		return panelOrigem;
	}

	private ListBox getLbOrigem() {
		if (lbOrigem == null) {
			lbOrigem = new ListBox();
			lbOrigem.setMultipleSelect(true);
			lbOrigem.setSize("110px", "110px");
			lbOrigem.setVisibleItemCount(20);
			lbOrigem.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					getLbOrigem().setStyleName("HFSListBox-focado");
				}
			});
			lbOrigem.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					getLbOrigem().setStyleName("");
				}
			});
		}
		return lbOrigem;
	}

	private Button getBtnIncluirTodos() {
		if (btnIncluirTodos == null) {
			btnIncluirTodos = new Button("");
			btnIncluirTodos.setEnabled(false);
			btnIncluirTodos.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnIncluirTodosClick();
				}
			});
			btnIncluirTodos.setSize("30", "25");
			btnIncluirTodos.setText(">>");
		}
		return btnIncluirTodos;
	}

	private Button getBtnIncluir() {
		if (btnIncluir == null) {
			btnIncluir = new Button("");
			btnIncluir.setEnabled(false);
			btnIncluir.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnIncluirClick();
				}
			});
			btnIncluir.setSize("30", "25");
			btnIncluir.setText(">");
		}
		return btnIncluir;
	}

	private Button getBtnRemover() {
		if (btnRemover == null) {
			btnRemover = new Button("");
			btnRemover.setEnabled(false);
			btnRemover.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnRemoverClick();
				}
			});
			btnRemover.setText("<");
			btnRemover.setSize("30", "25");
		}
		return btnRemover;
	}

	private Button getBtnRemoverTodos() {
		if (btnRemoverTodos == null) {
			btnRemoverTodos = new Button("");
			btnRemoverTodos.setEnabled(false);
			btnRemoverTodos.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnRemoverTodosClick();
				}
			});
			btnRemoverTodos.setSize("30", "25");
			btnRemoverTodos.setText("<<");
		}
		return btnRemoverTodos;
	}

	private void setTamanho(int nlargura, int naltura) {
		if (nlargura >= 90 && naltura >= 90) {
			String slargura = Integer.toString(nlargura) + "px";
			String saltura = Integer.toString(naltura) + "px";

			lbOrigem.setSize(slargura, saltura);
			botoesPanel.setSize("10", saltura);
			lbDestino.setSize(slargura, saltura);

//			panelOrigem.setWidth(slargura);
//			panelDestino.setWidth(slargura);
		}
	}

	public void setRotuloOrigem(String rotulo) {
		panelOrigem.setCaptionText(rotulo);
	}

	public String getRotuloOrigem() {
		return panelOrigem.getCaptionText();
	}
	
	public void setRotuloDestino(String rotulo) {
		panelDestino.setCaptionText(rotulo);
	}

	public String getRotuloDestino() {
		return panelDestino.getCaptionText();
	}

	private void desabilitaBotoes() {
		btnIncluir.setEnabled(false);
		btnIncluirTodos.setEnabled(false);
		btnRemover.setEnabled(false);
		btnRemoverTodos.setEnabled(false);
	}

	private void habilitaBotoes() {
		desabilitaBotoes();

		if (lbOrigem.getItemCount() > 0) {
			btnIncluir.setEnabled(true);
			btnIncluirTodos.setEnabled(true);
		}

		if (lbDestino.getItemCount() > 0) {
			btnRemover.setEnabled(true);
			btnRemoverTodos.setEnabled(true);
		}
	}

	public void removerItems() {
		desabilitaBotoes();
		lbOrigem.clear();
		lbDestino.clear();

		itemsOrigem.clear();
		itemsDestino.clear();
	}

	public void setItemsOrigem(ArrayList<HFSItem> item) {
		if (item.size() > 0) {
			itemsOrigem = item;
			
			lbOrigem.clear();
			for (HFSItem elemento : item) {
				lbOrigem.addItem(elemento.getValor());
			}		

			lbOrigem.setSelectedIndex(0);
			habilitaBotoes();
		}
	}
	
	public ArrayList<HFSItem> getItemsOrigem(){
		return itemsOrigem;
	}

	public void setItemsDestino(ArrayList<HFSItem> item) {
		if (item.size() > 0) {
			itemsDestino = item;
			
			lbDestino.clear();
			for (HFSItem elemento : item) {
				lbDestino.addItem(elemento.getValor());
			}			

			lbDestino.setSelectedIndex(0);
			habilitaBotoes();
		}
	}

	public ArrayList<HFSItem> getItemsDestino(){
		return itemsDestino;
	}
	
	private void moverItem(ListBox lbxOrigem, ListBox lbxDestino,
			ArrayList<HFSItem> itemxOrigem, ArrayList<HFSItem> itemxDestino) {
		String item;
		HFSItem elemento;

		for (int i = 0; i < lbxOrigem.getItemCount(); i++) {
			if (lbxOrigem.isItemSelected(i)) {
				item = lbxOrigem.getItemText(i);
				lbxDestino.addItem(item);

				elemento = itemxOrigem.get(i);
				itemxDestino.add(elemento);
			}
		}
		for (int i = (lbxOrigem.getItemCount() - 1); i >= 0; i--) {
			if (lbxOrigem.isItemSelected(i)) {
				lbxOrigem.removeItem(i);

				itemxOrigem.remove(i);
			}
		}
	}

	private void moverTodosItems(ListBox lbxOrigem, ListBox lbxDestino,
			ArrayList<HFSItem> itemxOrigem, ArrayList<HFSItem> itemxDestino) {
		String item;
		HFSItem elemento;

		for (int i = 0; i < lbxOrigem.getItemCount(); i++) {
			item = lbxOrigem.getItemText(i);
			lbxDestino.addItem(item);

			elemento = itemxOrigem.get(i);
			itemxDestino.add(elemento);
		}
		lbxOrigem.clear();

		itemxOrigem.clear();
	}

	public void btnIncluirClick() {
		moverItem(lbOrigem, lbDestino, itemsOrigem, itemsDestino);
		habilitaBotoes();
	}

	public void btnIncluirTodosClick() {
		moverTodosItems(lbOrigem, lbDestino, itemsOrigem, itemsDestino);
		habilitaBotoes();
	}

	public void btnRemoverTodosClick() {
		moverTodosItems(lbDestino, lbOrigem, itemsDestino, itemsOrigem);
		habilitaBotoes();
	}

	public void btnRemoverClick() {
		moverItem(lbDestino, lbOrigem, itemsDestino, itemsOrigem);
		habilitaBotoes();
	}

}
