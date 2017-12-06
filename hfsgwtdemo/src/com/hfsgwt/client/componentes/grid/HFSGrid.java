package com.hfsgwt.client.componentes.grid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.hfsgwt.client.componentes.HFSNavigator;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoTamanho;
import com.hfsgwt.client.componentes.HFSNavigator.BotaoVisivel;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSGrid extends Composite {

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public interface ItemHandler extends EventHandler {
		void onItemSelecionado(IHFSColumn item);
	}

	public interface CarregarHandler extends EventHandler {
		void onCarregarLinha(int intervaloInicial, int intervaloFinal,
				int coluna, boolean ordemCrescente);
	}

	public interface AdicionarHandler extends EventHandler {
		void onAposAdicionarLinhas(IHFSColumn item);
	}

	public interface ClickColunaHandler extends EventHandler {
		void onClickColuna(int intervaloInicial, int intervaloFinal,
				int coluna, boolean ordemCrescente);
	}

	private DockLayoutPanel dockLayoutPanel;
	private ScrollPanel scrollPanel;
	private FlexTable gridConteudo;

	private ItemHandler itemHandler;
	private CarregarHandler carregarHandler;
	private AdicionarHandler adicionarHandler;
	private ClickColunaHandler clickColunaHandler;
	private int linhaSelecionada = -1;
	private DockPanel dockPanel;
	private HFSNavigator paginador;
	private List<IHFSColumn> itemLinha;

	private boolean usarPaginador;
	private int paginacao;
	private int totalRegistros;
	private HorizontalPanel horizontalPanel;
	private Image image;
	private HFSColumn[] hfsColunas;
	private HFSGridColumn[] colunas;
	private HFSColumn hfsColunaSel;
	private int larguraFonte;
	private int larguraImagem;
	
	public HFSGrid(HFSGridColumn[] colunas, int larguraFonte) {
		this.colunas = colunas;
		this.larguraFonte = larguraFonte;
		this.larguraImagem = 8 * larguraFonte;
		this.usarPaginador = false;
		this.paginacao = 0;
		this.totalRegistros = 0;
		this.itemLinha = new ArrayList<IHFSColumn>();

		initComponents();

		this.addColunas(colunas, larguraFonte);
	}

	public HFSGrid(HFSGridColumn[] colunas, int larguraFonte, int paginacao,
			int totalRegistros) {
		this.colunas = colunas;
		this.larguraFonte = larguraFonte;
		this.larguraImagem = 8 * larguraFonte;
		this.usarPaginador = true;
		this.paginacao = paginacao;
		this.totalRegistros = totalRegistros;
		this.itemLinha = new ArrayList<IHFSColumn>();

		initComponents();

		paginador.setPaginacao(0, 0, 0);

		this.addColunas(colunas, larguraFonte);
	}

	private void initComponents() {
		initWidget(getDockLayoutPanel());
	}

	private HFSNavigator getPaginador() {
		if (paginador == null) {
			paginador = new HFSNavigator(BotaoVisivel.NAVEGAR,
					BotaoTamanho.PEQUENO, true);
			paginador.addNavClickHandler(new HFSNavigator.NavClickHandler() {
				@Override
				public void onBtnAnteriorClick() {
					atualizaGrid();
				}

				@Override
				public void onBtnPrimeiroClick() {
					atualizaGrid();
				}

				@Override
				public void onBtnProximoClick() {
					atualizaGrid();
				}

				@Override
				public void onBtnUltimoClick() {
					atualizaGrid();
				}
			});
		}
		return paginador;
	}

	private DockLayoutPanel getDockLayoutPanel() {
		if (dockLayoutPanel == null) {
			dockLayoutPanel = new DockLayoutPanel(Unit.EM);
			dockLayoutPanel.setSize("268px", "175px");
			dockLayoutPanel.setStyleName("HFSGrid-borda");
			if (usarPaginador) {
				dockLayoutPanel.addNorth(getHorizontalPanel(), 2.0);
			}
			dockLayoutPanel.add(getScrollPanel());
		}
		return dockLayoutPanel;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getPaginador());
			horizontalPanel.add(getImage());
			horizontalPanel.setCellHorizontalAlignment(getImage(),
					HasHorizontalAlignment.ALIGN_RIGHT);
			horizontalPanel.setCellWidth(getImage(), "100%");
			horizontalPanel.setCellVerticalAlignment(getImage(),
					HasVerticalAlignment.ALIGN_MIDDLE);
		}
		return horizontalPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image(img.carregando());
			image.setSize("16px", "16px");
			image.setVisible(false);
		}
		return image;
	}

	private DockPanel getDockPanel() {
		if (dockPanel == null) {
			dockPanel = new DockPanel();
			dockPanel.setSize("100%", "100%");
			dockPanel.add(getGridConteudo(), DockPanel.CENTER);
		}
		return dockPanel;
	}

	private ScrollPanel getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new ScrollPanel();
			scrollPanel.setSize("100%", "100%");
			scrollPanel.setWidget(getDockPanel());
		}
		return scrollPanel;
	}

	private void atribuirEstiloColuna(int ncols, int ncol) {
		String estilo;
		if (ncol==(ncols-1))
			estilo = "HFSGrid-ucabecalho";
		else
			estilo = "HFSGrid-cabecalho";
		gridConteudo.getCellFormatter().setStyleName(0, ncol, estilo);
	}
	
	private void atribuirEstiloLinha(int ncols, int nlinha, int ncol, boolean atribuir) {
		String estilo;
		if ((nlinha % 2)==0) {
			if (ncol==(ncols-1))
				estilo = "HFSGrid-ulinha1";
			else
				estilo = "HFSGrid-linha1";
		} else {
			if (ncol==(ncols-1))
				estilo = "HFSGrid-ulinha2";
			else
				estilo = "HFSGrid-linha2";
		}
		if (atribuir)
			gridConteudo.getCellFormatter().addStyleName(nlinha, ncol, estilo);
		else
			gridConteudo.getCellFormatter().removeStyleName(nlinha, ncol, estilo);
	}
	
	public void selecionaLinha(int nlinha) {
		if (nlinha > 0) {
			if (itemLinha.size() > 0) {
				IHFSColumn item = itemLinha.get(nlinha - 1);
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
				int ncols = gridConteudo.getCellCount(0);
				for (int ncol = 0; ncol < ncols; ncol++) {
					atribuirEstiloLinha(ncols, nlinha, ncol, false);
				}
				atribuirEstiloLinhaSelecionada(nlinha, true);
			} else {
				atribuirEstiloLinhaSelecionada(nlinha, false);
				
				int ncols = gridConteudo.getCellCount(0);
				for (int ncol = 0; ncol < ncols; ncol++) {
					atribuirEstiloLinha(ncols, nlinha, ncol, true);
				}
			}
		}
	}

	private void atribuirEstiloLinhaSelecionada(int nlinha, boolean atribuir) {
		String estilo;
		int ncols = gridConteudo.getCellCount(0);
		
		for (int ncol = 0; ncol < ncols; ncol++) {
			if (ncol==(ncols-1))
				estilo = "HFSGrid-ulinhaSelecionada";
			else
				estilo = "HFSGrid-linhaSelecionada";
			
			if (atribuir)
				gridConteudo.getCellFormatter().setStyleName(nlinha, ncol, estilo);
			else
				gridConteudo.getCellFormatter().removeStyleName(nlinha, ncol, estilo);
		}		
	}

	private FlexTable getGridConteudo() {
		if (gridConteudo == null) {
			gridConteudo = new FlexTable();
			gridConteudo.setSize("100%", "100%");
			gridConteudo.setCellPadding(0);
			gridConteudo.setCellSpacing(0);
			gridConteudo.setStyleName("HFSGrid-Borda");
			//gridConteudo.setBorderWidth(0);
			
			gridConteudo.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Cell cell = gridConteudo.getCellForEvent(event);
					if (cell != null) {
						if (cell.getRowIndex() > 0) {
							selecionaLinha(cell.getRowIndex());
						}
					}
				}
			});
			selecionaLinha(1);
		}
		return gridConteudo;
	}

	private HFSColumn getColuna(int indice, String titulo) {
		HFSColumn coluna = new HFSColumn(indice, titulo);
		coluna.addColClickHandler(new HFSColumn.ColClickHandler() {
			@Override
			public boolean onClick(HFSColumn col) {
				if (gridConteudo.getRowCount() > 1) {
					if (colunas[col.getIndice()].isPermitirOrdenacao()) {				
						for (HFSColumn item : hfsColunas) {
							if (item.getIndice() != col.getIndice()) {
								item.setImagemSemOrdem();
							}
						}
						if (clickColunaHandler != null) {
							hfsColunaSel = col;
							int rinicial = 0;
							int rfinal = 0;

							if (usarPaginador) {
								rinicial = getPaginador().getPaginacaoInicial();
								rfinal = getPaginador().getPaginacaoFinal();
							}
							if (rfinal > 0) {
								clickColunaHandler.onClickColuna(rinicial,
										rfinal, col.getIndice(), col
												.getOrdemCrescente());
							}
						}
					}
					return true;
				} else
					return false;
			}
		});
		return coluna;
	}

	private void addColunas(HFSGridColumn[] colunas, int larguraFonte) {
		int larguraMinima = 0;
		String slargura;
		String valor;

		hfsColunas = new HFSColumn[colunas.length];
		for (int ncol = 0; ncol < colunas.length; ncol++) {
			valor = colunas[ncol].getTitulo();
			hfsColunas[ncol] = getColuna(ncol, valor);
			gridConteudo.setWidget(0, ncol, hfsColunas[ncol]);
		}		
		for (int ncol = 0; ncol < colunas.length; ncol++) {
			valor = colunas[ncol].getTitulo();
			larguraMinima = valor.length() * larguraFonte;
			slargura = Integer.toString(larguraMinima + larguraImagem) + "px";
			gridConteudo.getCellFormatter().setWidth(0, ncol, slargura);
			atribuirEstiloColuna(colunas.length, ncol);
		}		
	}

	public HFSGridColumn[] getColunas() {
		return colunas;
	}
	
	public void addLinhas(List<IHFSColumn> linhas) {
		IHFSColumn lColuna;
		int nmaior = 0, ntamrotulo = 0, ntamlinha = 0;
		String srotulo, slargura;
		int ncols = gridConteudo.getCellCount(0);

		if (ncols > 0 && linhas.size() > 0) {
			int totalLinhas = gridConteudo.getRowCount();
			for (int nlinha = (totalLinhas - 1); nlinha >= 1; nlinha--) {
				gridConteudo.removeRow(nlinha);
			}
			itemLinha.clear();
			for (int nlinha = 0; nlinha < linhas.size(); nlinha++) {
				lColuna = linhas.get(nlinha);
				for (int ncol = 0; ncol < ncols; ncol++) {
					gridConteudo.setText(nlinha + 1, ncol, lColuna
							.getColuna(ncol));
					atribuirEstiloLinha(ncols, nlinha + 1, ncol, true);
				}
				itemLinha.add(linhas.get(nlinha));
			}

			if (colunas.length > 0) {
				for (int ncol = 0; ncol < ncols; ncol++) {
					if (colunas[ncol].isLarguraAutomatica()) {
						srotulo = gridConteudo.getText(0, ncol);
						ntamrotulo = srotulo.length() * larguraFonte;

						for (int nlinha = 0; nlinha < linhas.size(); nlinha++) {
							ntamlinha = linhas.get(nlinha).getColuna(ncol).length()	* larguraFonte;
							nmaior = Math.max(nmaior, ntamlinha);
						}
						
						if (nmaior > ntamrotulo) {
							slargura = Integer.toString(nmaior + larguraImagem) + "px";
						} else {
							slargura = Integer.toString(ntamrotulo + larguraImagem) + "px";
						}
						gridConteudo.getCellFormatter().setWidth(0,	ncol, slargura);						
					}
				}
			}

			estiloLinha(1, true);
			linhaSelecionada = 1;
			if (usarPaginador) {
				paginador.setPaginacao(paginador.getPaginacaoInicial(),
						this.totalRegistros, this.paginacao);
			}
		}
		if (usarPaginador) {
			getImage().setVisible(false);
		}

		if (adicionarHandler != null) {
			if (itemLinha.size() > 0) {
				IHFSColumn item = itemLinha.get(0);
				if (item != null) {
					adicionarHandler.onAposAdicionarLinhas(item);
				}
			}
		}

	}
	
	public void addLinha(List<IHFSColumn> linha) {
		int ncols = gridConteudo.getCellCount(0);
		int nlinha = gridConteudo.getRowCount();
		IHFSColumn lColuna = linha.get(nlinha);
		for (int ncol = 0; ncol < ncols; ncol++) {
			gridConteudo.setText(nlinha, ncol, lColuna
					.getColuna(ncol));
			atribuirEstiloLinha(ncols, nlinha, ncol, true);
		}
		itemLinha.add(linha.get(nlinha));		
	}
	
	public List<IHFSColumn> getLinhas() {
		return itemLinha;
	}

	public boolean existe(int ncol, String valor){
		String texto = "";
		for (int nlinha = 1; nlinha < gridConteudo.getRowCount(); nlinha++) {
			texto = gridConteudo.getText(nlinha, ncol);
			if (texto.equals(valor)){
				return true;
			}
		}
		return false;
	}

	public boolean existe(List<String> item){
		String texto = "";
		boolean achou = false;
		
		if (item.size() > 0) {
			int ncols = gridConteudo.getCellCount(0);		
			for (int nlinha = 1; nlinha < (itemLinha.size()+1); nlinha++) {
				achou = false;
				for (int ncol = 0; ncol < ncols; ncol++) {
					texto = gridConteudo.getText(nlinha, ncol);				
					achou = texto.equals(item.get(ncol));
					if (!achou){
						break;
					}
				}
				if (achou){
					break;
				}
			}
		}
		return achou;
	}
	
	public void removeLinhas() {
		int totalLinhas = gridConteudo.getRowCount();
		for (int nlinha = (totalLinhas - 1); nlinha >= 1; nlinha--) {
			gridConteudo.removeRow(nlinha);
		}
		itemLinha.clear();

		if (usarPaginador) {
			paginador.setPaginacao(0, 0, this.paginacao);
		}
	}

	public void removeLinhaSelecionada() {
		int numLinhas = gridConteudo.getRowCount();
		if (numLinhas > 1) {
			gridConteudo.removeRow(linhaSelecionada);
			itemLinha.remove(linhaSelecionada - 1);
		}
	}

	public int getQtdLinhas() {
		// return gridConteudo.getRowCount();
		return gridConteudo.getRowCount() - 1;
	}

	public void addItemHandler(ItemHandler handler) {
		this.itemHandler = handler;
	}

	public void addCarregarHandler(CarregarHandler handler) {
		this.carregarHandler = handler;
	}

	public void addAdicionarHandler(AdicionarHandler handler) {
		this.adicionarHandler = handler;
	}

	public void addClickColunaHandler(ClickColunaHandler handler) {
		this.clickColunaHandler = handler;
	}

	private void atualizaGrid() {
		int rinicial = 0;
		int rfinal = 0;

		if (usarPaginador) {
			getImage().setVisible(true);

			rinicial = this.getPaginador().getPaginacaoInicial();
			rfinal = this.getPaginador().getPaginacaoFinal();
		}

		if (carregarHandler != null) {
			if (hfsColunaSel!=null)
				carregarHandler.onCarregarLinha(rinicial, rfinal, 
						hfsColunaSel.getIndice(), hfsColunaSel.getOrdemCrescente());
			else
				carregarHandler.onCarregarLinha(rinicial, rfinal, -1, false);
		}
	}

	public int getPaginacao() {
		return this.paginacao;
	}

	public int getTotalRegistros() {
		return totalRegistros;
	}

	public void setTamanho(String largura, String altura){
		dockLayoutPanel.setSize(largura, altura);
	}
	
}
