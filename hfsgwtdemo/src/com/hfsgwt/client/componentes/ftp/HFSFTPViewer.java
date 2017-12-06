package com.hfsgwt.client.componentes.ftp;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSFTPViewer extends Composite {
	private VerticalPanel basePanel;
	private HorizontalPanel botoesPanel;
	private Label labEnviar;
	private PushButton btnEnviar;
	private Label labReceber;
	private PushButton btnReceber;
	private HorizontalSplitPanel splitPanel;
	private Tree arvore;
	private HFSStringGrid grid;
	private Image image;

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);
	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	private FTPEstrutura ftpe;
	private TreeItem itemAberto;

	private String largura;
	private String altura;
	private DockPanel dockPanel;
	private SimplePanel simplePanel;
	private Grid gridDados;
	private Label labServidor;
	private Label labLogin;
	private ListBox cmbServidor;
	private Label labSenha;
	private TextBox edtLogin;
	private PasswordTextBox edtSenha;
	private Button btnConectar;
	private Button btnDesconectar;
	private Button btnCarregar;

	private String[] servidores;

	public HFSFTPViewer(String largura, String altura, String[] servidores) {
		this.largura = largura;
		this.altura = altura;
		this.servidores = servidores;
		initComponents();
	}

	private void initComponents() {
		initWidget(getBasePanel());
	}

	private VerticalPanel getBasePanel() {
		if (basePanel == null) {
			basePanel = new VerticalPanel();
			basePanel.setStyleName("HFSFTPViewer-Panel");
			basePanel.setSize(largura, altura);
			basePanel.add(getGridDados());
			basePanel.add(getDockPanel());
			basePanel.add(getSplitPanel());
			basePanel.setCellHeight(getSplitPanel(), "100%");
			basePanel.setCellWidth(getSplitPanel(), "100%");
		}
		return basePanel;
	}

	private DockPanel getDockPanel() {
		if (dockPanel == null) {
			dockPanel = new DockPanel();
			dockPanel.setSpacing(2);
			dockPanel.add(getBotoesPanel(), DockPanel.WEST);
			dockPanel.add(getSimplePanel(), DockPanel.CENTER);
			dockPanel.setCellWidth(getSimplePanel(), "100%");
			dockPanel.setCellVerticalAlignment(getSimplePanel(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			dockPanel.setCellHorizontalAlignment(getSimplePanel(),
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
		return dockPanel;
	}

	private SimplePanel getSimplePanel() {
		if (simplePanel == null) {
			simplePanel = new SimplePanel();
			simplePanel.setSize("100%", "16px");
			simplePanel.setWidget(getImage());
		}
		return simplePanel;
	}

	private HorizontalPanel getBotoesPanel() {
		if (botoesPanel == null) {
			botoesPanel = new HorizontalPanel();
			botoesPanel.setSpacing(2);
			botoesPanel.add(getLabEnviar());
			botoesPanel.setCellVerticalAlignment(getLabEnviar(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.add(getBtnEnviar());
			botoesPanel.add(getLabReceber());
			botoesPanel.setCellVerticalAlignment(getLabReceber(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			botoesPanel.add(getBtnReceber());
			botoesPanel.setCellHorizontalAlignment(getImage(),
					HasHorizontalAlignment.ALIGN_RIGHT);
			botoesPanel.setCellVerticalAlignment(getImage(),
					HasVerticalAlignment.ALIGN_MIDDLE);

		}
		return botoesPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image(img.carregando());
			image.setSize("", "100%");
			image.setVisible(false);
		}
		return image;
	}

	private Label getLabEnviar() {
		if (labEnviar == null) {
			labEnviar = new Label("Enviar para o FTP");
			labEnviar.setWordWrap(false);
		}
		return labEnviar;
	}

	private PushButton getBtnEnviar() {
		if (btnEnviar == null) {
			btnEnviar = new PushButton(new Image(img.ftpViewerEnviar()));
			btnEnviar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
				}
			});
			btnEnviar.setEnabled(false);
			btnEnviar.setTitle("Enviar");
		}
		return btnEnviar;
	}

	private Label getLabReceber() {
		if (labReceber == null) {
			labReceber = new Label("Receber do FTP");
			labReceber.setWordWrap(false);
		}
		return labReceber;
	}

	private PushButton getBtnReceber() {
		if (btnReceber == null) {
			btnReceber = new PushButton(new Image(img.ftpViewerReceber()));
			btnReceber.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
				}
			});
			btnReceber.setEnabled(false);
			btnReceber.setTitle("Receber");
		}
		return btnReceber;
	}

	private HorizontalSplitPanel getSplitPanel() {
		if (splitPanel == null) {
			splitPanel = new HorizontalSplitPanel();
			splitPanel.setSplitPosition("40%");
			splitPanel.setLeftWidget(getArvore());
			splitPanel.setRightWidget(getGrid());
			splitPanel.setSize("97%", "92%");
		}
		return splitPanel;
	}

	private Tree getArvore() {
		if (arvore == null) {
			arvore = new Tree();
			arvore.setSize("99%", "99%");
		}
		return arvore;
	}

	private HFSStringGrid getGrid() {
		if (grid == null) {
			grid = new HFSStringGrid(
					new String[] { "Nome", "Tamanho", "Tipo", "Data" },
					new Boolean[] { true, true, true, true }, 5);
			grid.setSize("99%", "99%");
		}
		return grid;
	}

	private void criarArvore(FTPArquivo[] arquivos) {
		TreeItem item;
		TreeItem itemRaiz = this.getArvore().addTextItem("/");
		for (int i = 0; i < arquivos.length; i++) {
			item = itemRaiz.addTextItem(arquivos[i].getNome());

			// adicionar items temporariamente para poder expandir os nodes
			item.addTextItem("");
		}

		this.getArvore().addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				itemAberto = event.getTarget();
				if (itemAberto.getChildCount() == 1) {
					listarArquivosFTP(ftpe, true, false, true, false);
				}
			}
		});

		this.getArvore().addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				TreeItem item = event.getSelectedItem();
				String caminho = "";
				while (item != null) {
					caminho = item.getText() + caminho;
					item = item.getParentItem();
				}
				ftpe.setDiretorio(caminho);
				listarArquivosFTP(ftpe, false, false, false, true);

			}
		});

	}

	private void addDiretorios(FTPArquivo[] arquivos, TreeItem item) {
		if (arquivos.length > 0) {
			// Fecha o item imediatamente
			item.setState(false, false);

			// String itemText = item.getText();
			// int numFilhos = Random.nextInt(5) + 2;
			for (int i = 0; i < arquivos.length; i++) {
				TreeItem filho = item.addTextItem(arquivos[i].getNome());
				filho.addTextItem("");
			}

			// Remove o item temporario quando acabamos de carregar
			item.getChild(0).remove();

			// Reabre o item
			item.setState(true, false);
		}
	}

	private void carregarGrid(FTPArquivo[] arquivos) {
		if (arquivos.length > 0) {
			DateTimeFormat datahorafmt;
			String sData;
			List<List<String>> linhas = new ArrayList<List<String>>();
			List<String> cols;
			for (int i = 0; i < arquivos.length; i++) {
				cols = new ArrayList<String>();
				cols.add(arquivos[i].getNome());
				cols.add(Long.toString(arquivos[i].getTamanho()));

				switch (arquivos[i].getTipo()) {
				case ARQUIVO:
					cols.add("Arquivo");
					break;
				case DIRETORIO:
					cols.add("Diretório");
					break;
				case LINKSIMBOLICO:
					cols.add("Link Simbólico");
					break;
				default:
					cols.add("");
					break;
				}
				datahorafmt = DateTimeFormat
						.getFormat(HFSConst.MASCARA_DATAHORA);
				sData = datahorafmt.format(arquivos[i].getData());
				cols.add(sData);
				linhas.add(cols);
			}
			getGrid().addLinhas(linhas);
		}
	}

	private void conectaFTP(FTPEstrutura ftpe) {
		getImage().setVisible(true);

		servico.conectaFTP(ftpe, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				getImage().setVisible(false);
				HFSUtil.mostrarFalha(caught, this.getClass(), "conectaFTP");
			}

			public void onSuccess(Boolean res) {
				getImage().setVisible(false);
				getBtnConectar().setEnabled(false);
				getBtnDesconectar().setEnabled(true);
				getBtnCarregar().setEnabled(true);
			}
		});
	}

	private void desconectaFTP() {
		getImage().setVisible(true);

		servico.desconectaFTP(new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				getImage().setVisible(false);
				HFSUtil.mostrarFalha(caught, this.getClass(), "desconectaFTP");
			}

			public void onSuccess(Boolean res) {
				getImage().setVisible(false);
				getBtnConectar().setEnabled(true);
				getBtnDesconectar().setEnabled(false);
				getBtnCarregar().setEnabled(false);
			}
		});
	}

	private void listarArquivosFTP(FTPEstrutura ftpe, boolean somenteDiretorio,
			final boolean bCriarArvore, final boolean bAddDiretorios,
			final boolean bCarregarGrid) {
		getImage().setVisible(true);

		servico.listarArquivosFTP(ftpe, somenteDiretorio,
				new AsyncCallback<FTPArquivo[]>() {
					public void onFailure(Throwable caught) {
						getImage().setVisible(false);
						HFSUtil.mostrarFalha(caught, this.getClass(), "listarArquivosFTP");
					}

					public void onSuccess(FTPArquivo[] arquivos) {
						if (arquivos.length > 0) {
							if (bCriarArvore) {
								criarArvore(arquivos);
							} else if (bAddDiretorios) {
								addDiretorios(arquivos, itemAberto);
							} else if (bCarregarGrid) {
								carregarGrid(arquivos);
							}
						}
						getImage().setVisible(false);
					}
				});
	}

	private void listarArquivosFTP() {
		listarArquivosFTP(ftpe, true, true, false, false);
	}

	// public void enviarArquivoFTP(FTPEstrutura ftpe) {
	// getImage().setVisible(true);
	//		
	// servico.enviarArquivoFTP(ftpe, new AsyncCallback<Boolean>() {
	// public void onFailure(Throwable caught) {
	// getImage().setVisible(false);
	// Window.alert(HFSConst.SERVIDOR_ERRO
	// + "HFSFTPViewer[enviarArquivoFTP]");
	// }
	//
	// public void onSuccess(Boolean res) {
	// if (res){
	// Window.alert("Arquivo enviado com sucesso para o FTP!");
	// }
	// getImage().setVisible(false);
	// }
	// });
	// }
	//	
	// public void receberArquivoFTP(FTPEstrutura ftpe) {
	// getImage().setVisible(true);
	//		
	// servico.receberArquivoFTP(ftpe, new AsyncCallback<Boolean>() {
	// public void onFailure(Throwable caught) {
	// getImage().setVisible(false);
	// Window.alert(HFSConst.SERVIDOR_ERRO
	// + "HFSFTPViewer[receberArquivoFTP]");
	// }
	//
	// public void onSuccess(Boolean res) {
	// if (res){
	// Window.alert("Arquivo recebido com sucesso para o FTP!");
	// }
	// getImage().setVisible(false);
	// }
	// });
	// }

	private Grid getGridDados() {
		if (gridDados == null) {
			gridDados = new Grid(3, 3);
			gridDados.setWidget(0, 0, getLabServidor());
			gridDados.setWidget(0, 1, getLabLogin());
			gridDados.setWidget(0, 2, getLabSenha());
			gridDados.setWidget(1, 0, getCmbServidor());
			gridDados.setWidget(1, 1, getEdtLogin());
			gridDados.setWidget(1, 2, getEdtSenha());
			gridDados.setWidget(2, 0, getBtnConectar());
			gridDados.setWidget(2, 1, getBtnDesconectar());
			gridDados.getCellFormatter().setHorizontalAlignment(2, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			gridDados.getCellFormatter().setHorizontalAlignment(2, 1,
					HasHorizontalAlignment.ALIGN_CENTER);
			gridDados.setWidget(2, 2, getBtnCarregar());
			gridDados.getCellFormatter().setHorizontalAlignment(2, 2,
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return gridDados;
	}

	private Label getLabServidor() {
		if (labServidor == null) {
			labServidor = new Label("Servidor FTP:");
		}
		return labServidor;
	}

	private Label getLabLogin() {
		if (labLogin == null) {
			labLogin = new Label("Login:");
		}
		return labLogin;
	}

	private ListBox getCmbServidor() {
		if (cmbServidor == null) {
			cmbServidor = new ListBox();
			cmbServidor.setWidth("150px");
			for (String item : servidores) {
				cmbServidor.addItem(item);
			}
			if (servidores.length > 0)
				cmbServidor.setItemSelected(0, true);
		}
		return cmbServidor;
	}

	private Label getLabSenha() {
		if (labSenha == null) {
			labSenha = new Label("Senha:");
		}
		return labSenha;
	}

	private TextBox getEdtLogin() {
		if (edtLogin == null) {
			edtLogin = new TextBox();
			edtLogin.setMaxLength(50);
		}
		return edtLogin;
	}

	private PasswordTextBox getEdtSenha() {
		if (edtSenha == null) {
			edtSenha = new PasswordTextBox();
			edtSenha.setMaxLength(50);
		}
		return edtSenha;
	}

	private Button getBtnConectar() {
		if (btnConectar == null) {
			btnConectar = new Button("Conectar");
			btnConectar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (servidores.length > 0) {
						// ftpe = new FTPEstrutura("200.238.112.36",
						// "ftpadm", "ftp$adm!", "");
						String servidor = getCmbServidor().getItemText(
								getCmbServidor().getSelectedIndex());
						ftpe = new FTPEstrutura(servidor, getEdtLogin()
								.getText(), getEdtSenha().getText(), "");
						conectaFTP(ftpe);
					}
				}
			});
		}
		return btnConectar;
	}

	private Button getBtnDesconectar() {
		if (btnDesconectar == null) {
			btnDesconectar = new Button("Desconectar");
			btnDesconectar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					desconectaFTP();
				}
			});
			btnDesconectar.setEnabled(false);
		}
		return btnDesconectar;
	}

	private Button getBtnCarregar() {
		if (btnCarregar == null) {
			btnCarregar = new Button("Carregar");
			btnCarregar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					listarArquivosFTP();
					getBtnCarregar().setEnabled(false);
				}
			});
			btnCarregar.setEnabled(false);
		}
		return btnCarregar;
	}

	public void setLogin(String login) {
		getEdtLogin().setText(login);
	}

	public void setSenha(String senha) {
		getEdtSenha().setText(senha);
	}
}
