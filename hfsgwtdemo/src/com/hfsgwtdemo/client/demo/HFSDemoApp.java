package com.hfsgwtdemo.client.demo;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.hfsgwt.client.application.HFSApplication;
import com.hfsgwt.client.application.HFSSobre;
import com.hfsgwt.client.componentes.HFSAppendListBox;
import com.hfsgwt.client.componentes.HFSCalculator;
import com.hfsgwt.client.componentes.HFSCaptcha;
import com.hfsgwt.client.componentes.HFSDialogFileUpload;
import com.hfsgwt.client.componentes.HFSDualList;
import com.hfsgwt.client.componentes.HFSImageViewer;
import com.hfsgwt.client.componentes.HFSItem;
import com.hfsgwt.client.componentes.HFSLogin;
import com.hfsgwt.client.componentes.HFSMenu;
import com.hfsgwt.client.componentes.HFSScanner;
import com.hfsgwt.client.componentes.HFSTable;
import com.hfsgwt.client.componentes.HFSTextArea;
import com.hfsgwt.client.componentes.HFSThemeSelector;
import com.hfsgwt.client.componentes.chart.AreaChartParams;
import com.hfsgwt.client.componentes.chart.BarChartParams;
import com.hfsgwt.client.componentes.chart.BoxAndWhiskerChartDados;
import com.hfsgwt.client.componentes.chart.BoxAndWhiskerChartParams;
import com.hfsgwt.client.componentes.chart.BubbleChartParams;
import com.hfsgwt.client.componentes.chart.CandlestickChartParams;
import com.hfsgwt.client.componentes.chart.CategoryDados;
import com.hfsgwt.client.componentes.chart.GanttChartDados;
import com.hfsgwt.client.componentes.chart.GanttChartParams;
import com.hfsgwt.client.componentes.chart.HFSChart;
import com.hfsgwt.client.componentes.chart.HighLowChartParams;
import com.hfsgwt.client.componentes.chart.HistogramChartParams;
import com.hfsgwt.client.componentes.chart.LineChartParams;
import com.hfsgwt.client.componentes.chart.OHLCSeriesDados;
import com.hfsgwt.client.componentes.chart.OHLCSeriesParams;
import com.hfsgwt.client.componentes.chart.PieChartDados;
import com.hfsgwt.client.componentes.chart.PieChartParams;
import com.hfsgwt.client.componentes.chart.ScatterChartParams;
import com.hfsgwt.client.componentes.chart.SeriesDados;
import com.hfsgwt.client.componentes.chart.StackedAreaChartParams;
import com.hfsgwt.client.componentes.chart.StackedBarChartParams;
import com.hfsgwt.client.componentes.chart.TimeSeriesChartParams;
import com.hfsgwt.client.componentes.chart.TimeSeriesChartSerie;
import com.hfsgwt.client.componentes.chart.XYAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYBarChartParams;
import com.hfsgwt.client.componentes.chart.XYDados;
import com.hfsgwt.client.componentes.chart.XYLineChartParams;
import com.hfsgwt.client.componentes.chart.XYSeriesParams;
import com.hfsgwt.client.componentes.chart.XYSeriesParamsDouble;
import com.hfsgwt.client.componentes.chart.XYStepAreaChartParams;
import com.hfsgwt.client.componentes.chart.XYStepChartParams;
import com.hfsgwt.client.componentes.email.HFSDialogEmail;
import com.hfsgwt.client.componentes.ftp.HFSFTPViewer;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.map.HFSMap;
import com.hfsgwt.client.componentes.map.HFSStaticMap;
import com.hfsgwt.client.componentes.menuxml.MXEstrutura;
import com.hfsgwt.client.componentes.report.HFSReport;
import com.hfsgwt.client.componentes.report.HFSReportSaveBar;
import com.hfsgwt.client.componentes.richtext.HFSRichText;
import com.hfsgwt.client.componentes.stackpanel.HFSStackItem;
import com.hfsgwt.client.componentes.stackpanel.HFSStackPanel;
import com.hfsgwt.client.componentes.tabpanel.HFSTabItem;
import com.hfsgwt.client.componentes.tabpanel.HFSTabPanel;
import com.hfsgwtdemo.client.HFSCadastro;
import com.hfsgwtdemo.client.endereco.HFSEndereco;
import com.hfsgwtdemo.client.siadm.HFSSiadmTree;

public class HFSDemoApp extends HFSApplication implements EntryPoint {

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	private DemoTextBox demoTextBox;
	private DemoListBox demoListBox;
	private HFSDualList dlista;
	private DemoNavigator demoNavigator;
	private HFSStackPanel spanel;
	private DemoLinkedListBox demoLinkedListBox;
	private HFSTabPanel tabpanel;
	private HFSReport rel;
	private HFSReportSaveBar saveBar;
	private HFSCaptcha captcha;
	private HFSMap mapa;
	private HFSStaticMap smapa;
	private HFSLogin login;
	private HFSThemeSelector tema;
	private HFSCalculator calc;
	private DemoLabelEncode64 demoLabelCode64;
	private DemoLabelTextNumber demoLabelTextNumber;
	private DemoLabelCrypt demoLabelCrypt;
	private DemoLabelHash demoLabelHash;
	private DemoBarcode demoBarcode;
	private HFSRichText rico;
	private DemoProgressBar demoProgressBar;
	private HFSChart graficoPie;
	private HFSChart graficoPie3D;
	private HFSChart graficoXYLine;
	private HFSChart graficoLine;
	private HFSChart graficoLine3D;
	private HFSChart graficoXYArea;
	private HFSChart graficoXYStep;
	private HFSChart graficoXYStepArea;
	private HFSChart graficoBar;
	private HFSChart graficoBar3D;
	private HFSChart graficoTimeSeries;
	private HFSChart graficoArea;
	private HFSChart graficoStackedBar;
	private HFSChart graficoStackedBar3D;
	private HFSChart graficoXYBar;
	private HFSChart graficoHistogram;
	private HFSChart graficoBubble;	
	private HFSChart graficoBoxAndWhisker;
	private HFSChart graficoStackedArea;
	private HFSChart graficoScatter;
	private HFSChart graficoHighLow;
	private HFSChart graficoCandlestick;
	private HFSChart graficoGantt;	
	private DemoTree demoTree;
	private HFSMenu menu;
	private HFSFTPViewer ftpviewer;

	private DemoMultiCheckBox demoMultiCheckBox;
	private DemoCheckListBox demoCheckListBox;
	private HFSAppendListBox appendListBox;
	private DemoAppendGrid demoAppendGrid;
	private DemoMultiRadioButton demoMultiRadioButton;
	//private DemoSlider slider;
	private HFSEndereco endereco;
	private Button btnUpload;
	private HFSDialogFileUpload dlgUpload;
	private DemoStringGrid demoStringGrid;
	private DemoGrid demoGrid;
	private Button btnEmail;
	private HFSDialogEmail dlgEmail;
	private DemoDialogChat demoDialogChat;
	private DemoDialogMessage demoDialogMessage;
	private DemoCronometro demoCronometro;
	private DemoSuggestBox demoSuggestBox;
	private HFSCadastro cadastro;
	private HFSTextArea tarea;
	private DemoPasswordChecker demoPasswordChecker;
	private DemoFlowPlayer demoFlowPlayer;
	private HFSScanner scanner;
	private HFSSiadmTree siadmTree;
	private HFSImageViewer imageViewer;
	private HFSTable tabela;

	public void onModuleLoad() {
		super.onModuleLoad(HFSApplication.TipoMenu.STACKPANEL, img
				.aplicacaoDemo(), false, false, img.aplicacao(),
				"HFS_GWT_COMPONENTES_DEMO", "HFS GWT Componentes",
				"Demonstração");

		final double[][] mdados1 = new double[6][6];
		mdados1[0][0] = 1;
		mdados1[0][1] = 2;
		mdados1[0][2] = 3;
		mdados1[0][3] = 4;
		mdados1[0][4] = 5;
		mdados1[0][5] = 6;
		
		mdados1[1][0] = 7;
		mdados1[1][1] = 8;
		mdados1[1][2] = 9;
		mdados1[1][3] = 10;
		mdados1[1][4] = 11;
		mdados1[1][5] = 12;

		mdados1[2][0] = 27;
		mdados1[2][1] = 28;
		mdados1[2][2] = 29;
		mdados1[2][3] = 20;
		mdados1[2][4] = 21;
		mdados1[2][5] = 22;

		mdados1[3][0] = 17;
		mdados1[3][1] = 18;
		mdados1[3][2] = 19;
		mdados1[3][3] = 10;
		mdados1[3][4] = 11;
		mdados1[3][5] = 12;

		mdados1[4][0] = 37;
		mdados1[4][1] = 38;
		mdados1[4][2] = 19;
		mdados1[4][3] = 20;
		mdados1[4][4] = 11;
		mdados1[4][5] = 32;

		mdados1[5][0] = 27;
		mdados1[5][1] = 28;
		mdados1[5][2] = 29;
		mdados1[5][3] = 20;
		mdados1[5][4] = 21;
		mdados1[5][5] = 22;

		final double[][] mdados2 = new double[6][6];
		mdados2[0][0] = 13;
		mdados2[0][1] = 25;
		mdados2[0][2] = 36;
		mdados2[0][3] = 47;
		mdados2[0][4] = 58;
		mdados2[0][5] = 69;
		
		mdados2[1][0] = 75;
		mdados2[1][1] = 84;
		mdados2[1][2] = 93;
		mdados2[1][3] = 15;
		mdados2[1][4] = 16;
		mdados2[1][5] = 162;

		mdados2[2][0] = 257;
		mdados2[2][1] = 258;
		mdados2[2][2] = 259;
		mdados2[2][3] = 250;
		mdados2[2][4] = 251;
		mdados2[2][5] = 252;

		mdados2[3][0] = 176;
		mdados2[3][1] = 186;
		mdados2[3][2] = 196;
		mdados2[3][3] = 106;
		mdados2[3][4] = 161;
		mdados2[3][5] = 12;

		mdados2[4][0] = 367;
		mdados2[4][1] = 368;
		mdados2[4][2] = 169;
		mdados2[4][3] = 260;
		mdados2[4][4] = 161;
		mdados2[4][5] = 362;

		mdados2[5][0] = 27;
		mdados2[5][1] = 28;
		mdados2[5][2] = 29;
		mdados2[5][3] = 20;
		mdados2[5][4] = 21;
		mdados2[5][5] = 22;
		
		// this.getMenu().setCarregarHandler(new CarregarHandler() {
		// @Override
		// public void onCarregar(final MXEstrutura mx) {
		// getMenu().getSubMenu(mx,"SIMPLES", "HFSTextBox").setCommand(new
		// Command() {
		// @Override
		// public void execute() {
		// if (demoTextBox == null) {
		// demoTextBox = new DemoTextBox();
		// }
		// setTela(demoTextBox);
		// }
		// });
		// }
		// });

		// this.getTree().setArvoreSelecaoHandler(new ArvoreSelecaoHandler() {
		// @Override
		// public void onSelecao(MXItem mxitem) {
		// String id = mxitem.getCodigo();
		// //String rotulo = mxitem.getLabel();

		this.getStackPanel().addLinkHandler(new HFSStackPanel.LinkHandler() {
			@Override
			public void onLinkClick(String id, String rotulo) {
				if (id.equals("HFSTextBox")) {
					setSubTituloSistema("HFSTextBox usando puro GWT");
					if (demoTextBox == null) {
						demoTextBox = new DemoTextBox();
					}
					setTela(demoTextBox);
				} else if (id.equals("HFSPasswordChecker")) {
					setSubTituloSistema("HFSPasswordChecker usando puro GWT com RPC");
					if (demoPasswordChecker == null) {
						demoPasswordChecker = new DemoPasswordChecker();
					}
					setTela(demoPasswordChecker);
				} else if (id.equals("HFSTextArea")) {
					setSubTituloSistema("HFSTextArea usando puro GWT");
					if (tarea == null) {
						tarea = new HFSTextArea(
								HFSTextArea.PosicaoRotulo.ACIMA,
								"Área de Texto:", 300, 150, 150, false);
					}
					setTela(tarea);
				} else if (id.equals("HFSListBox")) {
					setSubTituloSistema("HFSListBox usando puro GWT");
					if (demoListBox == null) {
						demoListBox = new DemoListBox();
					}
					setTela(demoListBox);
				} else if (id.equals("HFSLabelEncode64")) {
					setSubTituloSistema("HFSLabelEncode64 usando puro GWT com RPC");
					if (demoLabelCode64 == null) {
						demoLabelCode64 = new DemoLabelEncode64();
					}
					setTela(demoLabelCode64);
				} else if (id.equals("HFSLabelTextNumber")) {
					setSubTituloSistema("HFSLabelTextNumber usando puro GWT com RPC");
					if (demoLabelTextNumber == null) {
						demoLabelTextNumber = new DemoLabelTextNumber();
					}
					setTela(demoLabelTextNumber);
				} else if (id.equals("HFSLabelCrypt")) {
					setSubTituloSistema("HFSLabelCrypt usando puro GWT com RPC");
					if (demoLabelCrypt == null) {
						demoLabelCrypt = new DemoLabelCrypt();
					}
					setTela(demoLabelCrypt);
				} else if (id.equals("HFSLabelHash")) {
					setSubTituloSistema("HFSLabelHash usando puro GWT com RPC");
					if (demoLabelHash == null) {
						demoLabelHash = new DemoLabelHash();
					}
					setTela(demoLabelHash);
				} else if (id.equals("HFSCalculator")) {
					setSubTituloSistema("HFSCalculator usando puro GWT");
					if (calc == null) {
						calc = new HFSCalculator(
								HFSCalculator.BotaoTamanho.PEQUENO);
					}
					setTela(calc);
				} else if (id.equals("HFSCaptcha")) {
					setSubTituloSistema("HFSCaptcha usando a JCaptcha API");
					if (captcha == null) {
						captcha = new HFSCaptcha();
					}
					setTela(captcha);
				} else if (id.equals("HFSNavigator")) {
					setSubTituloSistema("HFSNavigator usando puro GWT");
					if (demoNavigator == null) {
						demoNavigator = new DemoNavigator();
					}
					setTela(demoNavigator);
				} else if (id.equals("HFSStackPanel")) {
					setSubTituloSistema("HFSStackPanel usando puro GWT");
					if (spanel == null) {
						ArrayList<HFSStackItem> stackItems = new ArrayList<HFSStackItem>();
						ArrayList<HFSItem> links1 = new ArrayList<HFSItem>();
						links1.add(new HFSItem("11", "item 001"));
						links1.add(new HFSItem("12", "item 002"));
						links1.add(new HFSItem("13", "item 003"));
						stackItems.add(new HFSStackItem("aba 001", links1));
						ArrayList<HFSItem> links2 = new ArrayList<HFSItem>();
						links2.add(new HFSItem("21", "item 100"));
						links2.add(new HFSItem("22", "item 223"));
						links2.add(new HFSItem("23", "item 345"));
						stackItems.add(new HFSStackItem("aba 002", links2));
						spanel = new HFSStackPanel("200px", "200px", false,
								stackItems);
					}
					setTela(spanel);
				} else if (id.equals("HFSTabPanel")) {
					setSubTituloSistema("HFSTabPanel usando puro GWT");
					if (tabpanel == null) {
						HFSTabItem[] tabitems = new HFSTabItem[3];
						tabitems[0] = new HFSTabItem("tab 1", new HTML(
								"FALA CARA"), false);
						tabitems[1] = new HFSTabItem("tab 2", new HTML(
								"TESTE 2"), false);
						tabitems[2] = new HFSTabItem("tab 3",
								new HTML("BELEZA"), false);
						tabpanel = new HFSTabPanel("200px", "200px", tabitems,
								true);
					}
					setTela(tabpanel);
				} else if (id.equals("HFSMultiRadioButton")) {
					setSubTituloSistema("HFSMultiRadioButton usando puro GWT");
					if (demoMultiRadioButton == null) {
						demoMultiRadioButton = new DemoMultiRadioButton();
					}
					setTela(demoMultiRadioButton);
				} else if (id.equals("HFSMultiCheckBox")) {
					setSubTituloSistema("HFSMultiCheckBox usando puro GWT");
					if (demoMultiCheckBox == null) {
						demoMultiCheckBox = new DemoMultiCheckBox();
					}
					setTela(demoMultiCheckBox);
				} else if (id.equals("HFSCheckListBox")) {
					setSubTituloSistema("HFSCheckListBox usando puro GWT");
					if (demoCheckListBox == null) {
						demoCheckListBox = new DemoCheckListBox();
					}
					setTela(demoCheckListBox);
				} else if (id.equals("HFSAppendListBox")) {
					setSubTituloSistema("HFSAppendListBox usando puro GWT");
					if (appendListBox == null) {
						appendListBox = new HFSAppendListBox();
						appendListBox.setRotulo("Apelidos");
						appendListBox.setTamanho("150px", "150px");
					}
					setTela(appendListBox);
				} else if (id.equals("HFSAppendGrid")) {
					setSubTituloSistema("HFSAppendGrid usando puro GWT");
					if (demoAppendGrid == null) {
						demoAppendGrid = new DemoAppendGrid();
					}
					setTela(demoAppendGrid);
				} else if (id.equals("HFSDualList")) {
					setSubTituloSistema("HFSDualList usando puro GWT");
					if (dlista == null) {
						dlista = new HFSDualList(150, 120);

						ArrayList<HFSItem> ditems = new ArrayList<HFSItem>();
						ditems.add(new HFSItem("1", "lista item 1"));
						ditems.add(new HFSItem("2", "lista item 2"));
						ditems.add(new HFSItem("3", "lista item 3"));
						ditems.add(new HFSItem("4", "lista item 4"));
						ditems.add(new HFSItem("5", "lista item 5"));
						dlista.setItemsOrigem(ditems);
					}
					setTela(dlista);
				} else if (id.equals("HFSImageViewer")) {
					setSubTituloSistema("HFSImageViewer usando puro GWT");
					if (imageViewer == null) {
						imageViewer = new HFSImageViewer();
						imageViewer.setTamanho("250px", "250px");
						imageViewer.setImagem("imagem_teste.jpg");
					}
					setTela(imageViewer);
				} else if (id.equals("HFSLinkedListBox")) {
					setSubTituloSistema("HFSLinkedListBox usando puro GWT");
					if (demoLinkedListBox == null) {
						demoLinkedListBox = new DemoLinkedListBox();
					}
					setTela(demoLinkedListBox);
				} else if (id.equals("HFSTable")) {
					setSubTituloSistema("HFSTable usando puro GWT");
					if (tabela == null) {
						tabela = new HFSTable(true);
						tabela.addColunas(new String[] { "Código", "Descrição", "Ação" }, 5);
						ArrayList<String> linha1 = new ArrayList<String>();
						linha1.add("1"); linha1.add("valor1"); linha1.add("acao1");
						ArrayList<String> linha2 = new ArrayList<String>();
						linha2.add("2"); linha2.add("valor2"); linha2.add("acao2");
						ArrayList<String> linha3 = new ArrayList<String>();
						linha3.add("3"); linha3.add("valor3"); linha3.add("acao3");
						ArrayList<String> linha4 = new ArrayList<String>();
						linha4.add("4"); linha4.add("valor4"); linha4.add("acao4");
						tabela.addLinha(linha1);
						tabela.addLinha(linha2);
						tabela.addLinha(linha3);
						tabela.addLinha(linha4);
						tabela.selecionaLinha(1);
					}
					setTela(tabela);					
				} else if (id.equals("HFSDialogFileUpload")) {
					setSubTituloSistema("HFSDialogFileUpload usando a Apache Commons");
					if (dlgUpload == null) {
						dlgUpload = new HFSDialogFileUpload();

						btnUpload = new Button();
						btnUpload.setText("Mostrar");
						btnUpload.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								dlgUpload.center();
								dlgUpload.show();
							}
						});
					}
					setTela(btnUpload);
				} else if (id.equals("HFSDialogMessage")) {
					setSubTituloSistema("HFSDialogMessage usando puro GWT");
					if (demoDialogMessage == null) {
						demoDialogMessage = new DemoDialogMessage();
					}
					setTela(demoDialogMessage);
				} else if (id.equals("HFSProgressBar")) {
					setSubTituloSistema("HFSProgressBar usando puro GWT");
					if (demoProgressBar == null) {
						demoProgressBar = new DemoProgressBar();
					}
					setTela(demoProgressBar);
				} else if (id.equals("HFSLabelChronometer")) {
					setSubTituloSistema("HFSLabelChronometer usando puro GWT");
					if (demoCronometro == null) {
						demoCronometro = new DemoCronometro();
					}
					setTela(demoCronometro);
				} else if (id.equals("HFSSuggestBox")) {
					setSubTituloSistema("HFSSuggestBox usando puro GWT com RPC");
					if (demoSuggestBox == null) {
						demoSuggestBox = new DemoSuggestBox();
					}
					setTela(demoSuggestBox);
				} else if (id.equals("HFSThemeSelector")) {
					setSubTituloSistema("HFSThemeSelector usando puro GWT");
					if (tema == null) {
						tema = new HFSThemeSelector();
					}
					setTela(tema);
//				} else if (id.equals("HFSSlider")) {
//					setSubTituloSistema("HFSSlider usando puro GWT");
//					if (slider == null) {
//						slider = new DemoSlider();
//					}
//					setTela(slider);
				} else if (id.equals("HFSLogin")) {
					setSubTituloSistema("HFSLogin usando puro GWT com RPC");
					if (login == null) {
						login = new HFSLogin(false);
						login.addLoginHandler(new HFSLogin.LoginHandler() {
							@Override
							public boolean validarLogin(String login,
									String senha) {
								if (login.equals("02685748474")
										&& senha.equals("B5385CA"))
									return true;
								else
									return false;
							}

							@Override
							public boolean alterarSenha(String login,
									String senha, String novaSenha) {
								if (login.equals("02685748474")
										&& senha.equals("B5385CA"))
									return true;
								else
									return false;
							}

							@Override
							public void onLogin(boolean loginRealizado) {
								if (loginRealizado)
									Window
											.alert("login realizado com sucesso!");
								else
									Window.alert("login sem sucesso!");
							}

							@Override
							public void onMaximoTentativas(int maximoTentativas) {
								Window
										.alert("Login sem sucesso em "
												+ maximoTentativas
												+ " tentativas, você será desconectado!");
							}
						});
					}
					setTela(login);
				} else if (id.equals("HFSMenu")) {
					setSubTituloSistema("HFSMenu usando puro GWT com RPC");
					if (menu == null) {
						menu = new HFSMenu("400px", true, true,
								"HFS_GWT_COMPONENTES_DEMO");
						menu.addCarregarHandler(new HFSMenu.CarregarHandler() {
							@Override
							public void onCarregar(final MXEstrutura mx) {
								menu.getSubMenu(mx, "SIMPLES", "HFSNavigator")
										.setCommand(new Command() {
											@Override
											public void execute() {
												Window.alert(menu.getSubMenu(
														mx, "SIMPLES",
														"HFSNavigator")
														.getText());
											}
										});
							}
						});
					}
					setTela(menu);
				} else if (id.equals("HFSTree")) {
					setSubTituloSistema("HFSTree usando puro GWT com RPC");
					if (demoTree == null) {
						demoTree = new DemoTree();
					}
					setTela(demoTree);
				} else if (id.equals("HFSStringGrid")) {
					setSubTituloSistema("HFSStringGrid usando puro GWT com RPC");
					if (demoStringGrid == null) {
						demoStringGrid = new DemoStringGrid();
					}
					setTela(demoStringGrid);
				} else if (id.equals("HFSGrid")) {
					setSubTituloSistema("HFSGrid usando puro GWT com RPC");
					if (demoGrid == null) {
						demoGrid = new DemoGrid();
					}
					setTela(demoGrid);
				} else if (id.equals("HFSMap")) {
					setSubTituloSistema("HFSMap usando a Google Maps API");
					if (mapa == null) {
						mapa = new HFSMap(800, 500);
						mapa.mostrarMapa(-34.397, 150.644);
					}
					setTela(mapa);
				} else if (id.equals("HFSStaticMap")) {
					setSubTituloSistema("HFSStaticMap usando a Google Maps API");
					if (smapa == null) {
						smapa = new HFSStaticMap();
						smapa.mostrarMapa(512, 512, -8.0688, -34.8848, 14);
					}
					setTela(smapa);
				} else if (id.equals("HFSBarcode")) {
					setSubTituloSistema("HFSBarcode usando a Barcode4J API");
					if (demoBarcode == null) {
						demoBarcode = new DemoBarcode();
					}
					setTela(demoBarcode);
				} else if (id.equals("PieChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoPie == null) {
						ArrayList<PieChartDados> dados = new ArrayList<PieChartDados>();
						dados.add(new PieChartDados("A", new Integer(75)));
						dados.add(new PieChartDados("B", new Integer(10)));
						dados.add(new PieChartDados("C", new Integer(10)));
						dados.add(new PieChartDados("D", new Integer(5)));
						PieChartParams params = new PieChartParams(500, 300,
								"Distribuição HFS", true, true, false, false,
								dados);
						graficoPie = new HFSChart(params);
						graficoPie.mostrarGrafico();
					}
					setTela(graficoPie);
				} else if (id.equals("PieChart3D")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoPie3D == null) {
						ArrayList<PieChartDados> dados = new ArrayList<PieChartDados>();
						dados.add(new PieChartDados("A", new Integer(75)));
						dados.add(new PieChartDados("B", new Integer(10)));
						dados.add(new PieChartDados("C", new Integer(10)));
						dados.add(new PieChartDados("D", new Integer(5)));
						PieChartParams params = new PieChartParams(500, 300,
								"Distribuição HFS", true, true, false, true,
								dados);
						graficoPie3D = new HFSChart(params);
						graficoPie3D.mostrarGrafico();
					}
					setTela(graficoPie3D);
				} else if (id.equals("XYLineChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoXYLine == null) {
						ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
						dados1.add(new XYDados(1, 1));
						dados1.add(new XYDados(1, 2));
						dados1.add(new XYDados(2, 1));
						dados1.add(new XYDados(3, 9));
						dados1.add(new XYDados(4, 10));

						ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
						dados2.add(new XYDados(3, 6));
						dados2.add(new XYDados(8, 9));
						dados2.add(new XYDados(7, 10));
						dados2.add(new XYDados(8, 1));
						dados2.add(new XYDados(9, 4));

						ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
						series.add(new XYSeriesParams("População1", dados1));
						series.add(new XYSeriesParams("População2", dados2));

						XYLineChartParams params = new XYLineChartParams(500, 300,
								"Gráfico XY Linha", true, true, false, "x-eixo", "y-eixo",
								true, series);
						graficoXYLine = new HFSChart(params);
						graficoXYLine.mostrarGrafico();
					}
					setTela(graficoXYLine);
				} else if (id.equals("LineChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoLine == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						LineChartParams params = new LineChartParams(500, 300,
								"Comparação entre vendedor", false, true,
								false, "Vendedor", "Lucro", true, dados, false);
						graficoLine = new HFSChart(params);
						graficoLine.mostrarGrafico();
					}
					setTela(graficoLine);
				} else if (id.equals("LineChart3D")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoLine3D == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						LineChartParams params = new LineChartParams(500, 300,
								"Comparação entre vendedor", false, true,
								false, "Vendedor", "Lucro", true, dados, true);

						graficoLine3D = new HFSChart(params);
						graficoLine3D.mostrarGrafico();
					}
					setTela(graficoLine3D);
				} else if (id.equals("XYAreaChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoXYArea == null) {
						ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
						dados1.add(new XYDados(1, 1));
						dados1.add(new XYDados(1, 2));
						dados1.add(new XYDados(2, 1));
						dados1.add(new XYDados(3, 9));
						dados1.add(new XYDados(4, 10));

						ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
						dados2.add(new XYDados(3, 6));
						dados2.add(new XYDados(8, 9));
						dados2.add(new XYDados(7, 10));
						dados2.add(new XYDados(8, 1));
						dados2.add(new XYDados(9, 4));

						ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
						series.add(new XYSeriesParams("População1", dados1));
						series.add(new XYSeriesParams("População2", dados2));

						XYAreaChartParams params = new XYAreaChartParams(500, 300,
								"Gráfico XY Área", true, true, false, "x-eixo", "y-eixo", true,
								series);
						graficoXYArea = new HFSChart(params);
						graficoXYArea.mostrarGrafico();
					}
					setTela(graficoXYArea);
				} else if (id.equals("XYStepChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoXYStep == null) {
						ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
						dados1.add(new XYDados(1, 1));
						dados1.add(new XYDados(1, 2));
						dados1.add(new XYDados(2, 1));
						dados1.add(new XYDados(3, 9));
						dados1.add(new XYDados(4, 10));

						ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
						dados2.add(new XYDados(3, 6));
						dados2.add(new XYDados(8, 9));
						dados2.add(new XYDados(7, 10));
						dados2.add(new XYDados(8, 1));
						dados2.add(new XYDados(9, 4));

						ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
						series.add(new XYSeriesParams("População1", dados1));
						series.add(new XYSeriesParams("População2", dados2));

						XYStepChartParams params = new XYStepChartParams(500, 300,
								"Gráfico XY Step", true, true, false, "x-eixo", "y-eixo", true,
								series);
						graficoXYStep = new HFSChart(params);
						graficoXYStep.mostrarGrafico();
					}
					setTela(graficoXYStep);
				} else if (id.equals("XYStepAreaChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoXYStepArea == null) {
						ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
						dados1.add(new XYDados(1, 1));
						dados1.add(new XYDados(1, 2));
						dados1.add(new XYDados(2, 1));
						dados1.add(new XYDados(3, 9));
						dados1.add(new XYDados(4, 10));

						ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
						dados2.add(new XYDados(3, 6));
						dados2.add(new XYDados(8, 9));
						dados2.add(new XYDados(7, 10));
						dados2.add(new XYDados(8, 1));
						dados2.add(new XYDados(9, 4));

						ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
						series.add(new XYSeriesParams("População1", dados1));
						series.add(new XYSeriesParams("População2", dados2));

						XYStepAreaChartParams params = new XYStepAreaChartParams(500, 300,
								"Gráfico XY Step Área", true, true, false, "x-eixo", "y-eixo",
								true, series);
						graficoXYStepArea = new HFSChart(params);
						graficoXYStepArea.mostrarGrafico();
					}
					setTela(graficoXYStepArea);
				} else if (id.equals("BarChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoBar == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						BarChartParams params = new BarChartParams(500, 300,
								"Comparação entre vendedor", true, true, false,
								"Vendedor", "Lucro", true, dados, false);

						graficoBar = new HFSChart(params);
						graficoBar.mostrarGrafico();
					}
					setTela(graficoBar);
				} else if (id.equals("BarChart3D")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoBar3D == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						BarChartParams params = new BarChartParams(500, 300,
								"Comparação entre vendedor", true, true, false,
								"Vendedor", "Valor ($)", true, dados, true);
						graficoBar3D = new HFSChart(params);
						graficoBar3D.mostrarGrafico();
					}
					setTela(graficoBar3D);
				} else if (id.equals("TimeSeriesChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoTimeSeries == null) {
						ArrayList<SeriesDados> dados1 = new ArrayList<SeriesDados>();
						dados1.add(new SeriesDados(10, 1, 2004, 100));
						dados1.add(new SeriesDados(10, 2, 2004, 150));
						dados1.add(new SeriesDados(10, 3, 2004, 250));
						dados1.add(new SeriesDados(10, 4, 2004, 275));
						dados1.add(new SeriesDados(10, 5, 2004, 325));
						dados1.add(new SeriesDados(10, 6, 2004, 425));

						ArrayList<SeriesDados> dados2 = new ArrayList<SeriesDados>();
						dados2.add(new SeriesDados(20, 1, 2004, 200));
						dados2.add(new SeriesDados(20, 2, 2004, 250));
						dados2.add(new SeriesDados(20, 3, 2004, 450));
						dados2.add(new SeriesDados(20, 4, 2004, 475));
						dados2.add(new SeriesDados(20, 5, 2004, 125));

						ArrayList<TimeSeriesChartSerie> series = new ArrayList<TimeSeriesChartSerie>();
						series.add(new TimeSeriesChartSerie("População1",
								dados1));
						series.add(new TimeSeriesChartSerie("População2",
								dados2));

						TimeSeriesChartParams params = new TimeSeriesChartParams(
								500, 300, "População da Cidade do Recife",
								true, true, false, "Data", "População", series);
						graficoTimeSeries = new HFSChart(params);
						graficoTimeSeries.mostrarGrafico();
					}
					setTela(graficoTimeSeries);
				} else if (id.equals("AreaChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoArea == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						AreaChartParams params = new AreaChartParams(500, 300,
								"Comparação entre vendedor", false, true,
								false, "Vendedor", "Lucro", true, dados);
						graficoArea = new HFSChart(params);
						graficoArea.mostrarGrafico();
					}
					setTela(graficoArea);
				} else if (id.equals("StackedBarChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoStackedBar == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						StackedBarChartParams params = new StackedBarChartParams(
								500, 300, "Comparação entre vendedor", true,
								true, false, "Vendedor", "Lucro", true, dados,
								false);
						graficoStackedBar = new HFSChart(params);
						graficoStackedBar.mostrarGrafico();
					}
					setTela(graficoStackedBar);
				} else if (id.equals("StackedBarChart3D")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoStackedBar3D == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						StackedBarChartParams params = new StackedBarChartParams(
								500, 300, "Comparação entre vendedor", true,
								true, false, "Vendedor", "Valor ($)", true,
								dados, true);

						graficoStackedBar3D = new HFSChart(params);
						graficoStackedBar3D.mostrarGrafico();
					}
					setTela(graficoStackedBar3D);					
				} else if (id.equals("XYBarChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoXYBar == null) {						
						ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
						series.add(new XYSeriesParamsDouble("População1", mdados1));
						series.add(new XYSeriesParamsDouble("População2", mdados2));

						XYBarChartParams params = new XYBarChartParams(500, 300,
								"Gráfico XY Barra", true, true, false, "x-eixo", "y-eixo",
								true, series, false);
						graficoXYBar = new HFSChart(params);
						graficoXYBar.mostrarGrafico();
					}
					setTela(graficoXYBar);
				} else if (id.equals("HistogramChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoHistogram == null) {
						ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
						series.add(new XYSeriesParamsDouble("População1", mdados1));
						series.add(new XYSeriesParamsDouble("População2", mdados2));

						HistogramChartParams params = new HistogramChartParams(500, 300,
								"Gráfico XY Histograma", true, true, false, "x-eixo", "y-eixo",
								true, series);
						graficoHistogram = new HFSChart(params);
						graficoHistogram.mostrarGrafico();
					}
					setTela(graficoHistogram);
				} else if (id.equals("BubbleChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoBubble == null) {
						double[][] mdados1 = new double[3][6];
						mdados1[0][0] = 1;
						mdados1[0][1] = 2;
						mdados1[0][2] = 3;
						mdados1[0][3] = 4;
						mdados1[0][4] = 5;
						mdados1[0][5] = 6;
						
						mdados1[1][0] = 7;
						mdados1[1][1] = 8;
						mdados1[1][2] = 9;
						mdados1[1][3] = 10;
						mdados1[1][4] = 11;
						mdados1[1][5] = 12;

						mdados1[2][0] = 27;
						mdados1[2][1] = 28;
						mdados1[2][2] = 29;
						mdados1[2][3] = 20;
						mdados1[2][4] = 21;
						mdados1[2][5] = 22;
						
						double[][] mdados2 = new double[3][6];
						mdados2[0][0] = 13;
						mdados2[0][1] = 25;
						mdados2[0][2] = 36;
						mdados2[0][3] = 47;
						mdados2[0][4] = 58;
						mdados2[0][5] = 69;
						
						mdados2[1][0] = 75;
						mdados2[1][1] = 84;
						mdados2[1][2] = 93;
						mdados2[1][3] = 15;
						mdados2[1][4] = 16;
						mdados2[1][5] = 62;

						mdados2[2][0] = 27;
						mdados2[2][1] = 58;
						mdados2[2][2] = 39;
						mdados2[2][3] = 40;
						mdados2[2][4] = 11;
						mdados2[2][5] = 62;
						
						
						ArrayList<XYSeriesParamsDouble> series = new ArrayList<XYSeriesParamsDouble>();
						series.add(new XYSeriesParamsDouble("População1", mdados1));
						series.add(new XYSeriesParamsDouble("População2", mdados2));

						BubbleChartParams params = new BubbleChartParams(500, 300,
								"Gráfico XY Bolha", true, true, false, "x-eixo", "y-eixo",
								true, series);
						graficoBubble = new HFSChart(params);
						graficoBubble.mostrarGrafico();
					}
					setTela(graficoBubble);										
				} else if (id.equals("BoxAndWhiskerChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoBoxAndWhisker == null) {
						List<BoxAndWhiskerChartDados> dados = new ArrayList<BoxAndWhiskerChartDados>();
						List<Double> anexos = new ArrayList<Double>();
						anexos.add(new Double(34));
						anexos.add(new Double(24));
						anexos.add(new Double(14));
						anexos.add(new Double(6));
						anexos.add(new Double(50));
						dados.add(new BoxAndWhiskerChartDados(100, 25, 10, 8, 5, 30, 6, 80,
								anexos));

						BoxAndWhiskerChartParams params = new BoxAndWhiskerChartParams(500,
								300, "Comparação entre vendedor", true, "Vendedor", "Lucro",
								"ChaveLinha", "ChaveColuna", dados);
						graficoBoxAndWhisker = new HFSChart(params);
						graficoBoxAndWhisker.mostrarGrafico();
					}
					setTela(graficoBoxAndWhisker);
				} else if (id.equals("StackedAreaChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoStackedArea == null) {
						ArrayList<CategoryDados> dados = new ArrayList<CategoryDados>();
						dados.add(new CategoryDados(6, "Lucro1", "Carlos"));
						dados.add(new CategoryDados(3, "Lucro2", "Carlos"));
						dados.add(new CategoryDados(7, "Lucro1", "Raul"));
						dados.add(new CategoryDados(10, "Lucro2", "Raul"));
						dados.add(new CategoryDados(8, "Lucro1", "Luiz"));
						dados.add(new CategoryDados(8, "Lucro2", "Luiz"));
						dados.add(new CategoryDados(5, "Lucro1", "Rafael"));
						dados.add(new CategoryDados(6, "Lucro2", "Rafael"));
						dados.add(new CategoryDados(12, "Lucro1", "Márcio"));
						dados.add(new CategoryDados(5, "Lucro2", "Márcio"));

						StackedAreaChartParams params = new StackedAreaChartParams(500, 300,
								"Comparação entre vendedor", true, true, false, "Vendedor",
								"Lucro", true, dados);
						graficoStackedArea = new HFSChart(params);
						graficoStackedArea.mostrarGrafico();
					}
					setTela(graficoStackedArea);
				} else if (id.equals("ScatterChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoScatter == null) {
						ArrayList<XYDados> dados1 = new ArrayList<XYDados>();
						dados1.add(new XYDados(1, 1));
						dados1.add(new XYDados(1, 2));
						dados1.add(new XYDados(2, 1));
						dados1.add(new XYDados(3, 9));
						dados1.add(new XYDados(4, 10));

						ArrayList<XYDados> dados2 = new ArrayList<XYDados>();
						dados2.add(new XYDados(3, 6));
						dados2.add(new XYDados(8, 9));
						dados2.add(new XYDados(7, 10));
						dados2.add(new XYDados(8, 1));
						dados2.add(new XYDados(9, 4));

						ArrayList<XYSeriesParams> series = new ArrayList<XYSeriesParams>();
						series.add(new XYSeriesParams("População1", dados1));
						series.add(new XYSeriesParams("População2", dados2));

						ScatterChartParams params = new ScatterChartParams(500, 300,
								"Gráfico Scatter", true, true, false, "x-eixo", "y-eixo",
								true, series);
						graficoScatter = new HFSChart(params);
						graficoScatter.mostrarGrafico();
					}
					setTela(graficoScatter);
				} else if (id.equals("HighLowChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoHighLow == null) {
						ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
						dados1.add(new OHLCSeriesDados(10, 1, 2000, 100, 200, 150, 300));
						dados1.add(new OHLCSeriesDados(10, 2, 2000, 150, 50, 200, 140));
						dados1.add(new OHLCSeriesDados(10, 3, 2000, 250, 100, 80, 93));
						dados1.add(new OHLCSeriesDados(10, 4, 2000, 275, 230, 280, 168));
						dados1.add(new OHLCSeriesDados(10, 5, 2000, 325, 245, 123, 52));
						dados1.add(new OHLCSeriesDados(10, 6, 2000, 425, 45, 98, 195));

						ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
						dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
						dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
						dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
						dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
						dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

						ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
						series.add(new OHLCSeriesParams("População1", dados1));
						series.add(new OHLCSeriesParams("População2", dados2));

						HighLowChartParams params = new HighLowChartParams(500, 300,
								"População da Cidade do Recife", true, true, false, "Data",
								"População", "Chave", series);
						graficoHighLow = new HFSChart(params);
						graficoHighLow.mostrarGrafico();
					}
					setTela(graficoHighLow);
				} else if (id.equals("CandlestickChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoCandlestick == null) {
						ArrayList<OHLCSeriesDados> dados1 = new ArrayList<OHLCSeriesDados>();
						dados1.add(new OHLCSeriesDados(10, 1, 2004, 100, 200, 150, 300));
						dados1.add(new OHLCSeriesDados(10, 2, 2004, 150, 50, 200, 140));
						dados1.add(new OHLCSeriesDados(10, 3, 2004, 250, 100, 80, 93));
						dados1.add(new OHLCSeriesDados(10, 4, 2004, 275, 230, 280, 168));
						dados1.add(new OHLCSeriesDados(10, 5, 2004, 325, 245, 123, 52));
						dados1.add(new OHLCSeriesDados(10, 6, 2004, 425, 45, 98, 195));

						ArrayList<OHLCSeriesDados> dados2 = new ArrayList<OHLCSeriesDados>();
						dados2.add(new OHLCSeriesDados(20, 1, 2004, 200, 168, 201, 91));
						dados2.add(new OHLCSeriesDados(20, 2, 2004, 250, 83, 99, 134));
						dados2.add(new OHLCSeriesDados(20, 3, 2004, 450, 187, 321, 123));
						dados2.add(new OHLCSeriesDados(20, 4, 2004, 475, 92, 181, 123));
						dados2.add(new OHLCSeriesDados(20, 5, 2004, 125, 345, 214, 23));

						ArrayList<OHLCSeriesParams> series = new ArrayList<OHLCSeriesParams>();
						series.add(new OHLCSeriesParams("População1", dados1));
						series.add(new OHLCSeriesParams("População2", dados2));

						CandlestickChartParams params = new CandlestickChartParams(500, 300,
								"População da Cidade do Recife", true, true, false, "Data",
								"População", "Chave", series);
						graficoCandlestick = new HFSChart(params);
						graficoCandlestick.mostrarGrafico();
					}
					setTela(graficoCandlestick);
				} else if (id.equals("GanttChart")) {
					setSubTituloSistema("HFSChart usando a JFreeChart API");
					if (graficoGantt == null) {
						Number[] partidas1 = new Number[6];
						partidas1[0] = 100;
						partidas1[1] = 150;
						partidas1[2] = 250;
						partidas1[3] = 275;
						partidas1[4] = 325;
						partidas1[5] = 425;

						Number[] chegadas1 = new Number[6];
						chegadas1[0] = 200;
						chegadas1[1] = 250;
						chegadas1[2] = 450;
						chegadas1[3] = 475;
						chegadas1[4] = 125;
						chegadas1[5] = 305;

						Number[] chegadas2 = new Number[6];
						chegadas2[0] = 100;
						chegadas2[1] = 150;
						chegadas2[2] = 250;
						chegadas2[3] = 275;
						chegadas2[4] = 325;
						chegadas2[5] = 425;

						Number[] partidas2 = new Number[6];
						partidas2[0] = 200;
						partidas2[1] = 250;
						partidas2[2] = 450;
						partidas2[3] = 475;
						partidas2[4] = 125;
						partidas2[5] = 305;

						GanttChartDados[] series = new GanttChartDados[2];
						series[0] = new GanttChartDados("População1", partidas1, chegadas1);
						series[1] = new GanttChartDados("População2", partidas2, chegadas2);

						GanttChartParams params = new GanttChartParams(500, 300,
								"Comparação entre vendedor", true, true, false, "Vendedor",
								"Lucro", series);
						graficoGantt = new HFSChart(params);
						graficoGantt.mostrarGrafico();
					}
					setTela(graficoGantt);
				} else if (id.equals("HFSReport")) {
					setSubTituloSistema("HFSReport usando a JasperReports API");
					if (rel == null) {
						rel = new HFSReport((getLarguraConteudo()-25)+"px",
								(getAlturaConteudo()-25)+"px", "relPessoa",
								HFSReport.OpcaoGeracao.PADRAO,
								HFSReport.FormatoArquivo.PDF, true);
						rel.mostrarRelatorio();
					}
					setTela(rel);
				} else if (id.equals("HFSReportSaveBar")) {
					setSubTituloSistema("HFSReportSaveBar usando a JasperReports API");
					if (saveBar == null) {
						saveBar = new HFSReportSaveBar("relPessoa");
					}
					setTela(saveBar);
				} else if (id.equals("HFSFTPViewer")) {
					setSubTituloSistema("HFSFTPViewer usando a Apache Commons API");
					if (ftpviewer == null) {
						ftpviewer = new HFSFTPViewer("600px", "340px",
								new String[] { "200.238.112.36" });
						ftpviewer.setLogin("ftpadm");
						ftpviewer.setSenha("ftp$adm!");
					}
					setTela(ftpviewer);
				} else if (id.equals("HFSRichText")) {
					if (rico == null) {
						rico = new HFSRichText();
					}
					setTela(rico);
				} else if (id.equals("HFSDialogEmail")) {
					setSubTituloSistema("HFSDialogEmail usando a Apache Commons API");
					if (dlgEmail == null) {
						dlgEmail = new HFSDialogEmail("smtp.sds.pe.gov.br",
								"Henrique F. Souza",
								"henrique.souza@sds.pe.gov.br");

						btnEmail = new Button();
						btnEmail.setText("Mostrar");
						btnEmail.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								dlgEmail.center();
								dlgEmail.show();
								dlgEmail
										.setEmailDestino("henrique.souza@sds.pe.gov.br");
								dlgEmail.setAssunto("Teste HFSEmail");
								dlgEmail.setMensagem("mensagem do HFSEmail!");
							}
						});
					}
					setTela(btnEmail);
				} else if (id.equals("HFSDialogChat")) {
					setSubTituloSistema("HFSDialogChat usando a Smack XMPP API");
					if (demoDialogChat == null) {
						demoDialogChat = new DemoDialogChat();
					}
					setTela(demoDialogChat);
				} else if (id.equals("HFSEndereco")) {
					setSubTituloSistema("HFSEndereco usando puro GWT com RPC");
					if (endereco == null) {
						endereco = new HFSEndereco();
					}
					setTela(endereco);
				} else if (id.equals("HFSCadastro")) {
					setSubTituloSistema("HFSCadastro usando puro GWT com RPC");
					if (cadastro == null) {
						cadastro = new HFSCadastro();
					}
					setTela(cadastro);
				} else if (id.equals("HFSFlowPlayer")) {
					setSubTituloSistema("HFSFlowPlayer usando o componente FlowPlayer");
					if (demoFlowPlayer == null) {
						demoFlowPlayer = new DemoFlowPlayer();
					}
					setTela(demoFlowPlayer);
				} else if (id.equals("HFSScanner")) {
					setSubTituloSistema("HFSScanner usando a EZTW32.dll [SOMENTE FUNCIONA NO WINDOWS]");
					if (scanner == null) {
						scanner = new HFSScanner(600, 400);
					}
					setTela(scanner);
				} else if (id.equals("HFSSiadmTree")) {
					setSubTituloSistema("HFSSiadmTree usando puro GWT com RPC");
					if (siadmTree == null) {
						siadmTree = new HFSSiadmTree("500px", "500px", true,
								false, true);
					}
					setTela(siadmTree);
				}

			}
		});

		this.addAppClickHandler(new AppClickHandler() {
			@Override
			public void onSobreClick() {
				String htmlInfo = "HFSApplication - HFS GWT Demonstração<br>"
						+ "Desenvolvido em GWT - JAVA, Versão: 1.0.0<br>"
						+ "Por Henrique Figueiredo de Souza [O EXPERT]<br>"
						+ "Todos os direitos reservados 2010";
				HFSSobre.mostrar(img.aplicacao(), htmlInfo);
			}

			@Override
			public void onDesconectarClick() {
				Window.alert("Isto ainda não foi implementado! BOBÃO");
			}
		});

	}

}
