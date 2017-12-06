package com.hfsgwtdemo.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSNavigator;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoTamanho;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoVisivel;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.util.HFSConst;

public class HFSCadastro extends Composite {
	private final DemoServiceAsync servico = GWT.create(DemoService.class);

	private DockPanel dockPanel;
	private HFSNavigator navigator;
	private DecoratedTabPanel decoratedTabPanel;
	private VerticalPanel cadastroPanel;
	private HFSTextBox edtCodigo;
	private HFSTextBox edtDescricao;
	private HorizontalPanel pesquisaPanel;
	private HFSListBox listBox;
	private HFSTextBox edtPesquisa;
	private VerticalPanel localizarPanel;
	private HFSStringGrid gridPesquisa;
	private Button btnPesquisar;

	private int linhaGridPesquisa;

	public HFSCadastro() {
		linhaGridPesquisa = 0;

		initComponents();
	}

	private void initComponents() {
		initWidget(getDockPanel());
	}

	private DockPanel getDockPanel() {
		if (dockPanel == null) {
			dockPanel = new DockPanel();
			dockPanel.setSize("400px", "251px");
			dockPanel.add(getNavigator(), DockPanel.NORTH);
			dockPanel.add(getDecoratedTabPanel(), DockPanel.CENTER);
		}
		return dockPanel;
	}

	private HFSNavigator getNavigator() {
		if (navigator == null) {
			navigator = new HFSNavigator(BotaoVisivel.PADRAO,
					(BotaoTamanho) null, false);
			navigator.addNavClickHandler(new HFSNavigator.NavClickHandler() {
				@Override
				public void onBtnPrimeiroClick() {
					linhaGridPesquisa = 1;
					gridPesquisa.selecionaLinha(linhaGridPesquisa);
				}

				@Override
				public void onBtnAnteriorClick() {
					if (linhaGridPesquisa > 1) {
						linhaGridPesquisa--;
						gridPesquisa.selecionaLinha(linhaGridPesquisa);
					}
				}

				@Override
				public void onBtnProximoClick() {
					if (linhaGridPesquisa <= gridPesquisa.getQtdLinhas()) {
						linhaGridPesquisa++;
						gridPesquisa.selecionaLinha(linhaGridPesquisa);
					}
				}

				@Override
				public void onBtnUltimoClick() {
					linhaGridPesquisa = gridPesquisa.getQtdLinhas();
					gridPesquisa.selecionaLinha(linhaGridPesquisa);
				}
			});
			navigator.addManClickHandler(new HFSNavigator.ManClickHandler() {
				@Override
				public void onBtnIncluirClick() {
				}

				@Override
				public void onBtnAlterarClick() {
				}

				@Override
				public void onBtnExcluirClick() {
				}

				@Override
				public void onBtnConfirmarClick() {
				}

				@Override
				public void onBtnCancelarClick() {
				}

				@Override
				public void onBtnAtualizarClick() {
				}
			});
		}
		return navigator;
	}

	private DecoratedTabPanel getDecoratedTabPanel() {
		if (decoratedTabPanel == null) {
			decoratedTabPanel = new DecoratedTabPanel();
			// decoratedTabPanel.setSize("200px", "200px");
			decoratedTabPanel.add(getCadastroPanel(), "Cadastro", false);
			decoratedTabPanel.add(getLocalizarPanel(), "Localizar", false);
			decoratedTabPanel.selectTab(0);
		}
		return decoratedTabPanel;
	}

	private VerticalPanel getCadastroPanel() {
		if (cadastroPanel == null) {
			cadastroPanel = new VerticalPanel();
			cadastroPanel.setSpacing(5);
			// cadastroPanel.setSize("5cm", "3cm");
			cadastroPanel.add(getEdtCodigo());
			cadastroPanel.add(getEdtDescricao());
		}
		return cadastroPanel;
	}

	private HFSTextBox getEdtCodigo() {
		if (edtCodigo == null) {
			edtCodigo = new HFSTextBox(PosicaoRotulo.ACIMA, "Código",
					Formato.NUMERO, 10, 10, true);
		}
		return edtCodigo;
	}

	private HFSTextBox getEdtDescricao() {
		if (edtDescricao == null) {
			edtDescricao = new HFSTextBox(PosicaoRotulo.ACIMA, "Descrição",
					Formato.PADRAO, 50, 50, false);
		}
		return edtDescricao;
	}

	private HorizontalPanel getPesquisaPanel() {
		if (pesquisaPanel == null) {
			pesquisaPanel = new HorizontalPanel();
			pesquisaPanel.setSpacing(5);
			pesquisaPanel
					.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			pesquisaPanel.add(getListBox());
			pesquisaPanel.add(getEdtPesquisa());
			pesquisaPanel.add(getBtnPesquisar());
		}
		return pesquisaPanel;
	}

	private HFSListBox getListBox() {
		if (listBox == null) {
			ArrayList<HFSItem> items = new ArrayList<HFSItem>();
			items.add(new HFSItem("1", "Contendo"));
			items.add(new HFSItem("2", "Iniciando com"));
			items.add(new HFSItem("2", "Terminando com"));
			items.add(new HFSItem("3", "="));
			listBox = new HFSListBox(HFSListBox.PosicaoRotulo.ACIMA,
					TipoLista.COMBOBOX, "Pesquisa", 130, false, true);
			listBox.setItems(items);
			listBox.setIndiceSelecionado(1);
		}
		return listBox;
	}

	private HFSTextBox getEdtPesquisa() {
		if (edtPesquisa == null) {
			edtPesquisa = new HFSTextBox(PosicaoRotulo.ACIMA, "Descrição",
					Formato.PADRAO, 50, 30, false);
		}
		return edtPesquisa;
	}

	private VerticalPanel getLocalizarPanel() {
		if (localizarPanel == null) {
			localizarPanel = new VerticalPanel();
			// localizarPanel.setSize("5cm", "3cm");
			localizarPanel.add(getPesquisaPanel());
			localizarPanel.add(getGridPesquisa());
		}
		return localizarPanel;
	}

	private HFSStringGrid getGridPesquisa() {
		if (gridPesquisa == null) {
			gridPesquisa = new HFSStringGrid(new String[] { "Código", "Descrição" },
					new Boolean[] { true, true }, 10, 5, 11);
			gridPesquisa.setWidth("100%");
			gridPesquisa.addCarregarHandler(new HFSStringGrid.CarregarHandler() {
				@Override
				public void onCarregarLinha(int intervaloInicial,
						int intervaloFinal) {
					listarPessoas(intervaloInicial, intervaloFinal);
				}
			});
			gridPesquisa.addAdicionarHandler(new HFSStringGrid.AdicionarHandler() {
				@Override
				public void onAposAdicionarLinhas(List<String> item) {
					edtCodigo.setTexto(item.get(0));
					edtDescricao.setTexto(item.get(1));
				}
			});
			gridPesquisa.addItemHandler(new HFSStringGrid.ItemHandler() {
				@Override
				public void onItemSelecionado(List<String> item) {
					edtCodigo.setTexto(item.get(0));
					edtDescricao.setTexto(item.get(1));
				}
			});
		}
		return gridPesquisa;
	}

	private Button getBtnPesquisar() {
		if (btnPesquisar == null) {
			btnPesquisar = new Button("Pesquisar");
			btnPesquisar.setWidth("80px");
			btnPesquisar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					listarPessoas(0, gridPesquisa.getPaginacao() - 1);
				}
			});
		}
		return btnPesquisar;
	}

	private void listarPessoas(int min, int max) {
		servico.listarPessoas(min, max, new AsyncCallback<List<Pessoa>>() {
			public void onFailure(Throwable caught) {
				Window.alert(HFSConst.SERVIDOR_ERRO);
			}

			public void onSuccess(List<Pessoa> lista) {
				List<List<String>> linhas1 = new ArrayList<List<String>>();
				for (Iterator<Pessoa> iterator = lista.iterator(); iterator
						.hasNext();) {
					Pessoa pessoa = iterator.next();

					List<String> item1 = new ArrayList<String>();
					item1.add(Integer.toString(pessoa.getCodigo()));
					item1.add(pessoa.getNome());
					linhas1.add(item1);
				}
				gridPesquisa.addLinhas(linhas1);
			}
		});
	}

}
