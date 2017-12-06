package com.hfsgwt.client.componentes.suggest;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.hfsgwt.client.componentes.IHFSComposite;
import com.hfsgwt.client.componentes.HFSItem;

public class HFSSuggestBox extends Composite implements IHFSComposite {

	public interface CarregarHandler extends EventHandler {
		List<HFSItem> onCarregarAoIniciar();

		List<HFSItem> onCarregarAoDigitar(String consulta);
	}

	public interface ItemHandler extends EventHandler {
		void onItemSelecionado(HFSItem item);
	}

	private CarregarHandler carregarHandler;
	private ItemHandler itemHandler;
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private HTML rotulo;
	private SuggestBox suggestBox;

	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;
	
	private boolean habilitado;
	private boolean focado;
	private boolean mostrarRotulo;
	private boolean obrigatorio;
	private int larguraRotulo;
	private boolean carregaPorDemanda;
	private int limiteQtdSugestoes;
	private static final int MIN_TAM_CONSULTA = 4;

	private MultiWordSuggestOracle oracle;
	private List<HFSItem> listaSugestao;

	public HFSSuggestBox(PosicaoRotulo posicao, String rotulo, int largura,
			boolean obrigatorio, int limiteQtdSugestoes, String[] items) {
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;		
		this.habilitado = true;
		this.focado = true;
		this.mostrarRotulo = true;
		this.obrigatorio = obrigatorio;
		this.carregaPorDemanda = false;
		this.limiteQtdSugestoes = limiteQtdSugestoes;
		this.oracle = new MultiWordSuggestOracle();
		this.listaSugestao = new ArrayList<HFSItem>();

		if (items != null) {
			if (items.length > 0) {
				for (int i = 0; i < items.length; ++i) {
					oracle.add(items[i]);
				}
			}
		}

		initComponents(posicao);
		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();
		this.getSuggestBox().setWidth(largura + "px");
	}

	public HFSSuggestBox(PosicaoRotulo posicao, String rotulo, int largura,
			boolean obrigatorio, int limiteQtdSugestoes) {
		this.habilitado = true;
		this.focado = true;
		this.mostrarRotulo = true;
		this.obrigatorio = obrigatorio;
		this.carregaPorDemanda = true;
		this.limiteQtdSugestoes = limiteQtdSugestoes;
		this.oracle = new MultiWordSuggestOracle();
		this.listaSugestao = new ArrayList<HFSItem>();

		initComponents(posicao);
		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();
		this.getSuggestBox().setWidth(largura + "px");
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
			horizontalPanel.add(getSuggestBox());
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getSuggestBox());
		}
		return verticalPanel;
	}

	private void setRotulo(String rotulo) {
		if (this.obrigatorio) {
			rotulo = "<b>" + rotulo
					+ "</b><span style='color: rgb(255, 0, 0);'>*</span>";
		}
		getRotulo().setHTML(rotulo);
	}

	private HTML getRotulo() {
		if (rotulo == null) {
			rotulo = new HTML("Rotulo:");
		}
		return rotulo;
	}

	private SuggestBox getSuggestBox() {
		if (suggestBox == null) {
			suggestBox = new SuggestBox(oracle);
			suggestBox.setLimit(limiteQtdSugestoes);
			suggestBox.setStyleName("HFSSuggestBox-desfocado");
			suggestBox.getValueBox().addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					suggestBox.setStyleName("HFSSuggestBox-focado");
				}
			});
			suggestBox.getValueBox().addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					suggestBox.setStyleName("HFSSuggestBox-desfocado");
				}
			});
			suggestBox.getValueBox().addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (carregaPorDemanda) {
						int tam = suggestBox.getText().trim().length();
						if (tam >= MIN_TAM_CONSULTA) {
							//if (!suggestBox.isSuggestionListShowing())
							carregar(suggestBox.getText());
							
							if (listaSugestao.size() > 0) {
								if (!suggestBox.isSuggestionListShowing())
									suggestBox.showSuggestionList();
							}
						} else {
							if (listaSugestao.size() > 0) {
								if (suggestBox.isSuggestionListShowing())
									suggestBox.hideSuggestionList();
							}
						}
					}
				}
			});

			suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					if (itemHandler != null) {
						if (listaSugestao.size() == 0) {
							itemHandler
									.onItemSelecionado(new HFSItem("-1", event
											.getSelectedItem()
											.getDisplayString()));
						} else {
							for (HFSItem item : listaSugestao) {
								if (item.getValor().equals(
										event.getSelectedItem())) {
									itemHandler.onItemSelecionado(item);
									break;
								}
							}
						}
					}

				}
			});
		}
		return suggestBox;
	}

	private void carregar(String texto) {
		if (carregaPorDemanda) {
			if (carregarHandler != null) {				
				listaSugestao = carregarHandler.onCarregarAoDigitar(texto);
				if (listaSugestao != null) {
					if (listaSugestao.size() > 0) {
						for (HFSItem item : listaSugestao) {
							oracle.add(item.getValor());
						}
					}
				}
			}
		}
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		this.getSuggestBox().getTextBox().setEnabled(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		this.getSuggestBox().setFocus(focado);
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

	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}

	public void addCarregarHandler(CarregarHandler handler) {
		this.carregarHandler = handler;

		if (carregarHandler != null) {
			this.listaSugestao = carregarHandler.onCarregarAoIniciar();
			if (listaSugestao == null) {
				this.listaSugestao = new ArrayList<HFSItem>();
			}
		}
	}

	public void addItemHandler(ItemHandler handler) {
		this.itemHandler = handler;
	}

	public List<HFSItem> getListaSugestao() {
		return listaSugestao;
	}

	public boolean isMostrandoListaSugestao() {
		return suggestBox.isSuggestionListShowing();
	}

	public void mostrarListaSugestao() {
		suggestBox.showSuggestionList();
	}

	public void ocultarListaSugestao() {
		suggestBox.hideSuggestionList();
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
	
}
