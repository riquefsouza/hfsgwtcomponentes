package com.hfsgwt.client.componentes.multi;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.IHFSRotulo;

public class HFSCheckListBox extends Composite implements IHFSRotulo {
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private SimplePanel simplePanel;
	private FlexTable grid;
	private ScrollPanel scrollPanel;
	private DockPanel dockPanel;
	private HTML rotulo;

	private boolean mostrarRotulo;
	private boolean obrigatorio;
	private int larguraRotulo;
	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;

	public interface MultiClickHandler extends EventHandler {
		void onClick(HFSMultiItem item);
	}

	private MultiClickHandler multiClickHandler;
	private ArrayList<HFSMultiItem> items;

	public HFSCheckListBox(PosicaoRotulo posicao, String rotulo, boolean obrigatorio) {
		this.items = new ArrayList<HFSMultiItem>();
		this.mostrarRotulo = true;
		this.obrigatorio = obrigatorio;
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;		

		initComponents(posicao);

		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();
	}

	private void initComponents(PosicaoRotulo posicao) {
		if (posicao == PosicaoRotulo.ACIMA) {
			initWidget(getVerticalPanel());
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			initWidget(getHorizontalPanel());
		}
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getRotulo());
			horizontalPanel.add(getSimplePanel());
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getSimplePanel());
		}
		return verticalPanel;
	}

	private void setRotulo(String rotulo){
		if (this.obrigatorio) {
			rotulo="<b>"+rotulo+"</b><span style='color: rgb(255, 0, 0);'>*</span>";
		}
		getRotulo().setHTML(rotulo);
	}
	
	private HTML getRotulo() {
		if (rotulo == null) {
			rotulo = new HTML("Rotulo:");
		}
		return rotulo;
	}
	
	private FlexTable getGrid() {
		if (grid == null) {
			grid = new FlexTable();
			grid.setSize("100%", "100%");
			grid.setBorderWidth(0);
		}
		return grid;
	}

	private DockPanel getDockPanel() {
		if (dockPanel == null) {
			dockPanel = new DockPanel();
			dockPanel.setSize("100%", "100%");
			dockPanel.add(getGrid(), DockPanel.CENTER);
		}
		return dockPanel;
	}	

	private ScrollPanel getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new ScrollPanel();
			scrollPanel.setSize("100px", "100px");
			scrollPanel.setWidget(getDockPanel());
		}
		return scrollPanel;
	}

	private SimplePanel getSimplePanel() {
		if (simplePanel == null) {
			simplePanel = new SimplePanel();
			simplePanel.setStyleName("HFSCheckListBox-borda");
			simplePanel.setWidget(getScrollPanel());
		}
		return simplePanel;
	}
	
	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getRotulo().setVisible(this.mostrarRotulo);
	}
	
	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}
	
	public AlinhamentoRotulo getAlinhamentoRotulo() {
		return alinhamentoRotulo;
	}

	public void setAlinhamentoRotulo(AlinhamentoRotulo alinhamentoRotulo) {
		this.alinhamentoRotulo = alinhamentoRotulo;
		switch (alinhamentoRotulo) {
		case ESQUERDA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			break;
		case CENTRO:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			break;
		case DIREITA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			break;
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

	public void setItems(ArrayList<HFSMultiItem> items) {
		if (items.size() > 0) {
			this.items = items;
			
			if (grid.getRowCount() > 0)
				grid.removeAllRows();			
			
			int ncoluna = 0;
			int nOrdem = 0;
			for (int nlinha = 0; nlinha < this.items.size(); nlinha++) {
				grid.setWidget(nlinha, ncoluna,
						criarCheckBox(this.items.get(nOrdem)));
				this.items.get(nOrdem).setLinha(nlinha);
				this.items.get(nOrdem).setColuna(ncoluna);
				nOrdem++;
			}
		}
	}

	public void removeItem(HFSMultiItem item) {
		for (int i = (this.items.size() - 1); i >= 0; i--) {
			if (this.items.get(i).getId().equals(item.getId())) {
				this.grid.removeRow(i);
				this.items.remove(i);
			}
		}
	}

	public void removeItem(int indice) {
		this.grid.removeRow(indice);
		this.items.remove(indice);
	}

	public void limpar() {
		this.grid.removeAllRows();
		this.items.clear();
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

	public void setTamanho(String largura, String altura){
		getScrollPanel().setSize(largura, altura);
	}
	
}
