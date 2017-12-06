package com.hfsgwt.client.componentes.linkedlist;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.hfsgwt.client.componentes.IHFSComposite;
import com.hfsgwt.client.componentes.HFSItem;

public class HFSLinkedListBox extends Composite implements IHFSComposite {
	private Label labItem;
	private ListBox cmbItem;
	private Label labSubItem;
	private ListBox cmbSubItem;
	private Grid grid;

	public enum TipoLista {
		LISTBOX, COMBOBOX
	}

	private boolean habilitado;
	private boolean focado;
	private boolean mostrarRotulo;
	private int larguraRotulo;
	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;

	private ArrayList<HFSLinkedItem> linkedItems;
	private TipoLista tipo;

	public HFSLinkedListBox(PosicaoRotulo posicao, TipoLista tipo,
			String rotuloCombo, String rotuloSubCombo, int largura) {
		this.linkedItems = new ArrayList<HFSLinkedItem>();
		this.tipo = tipo;
		this.habilitado = true;
		this.focado = true;
		this.mostrarRotulo = true;
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;

		initComponents(posicao);

		this.getLabItem().setText(rotuloCombo);
		this.getLabSubItem().setText(rotuloSubCombo);
		this.larguraRotulo = this.getLabItem().getOffsetWidth();

		this.getCmbItem().setWidth(largura + "px");
		this.getCmbSubItem().setWidth(largura + "px");
	}

	private void initComponents(PosicaoRotulo posicao) {
		if (posicao == PosicaoRotulo.ACIMA) {
			initWidget(getGridAcima());
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			initWidget(getGridEsquerda());
		}
	}

	private Grid getGridAcima() {
		if (grid == null) {
			if (tipo == TipoLista.COMBOBOX) {
				grid = new Grid(4, 1);
				grid.setWidget(0, 0, getLabItem());
				grid.setWidget(1, 0, getCmbItem());
				grid.setWidget(2, 0, getLabSubItem());
				grid.setWidget(3, 0, getCmbSubItem());
			} else {
				grid = new Grid(2, 2);
				grid.setWidget(0, 0, getLabItem());
				grid.setWidget(0, 1, getLabSubItem());
				grid.setWidget(1, 0, getCmbItem());
				grid.setWidget(1, 1, getCmbSubItem());
			}
		}
		return grid;
	}

	private Grid getGridEsquerda() {
		if (grid == null) {
			if (tipo == TipoLista.COMBOBOX) {
				grid = new Grid(2, 2);
				grid.setWidget(0, 0, getLabItem());
				grid.setWidget(0, 1, getCmbItem());
				grid.setWidget(1, 0, getLabSubItem());
				grid.setWidget(1, 1, getCmbSubItem());
			} else {
				grid = new Grid(1, 4);
				grid.setWidget(0, 0, getLabItem());
				grid.setWidget(0, 1, getCmbItem());
				grid.setWidget(0, 2, getLabSubItem());
				grid.setWidget(0, 3, getCmbSubItem());
			}
		}
		return grid;
	}

	private Label getLabItem() {
		if (labItem == null) {
			labItem = new Label("Items:");
		}
		return labItem;
	}

	private ListBox getCmbItem() {
		if (cmbItem == null) {
			cmbItem = new ListBox();
			cmbItem.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					cmbItemChange(event);
				}
			});
			if (tipo == TipoLista.LISTBOX) {
				cmbItem.setVisibleItemCount(5);
			}
			cmbItem.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					getCmbItem().setStyleName("HFSListBox-focado");
				}
			});
			cmbItem.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					getCmbItem().setStyleName("");
				}
			});
		}
		return cmbItem;
	}

	private Label getLabSubItem() {
		if (labSubItem == null) {
			labSubItem = new Label("SubItems:");
		}
		return labSubItem;
	}

	private ListBox getCmbSubItem() {
		if (cmbSubItem == null) {
			cmbSubItem = new ListBox();
			if (tipo == TipoLista.LISTBOX) {
				cmbSubItem.setVisibleItemCount(5);
			}
			cmbSubItem.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					getCmbSubItem().setStyleName("HFSListBox-focado");
				}
			});
			cmbSubItem.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					getCmbSubItem().setStyleName("");
				}
			});
		}
		return cmbSubItem;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		this.getCmbItem().setEnabled(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		this.getCmbItem().setFocus(focado);
	}

	public boolean isFocado() {
		return this.focado;
	}

	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getLabItem().setVisible(this.mostrarRotulo);
		this.getLabSubItem().setVisible(this.mostrarRotulo);
	}

	public void setLinkedItems(ArrayList<HFSLinkedItem> items) {
		if (items.size() > 0) {
			this.linkedItems = items;
			
			this.getCmbItem().clear();
			for (HFSLinkedItem item : this.linkedItems) {
				this.getCmbItem().addItem(item.getItem().getValor());
			}
			this.getCmbItem().setSelectedIndex(0);
			setSubLinkedItems(this.linkedItems.get(0));
		}
	}

	private void setSubLinkedItems(HFSLinkedItem item) {
		this.getCmbSubItem().clear();
		for (HFSItem subitem : item.getSubItems()) {
			this.getCmbSubItem().addItem(subitem.getValor());
		}
		this.getCmbSubItem().setSelectedIndex(0);
	}

	private void cmbItemChange(ChangeEvent evento) {
		ListBox lb = (ListBox) evento.getSource();
		int indice = lb.getSelectedIndex();
		setSubLinkedItems(linkedItems.get(indice));
	}

	public ArrayList<HFSItem> getItems() {
		ArrayList<HFSItem> lista = new ArrayList<HFSItem>();
		for (int i = 0; i < this.linkedItems.size(); i++) {
			lista.add(this.linkedItems.get(i).getItem());
		}
		return lista;
	}

	public ArrayList<HFSItem> getSubItems() {
		int indice = this.getCmbItem().getSelectedIndex();
		return this.linkedItems.get(indice).getSubItems();
	}

	public ArrayList<HFSLinkedItem> getLinkedItems() {
		return linkedItems;
	}

	public HFSItem getItemSelecionado() {
		int indice = this.getCmbItem().getSelectedIndex();
		if (indice == -1)
			return new HFSItem();
		else
			return this.linkedItems.get(indice).getItem();
	}

	public HFSItem getSubItemSelecionado() {
		int indice = this.getCmbItem().getSelectedIndex();
		if (indice == -1)
			return new HFSItem();
		else {
			int subindice = this.getCmbSubItem().getSelectedIndex();
			if (subindice == -1)
				return new HFSItem();
			else
				return this.linkedItems.get(indice).getSubItems()
						.get(subindice);
		}
	}

	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getLabItem().setWidth(larguraRotulo + "px");
		this.getLabSubItem().setWidth(larguraRotulo + "px");
	}

	public AlinhamentoRotulo getAlinhamentoRotulo() {
		return alinhamentoRotulo;
	}

	public void setAlinhamentoRotulo(AlinhamentoRotulo alinhamentoRotulo) {
		this.alinhamentoRotulo = alinhamentoRotulo;

		HorizontalAlignmentConstant alinhamento = HasHorizontalAlignment.ALIGN_DEFAULT;

		switch (alinhamentoRotulo) {
		case ESQUERDA:
			alinhamento = HasHorizontalAlignment.ALIGN_LEFT;
			break;
		case CENTRO:
			alinhamento = HasHorizontalAlignment.ALIGN_CENTER;
			break;
		case DIREITA:
			alinhamento = HasHorizontalAlignment.ALIGN_RIGHT;
			break;
		}

		if (posicao == PosicaoRotulo.ACIMA) {
			if (tipo == TipoLista.COMBOBOX) {
				getGridAcima().getCellFormatter().setHorizontalAlignment(0, 0,
						alinhamento);
				getGridAcima().getCellFormatter().setHorizontalAlignment(2, 0,
						alinhamento);
			} else {
				getGridAcima().getCellFormatter().setHorizontalAlignment(0, 0,
						alinhamento);
				getGridAcima().getCellFormatter().setHorizontalAlignment(0, 1,
						alinhamento);
			}
		} else {
			if (tipo == TipoLista.COMBOBOX) {
				getGridEsquerda().getCellFormatter().setHorizontalAlignment(0,
						0, alinhamento);
				getGridEsquerda().getCellFormatter().setHorizontalAlignment(1,
						0, alinhamento);
			} else {
				getGridEsquerda().getCellFormatter().setHorizontalAlignment(0,
						0, alinhamento);
				getGridEsquerda().getCellFormatter().setHorizontalAlignment(0,
						2, alinhamento);
			}
		}

	}

}
