package com.hfsgwtdemo.client.endereco;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSListBox;
import com.hfsgwt.client.componentes.HFSLoading;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.HFSListBox.TipoLista;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.grid.HFSStringGrid;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwt.client.componentes.util.HFSUtil;
import com.hfsgwtdemo.client.DemoService;
import com.hfsgwtdemo.client.DemoServiceAsync;

public class HFSEndereco extends Composite {
	private VerticalPanel principalPanel;
	private Grid gridCombos;
	private CaptionPanel captionPanel;
	private HFSListBox cmbPais;
	private HFSListBox cmbMunicipio;
	private HFSListBox cmbUF;
	private HFSListBox cmbBairro;
	private VerticalPanel verticalPanel;
	private HFSStringGrid gridLogradouro;
	private HorizontalPanel botaoPanel;
	private HFSTextBox edtPsqLogradouro;
	private Button btnPsqLogradouro;
	private PushButton btnExcLogradouro;
	private Grid gridEndereco;
	private HFSTextBox edtLogradouro;
	private HFSTextBox edtNumero;
	private HFSTextBox edtComplemento;
	private HFSTextBox edtCEP;

	private final DemoServiceAsync servico = GWT.create(DemoService.class);

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	List<EnderecoTrecho> enderecoTrecho;

	public HFSEndereco() {
		initComponents();

		listarPais();
	}

	private void initComponents() {
		initWidget(getPrincipalPanel());
	}

	private VerticalPanel getPrincipalPanel() {
		if (principalPanel == null) {
			principalPanel = new VerticalPanel();
			principalPanel.add(getGridCombos());
			principalPanel.add(getCaptionPanel());
			principalPanel.add(getGridEndereco());
		}
		return principalPanel;
	}

	private Grid getGridCombos() {
		if (gridCombos == null) {
			gridCombos = new Grid(2, 2);
			gridCombos.setWidget(0, 0, getCmbPais());
			gridCombos.setWidget(0, 1, getCmbMunicipio());
			gridCombos.setWidget(1, 0, getCmbUF());
			gridCombos.setWidget(1, 1, getCmbBairro());
		}
		return gridCombos;
	}

	private CaptionPanel getCaptionPanel() {
		if (captionPanel == null) {
			captionPanel = new CaptionPanel("Logradouro - Pesquisa");
			captionPanel.setContentWidget(getVerticalPanel());
		}
		return captionPanel;
	}

	private HFSListBox getCmbPais() {
		if (cmbPais == null) {
			cmbPais = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.COMBOBOX,
					"País", 250, false, true);
			cmbPais.getListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					if (!getCmbPais().getItemSelecionado().getId().trim()
							.equals("")) {
						int codigoPais = Integer.parseInt(getCmbPais()
								.getItemSelecionado().getId());
						listarUF(codigoPais);
					}
				}
			});
		}
		return cmbPais;
	}

	private HFSListBox getCmbUF() {
		if (cmbUF == null) {
			cmbUF = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.COMBOBOX,
					"UF", 250, false, false);
			cmbUF.setHabilitado(false);
			cmbUF.getListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					if (!getCmbPais().getItemSelecionado().getId().trim()
							.equals("")) {
						int codigoPais = Integer.parseInt(getCmbPais()
								.getItemSelecionado().getId());

						if (!getCmbUF().getItemSelecionado().getId().trim()
								.equals("")) {
							int codigoUF = Integer.parseInt(getCmbUF()
									.getItemSelecionado().getId());
							listarMunicipio(codigoPais, codigoUF);
						}
					}
				}
			});
		}
		return cmbUF;
	}

	private HFSListBox getCmbMunicipio() {
		if (cmbMunicipio == null) {
			cmbMunicipio = new HFSListBox(PosicaoRotulo.ACIMA,
					TipoLista.COMBOBOX, "Município", 250, false, false);
			cmbMunicipio.setHabilitado(false);
			cmbMunicipio.getListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					if (!getCmbPais().getItemSelecionado().getId().trim()
							.equals("")) {
						int codigoPais = Integer.parseInt(getCmbPais()
								.getItemSelecionado().getId());

						if (!getCmbUF().getItemSelecionado().getId().trim()
								.equals("")) {
							int codigoUF = Integer.parseInt(getCmbUF()
									.getItemSelecionado().getId());

							if (!getCmbMunicipio().getItemSelecionado().getId()
									.trim().equals("")) {
								int codigoMunicipio = Integer
										.parseInt(getCmbMunicipio()
												.getItemSelecionado().getId());
								listarBairro(codigoPais, codigoUF,
										codigoMunicipio);
							}
						}
					}
				}
			});
		}
		return cmbMunicipio;
	}

	private HFSListBox getCmbBairro() {
		if (cmbBairro == null) {
			cmbBairro = new HFSListBox(PosicaoRotulo.ACIMA, TipoLista.COMBOBOX,
					"Bairro", 250, false, false);
			cmbBairro.setHabilitado(false);
		}
		return cmbBairro;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(2);
			verticalPanel.setSize("100%", "3cm");
			verticalPanel.add(getGridLogradouro());
			verticalPanel.add(getBotaoPanel());
		}
		return verticalPanel;
	}

	private HFSStringGrid getGridLogradouro() {
		if (gridLogradouro == null) {
			gridLogradouro = new HFSStringGrid(new String[] { "Bairro",
					"Logradouro", "Numeração", "CEP", "Trecho" },
					new Boolean[] { true, true, true, true, true }, 0);
			gridLogradouro.setWidth("100%");
			gridLogradouro
					.addAdicionarHandler(new HFSStringGrid.AdicionarHandler() {
						@Override
						public void onAposAdicionarLinhas(List<String> item) {
							getEdtLogradouro().setTexto(item.get(1));
							getEdtNumero().setTexto(item.get(2));
							getEdtCEP().setTexto(item.get(3));
						}
					});
			gridLogradouro.addItemHandler(new HFSStringGrid.ItemHandler() {
				@Override
				public void onItemSelecionado(List<String> item) {
					getEdtLogradouro().setTexto(item.get(1));
					getEdtNumero().setTexto(item.get(2));
					getEdtCEP().setTexto(item.get(3));
				}
			});

		}
		return gridLogradouro;
	}

	private HorizontalPanel getBotaoPanel() {
		if (botaoPanel == null) {
			botaoPanel = new HorizontalPanel();
			botaoPanel.setSpacing(2);
			botaoPanel.add(getEdtPsqLogradouro());
			botaoPanel.add(getBtnPsqLogradouro());
			botaoPanel.add(getBtnExcLogradouro());
		}
		return botaoPanel;
	}

	private HFSTextBox getEdtPsqLogradouro() {
		if (edtPsqLogradouro == null) {
			edtPsqLogradouro = new HFSTextBox(
					HFSTextBox.PosicaoRotulo.ESQUERDA, "Logradouro: ",
					Formato.PADRAO, 60, 56, false);
			edtPsqLogradouro.setCaixa(HFSTextBox.Caixa.MAIUSCULA);
			edtPsqLogradouro.setPermitirAcentuacao(false);
			edtPsqLogradouro.setPermitirCedilha(false);
		}
		return edtPsqLogradouro;
	}

	private Button getBtnPsqLogradouro() {
		if (btnPsqLogradouro == null) {
			btnPsqLogradouro = new Button("Procurar");
			btnPsqLogradouro.setTitle("Procurar Logradouro");
			btnPsqLogradouro.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					consultarTrecho();
				}
			});
			btnPsqLogradouro.setSize("80px", "28px");
		}
		return btnPsqLogradouro;
	}

	private PushButton getBtnExcLogradouro() {
		if (btnExcLogradouro == null) {
			btnExcLogradouro = new PushButton(new Image(img.enderecoLixo()));
			btnExcLogradouro.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnExcLogradouroClick();
				}
			});
			btnExcLogradouro.setTitle("Excluir Logradouro");
		}
		return btnExcLogradouro;
	}

	private Grid getGridEndereco() {
		if (gridEndereco == null) {
			gridEndereco = new Grid(2, 2);
			gridEndereco.setWidget(0, 0, getEdtLogradouro());
			gridEndereco.setWidget(0, 1, getEdtNumero());
			gridEndereco.setWidget(1, 0, getEdtComplemento());
			gridEndereco.setWidget(1, 1, getEdtCEP());
			gridEndereco.getCellFormatter().setWidth(1, 1, "");
		}
		return gridEndereco;
	}

	private HFSTextBox getEdtLogradouro() {
		if (edtLogradouro == null) {
			edtLogradouro = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA,
					"Logradouro", Formato.PADRAO, 80, 50, false);
			edtLogradouro.setHabilitado(false);
		}
		return edtLogradouro;
	}

	private HFSTextBox getEdtNumero() {
		if (edtNumero == null) {
			edtNumero = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA,
					"Número", Formato.NUMERO, 10, 12, false);
		}
		return edtNumero;
	}

	private HFSTextBox getEdtComplemento() {
		if (edtComplemento == null) {
			edtComplemento = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA,
					"Complemento", Formato.PADRAO, 50, 50, false);
		}
		return edtComplemento;
	}

	private HFSTextBox getEdtCEP() {
		if (edtCEP == null) {
			edtCEP = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA, "CEP",
					Formato.CEP, 9, 12, false);
		}
		return edtCEP;
	}

	private boolean validaLogradouro() {
		if (edtPsqLogradouro.getTexto().trim().length() >= 3) {
			return true;
		} else {
			Window
					.alert("Digite pelo menos 3 caracteres no campo de pesquisa do logradouro!");
			return false;
		}
	}

	private void btnExcLogradouroClick() {
		if (enderecoTrecho != null) {
			if (enderecoTrecho.size() > 0) {
				enderecoTrecho.clear();
				this.getGridLogradouro().removeLinhas();
				edtPsqLogradouro.setTexto("");
				edtLogradouro.setTexto("");
				edtNumero.setTexto("");
				edtComplemento.setTexto("");
				edtCEP.setTexto("");
				btnExcLogradouro.setEnabled(false);
			}
		}
	}

	private void CarregarGridTrecho(List<EnderecoTrecho> trecho) {
		List<List<String>> linhas = new ArrayList<List<String>>();
		List<String> colunas;
		for (EnderecoTrecho items : trecho) {
			colunas = new ArrayList<String>();
			colunas.add(items.getBairro());
			colunas.add(items.getLogradouro());
			colunas.add(items.getNumeracao());
			colunas.add(items.getCEP());
			colunas.add(Integer.toString(items.getCodigoTrecho()));
			linhas.add(colunas);
		}
		this.getGridLogradouro().addLinhas(linhas);
	}

	private void carregarPais(EnderecoPais[] paises) {
		for (EnderecoPais endPais : paises) {
			getCmbPais().addItem(
					new HFSItem(Integer.toString(endPais.getCodigo()), endPais
							.getNome()));
		}
		getCmbPais().setIndiceSelecionado(0);
	}

	private void carregarUF(EnderecoUF[] ufs) {
		this.getCmbUF().limpar();

		for (EnderecoUF itemUF : ufs) {
			getCmbUF().addItem(
					new HFSItem(Integer.toString(itemUF.getCodigo()), itemUF
							.getNome()));
		}
		if (getCmbUF().getItems().size() > 0) {
			getCmbUF().setIndiceSelecionado(0);
			getCmbUF().setHabilitado(true);
		} else
			getCmbUF().setHabilitado(false);
	}

	private void carregarMunicipio(EnderecoMunicipio[] municipios) {
		this.getCmbMunicipio().limpar();

		for (EnderecoMunicipio itemMunicipio : municipios) {
			getCmbMunicipio().addItem(
					new HFSItem(Integer.toString(itemMunicipio.getCodigo()),
							itemMunicipio.getNome()));
		}
		if (getCmbMunicipio().getItems().size() > 0) {
			getCmbMunicipio().setIndiceSelecionado(0);
			getCmbMunicipio().setHabilitado(true);
		} else
			getCmbMunicipio().setHabilitado(false);
	}

	private void carregarBairro(EnderecoBairro[] bairros) {
		this.getCmbBairro().limpar();

		for (EnderecoBairro itemBairro : bairros) {
			getCmbBairro().addItem(
					new HFSItem(Integer.toString(itemBairro.getCodigo()),
							itemBairro.getNome()));
		}
		if (getCmbBairro().getItems().size() > 0) {
			getCmbBairro().setIndiceSelecionado(0);
			getCmbBairro().setHabilitado(true);
		} else
			getCmbBairro().setHabilitado(false);
	}

	private void listarPais() {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Países...");

		servico.listarPais(new AsyncCallback<EnderecoPais[]>() {
			public void onFailure(Throwable caught) {
				dlg.hide();
				HFSUtil.mostrarFalha(caught, this.getClass(), "listarPais");
			}

			public void onSuccess(EnderecoPais[] endPaises) {
				carregarPais(endPaises);
				if (endPaises.length > 0) {
					listarUF(endPaises[0].getCodigo());
				}
				dlg.hide();
			}
		});
	}

	private void listarUF(final int codigoPais) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando UFs...");

		servico.listarUF(codigoPais, new AsyncCallback<EnderecoUF[]>() {
			public void onFailure(Throwable caught) {
				dlg.hide();
				HFSUtil.mostrarFalha(caught, this.getClass(), "listarUF");
			}

			public void onSuccess(EnderecoUF[] endUFs) {
				carregarUF(endUFs);
				if (endUFs.length > 0) {
					listarMunicipio(codigoPais, endUFs[0].getCodigo());
				}
				dlg.hide();
			}
		});
	}

	private void listarMunicipio(final int codigoPais, final int codigoUF) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Municípios...");

		servico.listarMunicipio(codigoPais, codigoUF,
				new AsyncCallback<EnderecoMunicipio[]>() {
					public void onFailure(Throwable caught) {
						dlg.hide();
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"listarMunicipio");
					}

					public void onSuccess(EnderecoMunicipio[] endMunicipios) {
						carregarMunicipio(endMunicipios);
						if (endMunicipios.length > 0) {
							listarBairro(codigoPais, codigoUF, endMunicipios[0]
									.getCodigo());
						}
						dlg.hide();
					}
				});
	}

	private void listarBairro(int codigoPais, int codigoUF, int codigoMunicipio) {
		final HFSLoading dlg = HFSLoading.mostrar("Carregando Bairros...");

		servico.listarBairro(codigoPais, codigoUF, codigoMunicipio,
				new AsyncCallback<EnderecoBairro[]>() {
					public void onFailure(Throwable caught) {
						dlg.hide();
						HFSUtil.mostrarFalha(caught, this.getClass(),
								"listarBairro");
					}

					public void onSuccess(EnderecoBairro[] endBairros) {
						carregarBairro(endBairros);
						dlg.hide();
					}
				});
	}

	private void consultarTrecho() {
		int codigoUF = -1;
		if (!getCmbUF().getItemSelecionado().getId().trim().equals(""))
			codigoUF = Integer
					.parseInt(getCmbUF().getItemSelecionado().getId());

		if (getCmbUF().getItemSelecionado().getValor().trim().equals(
				"NÃO INFORMADO"))
			codigoUF = -1;

		int codigoMunicipio = -1;
		if (!getCmbMunicipio().getItemSelecionado().getId().trim().equals(""))
			codigoMunicipio = Integer.parseInt(getCmbMunicipio()
					.getItemSelecionado().getId());

		if (codigoMunicipio == 999998) // NÃO INFORMADO
			codigoMunicipio = -1;

		int codigoBairro = -1;
		if (!getCmbBairro().getItemSelecionado().getId().trim().equals(""))
			codigoBairro = Integer.parseInt(getCmbBairro().getItemSelecionado()
					.getId());

		if (codigoBairro == 0) // NÃO INFORMADO
			codigoBairro = -1;

		String logradouro = edtPsqLogradouro.getTexto();

		if (validaLogradouro()) {
			final HFSLoading dlg = HFSLoading
					.mostrar("Consultando Logradouro...");

			servico.consultarTrecho(codigoUF, codigoMunicipio, codigoBairro,
					logradouro, new AsyncCallback<List<EnderecoTrecho>>() {
						public void onFailure(Throwable caught) {
							dlg.hide();
							Window.alert(HFSConst.SERVIDOR_ERRO
									+ "HFSEndereco[consultarTrecho]");
						}

						public void onSuccess(List<EnderecoTrecho> endTrechos) {
							enderecoTrecho = endTrechos;
							btnPsqLogradouroClick();
							dlg.hide();
						}
					});
		}
	}

	private void btnPsqLogradouroClick() {
		if (enderecoTrecho != null) {
			if (enderecoTrecho.size() > 0) {
				CarregarGridTrecho(enderecoTrecho);
				btnExcLogradouro.setEnabled(true);
				edtPsqLogradouro.setTexto("");
			} else {
				btnExcLogradouro.setEnabled(false);
				Window.alert("Logradouro(s) não encontrado(s)!");
			}
		} else {
			Window.alert("EnderecoTrecho não pode ser nulo");
		}
	}

}
