package com.hfsgwt.client.componentes;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HFSListBox extends Composite implements IHFSComposite {
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private HTML rotulo;
	private ListBox listBox;

	public enum TipoLista {
		LISTBOX, COMBOBOX
	}

	private boolean habilitado;
	private boolean focado;
	private boolean mostrarRotulo;
	private boolean obrigatorio;
	private boolean selecaoMultipla;
	private int larguraRotulo;
	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;

	private ArrayList<HFSItem> items;
	private TipoLista tipo;

	public HFSListBox(PosicaoRotulo posicao, TipoLista tipo,
			String rotulo, int largura, boolean selecaoMultipla, boolean obrigatorio) {
		this.items = new ArrayList<HFSItem>();
		this.tipo = tipo;
		this.habilitado = true;
		this.focado = true;
		this.mostrarRotulo = true;
		this.selecaoMultipla = selecaoMultipla;
		this.obrigatorio = obrigatorio;
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;		

		initComponents(posicao);

		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();

		if (largura == 0)
			largura = 5;
		this.getListBox().setWidth(largura + "px");		
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
			horizontalPanel.add(getListBox());
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getListBox());
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

	public ListBox getListBox() {
		if (listBox == null) {
			listBox = new ListBox();
			listBox.setMultipleSelect(selecaoMultipla);
			listBox.setStyleName("HFSListBox-desfocado");
			if (tipo == TipoLista.LISTBOX) {
				setQtdItemsVisiveis(5); 
			}
			listBox.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					listBox.setStyleName("HFSListBox-focado");
				}
			});
			listBox.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					listBox.setStyleName("HFSListBox-desfocado");
				}
			});
		}
		return listBox;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		this.getListBox().setEnabled(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		this.getListBox().setFocus(focado);
	}

	public boolean isFocado() {
		return this.focado;
	}

	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getRotulo().setVisible(this.mostrarRotulo);
	}

	public void setItems(ArrayList<HFSItem> items) {
		if (items.size() > 0) {
			this.items = items;

			this.getListBox().clear();
			for (HFSItem item : this.items) {
				this.getListBox().addItem(item.getValor());
			}
			this.getListBox().setSelectedIndex(0);
		}
	}

	public ArrayList<HFSItem> getItems() {
		return items;
	}

	public HFSItem getItemSelecionado() {
		int indice = this.getListBox().getSelectedIndex();
		if (indice==-1)
			return new HFSItem();
		else
			return this.items.get(indice);
	}

	public void limpar() {
		this.getListBox().clear();
		this.items.clear();
	}

	public int getIndiceSelecionado() {
		return this.getListBox().getSelectedIndex();
	}

	public void setIndiceSelecionado(int indice) {
		this.getListBox().setSelectedIndex(indice);
	}

	public void addItem(HFSItem item) {
		this.getListBox().addItem(item.getValor());
		this.items.add(item);
	}

	public boolean existe(String valor) {
		if (items.size() > 0) {
			for (HFSItem item : this.items) {
				if (item.getValor().equals(valor)){
					return true;
				}
			}		
		}
		return false;
	}
	
	public void removeItem(HFSItem item) {
		for (int i = (this.items.size() - 1); i >= 0; i--) {
			if (this.items.get(i).getId().equals(item.getId())) {
				this.getListBox().removeItem(i);
				this.items.remove(i);
			}
		}
	}

	public void removeItem(int indice) {
		this.getListBox().removeItem(indice);
		this.items.remove(indice);
	}

	public void removeSelecionados() {
		for (int i = (this.getListBox().getItemCount() - 1); i >= 0; i--) {
			if (this.getListBox().isItemSelected(i)) {
				this.getListBox().removeItem(i);
	
				this.items.remove(i);
			}
		}
	}
	
	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}

	public int getQtdItemsVisiveis() {
		return listBox.getVisibleItemCount();
	}

	public void setQtdItemsVisiveis(int qtdItemsVisiveis) {
		if (tipo == TipoLista.LISTBOX) {
			listBox.setVisibleItemCount(qtdItemsVisiveis);
		}
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

	public void setTamanho(String largura, String altura){
		this.getListBox().setSize(largura, altura);

		if (posicao == PosicaoRotulo.ACIMA) {
			getVerticalPanel().setSize(largura, altura);
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			getHorizontalPanel().setSize(largura, altura);
		}
	}
}
