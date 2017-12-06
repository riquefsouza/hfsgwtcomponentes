package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSDialogMessage extends HFSPopupPanel {
	private VerticalPanel panelPrincipal;
	private Label labMensagem;
	private TextBox edtPrompt;
	private TextArea edtMulti;
	private HFSProgressBar barraProgresso;
	private HorizontalPanel panelBotoes;
	private Button btnBotao;
	private Grid grid;	

	public enum Icone {
		NENHUM, ALERTA, CONFIRMACAO, ERRO, INFORMACAO
	}

	public enum Botao {
		SIM, NAO, OK, CANCELAR, ABORTAR, IGNORAR, REPETIR, TODOS, NAO_PARA_TODOS, SIM_PARA_TODOS, AJUDA
	}

	public enum TipoDialog {
		CUSTOMIZADO, ALERTA, CONFIRMACAO, ERRO, INFORMACAO, PROMPT, MULTI_LINHA_PROMPT, SIM_NAO_CANCELAR, PROGRESSO
	}

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public interface BtnClickHandler extends EventHandler {
		void onBotaoClick(Botao botao, String prompt);
	}

	private BtnClickHandler btnClickHandler;
	private TipoDialog tipo;
	private Icone icone;
	private Botao[] botoes;
	private String mensagem;
	private int duracaoAnimacao;
	private int maxProgresso;

	public HFSDialogMessage(TipoDialog tipo, Icone icone, Botao[] botoes,
			String titulo, String mensagem) {
		super(titulo);
		this.tipo = tipo;
		this.icone = icone;
		this.botoes = botoes;
		this.mensagem = mensagem;
		initComponents(titulo);
	}

	public HFSDialogMessage(TipoDialog tipo, Icone icone, Botao[] botoes,
			String titulo, String mensagem, int maxProgresso,
			int duracaoAnimacao) {
		super(titulo);
		this.tipo = tipo;
		this.icone = icone;
		this.botoes = botoes;
		this.mensagem = mensagem;
		this.maxProgresso = maxProgresso;
		this.duracaoAnimacao = duracaoAnimacao;
		initComponents(titulo);
	}

	private void initComponents(String titulo) {
		if (titulo.trim().length() == 0)
			setTitulo(getTituloPeloTipo(this.tipo));

		setGlassEnabled(true);
		setWidget(getPanelPrincipal());
	}

	private VerticalPanel getPanelPrincipal() {
		if (panelPrincipal == null) {
			panelPrincipal = new VerticalPanel();
			//panelPrincipal.setWidth("380px");
			panelPrincipal.setSpacing(4);
			panelPrincipal.add(getTitulo());
			panelPrincipal.add(getGrid());
//			panelPrincipal.setCellVerticalAlignment(getGrid(), HasVerticalAlignment.ALIGN_MIDDLE);
//			panelPrincipal.setCellHorizontalAlignment(getGrid(), HasHorizontalAlignment.ALIGN_CENTER);			
			if (this.botoes.length > 0) {
				panelPrincipal.add(getPanelBotoes());
				panelPrincipal.setCellVerticalAlignment(getPanelBotoes(),
						HasVerticalAlignment.ALIGN_MIDDLE);
				panelPrincipal.setCellHorizontalAlignment(getPanelBotoes(),
						HasHorizontalAlignment.ALIGN_CENTER);
			}
		}
		return panelPrincipal;
	}

	private Grid getGrid() {
		if (grid == null) {
			if (this.tipo == TipoDialog.PROMPT
					|| this.tipo == TipoDialog.MULTI_LINHA_PROMPT
					|| this.tipo == TipoDialog.PROGRESSO)
				grid = new Grid(2, 2);
			else
				grid = new Grid(1, 2);

			grid.setCellSpacing(4);
			if (this.icone != Icone.NENHUM) {
				grid.setWidget(0, 0, getIcone(this.icone));
				grid.getCellFormatter().setHorizontalAlignment(0, 0,
						HasHorizontalAlignment.ALIGN_LEFT);
				grid.getCellFormatter().setVerticalAlignment(0, 0,
						HasVerticalAlignment.ALIGN_MIDDLE);
			}

			grid.setWidget(0, 1, getLabMensagem());
			if (this.tipo == TipoDialog.PROMPT
					|| this.tipo == TipoDialog.MULTI_LINHA_PROMPT
					|| this.tipo == TipoDialog.PROGRESSO)
				grid.getCellFormatter().setHorizontalAlignment(0, 1,
						HasHorizontalAlignment.ALIGN_LEFT);
			else
				grid.getCellFormatter().setHorizontalAlignment(0, 1,
						HasHorizontalAlignment.ALIGN_CENTER);

			grid.getCellFormatter().setVerticalAlignment(0, 1,
					HasVerticalAlignment.ALIGN_MIDDLE);

			if (this.tipo == TipoDialog.PROMPT) {
				grid.setWidget(1, 1, getEdtPrompt());
			} else if (this.tipo == TipoDialog.MULTI_LINHA_PROMPT) {
				grid.setWidget(1, 1, getEdtMulti());
			} else if (this.tipo == TipoDialog.PROGRESSO) {
				grid.setWidget(1, 1, getBarraProgresso());
				getBarraProgresso().rodarAnimacao();
			}

		}
		return grid;
	}

	private Label getLabMensagem() {
		if (labMensagem == null) {
			labMensagem = new Label(this.mensagem);
			if (this.tipo == TipoDialog.PROMPT
					|| this.tipo == TipoDialog.MULTI_LINHA_PROMPT)
				labMensagem
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			else
				labMensagem
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return labMensagem;
	}

	private TextBox getEdtPrompt() {
		if (edtPrompt == null) {
			edtPrompt = new TextBox();
			edtPrompt.setWidth("200px");
			edtPrompt.setFocus(true);
		}
		return edtPrompt;
	}

	private TextArea getEdtMulti() {
		if (edtMulti == null) {
			edtMulti = new TextArea();
			edtMulti.setAlignment(TextAlignment.JUSTIFY);
			edtMulti.setSize("200px", "100px");
		}
		return edtMulti;
	}

	private HFSProgressBar getBarraProgresso() {
		if (barraProgresso == null) {
			barraProgresso = new HFSProgressBar(200, true, duracaoAnimacao);
			barraProgresso.setMaxValor(maxProgresso);
			barraProgresso.addAnimacaoHandler(new HFSProgressBar.AnimacaoHandler() {
				@Override
				public void onAnimacaoCompletada() {
					hide();
				}
			});
		}
		return barraProgresso;
	}

	private HorizontalPanel getPanelBotoes() {
		if (panelBotoes == null) {
			panelBotoes = new HorizontalPanel();
			panelBotoes.setHeight("25px");
			panelBotoes.setSpacing(5);
			Button btn;
			for (Botao botao : this.botoes) {
				btn = criarBtnBotao(botao);
				panelBotoes.add(btn);
				panelBotoes.setCellVerticalAlignment(btn,
						HasVerticalAlignment.ALIGN_BOTTOM);
			}
		}
		return panelBotoes;
	}

	private Button criarBtnBotao(Botao botao) {
		btnBotao = new Button(getTextoPeloBotao(botao));
		btnBotao.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Button btn = (Button) event.getSource();
				Botao mbotao = getBotaoPeloTexto(btn.getHTML());
				String prompt = "";
				if (tipo == TipoDialog.PROMPT)
					prompt = getEdtPrompt().getText();
				else if (tipo == TipoDialog.MULTI_LINHA_PROMPT)
					prompt = getEdtMulti().getText();

				hide();

				if (btnClickHandler != null) {
					btnClickHandler.onBotaoClick(mbotao, prompt);
				}
			}
		});
		btnBotao.setSize("100px", "");
		return btnBotao;
	}

	private String getTextoPeloBotao(Botao botao) {
		String txt = "";
		switch (botao) {
		case SIM:
			txt = "Sim";
			break;
		case NAO:
			txt = "Não";
			break;
		case OK:
			txt = "OK";
			break;
		case CANCELAR:
			txt = "Cancelar";
			break;
		case ABORTAR:
			txt = "Abortar";
			break;
		case IGNORAR:
			txt = "Ignorar";
			break;
		case REPETIR:
			txt = "Repetir";
			break;
		case TODOS:
			txt = "Todos";
			break;
		case NAO_PARA_TODOS:
			txt = "Não para Todos";
			break;
		case SIM_PARA_TODOS:
			txt = "Sim para Todos";
			break;
		case AJUDA:
			txt = "Ajuda";
			break;
		}
		return txt;
	}

	private Image getIcone(Icone icone) {
		Image imagem = null;
		switch (icone) {
		case ALERTA:
			imagem = new Image(img.dialogMessageAlerta());
			break;
		case CONFIRMACAO:
			imagem = new Image(img.dialogMessageConfirmacao());
			break;
		case ERRO:
			imagem = new Image(img.dialogMessageErro());
			break;
		case INFORMACAO:
			imagem = new Image(img.dialogMessageInformacao());
			break;
		}
		return imagem;
	}

	private Botao getBotaoPeloTexto(String txt) {
		if (txt.equals("Sim"))
			return Botao.SIM;
		else if (txt.equals("Não"))
			return Botao.NAO;
		else if (txt.equals("OK"))
			return Botao.OK;
		else if (txt.equals("Cancelar"))
			return Botao.CANCELAR;
		else if (txt.equals("Abortar"))
			return Botao.ABORTAR;
		else if (txt.equals("Ignorar"))
			return Botao.IGNORAR;
		else if (txt.equals("Repetir"))
			return Botao.REPETIR;
		else if (txt.equals("Todos"))
			return Botao.TODOS;
		else if (txt.equals("Não para Todos"))
			return Botao.NAO_PARA_TODOS;
		else if (txt.equals("Sim para Todos"))
			return Botao.SIM_PARA_TODOS;
		else if (txt.equals("Ajuda"))
			return Botao.AJUDA;
		else
			return Botao.OK;
	}

	public void addBtnClickHandler(BtnClickHandler handler) {
		this.btnClickHandler = handler;
	}

	private String getTituloPeloTipo(TipoDialog tipo) {
		String titulo = "";
		switch (tipo) {
		case ALERTA:
			titulo = "Alerta";
			break;
		case CONFIRMACAO:
			titulo = "Confirmação";
			break;
		case ERRO:
			titulo = "Erro";
			break;
		case INFORMACAO:
			titulo = "Informação";
			break;
		case MULTI_LINHA_PROMPT:
		case PROMPT:
			titulo = "Informe";
			break;
		case PROGRESSO:
			// case ESPERA:
			titulo = "Por favor espere...";
			break;
		}
		return titulo;
	}

	/*
	 * ALERTA, ERRO, INFORMACAO = icone de cada, mensagem, botao OK
	 * 
	 * CONFIRMACAO = icone, mensagem, botao Sim Nao
	 * 
	 * PROMPT = sem icone, mensagem, textbox, botao ok Cancelar
	 * 
	 * MULTI_LINHA_PROMPT = sem icone, mensagem, textbox com word wrap e
	 * tamanho, botao ok Cancelar
	 * 
	 * SIM_NAO_CANCELAR = sem icone, mensagem, escreve titulo, tres botoes
	 * 
	 * PROGRESSO = sem icone, mensagem e animado como barra de progresso
	 * (Carregando item 1 de 10) setProgresso(true); setMaxProgresso(10);
	 * 
	 * ESPERA = sem icone e animado com fluxo continuo e repetido
	 * setTempoMili(8000);
	 */

	public static void alerta(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.ALERTA,
				Icone.ALERTA, new Botao[] { Botao.OK }, "", mensagem);
		dlg.show();
		dlg.center();
	}

	public static void erro(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.ERRO,
				Icone.ERRO, new Botao[] { Botao.OK }, "", mensagem);
		dlg.show();
		dlg.center();
	}

	public static void informacao(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.INFORMACAO,
				Icone.INFORMACAO, new Botao[] { Botao.OK }, "", mensagem);
		dlg.show();
		dlg.center();
	}

	public static HFSDialogMessage confirmacao(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.CONFIRMACAO,
				Icone.CONFIRMACAO, new Botao[] { Botao.SIM, Botao.NAO }, "",
				mensagem);
		dlg.show();
		dlg.center();
		return dlg;
	}

	public static HFSDialogMessage prompt(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.PROMPT,
				Icone.NENHUM, new Botao[] { Botao.OK, Botao.CANCELAR }, "",
				mensagem);
		dlg.show();
		dlg.center();
		dlg.getEdtPrompt().setFocus(true);
		return dlg;
	}

	public static HFSDialogMessage multiLinhaPrompt(String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(
				TipoDialog.MULTI_LINHA_PROMPT, Icone.NENHUM, new Botao[] {
						Botao.OK, Botao.CANCELAR }, "", mensagem);
		dlg.show();
		dlg.center();
		dlg.getEdtMulti().setFocus(true);
		return dlg;
	}

	public static HFSDialogMessage simNaoCancelar(String titulo, String mensagem) {
		HFSDialogMessage dlg = new HFSDialogMessage(
				TipoDialog.SIM_NAO_CANCELAR, Icone.NENHUM, new Botao[] {
						Botao.SIM, Botao.NAO, Botao.CANCELAR }, titulo,
				mensagem);
		dlg.show();
		dlg.center();
		return dlg;
	}

	public static void progresso(String mensagem, int maxProgresso,
			int duracaoAnimacao) {
		HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.PROGRESSO,
				Icone.NENHUM, new Botao[] {}, "", mensagem, maxProgresso,
				duracaoAnimacao);
		dlg.show();
		dlg.center();
	}

	// public static void espera(String mensagem, int tempoMiliSegundos) {
	// HFSDialogMessage dlg = new HFSDialogMessage(TipoDialog.ESPERA,
	// Icone.NENHUM, new Botao[] {}, "", mensagem);
	// dlg.show();
	// dlg.center();
	// }
}
