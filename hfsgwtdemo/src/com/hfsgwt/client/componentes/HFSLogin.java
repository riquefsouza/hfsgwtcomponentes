package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.util.HFSCRC32;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSLogin extends Composite {
	private Grid grid;
	private VerticalPanel informativoPanel;
	private CaptionPanel capLogin;
	private Image imageInformativo;

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public interface LoginHandler extends EventHandler {
		void onLogin(boolean loginRealizado);

		boolean validarLogin(String login, String senha);

		boolean alterarSenha(String login, String senha, String novaSenha);

		void onMaximoTentativas(int maximoTentativas);
	}

	public enum EstadoTela {
		PADRAO, ALTERARSENHA
	}

	private VerticalPanel textoPanel;
	private HFSTextBox edtLogin;
	private HFSTextBox edtSenha;
	private HFSTextBox edtNovaSenha;
	private HFSTextBox edtConfirmarSenha;
	private HorizontalPanel horizontalPanel;
	private Button btnLogin;
	private Button btnAlterar;
	private Button btnLimpar;
	private LoginHandler loginHandler;
	private VerticalPanel verticalPanel;
	private Image imageLogin;
	private EstadoTela estadoTela;
	private HTML htmlInformativo;
	private String informativo;
	private ImageResource imagemLogin;
	private boolean usarServidorLogin;
	private int maximoTentativas;
	private int numeroTentativas;

	public HFSLogin(ImageResource imagemLogin, String informativo,
			boolean usarServidorLogin, int maximoTentativas) {
		this.imagemLogin = imagemLogin;
		this.informativo = informativo;
		this.usarServidorLogin = usarServidorLogin;
		this.maximoTentativas = maximoTentativas;
		this.numeroTentativas = 0;

		this.estadoTela = EstadoTela.PADRAO;

		initComponents();

		this.getEdtLogin().setFocado(true);
	}

	public HFSLogin(boolean usarServidorLogin) {
		this(img.loginImagem(), "Bem Vindo ao PortalWeb!<br><br>"
				+ "Use um Usuário e Senha válido para ter acesso ao sistema.",
				usarServidorLogin, 3);
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(1, 2);
			grid.setStyleName("HFSLogin-grid");
			grid.setSize("420px", "158px");
			grid.setWidget(0, 0, getInformativoPanel());
			grid.setWidget(0, 1, getVerticalPanel());
		}
		return grid;
	}

	private VerticalPanel getInformativoPanel() {
		if (informativoPanel == null) {
			informativoPanel = new VerticalPanel();
			informativoPanel.add(getImageInformativo());
			informativoPanel.setCellVerticalAlignment(getImageInformativo(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			informativoPanel.setCellHorizontalAlignment(getImageInformativo(),
					HasHorizontalAlignment.ALIGN_CENTER);
			informativoPanel.add(getHtmlInformativo());
			informativoPanel.setCellHeight(getHtmlInformativo(), "150px");
			informativoPanel.setCellWidth(getHtmlInformativo(), "150px");
			informativoPanel.setCellVerticalAlignment(getHtmlInformativo(),
					HasVerticalAlignment.ALIGN_MIDDLE);
		}
		return informativoPanel;
	}

	private HTML getHtmlInformativo() {
		if (htmlInformativo == null) {
			htmlInformativo = new HTML(informativo, true);
			htmlInformativo
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return htmlInformativo;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getImageLogin());
			verticalPanel.add(getCapLogin());
		}
		return verticalPanel;
	}

	private Image getImageLogin() {
		if (imageLogin == null) {
			imageLogin = new Image(img.loginCaption());
		}
		return imageLogin;
	}

	private CaptionPanel getCapLogin() {
		if (capLogin == null) {
			capLogin = new CaptionPanel("Login");
			capLogin.setContentWidget(getTextoPanel());
		}
		return capLogin;
	}

	private Image getImageInformativo() {
		if (imageInformativo == null) {
			imageInformativo = new Image(imagemLogin);
		}
		return imageInformativo;
	}

	private VerticalPanel getTextoPanel() {
		if (textoPanel == null) {
			textoPanel = new VerticalPanel();
		} else {
			textoPanel.clear();
		}
		textoPanel.setSpacing(12);
		textoPanel.add(getEdtLogin());
		textoPanel.setCellHorizontalAlignment(getEdtLogin(),
				HasHorizontalAlignment.ALIGN_CENTER);
		textoPanel.add(getEdtSenha());
		textoPanel.setCellHorizontalAlignment(getEdtSenha(),
				HasHorizontalAlignment.ALIGN_CENTER);
		textoPanel.add(getHorizontalPanel());

		return textoPanel;
	}

	private HFSTextBox getEdtLogin() {
		if (edtLogin == null) {
			edtLogin = new HFSTextBox(PosicaoRotulo.ESQUERDA, "CPF:",
					Formato.CPF, 0, 0, false);
			edtLogin.setLarguraRotulo(50);
		}
		return edtLogin;
	}

	private HFSTextBox getEdtSenha() {
		if (edtSenha == null) {
			edtSenha = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Senha:",
					Formato.SENHA, 10, 12, false);
			edtSenha.setLarguraRotulo(50);
		}
		return edtSenha;
	}

	private VerticalPanel getAlterarTextoPanel() {
		if (textoPanel == null) {
			textoPanel = new VerticalPanel();
		} else {
			textoPanel.clear();
		}
		textoPanel.setSpacing(12);
		textoPanel.add(getEdtNovaSenha());
		textoPanel.setCellHorizontalAlignment(getEdtNovaSenha(),
				HasHorizontalAlignment.ALIGN_CENTER);
		textoPanel.add(getEdtConfirmarSenha());
		textoPanel.setCellHorizontalAlignment(getEdtConfirmarSenha(),
				HasHorizontalAlignment.ALIGN_CENTER);
		textoPanel.add(getHorizontalPanel());

		return textoPanel;
	}

	private HFSTextBox getEdtNovaSenha() {
		if (edtNovaSenha == null) {
			edtNovaSenha = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Nova:",
					Formato.SENHA, 10, 12, false);
			edtNovaSenha.setLarguraRotulo(60);
		}
		return edtNovaSenha;
	}

	private HFSTextBox getEdtConfirmarSenha() {
		if (edtConfirmarSenha == null) {
			edtConfirmarSenha = new HFSTextBox(PosicaoRotulo.ESQUERDA,
					"Confirmar:", Formato.SENHA, 10, 12, false);
			edtConfirmarSenha.setLarguraRotulo(60);
		}
		return edtConfirmarSenha;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setSpacing(2);
			horizontalPanel.add(getBtnLogin());
			horizontalPanel.add(getBtnAlterar());
			horizontalPanel.add(getBtnLimpar());
		}
		return horizontalPanel;
	}

	private Button getBtnLogin() {
		if (btnLogin == null) {
			btnLogin = new Button("Login");
			btnLogin.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnLoginClick();
				}
			});
		}
		return btnLogin;
	}

	private Button getBtnAlterar() {
		if (btnAlterar == null) {
			btnAlterar = new Button("Alterar");
			btnAlterar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnAlterarClick();
				}
			});
		}
		return btnAlterar;
	}

	private Button getBtnLimpar() {
		if (btnLimpar == null) {
			btnLimpar = new Button("Limpar");
			btnLimpar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnLimparClick(true);
				}
			});
		}
		return btnLimpar;
	}

	public void addLoginHandler(LoginHandler handler) {
		this.loginHandler = handler;
	}

	private void alterarTela() {
		if (this.estadoTela == EstadoTela.PADRAO) {
			getAlterarTextoPanel();
			getBtnLogin().setText("Alterar");
			getBtnAlterar().setText("Cancelar");
			this.estadoTela = EstadoTela.ALTERARSENHA;
		} else {
			getTextoPanel();
			getBtnLogin().setText("Login");
			getBtnAlterar().setText("Alterar");
			this.estadoTela = EstadoTela.PADRAO;
		}
	}

	private boolean validaDados() {
		if (this.estadoTela == EstadoTela.PADRAO) {
			if (this.getEdtLogin().getTexto().trim().length() == 0) {
				Window.alert("O CPF é obrigatório!");
				this.getEdtLogin().setFocado(true);
				return false;
			}
			if (!this.getEdtLogin().isValido()) {
				Window.alert("O CPF não é válido!");
				this.getEdtLogin().setFocado(true);
				return false;
			}
			if (this.getEdtSenha().getTexto().trim().length() == 0) {
				Window.alert("A Senha é obrigatória!");
				this.getEdtSenha().setFocado(true);
				return false;
			}
		} else {
			if (getEdtNovaSenha().getTexto().trim().equals(
					getEdtSenha().getTexto().trim())) {
				Window.alert("A Nova Senha não pode ser igual a antiga!");
				this.getEdtNovaSenha().setFocado(true);
				return false;
			}
			if (getEdtNovaSenha().getTexto().trim().length() == 0) {
				Window.alert("A Nova Senha é obrigatória!");
				this.getEdtNovaSenha().setFocado(true);
				return false;
			}
			if (getEdtConfirmarSenha().getTexto().trim().length() == 0) {
				Window.alert("A Confirmação da Nova Senha é obrigatória!");
				this.getEdtConfirmarSenha().setFocado(true);
				return false;
			}
			if (!this.getEdtNovaSenha().getTexto().trim().equals(
					this.getEdtConfirmarSenha().getTexto().trim())) {
				Window
						.alert("A Confirmação de senha não confere com a nova senha!");
				this.getEdtConfirmarSenha().setFocado(true);
				return false;
			}
		}
		return true;
	}

	private void btnLimparClick(boolean bFocar) {
		if (estadoTela == EstadoTela.PADRAO) {
			getEdtLogin().setTexto("");
			getEdtSenha().setTexto("");
			if (bFocar)
				getEdtLogin().setFocado(true);
		} else {
			getEdtNovaSenha().setTexto("");
			getEdtConfirmarSenha().setTexto("");
			if (bFocar)
				getEdtNovaSenha().setFocado(true);
		}
	}

	private void btnLoginClick() {
		if (validaDados()) {
			String login = this.getEdtLogin().getTexto();
			String senha = HFSCRC32.CryptCRC32(this.getEdtSenha().getTexto());

			if (estadoTela == EstadoTela.PADRAO) {

				if (usarServidorLogin)
					servicoValidarLogin(login, senha, true);
				else
					validarLogin(login, senha, true);

			} else {
				String novaSenha = HFSCRC32.CryptCRC32(this.getEdtNovaSenha()
						.getTexto());

				if (usarServidorLogin)
					servicoAlterarSenha(login, senha, novaSenha);
				else
					alterarSenha(login, senha, novaSenha);

			}
		}
	}

	private void btnAlterarClick() {
		if (estadoTela == EstadoTela.PADRAO) {
			if (validaDados()) {
				String login = this.getEdtLogin().getTexto();
				String senha = HFSCRC32.CryptCRC32(this.getEdtSenha()
						.getTexto());

				if (usarServidorLogin)
					servicoValidarLogin(login, senha, false);
				else
					validarLogin(login, senha, false);
			}
		} else {
			alterarTela();
		}
	}

	private void validarMaximoTentativas() {
		if (maximoTentativas > 0) {
			if (maximoTentativas == numeroTentativas) {
				loginHandler.onMaximoTentativas(maximoTentativas);
				numeroTentativas = 0;
			}
		}
	}

	private void validarLogin(String login, String senha, final boolean btnLogin) {
		if (btnLogin) {
			btnLimparClick(false);

			if (loginHandler != null) {
				if (loginHandler.validarLogin(login, senha))
					loginHandler.onLogin(true);
				else {
					loginHandler.onLogin(false);
					numeroTentativas++;
					validarMaximoTentativas();
				}				
			}
		} else {
			if (loginHandler != null) {
				if (!loginHandler.validarLogin(login, senha)) {
					Window.alert("Login(CPF) ou Senha inválidos!");
					btnLimparClick(true);
				} else {
					alterarTela();
					btnLimparClick(true);
				}
			}
		}
	}

	private void alterarSenha(String login, String senha, String novaSenha) {
		if (loginHandler != null) {
			if (loginHandler.alterarSenha(login, senha, novaSenha)) {
				Window.alert("Senha alterada com sucesso!");
				alterarTela();
				btnLimparClick(true);
			} else {
				Window.alert("Senha NÃO alterada!");
				btnLimparClick(true);
			}
		}
	}

	private void servicoValidarLogin(String login, String senha,
			final boolean btnLogin) {
		servico.validarLogin(GWT.getModuleBaseURL(), login, senha,
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(), "validarLogin");
					}

					public void onSuccess(Boolean resultado) {
						if (btnLogin) {
							btnLimparClick(false);

							if (loginHandler != null) {
								loginHandler.onLogin(resultado.booleanValue());
							}	
							if (!resultado.booleanValue()){
								numeroTentativas++;
								validarMaximoTentativas();
							}							
						} else {
							if (!resultado) {
								Window.alert("Login(CPF) ou Senha inválidos!");
								btnLimparClick(true);
							} else {
								alterarTela();
								btnLimparClick(true);
							}
						}
					}
				});
	}

	private void servicoAlterarSenha(String login, String senha,
			String novaSenha) {
		servico.alterarSenha(GWT.getModuleBaseURL(), login, senha, novaSenha,
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						HFSUtil.mostrarFalha(caught, this.getClass(), "alterarSenha");
					}

					public void onSuccess(Boolean resultado) {
						if (resultado) {
							Window.alert("Senha alterada com sucesso!");
							alterarTela();
							btnLimparClick(true);
						} else {
							Window.alert("Senha NÃO alterada!");
							btnLimparClick(true);
						}
					}
				});
	}

}
