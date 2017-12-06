package com.hfsgwt.client.componentes;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HFSCalculator extends Composite {
	private VerticalPanel verticalPanel;
//	private HFSTextBox edtPainel;
//	private HFSTextBox edtPainelFormatado;
	private Label edtPainel;
	private Label edtPainelFormatado;
	private Grid grid;
	private Button btnC;
	private Button btnMemoryRead;
	private Button btnMemorySub;
	private Button btnMemoryAdd;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btnDivide;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btnMultiplica;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btnSubtracao;
	private Button btn0;
	private Button btnPonto;
	private Button btnIgual;
	private Button btnSoma;

	private String sText1, sText2;
	private double dReg1, dReg2, dMem;
	private String sOperator;
	private boolean isFixReg;

	private boolean decimalComVirgula;

	public enum BotaoTamanho {
		GRANDE, PEQUENO
	}

	private BotaoTamanho btamanho;

	public interface BtnClickHandler extends EventHandler {
		void onBtnIgualClick();

		void onBotoesClick(double valor, String valorFormatado);
	}

	private BtnClickHandler btnClickHandler;

	public HFSCalculator(BotaoTamanho botaoTamanho) {
		this.decimalComVirgula = true;
		this.btamanho = botaoTamanho;
		initComponents();

		dReg1 = 0.0d;
		dReg2 = 0.0d;
		dMem = 0.0d;
		sOperator = "";
		isFixReg = true;
	}

	private void initComponents() {
		initWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(5);
			verticalPanel.setBorderWidth(1);
			verticalPanel.add(getEdtPainelFormatado());
			verticalPanel.add(getGrid());
		}
		return verticalPanel;
	}

	private Label getEdtPainel() {
		if (edtPainel == null) {
			edtPainel = new Label("0");
			edtPainel.setWidth("100%");
			edtPainel.setWordWrap(false);
			edtPainel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		}
		return edtPainel;
	}

	private Label getEdtPainelFormatado() {
		if (edtPainelFormatado == null) {
			edtPainelFormatado = new Label("0");
			edtPainelFormatado.setWidth("100%");
			edtPainelFormatado.setWordWrap(false);
			edtPainelFormatado.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		}
		return edtPainelFormatado;
	}

//	private HFSTextBox getEdtPainel() {
//	if (edtPainel == null) {
//		edtPainel = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA, "",
//				HFSTextBox.Formato.DECIMAL, 15, 22, false);
//		edtPainel.setMostrarRotulo(false);
//		edtPainel.setWidth("100%");
//		edtPainel.setDecimal(0);
//	}
//	return edtPainel;
//}
//
//private HFSTextBox getEdtPainelFormatado() {
//	if (edtPainelFormatado == null) {
//		edtPainelFormatado = new HFSTextBox(HFSTextBox.PosicaoRotulo.ACIMA,
//				"", HFSTextBox.Formato.DECIMAL, 15, 22, false);
//		edtPainelFormatado.setMostrarRotulo(false);
//		edtPainelFormatado.setWidth("100%");
//		edtPainelFormatado.setDecimal(0);
//		edtPainelFormatado.setFocado(true);
//		edtPainelFormatado.addKeyDownHandler(new KeyDownHandler() {
//			public void onKeyDown(KeyDownEvent event) {
//				clickBotaoPorTecla(event.getNativeKeyCode());
//			}
//		});
//
//	}
//	return edtPainelFormatado;
//}
	
	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(5, 4);
			grid.setWidget(0, 0, getBtnC());
			grid.setWidget(0, 1, getBtnMemoryRead());
			grid.setWidget(0, 2, getBtnMemorySub());
			grid.setWidget(0, 3, getBtnMemoryAdd());
			grid.setWidget(1, 0, getBtn7());
			grid.setWidget(1, 1, getBtn8());
			grid.setWidget(1, 2, getBtn9());
			grid.setWidget(1, 3, getBtnDivide());
			grid.setWidget(2, 0, getBtn4());
			grid.setWidget(2, 1, getBtn5());
			grid.setWidget(2, 2, getBtn6());
			grid.setWidget(2, 3, getBtnMultiplica());
			grid.setWidget(3, 0, getBtn1());
			grid.setWidget(3, 1, getBtn2());
			grid.setWidget(3, 2, getBtn3());
			grid.setWidget(3, 3, getBtnSubtracao());
			grid.setWidget(4, 0, getBtn0());
			grid.setWidget(4, 1, getBtnPonto());
			grid.setWidget(4, 2, getBtnIgual());
			grid.setWidget(4, 3, getBtnSoma());
		}
		return grid;
	}

	private Button getBtnC() {
		if (btnC == null) {
			btnC = new Button("<b>C<b>");
			btnC.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("C");
				}
			});

			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnC.setSize("35px", "26px");
			else
				btnC.setSize("44px", "28px");
		}
		return btnC;
	}

	private Button getBtnMemoryRead() {
		if (btnMemoryRead == null) {
			btnMemoryRead = new Button("<b>MR<b>");
			btnMemoryRead.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("MR");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnMemoryRead.setSize("35px", "26px");
			else
				btnMemoryRead.setSize("44px", "28px");
		}
		return btnMemoryRead;
	}

	private Button getBtnMemorySub() {
		if (btnMemorySub == null) {
			btnMemorySub = new Button("<b>M-<b>");
			btnMemorySub.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("M-");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnMemorySub.setSize("35px", "26px");
			else
				btnMemorySub.setSize("44px", "28px");
		}
		return btnMemorySub;
	}

	private Button getBtnMemoryAdd() {
		if (btnMemoryAdd == null) {
			btnMemoryAdd = new Button("<b>M+<b>");
			btnMemoryAdd.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("M+");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnMemoryAdd.setSize("35px", "26px");
			else
				btnMemoryAdd.setSize("44px", "28px");
		}
		return btnMemoryAdd;
	}

	private Button getBtn7() {
		if (btn7 == null) {
			btn7 = new Button("<b>7<b>");
			btn7.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("7");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn7.setSize("35px", "26px");
			else
				btn7.setSize("44px", "28px");
		}
		return btn7;
	}

	private Button getBtn8() {
		if (btn8 == null) {
			btn8 = new Button("<b>8<b>");
			btn8.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("8");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn8.setSize("35px", "26px");
			else
				btn8.setSize("44px", "28px");
		}
		return btn8;
	}

	private Button getBtn9() {
		if (btn9 == null) {
			btn9 = new Button("<b>9<b>");
			btn9.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("9");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn9.setSize("35px", "26px");
			else
				btn9.setSize("44px", "28px");
		}
		return btn9;
	}

	private Button getBtnDivide() {
		if (btnDivide == null) {
			btnDivide = new Button("<b>/<b>");
			btnDivide.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("/");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnDivide.setSize("35px", "26px");
			else
				btnDivide.setSize("44px", "28px");
		}
		return btnDivide;
	}

	private Button getBtn4() {
		if (btn4 == null) {
			btn4 = new Button("<b>4<b>");
			btn4.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("4");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn4.setSize("35px", "26px");
			else
				btn4.setSize("44px", "28px");
		}
		return btn4;
	}

	private Button getBtn5() {
		if (btn5 == null) {
			btn5 = new Button("<b>5<b>");
			btn5.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("5");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn5.setSize("35px", "26px");
			else
				btn5.setSize("44px", "28px");
		}
		return btn5;
	}

	private Button getBtn6() {
		if (btn6 == null) {
			btn6 = new Button("<b>6<b>");
			btn6.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("6");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn6.setSize("35px", "26px");
			else
				btn6.setSize("44px", "28px");
		}
		return btn6;
	}

	private Button getBtnMultiplica() {
		if (btnMultiplica == null) {
			btnMultiplica = new Button("<b>*<b>");
			btnMultiplica.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("*");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnMultiplica.setSize("35px", "26px");
			else
				btnMultiplica.setSize("44px", "28px");
		}
		return btnMultiplica;
	}

	private Button getBtn1() {
		if (btn1 == null) {
			btn1 = new Button("<b>1<b>");
			btn1.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("1");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn1.setSize("35px", "26px");
			else
				btn1.setSize("44px", "28px");
		}
		return btn1;
	}

	private Button getBtn2() {
		if (btn2 == null) {
			btn2 = new Button("<b>2<b>");
			btn2.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("2");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn2.setSize("35px", "26px");
			else
				btn2.setSize("44px", "28px");
		}
		return btn2;
	}

	private Button getBtn3() {
		if (btn3 == null) {
			btn3 = new Button("<b>3<b>");
			btn3.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("3");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn3.setSize("35px", "26px");
			else
				btn3.setSize("44px", "28px");
		}
		return btn3;
	}

	private Button getBtnSubtracao() {
		if (btnSubtracao == null) {
			btnSubtracao = new Button("<b>-<b>");
			btnSubtracao.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("-");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnSubtracao.setSize("35px", "26px");
			else
				btnSubtracao.setSize("44px", "28px");
		}
		return btnSubtracao;
	}

	private Button getBtn0() {
		if (btn0 == null) {
			btn0 = new Button("<b>0<b>");
			btn0.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("0");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btn0.setSize("35px", "26px");
			else
				btn0.setSize("44px", "28px");
		}
		return btn0;
	}

	private Button getBtnPonto() {
		if (btnPonto == null) {
			if (decimalComVirgula)
				btnPonto = new Button("<b>,<b>");
			else
				btnPonto = new Button("<b>.<b>");
			btnPonto.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao(".");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnPonto.setSize("35px", "26px");
			else
				btnPonto.setSize("44px", "28px");
		}
		return btnPonto;
	}

	private Button getBtnIgual() {
		if (btnIgual == null) {
			btnIgual = new Button("<b>=<b>");
			btnIgual.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("=");
					if (btnClickHandler != null) {
						btnClickHandler.onBtnIgualClick();
					}
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnIgual.setSize("35px", "26px");
			else
				btnIgual.setSize("44px", "28px");
		}
		return btnIgual;
	}

	private Button getBtnSoma() {
		if (btnSoma == null) {
			btnSoma = new Button("<b>+<b>");
			btnSoma.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					acao("+");
				}
			});
			if (this.btamanho == BotaoTamanho.PEQUENO)
				btnSoma.setSize("35px", "26px");
			else
				btnSoma.setSize("44px", "28px");
		}
		return btnSoma;
	}

	private void acao(String tecla) {
		//
		// tecla numerica
		//
		if ("C".equals(tecla)) {
			dReg1 = 0.0d;
			dReg2 = 0.0d;
			sOperator = "";
			this.getEdtPainel().setText("0");			
			isFixReg = true;
		} else if (("0".equals(tecla)) | ("1".equals(tecla))
				| ("2".equals(tecla)) | ("3".equals(tecla))
				| ("4".equals(tecla)) | ("5".equals(tecla))
				| ("6".equals(tecla)) | ("7".equals(tecla))
				| ("8".equals(tecla)) | ("9".equals(tecla))
				| (".".equals(tecla))) {
			if (isFixReg)
				sText2 = (String) tecla;
			else
				sText2 = this.getEdtPainel().getText() + tecla;
			this.getEdtPainel().setText(sText2);
			isFixReg = false;
		}
		//
		// operacoes
		//
		else if (("+".equals(tecla)) | ("-".equals(tecla))
				| ("*".equals(tecla)) | ("/".equals(tecla))
				| ("=".equals(tecla))) {
			sText1 = this.getEdtPainel().getText();
			dReg2 = (Double.valueOf(sText1)).doubleValue();
			dReg1 = realizarCalculo(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			sText2 = dTemp.toString();
			this.getEdtPainel().setText(sText2);
			sOperator = (String) tecla;
			isFixReg = true;
		}
		//
		// operacao memory read
		//
		else if ("MR".equals(tecla)) {
			Double dTemp = new Double(dMem);
			sText2 = dTemp.toString();
			this.getEdtPainel().setText(sText2);
			sOperator = "";
			isFixReg = true;
		}
		//
		// operacao memory add
		//
		else if ("M+".equals(tecla)) {
			sText1 = this.getEdtPainel().getText();
			dReg2 = (Double.valueOf(sText1)).doubleValue();
			dReg1 = realizarCalculo(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			sText2 = dTemp.toString();
			this.getEdtPainel().setText(sText2);
			dMem = dMem + dReg1;
			sOperator = "";
			isFixReg = true;
		}
		//
		// operacao memory sub
		//
		else if ("M-".equals(tecla)) {
			sText1 = this.getEdtPainel().getText();
			dReg2 = (Double.valueOf(sText1)).doubleValue();
			dReg1 = realizarCalculo(sOperator, dReg1, dReg2);
			Double dTemp = new Double(dReg1);
			sText2 = dTemp.toString();
			this.getEdtPainel().setText(sText2);
			dMem = dMem - dReg1;
			sOperator = "";
			isFixReg = true;
		}

		formataNumero(this.getEdtPainel().getText(), NumberFormat
				.getDecimalFormat());
	}

	private boolean formataNumero(String sValor, NumberFormat numerofmt) {
		double nvalor = 0;
		String valorFormatado = "";
		try {
			if (this.decimalComVirgula) {
				nvalor = Double.parseDouble(sValor.replace(",", "."));
				valorFormatado = numerofmt.format(nvalor).replace(".", ","); 
				this.getEdtPainelFormatado().setText(valorFormatado);
				
				if (btnClickHandler != null) {
					nvalor = Double.parseDouble(valorFormatado.replace(",", "."));
					btnClickHandler.onBotoesClick(nvalor, valorFormatado);
				}			
				
			} else {
				nvalor = Double.parseDouble(sValor);
				valorFormatado = numerofmt.format(nvalor);
				this.getEdtPainelFormatado().setText(valorFormatado);
				
				if (btnClickHandler != null) {
					btnClickHandler.onBotoesClick(nvalor, valorFormatado);
				}			
				
			}
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// -------------
	// Calculo
	// -------------
	private double realizarCalculo(String sOperator, double dReg1, double dReg2) {
		if ("+".equals(sOperator))
			dReg1 = dReg1 + dReg2;
		else if ("-".equals(sOperator))
			dReg1 = dReg1 - dReg2;
		else if ("*".equals(sOperator))
			dReg1 = dReg1 * dReg2;
		else if ("/".equals(sOperator))
			dReg1 = dReg1 / dReg2;
		else
			dReg1 = dReg2;
		return dReg1;
	}

	public boolean isDecimalComVirgula() {
		return decimalComVirgula;
	}

	public void setDecimalComVirgula(boolean decimalComVirgula) {
		this.decimalComVirgula = decimalComVirgula;
	}

	public void addBtnClickHandler(BtnClickHandler handler) {
		this.btnClickHandler = handler;
	}

	public PopupPanel getPopupPanel() {
		PopupPanel popupCalculadora = new PopupPanel(true);
		this.getGrid().setBorderWidth(1);
		popupCalculadora.setWidget(this.getGrid());
		popupCalculadora.setStyleName("");
		return popupCalculadora;
	}

	public void setValor(double valor) {
		this.getEdtPainel().setText(Double.toString(valor));
	}

//	private void clickBotaoPorTecla(int tecla) {
//		switch (tecla) {
//		case HFSConst.TECLA_0:
//			this.getBtn0().click();
//			break;
//		case HFSConst.TECLA_1:
//			this.getBtn1().click();
//			break;
//		case HFSConst.TECLA_2:
//			this.getBtn2().click();
//			break;
//		case HFSConst.TECLA_3:
//			this.getBtn3().click();
//			break;
//		case HFSConst.TECLA_4:
//			this.getBtn4().click();
//			break;
//		case HFSConst.TECLA_5:
//			this.getBtn5().click();
//			break;
//		case HFSConst.TECLA_6:
//			this.getBtn6().click();
//			break;
//		case HFSConst.TECLA_7:
//			this.getBtn7().click();
//			break;
//		case HFSConst.TECLA_8:
//			this.getBtn8().click();
//			break;
//		case HFSConst.TECLA_9:
//			this.getBtn9().click();
//			break;
//		case HFSConst.TECLA_SOMAR:
//			this.getBtnSoma().click();
//			break;
//		case HFSConst.TECLA_DIMINUIR:
//			this.getBtnSubtracao().click();
//			break;
//		case HFSConst.TECLA_MULTIPLICAR:
//			this.getBtnMultiplica().click();
//			break;
//		case HFSConst.TECLA_DIVIDIR:
//			this.getBtnDivide().click();
//			break;
//		case HFSConst.TECLA_ENTER:
//			this.getBtnIgual().click();
//			break;
//		case HFSConst.TECLAKEYPAD_PONTO:
//		case HFSConst.TECLA_PONTO:
//		case HFSConst.TECLAKEYPAD_VIRGULA:
//		case HFSConst.TECLA_VIRGULA:
//			this.getBtnPonto().click();
//			break;
//		}
//	}
}
