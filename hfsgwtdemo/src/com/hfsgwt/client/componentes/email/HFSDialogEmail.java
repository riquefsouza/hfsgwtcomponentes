package com.hfsgwt.client.componentes.email;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.richtext.HFSRichText;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSDialogEmail extends DialogBox {
	private VerticalPanel verticalPanel;
	private HorizontalPanel botoesPanel;
	private PushButton btnEnviar;
	private PushButton btnAnexar;
	private HFSTextBox edtEmailDestino;
	private HFSTextBox edtEmailCCDestino;
	private HFSTextBox edtAssunto;
	private HFSRichText conteudo;
	private PushButton btnFechar;

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);
	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	private EmailConteudo emailConteudo;
	private List<EmailAnexo> anexos;
	private Label labEnviar;
	private Label labAnexar;
	private Label labFechar;

	public HFSDialogEmail(String nomeServidor, String nomeOrigem,
			String emailOrigem) {
		emailConteudo = new EmailConteudo();
		emailConteudo.setNomeServidor(nomeServidor);
		emailConteudo.setNomeOrigem(nomeOrigem);
		emailConteudo.setEmailOrigem(emailOrigem);
		this.anexos = new ArrayList<EmailAnexo>();
		initComponents();
	}

	private void initComponents() {
		setText("Nova mensagem de Email");
		setGlassEnabled(true);
		setWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getBotoesPanel());
			verticalPanel.add(getEdtEmailDestino());
			verticalPanel.add(getEdtEmailCCDestino());
			verticalPanel.add(getEdtAssunto());
			verticalPanel.add(getConteudo());
		}
		return verticalPanel;
	}

	private HorizontalPanel getBotoesPanel() {
		if (botoesPanel == null) {
			botoesPanel = new HorizontalPanel();
			botoesPanel.setSpacing(2);
			botoesPanel.add(getLabEnviar());
			botoesPanel.setCellVerticalAlignment(getLabEnviar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.add(getBtnEnviar());
			botoesPanel.setCellVerticalAlignment(getBtnEnviar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.setCellHorizontalAlignment(getBtnEnviar(),
					HasHorizontalAlignment.ALIGN_CENTER);
			botoesPanel.add(getLabAnexar());
			botoesPanel.setCellVerticalAlignment(getLabAnexar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.add(getBtnAnexar());
			botoesPanel.setCellVerticalAlignment(getBtnAnexar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.setCellHorizontalAlignment(getBtnAnexar(),
					HasHorizontalAlignment.ALIGN_CENTER);
			botoesPanel.add(getLabFechar());
			botoesPanel.setCellVerticalAlignment(getLabFechar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.add(getBtnFechar());
			botoesPanel.setCellVerticalAlignment(getBtnFechar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.setCellHorizontalAlignment(getBtnFechar(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return botoesPanel;
	}

	private PushButton getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new PushButton(new Image(img.mailEnviar()));
			btnEnviar.setSize("20", "20");
			btnEnviar.setTitle("Enviar");
			btnEnviar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnEnviarClick();
				}
			});
		}
		return btnEnviar;
	}

	private PushButton getBtnAnexar() {
		if (btnAnexar == null) {
			btnAnexar = new PushButton(new Image(img.mailAnexar()));
			btnAnexar.setSize("20", "20");
			btnAnexar.setTitle("Anexar");
			btnAnexar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnAnexarClick();
				}
			});
		}
		return btnAnexar;
	}

	private PushButton getBtnFechar() {
		if (btnFechar == null) {
			btnFechar = new PushButton(new Image(img.botaoFechar23()));
			btnFechar.setSize("20", "20");
			btnFechar.setTitle("Fechar");
			btnFechar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
		}
		return btnFechar;
	}

	private HFSTextBox getEdtEmailDestino() {
		if (edtEmailDestino == null) {
			edtEmailDestino = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Para:",
					Formato.EMAIL, 68, 68, false);
			edtEmailDestino.setLarguraRotulo(60);
		}
		return edtEmailDestino;
	}

	private HFSTextBox getEdtEmailCCDestino() {
		if (edtEmailCCDestino == null) {
			edtEmailCCDestino = new HFSTextBox(PosicaoRotulo.ESQUERDA, "CC:",
					Formato.EMAIL, 68, 68, false);
			edtEmailCCDestino.setLarguraRotulo(60);
		}
		return edtEmailCCDestino;
	}

	private HFSTextBox getEdtAssunto() {
		if (edtAssunto == null) {
			edtAssunto = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Assunto:",
					Formato.PADRAO, 68, 68, false);
			edtAssunto.setLarguraRotulo(60);
		}
		return edtAssunto;
	}

	private HFSRichText getConteudo() {
		if (conteudo == null) {
			conteudo = new HFSRichText();
		}
		return conteudo;
	}

	private void btnEnviarClick() {
		emailConteudo.setNomeDestino(this.getEdtEmailDestino().getTexto());
		emailConteudo.setEmailDestino(this.getEdtEmailDestino().getTexto());
		emailConteudo.setNomeCCDestino(this.getEdtEmailCCDestino().getTexto());
		emailConteudo.setEmailCCDestino(this.getEdtEmailCCDestino().getTexto());
		emailConteudo.setAssunto(this.getEdtAssunto().getTexto());
		emailConteudo.setHtmlEmail(true);
		emailConteudo.setMensagem(this.getConteudo().getHTML());
		emailConteudo.setAnexos(this.anexos);

		servico.enviarEmail(emailConteudo, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "enviarEmail");				
			}

			public void onSuccess(Boolean retorno) {
				if (retorno) {
					Window.alert("Email enviado com sucesso!");
					hide();
				}
			}
		});
	}

	private void btnAnexarClick() {
		EmailAnexo anexo = new EmailAnexo();
		this.anexos.add(anexo);
	}

	private Label getLabEnviar() {
		if (labEnviar == null) {
			labEnviar = new Label("Enviar");
			labEnviar.setWordWrap(false);
		}
		return labEnviar;
	}

	private Label getLabAnexar() {
		if (labAnexar == null) {
			labAnexar = new Label("Anexar");
			labAnexar.setWordWrap(false);
		}
		return labAnexar;
	}

	private Label getLabFechar() {
		if (labFechar == null) {
			labFechar = new Label("Fechar");
			labFechar.setWordWrap(false);
		}
		return labFechar;
	}
	
	public void setEmailDestino(String email) {
		getEdtEmailDestino().setTexto(email);
	}

	public void setEmailCCDestino(String emailCC) {
		getEdtEmailCCDestino().setTexto(emailCC);
	}

	public void setAssunto(String assunto) {
		getEdtAssunto().setTexto(assunto);
	}
	
	public void setMensagem(String mensagem) {
		getConteudo().setHTML(mensagem);
	}
}
