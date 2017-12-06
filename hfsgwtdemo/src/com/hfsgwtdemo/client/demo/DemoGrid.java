package com.hfsgwtdemo.client.demo;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.hfsgwt.client.componentes.grid.HFSGrid;
import com.hfsgwt.client.componentes.grid.IHFSColumn;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwtdemo.client.DemoService;
import com.hfsgwtdemo.client.DemoServiceAsync;
import com.hfsgwtdemo.client.Pessoa;

public class DemoGrid extends Composite {
	private final DemoServiceAsync servico = GWT.create(DemoService.class);
	private AbsolutePanel absolutePanel;

	private HFSGrid grid;
	private Label labCodigo;
	private TextBox edtCodigo;
	private Label labNome;
	private TextBox edtNome;

	public DemoGrid() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());

		grid = new HFSGrid(Pessoa.getColunas(), 6, 8, 12);

		grid.addClickColunaHandler(new HFSGrid.ClickColunaHandler() {
			@Override
			public void onClickColuna(int intervaloInicial, int intervaloFinal,
					int coluna, boolean ordemCrescente) {
				listarPessoas(intervaloInicial, intervaloFinal, coluna, ordemCrescente);
			}
		});

		grid.addItemHandler(new HFSGrid.ItemHandler() {
			public void onItemSelecionado(IHFSColumn item) {
				edtCodigo.setText(item.getColuna(0));
				edtNome.setText(item.getColuna(1));
			}
		});

		grid.addCarregarHandler(new HFSGrid.CarregarHandler() {
			public void onCarregarLinha(int intervaloInicial, int intervaloFinal,
					int coluna, boolean ordemCrescente) {
				listarPessoas(intervaloInicial, intervaloFinal, coluna, ordemCrescente);
			}
		});

		grid.addAdicionarHandler(new HFSGrid.AdicionarHandler() {
			@Override
			public void onAposAdicionarLinhas(IHFSColumn item) {
				edtCodigo.setText(item.getColuna(0));
				edtNome.setText(item.getColuna(1));
			}
		});

		getAbsolutePanel().add(grid, 10, 10);

		Button btnIncluir = new Button("Incluir linhas");
		btnIncluir.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listarPessoas(0, grid.getPaginacao() - 1, -1, false);
			}
		});

		getAbsolutePanel().add(btnIncluir, 10, 200);
		btnIncluir.setSize("92px", "28px");

		Button btnExcluir = new Button("Excluir linha selecionada");
		btnExcluir.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				grid.removeLinhaSelecionada();
			}
		});

		getAbsolutePanel().add(btnExcluir, 110, 200);
		btnExcluir.setSize("170px", "28px");
		getAbsolutePanel().add(getLabCodigo(), 10, 260);
		getAbsolutePanel().add(getEdtCodigo(), 60, 260);
		getAbsolutePanel().add(getLabNome(), 10, 306);
		getAbsolutePanel().add(getEdtNome(), 60, 306);

	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setSize("464px", "363px");
		}
		return absolutePanel;
	}

	private void listarPessoas(int min, int max, final int colunaOrdena,
			final boolean ordemCrescente) {
		servico.listarPessoas(min, max, new AsyncCallback<List<Pessoa>>() {
			public void onFailure(Throwable caught) {
				Window.alert(HFSConst.SERVIDOR_ERRO);
			}

			public void onSuccess(List<Pessoa> lista) {
				grid.addLinhas(Pessoa.getLista(lista, colunaOrdena,
						ordemCrescente));
			}
		});
	}

	private Label getLabCodigo() {
		if (labCodigo == null) {
			labCodigo = new Label("Código:");
		}
		return labCodigo;
	}

	private TextBox getEdtCodigo() {
		if (edtCodigo == null) {
			edtCodigo = new TextBox();
		}
		return edtCodigo;
	}

	private Label getLabNome() {
		if (labNome == null) {
			labNome = new Label("Nome:");
		}
		return labNome;
	}

	private TextBox getEdtNome() {
		if (edtNome == null) {
			edtNome = new TextBox();
			edtNome.setVisibleLength(50);
			edtNome.setSize("347", "24");
		}
		return edtNome;
	}

}
