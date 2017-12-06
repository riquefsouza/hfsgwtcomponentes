package com.hfsgwt.client.componentes.multi;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public class HFSMultiCheckBox extends Composite {
	private CaptionPanel panelTitulo;
	private FlexTable grid;

	public interface MultiClickHandler extends EventHandler {
		void onClick(HFSMultiItem item);
	}

	private MultiClickHandler multiClickHandler;
	private ArrayList<HFSMultiItem> items;

	public HFSMultiCheckBox() {
		this.items = new ArrayList<HFSMultiItem>();
		initComponents();
	}

	private void initComponents() {
		initWidget(getPanelTitulo());
	}

	private CaptionPanel getPanelTitulo() {
		if (panelTitulo == null) {
			panelTitulo = new CaptionPanel();
			panelTitulo.setContentWidget(getGrid());
		}
		return panelTitulo;
	}

	public void setRotulo(String rotulo) {
		panelTitulo.setCaptionText(rotulo);
	}

	public String getRotulo() {
		return panelTitulo.getCaptionText();
	}

	private FlexTable getGrid() {
		if (grid == null) {
			grid = new FlexTable(); //(nQtdLinhas, nQtdColunas);
		}
		return grid;
	}

	public void setItems(int maxColunas, ArrayList<HFSMultiItem> items) {
		if (items.size() > 0) {
			if (maxColunas == 0)
				maxColunas = 1;

			this.items = items;			
			int ntotal = items.size();
			int nQtdLinhas = 0;
			int nQtdColunas = 0;
			
			if (ntotal <= maxColunas) {
				nQtdLinhas = 1;
				nQtdColunas = ntotal;
			} else {
				int nqtd = 0;
				nQtdColunas = maxColunas;
				while (ntotal > nqtd) {
					nqtd += nQtdColunas;
					nQtdLinhas++;
				}
			}

			if (grid.getRowCount() > 0)
				grid.removeAllRows();			
			
			int nOrdem = 0;
			for (int nlinha = 0; nlinha < nQtdLinhas; nlinha++) {
				for (int ncoluna = 0; ncoluna < nQtdColunas; ncoluna++) {
					if (nOrdem < ntotal) {
						grid.setWidget(nlinha, ncoluna,
								criarCheckBox(this.items.get(nOrdem)));
						this.items.get(nOrdem).setLinha(nlinha);
						this.items.get(nOrdem).setColuna(ncoluna);
						nOrdem++;
					}
				}
			}
		}
	}

	private CheckBox criarCheckBox(HFSMultiItem item) {
		CheckBox checkBox = new CheckBox(item.getValor());
		checkBox.setValue(item.isChecado());
		checkBox.setName(item.getId());
		checkBox.setEnabled(item.isHabilitado());
		checkBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (multiClickHandler != null) {
					CheckBox check = (CheckBox) event.getSource();
					for (HFSMultiItem elemento : items) {
						if (elemento.getId().equals(check.getName())) {
							elemento.setChecado(check.getValue());
							elemento.setHabilitado(check.isEnabled());
							multiClickHandler.onClick(elemento);
						}
					}
				}

			}
		});
		return checkBox;
	}

	public boolean isChecado(int nlinha, int ncoluna) {
		CheckBox cb = (CheckBox) grid.getWidget(nlinha, ncoluna);
		return cb.getValue();
	}

	public boolean isChecado(String itemId) {
		for (HFSMultiItem elemento : this.items) {
			if (elemento.getId().equals(itemId)) {
				int nlinha = elemento.getLinha();
				int ncoluna = elemento.getColuna();
				CheckBox checkBox = (CheckBox) grid.getWidget(nlinha, ncoluna);
				return checkBox.getValue();
			}
		}
		return false;
	}

	public boolean isChecadoPorValor(String itemValor) {
		for (HFSMultiItem elemento : this.items) {
			if (elemento.getValor().equals(itemValor)) {
				int nlinha = elemento.getLinha();
				int ncoluna = elemento.getColuna();
				CheckBox checkBox = (CheckBox) grid.getWidget(nlinha, ncoluna);
				return checkBox.getValue();
			}
		}
		return false;
	}

	public ArrayList<HFSMultiItem> getItems() {
		ArrayList<HFSMultiItem> todosItems = this.items;
		for (HFSMultiItem elemento : todosItems) {
			int nlinha = elemento.getLinha();
			int ncoluna = elemento.getColuna();
			CheckBox checkBox = (CheckBox) grid.getWidget(nlinha, ncoluna);
			elemento.setChecado(checkBox.getValue());
			elemento.setHabilitado(checkBox.isEnabled());
		}
		return this.items;
	}

	public ArrayList<HFSMultiItem> getItemsChecados() {
		ArrayList<HFSMultiItem> checados = new ArrayList<HFSMultiItem>();
		checados.addAll(getItems());
		for (int i = (checados.size() - 1); i >= 0; i--) {
			if (!checados.get(i).isChecado()) {
				checados.remove(i);
			}
		}
		return checados;
	}

	public ArrayList<HFSMultiItem> getItemsNaoChecados() {
		ArrayList<HFSMultiItem> naoChecados = new ArrayList<HFSMultiItem>();
		naoChecados.addAll(getItems());
		for (int i = (naoChecados.size() - 1); i >= 0; i--) {
			if (naoChecados.get(i).isChecado()) {
				naoChecados.remove(i);
			}
		}
		return naoChecados;
	}

	public void addMultiClickHandler(MultiClickHandler handler) {
		this.multiClickHandler = handler;
	}

	public boolean isHabilitado(int nlinha, int ncoluna) {
		CheckBox cb = (CheckBox) grid.getWidget(nlinha, ncoluna);
		return cb.isEnabled();
	}

	public boolean isHabilitado(String itemId) {
		for (HFSMultiItem elemento : this.items) {
			if (elemento.getId().equals(itemId)) {
				int nlinha = elemento.getLinha();
				int ncoluna = elemento.getColuna();
				CheckBox checkBox = (CheckBox) grid.getWidget(nlinha, ncoluna);
				return checkBox.isEnabled();
			}
		}
		return false;
	}

	public boolean isHabilitadoPorValor(String itemValor) {
		for (HFSMultiItem elemento : this.items) {
			if (elemento.getValor().equals(itemValor)) {
				int nlinha = elemento.getLinha();
				int ncoluna = elemento.getColuna();
				CheckBox checkBox = (CheckBox) grid.getWidget(nlinha, ncoluna);
				return checkBox.isEnabled();
			}
		}
		return false;
	}

	public void setLargura(String largura){
		getPanelTitulo().setWidth(largura);
	}
	
	public void removeItem(HFSMultiItem item) {
		for (int i = (this.items.size() - 1); i >= 0; i--) {
			if (this.items.get(i).getLinha() == item.getLinha() 
					&& this.items.get(i).getColuna() == item.getColuna()) {	
				this.grid.removeCell(item.getLinha(), item.getColuna());
				this.items.remove(i);
			}
		}
	}

	public void removeItem(int nlinha, int ncoluna) {
		this.grid.removeCell(nlinha, ncoluna);
		for (int i = (this.items.size() - 1); i >= 0; i--) {			
			if (this.items.get(i).getLinha() == nlinha 
					&& this.items.get(i).getColuna() == ncoluna) {	
				this.items.remove(i);
			}
		}
	}

	public void limpar() {
		this.grid.removeAllRows();
		this.items.clear();
	}
	
}
