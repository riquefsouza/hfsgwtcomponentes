package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSNavigator extends Composite {
	private HorizontalPanel panelNavigator;
	private PushButton btnPrimeiro;
	private PushButton btnAnterior;
	private PushButton btnProximo;
	private PushButton btnUltimo;
	private PushButton btnIncluir;
	private PushButton btnExcluir;
	private PushButton btnAlterar;
	private PushButton btnConfirmar;
	private PushButton btnCancelar;
	private PushButton btnAtualizar;
	private Label labPaginacao;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);
	
	public interface NavClickHandler extends EventHandler {
		void onBtnPrimeiroClick();

		void onBtnAnteriorClick();

		void onBtnProximoClick();

		void onBtnUltimoClick();
	}

	public interface ManClickHandler extends EventHandler {
		void onBtnIncluirClick();

		void onBtnExcluirClick();

		void onBtnAlterarClick();

		void onBtnConfirmarClick();

		void onBtnCancelarClick();

		void onBtnAtualizarClick();
	}

	public enum Botao {
		PRIMEIRO, ANTERIOR, PROXIMO, ULTIMO, INCLUIR, EXCLUIR, ALTERAR, CONFIRMAR, CANCELAR, ATUALIZAR
	}

	public enum BotaoVisivel {
		PADRAO, NAVEGAR, MANIPULAR
	}

	public enum BotaoTamanho {
		GRANDE, PEQUENO
	}

	private ManClickHandler manClickHandler;
	private NavClickHandler navClickHandler;
	private boolean confirmaExcluir;
	private boolean usarPaginacao;
	private int paginacaoInicial, paginacaoFinal, paginacaoTotal, paginacao;
	private BotaoTamanho btamanho;

	public HFSNavigator(BotaoVisivel botaoVisivel, BotaoTamanho botaoTamanho,
			boolean usarPaginacao) {
		this.confirmaExcluir = false;
		this.btamanho = botaoTamanho;
		this.usarPaginacao = usarPaginacao;
		this.paginacaoInicial = 0;
		this.paginacaoFinal = 0;
		this.paginacaoTotal = 0;
		this.paginacao = 0;

		initComponents();

		if (usarPaginacao) {
			botaoVisivel = BotaoVisivel.NAVEGAR;
			definePaginacao(0);
			habilitaBotoesNav(false, false, false, false);
		}

		switch (botaoVisivel) {
		case NAVEGAR:
			setBotoesVisiveis(new Botao[] { Botao.PRIMEIRO, Botao.ANTERIOR,
					Botao.PROXIMO, Botao.ULTIMO });
			break;
		case MANIPULAR:
			setBotoesVisiveis(new Botao[] { Botao.INCLUIR, Botao.EXCLUIR,
					Botao.ALTERAR, Botao.CONFIRMAR, Botao.CANCELAR,
					Botao.ATUALIZAR });
			break;
		}
	}

	private void initComponents() {
		initWidget(getPanelNavigator());
	}

	private HorizontalPanel getPanelNavigator() {
		if (panelNavigator == null) {
			panelNavigator = new HorizontalPanel();
			panelNavigator.add(getBtnPrimeiro());
			panelNavigator.add(getBtnAnterior());
			if (usarPaginacao) {
				panelNavigator.add(getLabPaginacao());
				panelNavigator.setCellVerticalAlignment(getLabPaginacao(),
						HasVerticalAlignment.ALIGN_MIDDLE);
			}
			panelNavigator.add(getBtnProximo());
			panelNavigator.add(getBtnUltimo());
			panelNavigator.add(getBtnIncluir());
			panelNavigator.add(getBtnExcluir());
			panelNavigator.add(getBtnAlterar());
			panelNavigator.add(getBtnConfirmar());
			panelNavigator.add(getBtnCancelar());
			panelNavigator.add(getBtnAtualizar());
		}
		return panelNavigator;
	}

	private Label getLabPaginacao() {
		if (labPaginacao == null) {
			labPaginacao = new Label();
			labPaginacao.setWordWrap(false);
			labPaginacao.setTitle("Paginação");
			labPaginacao.setStyleName("HFSNavigator-labPaginacao");
		}
		return labPaginacao;
	}

	private PushButton getBtnPrimeiro() {
		if (btnPrimeiro == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnPrimeiro = new PushButton(new Image(img
						.navigatorPrimeiro10()));
				btnPrimeiro.setSize("10px", "15px");
			} else {
				btnPrimeiro = new PushButton(new Image(img
						.navigatorPrimeiro16()));
				btnPrimeiro.setSize("16px", "16px");
			}
			btnPrimeiro.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (usarPaginacao) {
						manipulaPaginacaoLimite(true);
					}
					if (navClickHandler != null) {
						navClickHandler.onBtnPrimeiroClick();
					}
				}
			});
			btnPrimeiro.setTitle("Primeiro Registro");
		}
		return btnPrimeiro;
	}

	private PushButton getBtnAnterior() {
		if (btnAnterior == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnAnterior = new PushButton(new Image(img
						.navigatorAnterior10()));
				btnAnterior.setSize("10px", "15px");
			} else {
				btnAnterior = new PushButton(new Image(img
						.navigatorAnterior16()));
				btnAnterior.setSize("16px", "16px");
			}
			btnAnterior.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (usarPaginacao) {
						manipulaPaginacao(false);
					}
					if (navClickHandler != null) {
						navClickHandler.onBtnAnteriorClick();
					}
				}
			});
			btnAnterior.setTitle("Registro Anterior");
		}
		return btnAnterior;
	}

	private PushButton getBtnProximo() {
		if (btnProximo == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnProximo = new PushButton(new Image(img
						.navigatorProximo10()));
				btnProximo.setSize("10px", "15px");
			} else {
				btnProximo = new PushButton(new Image(img
						.navigatorProximo16()));
				btnProximo.setSize("16px", "16px");
			}
			btnProximo.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (usarPaginacao) {
						manipulaPaginacao(true);
					}
					if (navClickHandler != null) {
						navClickHandler.onBtnProximoClick();
					}
				}
			});
			btnProximo.setTitle("Próximo Registro");
		}
		return btnProximo;
	}

	private PushButton getBtnUltimo() {
		if (btnUltimo == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnUltimo = new PushButton(new Image(img
						.navigatorUltimo10()));
				btnUltimo.setSize("10px", "15px");
			} else {
				btnUltimo = new PushButton(new Image(img
						.navigatorUltimo16()));
				btnUltimo.setSize("16px", "16px");
			}
			btnUltimo.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (usarPaginacao) {
						manipulaPaginacaoLimite(false);
					}
					if (navClickHandler != null) {
						navClickHandler.onBtnUltimoClick();
					}
				}
			});
			btnUltimo.setTitle("Último Registro");
		}
		return btnUltimo;
	}

	private PushButton getBtnIncluir() {
		if (btnIncluir == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnIncluir = new PushButton(new Image(img
						.navigatorIncluir10()));
				btnIncluir.setSize("10px", "15px");
			} else {
				btnIncluir = new PushButton(new Image(img
						.navigatorIncluir16()));
				btnIncluir.setSize("16px", "16px");
			}
			btnIncluir.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						manClickHandler.onBtnIncluirClick();
					}
				}
			});
			btnIncluir.setTitle("Incluir Registro");
		}
		return btnIncluir;
	}

	private PushButton getBtnExcluir() {
		if (btnExcluir == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnExcluir = new PushButton(new Image(img
						.navigatorExcluir10()));
				btnExcluir.setSize("10px", "15px");
			} else {
				btnExcluir = new PushButton(new Image(img
						.navigatorExcluir16()));
				btnExcluir.setSize("16px", "16px");
			}
			btnExcluir.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						if (confirmaExcluir) {
							if (Window.confirm("Deseja excluir?")) {
								manClickHandler.onBtnExcluirClick();
							}
						} else
							manClickHandler.onBtnExcluirClick();
					}
				}
			});
			btnExcluir.setTitle("Excluir Registro");
		}
		return btnExcluir;
	}

	private PushButton getBtnAlterar() {
		if (btnAlterar == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnAlterar = new PushButton(new Image(img
						.navigatorAlterar10()));
				btnAlterar.setSize("10px", "15px");
			} else {
				btnAlterar = new PushButton(new Image(img
						.navigatorAlterar16()));
				btnAlterar.setSize("16px", "16px");
			}
			btnAlterar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						manClickHandler.onBtnAlterarClick();
					}
				}
			});
			btnAlterar.setTitle("Alterar Registro");
		}
		return btnAlterar;
	}

	private PushButton getBtnConfirmar() {
		if (btnConfirmar == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnConfirmar = new PushButton(new Image(img
						.navigatorConfirmar10()));
				btnConfirmar.setSize("10px", "15px");
			} else {
				btnConfirmar = new PushButton(new Image(img
						.navigatorConfirmar16()));
				btnConfirmar.setSize("16px", "16px");
			}
			btnConfirmar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						manClickHandler.onBtnConfirmarClick();
					}
				}
			});
			btnConfirmar.setTitle("Confirmar Gravação de Registro");
		}
		return btnConfirmar;
	}

	private PushButton getBtnCancelar() {
		if (btnCancelar == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnCancelar = new PushButton(new Image(img
						.navigatorCancelar10()));
				btnCancelar.setSize("10px", "15px");
			} else {
				btnCancelar = new PushButton(new Image(img
						.navigatorCancelar16()));
				btnCancelar.setSize("16px", "16px");
			}
			btnCancelar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						manClickHandler.onBtnCancelarClick();
					}
				}
			});
			btnCancelar.setTitle("Cancelar Gravação de Registro");
		}
		return btnCancelar;
	}

	private PushButton getBtnAtualizar() {
		if (btnAtualizar == null) {
			if (btamanho == BotaoTamanho.PEQUENO) {
				btnAtualizar = new PushButton(new Image(img
						.navigatorAtualizar10()));
				btnAtualizar.setSize("10px", "15px");
			} else {
				btnAtualizar = new PushButton(new Image(img
						.navigatorAtualizar16()));
				btnAtualizar.setSize("16px", "16px");
			}
			btnAtualizar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (manClickHandler != null) {
						manClickHandler.onBtnAtualizarClick();
					}
				}
			});
			btnAtualizar.setTitle("Atualizar Dados do Registro");
		}
		return btnAtualizar;
	}

	public void addManClickHandler(ManClickHandler handler) {
		this.manClickHandler = handler;
	}

	public void addNavClickHandler(NavClickHandler handler) {
		this.navClickHandler = handler;
	}

	public void setConfirmaExcluir(boolean confirmaExcluir) {
		this.confirmaExcluir = confirmaExcluir;
	}

	private void botoesNaoVisiveis() {
		btnPrimeiro.setVisible(false);
		btnAnterior.setVisible(false);
		btnProximo.setVisible(false);
		btnUltimo.setVisible(false);
		btnIncluir.setVisible(false);
		btnExcluir.setVisible(false);
		btnAlterar.setVisible(false);
		btnConfirmar.setVisible(false);
		btnCancelar.setVisible(false);
		btnAtualizar.setVisible(false);
	}

	public void setBotoesVisiveis(Botao[] botoes) {
		botoesNaoVisiveis();

		for (Botao btn : botoes) {
			switch (btn) {
			case PRIMEIRO:
				btnPrimeiro.setVisible(true);
				break;
			case ANTERIOR:
				btnAnterior.setVisible(true);
				break;
			case PROXIMO:
				btnProximo.setVisible(true);
				break;
			case ULTIMO:
				btnUltimo.setVisible(true);
				break;
			case INCLUIR:
				btnIncluir.setVisible(true);
				break;
			case EXCLUIR:
				btnExcluir.setVisible(true);
				break;
			case ALTERAR:
				btnAlterar.setVisible(true);
				break;
			case CONFIRMAR:
				btnConfirmar.setVisible(true);
				break;
			case CANCELAR:
				btnCancelar.setVisible(true);
				break;
			case ATUALIZAR:
				btnAtualizar.setVisible(true);
				break;
			}
		}

	}

	public int getPaginacaoInicial() {
		return paginacaoInicial;
	}

	public int getPaginacaoFinal() {
		return paginacaoFinal;
	}

	public int getPaginacaoTotal() {
		return paginacaoTotal;
	}

	public int getPaginacao() {
		return paginacao;
	}

	public void setPaginacao(int intervaloInicial, int intervaloTotal,
			int paginacao) {
		this.paginacaoInicial = intervaloInicial;
		this.paginacaoTotal = intervaloTotal;
		this.paginacao = paginacao;
		this.paginacaoFinal = this.paginacao - this.paginacaoInicial;
		definePaginacao(this.paginacaoInicial);
		habilitaBotoesNav();
	}

	private void definePaginacao(int npagina) {
		String pag = "";
		this.paginacaoInicial = npagina;
		this.paginacaoFinal = this.paginacaoInicial + this.paginacao - 1;
		if (this.paginacaoFinal >= this.paginacaoTotal) {
			this.paginacaoFinal = this.paginacaoTotal - 1;
		}
		pag = (this.paginacaoInicial + 1) + " - " + (this.paginacaoFinal + 1)
				+ " de " + this.paginacaoTotal;
		this.getLabPaginacao().setText(pag);
	}

	private void habilitaBotoesNav(boolean bPrimeiro, boolean bAnterior,
			boolean bProximo, boolean bUltimo) {
		this.getBtnPrimeiro().setEnabled(bPrimeiro);
		this.getBtnAnterior().setEnabled(bAnterior);
		this.getBtnProximo().setEnabled(bProximo);
		this.getBtnUltimo().setEnabled(bUltimo);
	}

	private void habilitaBotoesNav() {
		if (this.paginacaoTotal == 0) {
			habilitaBotoesNav(false, false, false, false);
		} else if (this.paginacaoInicial == 0) {
			habilitaBotoesNav(false, false, true, true);
		} else if (this.paginacaoFinal == (this.paginacaoTotal - 1)) {
			habilitaBotoesNav(true, true, false, false);
		} else
			habilitaBotoesNav(true, true, true, true);
	}

	private void manipulaPaginacaoLimite(boolean bPrimeiro) {
		int npagina = 0;

		if (bPrimeiro) {
			npagina = 0;
			definePaginacao(npagina);
		} else {
			int nfator = (int) (Math.round((double) this.paginacaoTotal
					/ this.paginacao));
			for (int i = 0; i < nfator; i++) {
				npagina += this.paginacao;
			}
			definePaginacao(npagina);
		}
		habilitaBotoesNav();
	}

	private void manipulaPaginacao(boolean bProximo) {
		int npagina;

		if (bProximo) {
			npagina = this.paginacaoInicial + this.paginacao;
			if (npagina < this.paginacaoTotal) {
				definePaginacao(npagina);
			}
		} else {
			npagina = this.paginacaoInicial - this.paginacao;
			if (npagina >= 0) {
				definePaginacao(npagina);
			}
		}
		habilitaBotoesNav();
	}

	public void setLarguraMaxima() {
		getPanelNavigator().setWidth("100%");
	}

	public void setAlinhamentoADireita() {
		getPanelNavigator().setCellHorizontalAlignment(getBtnPrimeiro(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnAnterior(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		if (usarPaginacao) {
			getPanelNavigator().setCellHorizontalAlignment(getLabPaginacao(),
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
		getPanelNavigator().setCellHorizontalAlignment(getBtnProximo(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnUltimo(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnIncluir(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnExcluir(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnAlterar(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnConfirmar(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnCancelar(),
				HasHorizontalAlignment.ALIGN_RIGHT);
		getPanelNavigator().setCellHorizontalAlignment(getBtnAtualizar(),
				HasHorizontalAlignment.ALIGN_RIGHT);

	}
}
