package com.hfsgwt.client.componentes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class HFSTable extends Composite {
	private FlexTable tabela;
	private int linhaSelecionada = -1;
	private List<List<String>> itemLinha;
	
	public interface ItemHandler extends EventHandler {
		void onItemSelecionado(List<String> item);
	}

	private ItemHandler itemHandler;
	private boolean selecionaLinha;
	
	public HFSTable(boolean selecionaLinha) {
		this.selecionaLinha = selecionaLinha;
		
		initComponents();
	}

	private void initComponents() {
		this.itemLinha = new ArrayList<List<String>>();
		
		initWidget(getTabela());
	}

	private FlexTable getTabela() {
		if (tabela == null) {
			tabela = new FlexTable();
			tabela.setCellPadding(0);
			tabela.setCellSpacing(0);
			if (selecionaLinha) {
				tabela.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Cell cell = tabela.getCellForEvent(event);
						if (cell != null) {
							if (cell.getRowIndex() > 0) {
								selecionaLinha(cell.getRowIndex());
							}
						}
					}
				});
				//selecionaLinha(1);
			}
		}
		return tabela;
	}

	public void addColunas(String[] colunas, int larguraFonte) {
		int larguraMinima = 0;
		String slargura;
		String valor;

		for (int ncol = 0; ncol < colunas.length; ncol++) {
			tabela.setText(0, ncol, colunas[ncol]);
		}
		for (int ncol = 0; ncol < colunas.length; ncol++) {
			valor = colunas[ncol];
			larguraMinima = valor.length() * larguraFonte;
			slargura = Integer.toString(larguraMinima + larguraMinima) + "px";
			tabela.getCellFormatter().setWidth(0, ncol, slargura);
			atribuirEstiloColuna(colunas.length, ncol);
		}
	}

	private void atribuirEstiloColuna(int ncols, int ncol) {
		String estilo;
		if (ncol==(ncols-1))
			estilo = "HFSTable-ucabecalho";
		else
			estilo = "HFSTable-cabecalho";
		tabela.getCellFormatter().setStyleName(0, ncol, estilo);
	}

	public void addLinhas(List<List<String>> linhas) {
		List<String> lColuna;
		int ncols = tabela.getCellCount(0);

		if (ncols > 0 && linhas.size() > 0) {
			int totalLinhas = tabela.getRowCount();
			for (int nlinha = (totalLinhas - 1); nlinha >= 1; nlinha--) {
				tabela.removeRow(nlinha);
			}			
			itemLinha.clear();
			for (int nlinha = 0; nlinha < linhas.size(); nlinha++) {
				lColuna = linhas.get(nlinha);				
				for (int ncol = 0; ncol < ncols; ncol++) {
					tabela.setText(nlinha + 1, ncol, lColuna.get(ncol));
					atribuirEstiloLinha(ncols, nlinha, ncol, true);
				}				
				itemLinha.add(linhas.get(nlinha));
			}
		}
	}
	
	public void addLinha(List<String> linha) {
		int ncols = tabela.getCellCount(0);
		int nlinha = tabela.getRowCount();
		for (int ncol = 0; ncol < ncols; ncol++) {
			tabela.setText(nlinha, ncol, linha.get(ncol));
			atribuirEstiloLinha(ncols, nlinha, ncol, true);			
		}			
		//tabela.getCellFormatter().setHorizontalAlignment(nlinha, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		itemLinha.add(linha);		
	}

	private void atribuirEstiloLinha(int ncols, int nlinha, int ncol, boolean atribuir) {
		String estilo;
		if ((nlinha % 2)==0) {
			if (ncol==(ncols-1))
				estilo = "HFSTable-ulinha1";
			else
				estilo = "HFSTable-linha1";
		} else {
			if (ncol==(ncols-1))
				estilo = "HFSTable-ulinha2";
			else
				estilo = "HFSTable-linha2";
		}
		if (atribuir)
			tabela.getCellFormatter().addStyleName(nlinha, ncol, estilo);
		else
			tabela.getCellFormatter().removeStyleName(nlinha, ncol, estilo);
	}
	
	public void selecionaLinha(int nlinha) {
		if (nlinha > 0) {
			if (itemLinha.size() > 0) {
				List<String> item = itemLinha.get(nlinha-1);
				if (item == null) {
					return;
				}

				estiloLinha(linhaSelecionada, false);
				estiloLinha(nlinha, true);
				linhaSelecionada = nlinha;

				if (itemHandler != null) {
					itemHandler.onItemSelecionado(item);
				}
			}
		}
	}

	private void estiloLinha(int nlinha, boolean selected) {
		if (nlinha != -1) {
			if (selected) {
				int ncols = tabela.getCellCount(0);
				for (int ncol = 0; ncol < ncols; ncol++) {
					atribuirEstiloLinha(ncols, nlinha, ncol, false);
				}
				atribuirEstiloLinhaSelecionada(nlinha, true);
			} else {
				atribuirEstiloLinhaSelecionada(nlinha, false);
				
				int ncols = tabela.getCellCount(0);
				for (int ncol = 0; ncol < ncols; ncol++) {
					atribuirEstiloLinha(ncols, nlinha, ncol, true);
				}
			}
		}
	}

	private void atribuirEstiloLinhaSelecionada(int nlinha, boolean atribuir) {
		String estilo;
		int ncols = tabela.getCellCount(0);
		
		for (int ncol = 0; ncol < ncols; ncol++) {
			if (ncol==(ncols-1))
				estilo = "HFSTable-ulinhaSelecionada";
			else
				estilo = "HFSTable-linhaSelecionada";
			
			if (atribuir)
				tabela.getCellFormatter().setStyleName(nlinha, ncol, estilo);
			else
				tabela.getCellFormatter().removeStyleName(nlinha, ncol, estilo);
		}		
	}
	
	public List<List<String>> getLinhas() {
		return itemLinha;
	}

	public void addItemHandler(ItemHandler handler) {
		this.itemHandler = handler;
	}
	
}
