package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.hfsgwt.client.componentes.HFSDialogMessage;
import com.hfsgwt.client.componentes.HFSDialogMessage.Botao;

public class DemoDialogMessage extends Composite {
	private Grid grid;
	private Button btnAlerta;
	private Button btnErro;
	private Button btnMultiLinhaPrompt;
	private Button btnPrompt;
	private Button btnInformacao;
	private Button btnConfirmacao;
	private Button btnSimNaoCancelar;
	private Button btnProgresso;

	public DemoDialogMessage() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(3, 3);
			grid.setCellPadding(5);
			grid.setCellSpacing(5);
			grid.setWidget(0, 0, getBtnAlerta());
			grid.setWidget(0, 1, getBtnErro());
			grid.setWidget(0, 2, getBtnInformacao());
			grid.setWidget(1, 0, getBtnConfirmacao());
			grid.setWidget(1, 1, getBtnPrompt());
			grid.setWidget(1, 2, getBtnMultiLinhaPrompt());
			grid.setWidget(2, 0, getBtnSimNaoCancelar());
			grid.setWidget(2, 1, getBtnProgresso());
			grid.getCellFormatter().setHorizontalAlignment(0, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(0, 1,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(0, 2,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(1, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(1, 1,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(1, 2,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(2, 0,
					HasHorizontalAlignment.ALIGN_CENTER);			
			grid.getCellFormatter().setHorizontalAlignment(2, 1,
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return grid;
	}

	private Button getBtnAlerta() {
		if (btnAlerta == null) {
			btnAlerta = new Button("Alerta");
			btnAlerta.setSize("167", "28");
			btnAlerta.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage.alerta("Isto é uma alerta!");
				}
			});
		}
		return btnAlerta;
	}

	private Button getBtnErro() {
		if (btnErro == null) {
			btnErro = new Button("Erro");
			btnErro.setSize("167", "28");
			btnErro.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage.erro("Isto é um erro!");
				}
			});
		}
		return btnErro;
	}

	private Button getBtnMultiLinhaPrompt() {
		if (btnMultiLinhaPrompt == null) {
			btnMultiLinhaPrompt = new Button("MultiLinhaPrompt");
			btnMultiLinhaPrompt.setSize("167", "28");
			btnMultiLinhaPrompt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage dlg = HFSDialogMessage
							.multiLinhaPrompt("diga seus dados?");

					dlg.addBtnClickHandler(new HFSDialogMessage.BtnClickHandler() {
						@Override
						public void onBotaoClick(Botao botao, String prompt) {
							Window.alert(botao.toString() + " - " + prompt);
						}
					});

					dlg.center();

				}
			});
		}
		return btnMultiLinhaPrompt;
	}

	private Button getBtnPrompt() {
		if (btnPrompt == null) {
			btnPrompt = new Button("Prompt");
			btnPrompt.setSize("167", "28");
			btnPrompt.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage dlg = HFSDialogMessage
							.prompt("diga seu nome?");

					dlg.addBtnClickHandler(new HFSDialogMessage.BtnClickHandler() {
						@Override
						public void onBotaoClick(Botao botao, String prompt) {
							Window.alert(botao.toString() + " - " + prompt);
						}
					});

					dlg.center();
				}
			});
		}
		return btnPrompt;
	}

	private Button getBtnInformacao() {
		if (btnInformacao == null) {
			btnInformacao = new Button("Informação");
			btnInformacao.setSize("167", "28");
			btnInformacao.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage.informacao("Isto é uma informação!");
				}
			});
		}
		return btnInformacao;
	}

	private Button getBtnConfirmacao() {
		if (btnConfirmacao == null) {
			btnConfirmacao = new Button("Confirmação");
			btnConfirmacao.setSize("167", "28");
			btnConfirmacao.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage dlg = HFSDialogMessage
							.confirmacao("Isto é uma confirmação!");
					dlg.addBtnClickHandler(new HFSDialogMessage.BtnClickHandler() {
						@Override
						public void onBotaoClick(Botao botao, String prompt) {
							Window.alert(botao.toString());
						}
					});

					dlg.center();
				}
			});
		}
		return btnConfirmacao;
	}

	private Button getBtnSimNaoCancelar() {
		if (btnSimNaoCancelar == null) {
			btnSimNaoCancelar = new Button("SimNaoCancelar");
			btnSimNaoCancelar.setSize("167", "28");
			btnSimNaoCancelar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage dlg = HFSDialogMessage.simNaoCancelar(
							"titulo customizado",
							"voce vai decidi o que meu irmão?");

					dlg.addBtnClickHandler(new HFSDialogMessage.BtnClickHandler() {
						@Override
						public void onBotaoClick(Botao botao, String prompt) {
							Window.alert(botao.toString());
						}
					});

					dlg.center();
				}
			});
		}
		return btnSimNaoCancelar;
	}

	private Button getBtnProgresso() {
		if (btnProgresso == null) {
			btnProgresso = new Button("Progresso");
			btnProgresso.setSize("167", "28");
			btnProgresso.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogMessage.progresso("rodando o progresso", 10, 1000);
				}
			});
		}
		return btnProgresso;
	}
}
