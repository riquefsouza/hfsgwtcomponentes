package com.hfsgwt.client.componentes.chat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSDialogChat extends DialogBox {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private VerticalPanel verticalPanel;
	private HorizontalSplitPanel horizontalSplitPanel;
	//private SplitLayoutPanel horizontalSplitPanel;
	private HorizontalPanel botaoPanel;
	private Button btnEnviar;
	private TextArea taMensagem;
	private VerticalPanel msgsPanel;
	private VerticalPanel usuariosPanel;
	private Label labMensagens;
	private Label labUsuarios;
	private ListBox lstUsuarios;
	private ListBox lstMensagens;
	private HorizontalPanel usuDestPanel;
	private Label labEnviarMens;
	private Label labUsuDestino;

	private int largura;
	private int altura;
	private boolean bConectado;
	private ChatUsuario usuario;
	private Button btnFechar;

	public HFSDialogChat(int largura, int altura, ChatUsuario usuario) {
		this.bConectado = false;
		this.largura = largura;
		this.altura = altura;
		this.usuario = usuario;

		initComponents();
	}

	private void initComponents() {
		setModal(false);
		setHTML("HFSChat");
		setWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel
					.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			verticalPanel.setSize(largura + "px", altura + "px");
			verticalPanel.add(getHorizontalSplitPanel());
			verticalPanel.setCellHeight(getHorizontalSplitPanel(), "100%");
			verticalPanel.setCellWidth(getHorizontalSplitPanel(), "100%");
			verticalPanel
					.add(new HTML("<hr style='width: 100%; height: 2px;'>"));
			verticalPanel.add(getUsuDestPanel());
			verticalPanel.add(getBotaoPanel());
		}
		return verticalPanel;
	}

	private HorizontalSplitPanel getHorizontalSplitPanel() {
		if (horizontalSplitPanel == null) {
			horizontalSplitPanel = new HorizontalSplitPanel();
			//horizontalSplitPanel = new SplitLayoutPanel();
			horizontalSplitPanel.setSplitPosition("60%");
			// horizontalSplitPanel.setStyleName("HFSHorizontalSplitPanel");
			horizontalSplitPanel.setLeftWidget(getMsgsPanel());
			horizontalSplitPanel.setRightWidget(getUsuariosPanel());
		}
		return horizontalSplitPanel;
	}

	private HorizontalPanel getBotaoPanel() {
		if (botaoPanel == null) {
			botaoPanel = new HorizontalPanel();
			botaoPanel.setSpacing(2);
			botaoPanel.add(getTaMensagem());
			botaoPanel.setCellWidth(getTaMensagem(), "90%");
			botaoPanel.add(getBtnEnviar());
			botaoPanel.setCellVerticalAlignment(getBtnEnviar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botaoPanel.add(getBtnFechar());
			botaoPanel.setCellVerticalAlignment(getBtnFechar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
		}
		return botaoPanel;
	}

	private Button getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new Button("Enviar");
			btnEnviar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnEnviarClick();
				}
			});
		}
		return btnEnviar;
	}

	private Button getBtnFechar() {
		if (btnFechar == null) {
			btnFechar = new Button("Fechar");
			btnFechar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnFecharClick();
				}
			});
		}
		return btnFechar;
	}

	private TextArea getTaMensagem() {
		if (taMensagem == null) {
			taMensagem = new TextArea();
			// taMensagem.addKeyUpHandler(new KeyUpHandler() {
			// public void onKeyUp(KeyUpEvent event) {
			// //
			// }
			// });
			taMensagem.addKeyDownHandler(new KeyDownHandler() {
				public void onKeyDown(KeyDownEvent event) {
					taMensagemKeyDown(event);
				}
			});
			taMensagem.setSize("90%", "40px");
		}
		return taMensagem;
	}

	private VerticalPanel getMsgsPanel() {
		if (msgsPanel == null) {
			msgsPanel = new VerticalPanel();
			msgsPanel.setSize("100%", "100%");
			msgsPanel.add(getLabMensagens());
			msgsPanel.add(getLstMensagens());
		}
		return msgsPanel;
	}

	private VerticalPanel getUsuariosPanel() {
		if (usuariosPanel == null) {
			usuariosPanel = new VerticalPanel();
			usuariosPanel.setSize("100%", "100%");
			usuariosPanel.add(getLabUsuarios());
			usuariosPanel.add(getLstUsuarios());
		}
		return usuariosPanel;
	}

	private Label getLabMensagens() {
		if (labMensagens == null) {
			labMensagens = new Label("Mensagens");
			labMensagens
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return labMensagens;
	}

	private Label getLabUsuarios() {
		if (labUsuarios == null) {
			labUsuarios = new Label("Usuários");
			labUsuarios
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return labUsuarios;
	}

	private ListBox getLstUsuarios() {
		if (lstUsuarios == null) {
			lstUsuarios = new ListBox();
			lstUsuarios.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					lstUsuariosClick();
				}
			});
			lstUsuarios.setSize("100%", "100%");
			lstUsuarios.setVisibleItemCount(8);
		}
		return lstUsuarios;
	}

	private ListBox getLstMensagens() {
		if (lstMensagens == null) {
			lstMensagens = new ListBox();
			lstMensagens.setSize("100%", "100%");
			lstMensagens.setVisibleItemCount(8);
		}
		return lstMensagens;
	}

	private HorizontalPanel getUsuDestPanel() {
		if (usuDestPanel == null) {
			usuDestPanel = new HorizontalPanel();
			usuDestPanel.add(getLabEnviarMens());
			usuDestPanel.add(getLabUsuDestino());
		}
		return usuDestPanel;
	}

	private Label getLabEnviarMens() {
		if (labEnviarMens == null) {
			labEnviarMens = new Label("Envia mensagem para:");
		}
		return labEnviarMens;
	}

	private Label getLabUsuDestino() {
		if (labUsuDestino == null) {
			labUsuDestino = new Label("Nenhum usuário selecionado!");
		}
		return labUsuDestino;
	}

	private void btnEnviarClick() {
		if (bConectado) {
			String mens = getTaMensagem().getText();
			enviarMensagemChat(this.getLabUsuDestino().getText(), mens);
		}
	}

	private void btnFecharClick() {
		if (bConectado) {
			desconectaChat();
		}
		this.hide();
	}

	private void taMensagemKeyDown(KeyDownEvent evento) {
		if (evento.getNativeKeyCode() == HFSConst.TECLA_ENTER) {
			btnEnviarClick();
		}
	}

	private void lstUsuariosClick() {
		if (getLstUsuarios().getItemCount() > 0) {
			String item = getLstUsuarios().getItemText(
					getLstUsuarios().getSelectedIndex());
			this.getLabUsuDestino().setText(item);
		}
	}

	private void conectaChat(ChatUsuario usuario) {
		servico.conectaChat(usuario, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "conectaChat");
			}

			public void onSuccess(Boolean res) {
				bConectado = res.booleanValue();
				if (res) {
					getUsuariosChat();
				}
			}
		});
	}

	private void desconectaChat() {
		servico.desconectaChat(new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				HFSUtil.mostrarFalha(caught, this.getClass(), "desconectaChat");
			}

			public void onSuccess(Boolean res) {
				// bDesconectou = res;
			}
		});
	}

	private void enviarMensagemChat(String usuarioDestino, final String mensagem) {
		servico.enviarMensagemChat(usuarioDestino, mensagem,
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"enviarMensagemChat");
					}

					public void onSuccess(Boolean res) {
						if (res) {
							getLstMensagens().addItem(mensagem);
							getTaMensagem().setText("");
							getTaMensagem().setFocus(true);
						}
					}
				});
	}

	// private void getMensagemChat() {
	// servico.getMensagemChat(new AsyncCallback<ChatMensagem>() {
	// public void onFailure(Throwable caught) {
	// Window.alert(HFSConst.SERVIDOR_ERRO + "HFSChat[getMensagemChat]");
	// }
	//
	// public void onSuccess(ChatMensagem mens) {
	// if (mens!=null){
	// getLstMensagens().addItem("["+mens.getUsuarioDestino()+"]: ");
	// getLstMensagens().addItem("  "+mens.getMensagem());
	// }
	// }
	// });
	// }

	private void getUsuariosChat() {
		servico.getUsuariosChat(new AsyncCallback<ChatUsuario[]>() {
			public void onFailure(Throwable caught) {
				HFSUtil
						.mostrarFalha(caught, this.getClass(),
								"getUsuariosChat");
			}

			public void onSuccess(ChatUsuario[] usuarios) {
				for (ChatUsuario usu : usuarios) {
					getLstUsuarios().addItem(usu.getUsuario());
				}
				if (usuarios.length > 0) {
					getLstUsuarios().setItemSelected(0, true);
					lstUsuariosClick();
				}
			}
		});
	}

	public void conectar() {
		conectaChat(this.usuario);
	}
}
