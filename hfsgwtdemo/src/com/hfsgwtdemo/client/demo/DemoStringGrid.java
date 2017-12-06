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
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwtdemo.client.DemoService;
import com.hfsgwtdemo.client.DemoServiceAsync;
import com.hfsgwtdemo.client.Pessoa;

public class DemoStringGrid extends Composite {
	private final DemoServiceAsync servico = GWT.create(DemoService.class);
	private AbsolutePanel absolutePanel;

	private HFSStringGrid grid;
	private Label labCodigo;
	private TextBox edtCodigo;
	private Label labNome;
	private TextBox edtNome;

	public DemoStringGrid() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());

		String[] colunas = new String[] { "Código", "Nome" };
		//grid = new HFSStringGrid(colunas, new Boolean[] { true, true }, 6, 8, 12);
		grid = new HFSStringGrid(colunas, new Boolean[] { true, true }, 6);
		grid.addItemHandler(new HFSStringGrid.ItemHandler() {
			public void onItemSelecionado(List<String> item) {
				edtCodigo.setText(item.get(0));
				edtNome.setText(item.get(1));
			}
		});

		grid.addCarregarHandler(new HFSStringGrid.CarregarHandler() {
			public void onCarregarLinha(int intervaloInicial, int intervaloFinal) {
				//listarPessoas(intervaloInicial, intervaloFinal);
				listarPessoas(0, 11);
			}
		});

		grid.addAdicionarHandler(new HFSStringGrid.AdicionarHandler() {
			@Override
			public void onAposAdicionarLinhas(List<String> item) {
				edtCodigo.setText(item.get(0));
				edtNome.setText(item.get(1));
			}
		});

		getAbsolutePanel().add(grid, 10, 10);

		Button btnIncluir = new Button("Incluir linhas");
		btnIncluir.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//listarPessoas(0, grid.getPaginacao() - 1);
				listarPessoas(0, 11);
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

	private void listarPessoas(int min, int max) {
		servico.listarPessoas(min, max, new AsyncCallback<List<Pessoa>>() {
			public void onFailure(Throwable caught) {
				Window.alert(HFSConst.SERVIDOR_ERRO);
			}

			public void onSuccess(List<Pessoa> lista) {
				grid.addLinhas((new Pessoa()).getListaGrid(lista));
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
