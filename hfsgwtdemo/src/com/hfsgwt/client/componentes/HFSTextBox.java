package com.hfsgwt.client.componentes;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.hfsgwt.client.componentes.imagens.HFSImages;
import com.hfsgwt.client.componentes.util.HFSConst;
import com.hfsgwt.client.componentes.util.HFSUtil;
import com.hfsgwt.client.componentes.util.HFSValida;

public class HFSTextBox extends Composite implements IHFSComposite {
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	// private Label rotulo;
	private HTML rotulo;
	private TextBox caixaTexto;
	private PasswordTextBox caixaSenha;
	private HFSCalculator calculadora;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public enum Caixa {
		PADRAO, MAIUSCULA, MINUSCULA, CAPITALIZAR
	}

	public enum Formato {
		PADRAO, SENHA, LETRA_NUMERO, LETRA, NUMERO, DECIMAL, EMAIL, MOEDA, DATA, HORA, DATAHORA, CPF, CNPJ, DATA_CALENDARIO, DECIMAL_CALCULADORA, CEP
	}

	private boolean somenteLeitura;
	private boolean habilitado;
	private boolean focado;
	private boolean decimalComVirgula;
	private boolean mostrarRotulo;
	private boolean obrigatorio;
	private boolean valido;
	private boolean permitirAcentuacao;
	private boolean permitirCedilha;
	private boolean permitirCaractereEspecial;

	private Formato formato;
	private Caixa caixa;
	private AlinhamentoRotulo alinhamentoRotulo;

	private String nomeCaixaTexto;
	private String valorFormatado;
	private String valor;
	private Date dataHora;
	private long numero;
	private double decimal;
	private double moeda;
	private HorizontalPanel caixaPanel;
	private PushButton btnCalendario;
	private PushButton btnCalculadora;
	private PopupPanel popupCalendario;
	private PopupPanel popupCalculadora;
	private int larguraRotulo;
	private PosicaoRotulo posicao;
	
	private KeyDownHandler keyDownHandler;

	public HFSTextBox(PosicaoRotulo posicao, String rotulo, Formato formato,
			int tamanhoMaximoTexto, int largura, boolean obrigatorio) {
		this.somenteLeitura = false;
		this.habilitado = true;
		this.focado = true;
		this.obrigatorio = obrigatorio;
		this.valido = true;
		this.nomeCaixaTexto = "";
		this.permitirAcentuacao = false;
		this.permitirCedilha = false;
		this.permitirCaractereEspecial = false;

		this.formato = formato;
		this.caixa = HFSTextBox.Caixa.PADRAO;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;
		this.decimalComVirgula = true;
		this.mostrarRotulo = true;
		this.valor = "";
		this.valorFormatado = "";
		this.posicao = posicao;
		
		initComponents(posicao);

		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();
		
		if (formato == Formato.SENHA)
			estiloPadrao(false);
		else
			estiloPadrao(true);
		
		alinhaTexto();
		atribuiTamanho(tamanhoMaximoTexto, largura);
	}

	private void initComponents(PosicaoRotulo posicao) {
		if (posicao == PosicaoRotulo.ACIMA) {
			initWidget(getVerticalPanel());
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			initWidget(getHorizontalPanel());
		}
	}
	
	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getRotulo());			
			horizontalPanel.add(getCaixaPanel());
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getCaixaPanel());
		}
		return verticalPanel;
	}

	private PushButton getBtnCalendario() {
		if (btnCalendario == null) {
			btnCalendario = new PushButton(new Image(img.textboxCalendario()));
			btnCalendario.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnCalendarioClick(event);
				}
			});
			btnCalendario.setSize("13px", "12px");
		}
		return btnCalendario;
	}

	private PushButton getBtnCalculadora() {
		if (btnCalculadora == null) {
			btnCalculadora = new PushButton(new Image(img.textboxCalculadora()));
			btnCalculadora.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnCalculadoraClick(event);
				}
			});
			btnCalculadora.setSize("13px", "12px");
		}
		return btnCalculadora;
	}

	// private Image getImage() {
	// if (image == null) {
	// image = new Image(img.textboxObrigatorio());
	// }
	// return image;
	// }

	private HorizontalPanel getCaixaPanel() {
		if (caixaPanel == null) {
			caixaPanel = new HorizontalPanel();
			// if (this.obrigatorio) {
			// caixaPanel.add(getImage());
			// caixaPanel.setCellHorizontalAlignment(getImage(),
			// HasHorizontalAlignment.ALIGN_RIGHT);
			// caixaPanel.setCellWidth(getImage(), "20x");
			// caixaPanel.setCellVerticalAlignment(getImage(),
			// HasVerticalAlignment.ALIGN_MIDDLE);
			// }

			if (formato == HFSTextBox.Formato.SENHA)
				caixaPanel.add(getCaixaSenha());
			else
				caixaPanel.add(getCaixaTexto());

			if (formato == HFSTextBox.Formato.DATA_CALENDARIO) {
				caixaPanel.add(getBtnCalendario());
			}
			if (formato == HFSTextBox.Formato.DECIMAL_CALCULADORA) {
				caixaPanel.add(getBtnCalculadora());
			}
		}
		return caixaPanel;
	}

	private void setRotulo(String rotulo) {
		if (this.obrigatorio) {
			rotulo = "<b>" + rotulo
					+ "</b><span style='color: rgb(255, 0, 0);'>*</span>";
		}
		getRotulo().setHTML(rotulo);
	}

	private HTML getRotulo() {
		if (rotulo == null) {
			rotulo = new HTML("Rotulo:");
		}
		return rotulo;
	}

	private TextBox getCaixaTexto() {
		if (caixaTexto == null) {
			caixaTexto = new TextBox();
			caixaTexto.addKeyDownHandler(new KeyDownHandler() {
				public void onKeyDown(KeyDownEvent event) {
					caixaTextoKeyDown(event);
				}
			});
			caixaTexto.addKeyUpHandler(new KeyUpHandler() {
				public void onKeyUp(KeyUpEvent event) {
					caixaTextoKeyUp(event);
				}
			});
			caixaTexto.addFocusHandler(new FocusHandler() {
				public void onFocus(FocusEvent event) {
					caixaTextoFocusIn(event);
				}
			});
			caixaTexto.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					caixaTextoFocusOut(event);
				}
			});
		}
		return caixaTexto;
	}

	public PasswordTextBox getCaixaSenha() {
		if (caixaSenha == null) {
			caixaSenha = new PasswordTextBox();
			caixaSenha.addKeyDownHandler(new KeyDownHandler() {
				public void onKeyDown(KeyDownEvent event) {
					if (keyDownHandler != null) {
						keyDownHandler.onKeyDown(event);
					}
				}
			});
			caixaSenha.addFocusHandler(new FocusHandler() {
				public void onFocus(FocusEvent event) {
					caixaSenhaFocusIn(event);
				}
			});
			caixaSenha.addBlurHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					caixaSenhaFocusOut(event);
				}
			});
		}
		return caixaSenha;
	}

	public void setSomenteLeitura(boolean somenteLeitura) {
		this.somenteLeitura = somenteLeitura;
		if (formato == HFSTextBox.Formato.SENHA)
			this.getCaixaTexto().setReadOnly(somenteLeitura);
		else
			this.getCaixaSenha().setReadOnly(somenteLeitura);
	}

	public boolean isSomenteLeitura() {
		return this.somenteLeitura;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		if (formato == HFSTextBox.Formato.SENHA)
			this.getCaixaSenha().setEnabled(habilitado);
		else
			this.getCaixaTexto().setEnabled(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		if (formato == HFSTextBox.Formato.SENHA)
			this.getCaixaSenha().setFocus(focado);
		else
			this.getCaixaTexto().setFocus(focado);
	}

	public boolean isFocado() {
		return this.focado;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public boolean isDecimalComVirgula() {
		return decimalComVirgula;
	}

	public void setDecimalComVirgula(boolean decimalComVirgula) {
		this.decimalComVirgula = decimalComVirgula;
	}

	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getRotulo().setVisible(this.mostrarRotulo);
	}

	public String getTextoFormatado() {
		return valorFormatado;
	}

	public String getTexto() {
		String svalor = this.valor;

		switch (this.formato) {
		case DECIMAL:
			svalor = this.valor.replace(",", ".");
			break;
		case DECIMAL_CALCULADORA:
			svalor = this.valor.replace(",", ".");
			break;
		case MOEDA:
			svalor = this.valor.replace(",", ".");
			break;
		case CPF:
			svalor = HFSUtil.desformataCPF(this.valor);
			break;
		case CNPJ:
			svalor = HFSUtil.desformataCNPJ(this.valor);
			break;
		case CEP:
			svalor = HFSUtil.desformataCEP(this.valor);
			break;
		case SENHA:
			svalor = this.getCaixaSenha().getText();
			break;
		}
		return svalor;
	}

	public void setTexto(String valor) {
		this.valor = valor;
		this.valorFormatado = valor;
		if (formato == HFSTextBox.Formato.SENHA)
			this.getCaixaSenha().setText(valor);
		else {
			this.getCaixaTexto().setText(valor);
			formataTexto();
			// mudaCaixa();
		}
	}

	private void alinhaTexto() {
		switch (formato) {
		case NUMERO:
			this.getCaixaTexto().setAlignment(TextAlignment.RIGHT);
			break;
		case DECIMAL:
			this.getCaixaTexto().setAlignment(TextAlignment.RIGHT);
			break;
		case DECIMAL_CALCULADORA:
			this.getCaixaTexto().setAlignment(TextAlignment.RIGHT);
			break;
		case MOEDA:
			this.getCaixaTexto().setAlignment(TextAlignment.RIGHT);
			break;
		default:
			break;
		}
	}

	private void atribuiTamanho(int tamanhoMaximoTexto, int largura) {
		
		if (tamanhoMaximoTexto == 0)
			tamanhoMaximoTexto = 5;
		if (largura == 0)
			largura = 5;
		
		switch (this.formato) {
		case DATA:
			this.getCaixaTexto().setVisibleLength(8);
			this.getCaixaTexto().setMaxLength(10);
			break;
		case DATA_CALENDARIO:
			this.getCaixaTexto().setVisibleLength(8);
			this.getCaixaTexto().setMaxLength(10);
			break;
		case HORA:
			this.getCaixaTexto().setVisibleLength(5);
			this.getCaixaTexto().setMaxLength(5);
			break;
		case DATAHORA:
			this.getCaixaTexto().setVisibleLength(16);
			this.getCaixaTexto().setMaxLength(16);
			break;
		case CPF:
			this.getCaixaTexto().setVisibleLength(12);
			this.getCaixaTexto().setMaxLength(14);
			break;
		case CNPJ:
			this.getCaixaTexto().setVisibleLength(15);
			this.getCaixaTexto().setMaxLength(18);
			break;
		case CEP:
			this.getCaixaTexto().setVisibleLength(9);
			this.getCaixaTexto().setMaxLength(9);
			break;
		case SENHA:
			this.getCaixaSenha().setVisibleLength(largura);
			this.getCaixaSenha().setMaxLength(tamanhoMaximoTexto);
			break;
		default:
			this.getCaixaTexto().setVisibleLength(largura);
			this.getCaixaTexto().setMaxLength(tamanhoMaximoTexto);
			break;
		}
	}

	private void mudaEstilo(boolean bCaixaTexto, boolean bFocado) {
		String estilo;

		if (caixa == HFSTextBox.Caixa.MAIUSCULA) {
			if (bFocado)
				estilo = "HFSTextBox-focado-uppercase";
			else
				estilo = "HFSTextBox-erro-uppercase";
		} else if (caixa == HFSTextBox.Caixa.MINUSCULA) {
			if (bFocado)
				estilo = "HFSTextBox-focado-lowercase";
			else
				estilo = "HFSTextBox-erro-lowercase";
		} else if (caixa == HFSTextBox.Caixa.CAPITALIZAR) {
			if (bFocado)
				estilo = "HFSTextBox-focado-capitalize";
			else
				estilo = "HFSTextBox-erro-capitalize";
		} else {
			if (bFocado)
				estilo = "HFSTextBox-focado";
			else
				estilo = "HFSTextBox-erro";
		}
		if (bCaixaTexto)
			this.getCaixaTexto().setStyleName(estilo);
		else
			this.getCaixaSenha().setStyleName(estilo);
	}
	
	private void estiloPadrao(boolean bCaixaTexto){
		if (bCaixaTexto)
			this.getCaixaTexto().setStyleName("HFSTextBox-desfocado");
		else
			this.getCaixaSenha().setStyleName("HFSTextBox-desfocado");
	}

	private void caixaTextoFocusIn(FocusEvent evento) {
		this.getCaixaTexto().setText(this.valor);
		mudaEstilo(true, true);
	}

	private void caixaSenhaFocusIn(FocusEvent evento) {
		mudaEstilo(false, true);
	}

	private boolean formataDataHora(String sData) {
		DateTimeFormat datahorafmt;
		Date dvalor;

		try {
			switch (this.formato) {
			case DATA:
				if (HFSValida.validaDataSimples(sData)) {
					datahorafmt = DateTimeFormat
							.getFormat(HFSConst.MASCARA_DATA);
					dvalor = datahorafmt.parseStrict(sData);
					this.valorFormatado = datahorafmt.format(dvalor);
					if (HFSValida.validaAnoBisexto(this.valorFormatado)) {
						return true;
					}
				}
				break;
			case DATA_CALENDARIO:
				if (HFSValida.validaDataSimples(sData)) {
					datahorafmt = DateTimeFormat
							.getFormat(HFSConst.MASCARA_DATA);
					dvalor = datahorafmt.parseStrict(sData);
					this.valorFormatado = datahorafmt.format(dvalor);
					if (HFSValida.validaAnoBisexto(this.valorFormatado)) {
						return true;
					}
				}
				break;
			case HORA:
				if (HFSValida.validaHoraH24M(sData)) {
					datahorafmt = DateTimeFormat
							.getFormat(HFSConst.MASCARA_HORA);
					dvalor = datahorafmt.parseStrict(sData);
					this.valorFormatado = datahorafmt.format(dvalor);
					return true;
				}
				break;
			case DATAHORA:
				if (HFSValida.validaDataHora(sData)) {
					datahorafmt = DateTimeFormat
							.getFormat(HFSConst.MASCARA_DATAHORA);
					dvalor = datahorafmt.parseStrict(sData);
					this.valorFormatado = datahorafmt.format(dvalor);
					if (HFSValida.validaAnoBisexto(this.valorFormatado)) {
						return true;
					}
				}
				break;
			}
		} catch (Exception ex) {
			mudaEstilo(true, false);
			return false;
		}

		mudaEstilo(true, false);
		return false;
	}

	private boolean formataNumero(String sValor, NumberFormat numerofmt) {
		double nvalor = 0;
		try {
			if (this.decimalComVirgula) {
				nvalor = Double.parseDouble(sValor.replace(",", "."));
				this.valorFormatado = numerofmt.format(nvalor);
				this.valorFormatado = HFSUtil.pontoParaVirgula(this.valorFormatado);
			} else {
				nvalor = Double.parseDouble(sValor);
				this.valorFormatado = numerofmt.format(nvalor);
			}
			return true;
		} catch (Exception e) {
			mudaEstilo(true, false);
			return false;
		}
	}

	private boolean formataCPF(String sValor) {
		if (HFSValida.validaCPF(sValor, true)) {
			// this.valorFormatado = HFSValida.formataCPF(sValor);
			this.valorFormatado = sValor;
			return true;
		} else {
			mudaEstilo(true, false);
			return false;
		}
	}

	private boolean formataCNPJ(String sValor) {
		if (HFSValida.validaCNPJ(sValor, true)) {
			// this.valorFormatado = HFSValida.formataCNPJ(sValor);
			this.valorFormatado = sValor;
			return true;
		} else {
			mudaEstilo(true, false);
			return false;
		}
	}

	private boolean formataEmail(String sValor) {
		if (HFSValida.validaEmailLivre(sValor)) {
			this.valorFormatado = sValor;
			return true;
		} else {
			mudaEstilo(true, false);
			return false;
		}
	}

	private boolean formataCEP(String sValor) {
		if (HFSValida.validaCEP(sValor)) {
			this.valorFormatado = sValor;
			return true;
		} else {
			mudaEstilo(true, false);			
			return false;
		}
	}

	private void formataTexto() {
		estiloPadrao(true);
		this.valorFormatado = "";

		if (this.valor.trim().length() > 0) {
			switch (formato) {
			case DECIMAL:
				this.valido = formataNumero(this.valor, NumberFormat
						.getDecimalFormat());
				break;
			case DECIMAL_CALCULADORA:
				this.valido = formataNumero(this.valor, NumberFormat
						.getDecimalFormat());
				break;
			case MOEDA:
				this.valido = formataNumero(this.valor, NumberFormat
						.getCurrencyFormat("BRL"));
				// formataNumero(this.valor, NumberFormat.getDecimalFormat());
				break;
			case EMAIL:
				this.valido = formataEmail(this.valor);
				break;
			case DATA:
				this.valido = formataDataHora(this.valor);
				break;
			case DATA_CALENDARIO:
				this.valido = formataDataHora(this.valor);
				break;
			case HORA:
				this.valido = formataDataHora(this.valor);
				break;
			case DATAHORA:
				this.valido = formataDataHora(this.valor);
				break;
			case CPF:
				this.valido = formataCPF(this.valor);
				break;
			case CNPJ:
				this.valido = formataCNPJ(this.valor);
				break;
			case CEP:
				this.valido = formataCEP(this.valor);
				break;
			default:
				break;
			}

			if (this.valorFormatado.trim().length() > 0) {
				this.getCaixaTexto().setValue(this.valorFormatado);
			} else {
				this.valorFormatado = this.valor;
			}
		}
	}

	private void caixaTextoFocusOut(BlurEvent evento) {
		formataTexto();		
	}

	private void caixaSenhaFocusOut(BlurEvent evento) {
		estiloPadrao(false);
	}

	private void addChar(int npos, char caractere) {
		if (this.valor.indexOf(caractere) != npos)
			this.valor += caractere;
	}

	private void adicionaCaractere(int tecla) {
		this.valor = this.getCaixaTexto().getValue();

		if (this.valor.trim().length() > 0) {
			if (!isTeclaEspecial(tecla)) {
				int npos = this.getCaixaTexto().getCursorPos();

				switch (this.formato) {
				// case MOEDA:
				// if (npos == 1) {
				// this.valor = "R$" + this.valor;
				// }
				// break;
				case DATA:
					if (npos == 2) {
						addChar(npos, '/');
					} else if (npos == 5) {
						addChar(npos, '/');
					}
					break;
				case DATA_CALENDARIO:
					if (npos == 2) {
						addChar(npos, '/');
					} else if (npos == 5) {
						addChar(npos, '/');
					}
					break;
				case HORA:
					if (npos == 2) {
						addChar(npos, ':');
						// } else if (npos == 5) {
						// addChar(npos, ':');
					}
					break;
				case DATAHORA:
					if (npos == 2) {
						addChar(npos, '/');
					} else if (npos == 5) {
						addChar(npos, '/');
					} else if (npos == 10) {
						addChar(npos, ' ');
					} else if (npos == 13) {
						addChar(npos, ':');
						// } else if (npos == 16) {
						// addChar(npos, ':');
					}
					break;
				case CPF:
					if (npos == 3) {
						addChar(npos, '.');
					} else if (npos == 7) {
						addChar(npos, '.');
					} else if (npos == 11) {
						addChar(npos, '-');
					}
					break;
				case CNPJ:
					if (npos == 2) {
						addChar(npos, '.');
					} else if (npos == 6) {
						addChar(npos, '.');
					} else if (npos == 10) {
						addChar(npos, '/');
					} else if (npos == 15) {
						addChar(npos, '-');
					}
					break;
				case CEP:
					if (npos == 5) {
						addChar(npos, '-');
					}
					break;
				}

				this.getCaixaTexto().setText(this.valor);
			}
		}
		// mudaCaixa();
	}

	private void caixaTextoKeyUp(KeyUpEvent evento) {
		adicionaCaractere(evento.getNativeKeyCode());
	}

	private boolean isTeclaEspecial(int tecla) {
		boolean bEspecial = (tecla == KeyCodes.KEY_BACKSPACE
				|| tecla == KeyCodes.KEY_TAB || tecla == KeyCodes.KEY_ENTER
				|| tecla == KeyCodes.KEY_DELETE || tecla == KeyCodes.KEY_SHIFT
				|| tecla == KeyCodes.KEY_LEFT || tecla == KeyCodes.KEY_RIGHT
				|| tecla == KeyCodes.KEY_HOME || tecla == KeyCodes.KEY_END);
		return bEspecial;
	}
	
	private boolean isTeclaAcentuacao(int tecla, boolean isShiftKey) {
		boolean bAcentua = (tecla == HFSConst.TECLA_ACENTO_AGUDO
				|| tecla == HFSConst.TECLA_ACENTO_TIL
				|| (tecla == HFSConst.TECLA_ACENTO_CRASE && isShiftKey)
				|| (tecla == HFSConst.TECLA_ACENTO_TREMA && isShiftKey) 
				|| (tecla == HFSConst.TECLA_ACENTO_CIRCUNFLEXO && isShiftKey));
		return bAcentua;
	}

	private boolean isTeclaCaractereEspecial(int tecla, boolean isShiftKey){
		boolean bCaracEspecial = (tecla == HFSConst.TECLA_ASPAS
		|| (tecla == HFSConst.TECLA_EXCLAMACAO && isShiftKey)
		|| (tecla == HFSConst.TECLA_ARROBA && isShiftKey)
		|| (tecla == HFSConst.TECLA_CERQUILHA  && isShiftKey)
		|| (tecla == HFSConst.TECLA_CIFRAO  && isShiftKey)
		|| (tecla == HFSConst.TECLA_PERCENTE  && isShiftKey)
		|| (tecla == HFSConst.TECLA_ECOMERCIAL  && isShiftKey)
		|| (tecla == HFSConst.TECLA_ASTERISTICO  && isShiftKey)
		|| (tecla == HFSConst.TECLA_ABREPARENTESES  && isShiftKey)
		|| (tecla == HFSConst.TECLA_FECHAPARENTESES  && isShiftKey)
		|| tecla == HFSConst.TECLA_MENOS 
		|| tecla == HFSConst.TECLA_MAIS 
		|| tecla == HFSConst.TECLA_ABRECHAVES 
		|| tecla == HFSConst.TECLA_FECHACHAVES 
		|| tecla == HFSConst.TECLA_INTERROGACAO 
		|| tecla == HFSConst.TECLA_BARRAINVERTIDA
		|| tecla == HFSConst.TECLA_DOISPONTOS
		|| tecla == HFSConst.TECLA_VIRGULA
		|| tecla == HFSConst.TECLA_PONTO);
		return bCaracEspecial;		
	}
	
	private boolean permitirOutraTeclas(boolean bTeclar, int tecla, KeyDownEvent evento){
		boolean res = bTeclar;
		
		if (!this.permitirAcentuacao) {
			if (isTeclaAcentuacao(tecla, evento.isShiftKeyDown()))
				res = false;
		}
		if (!this.permitirCedilha) {
			if (tecla == HFSConst.TECLA_CEDILHA)
				res = false;
		}
		if (!this.permitirCaractereEspecial) {
			if (isTeclaCaractereEspecial(tecla, evento.isShiftKeyDown()))
				res = false;
		}
				
		return res;
	}
	
	private void caixaTextoKeyDown(KeyDownEvent evento) {
		int tecla = evento.getNativeKeyCode();

		boolean bEspecial = isTeclaEspecial(tecla);		
		boolean bLetraNumero = permitirOutraTeclas(true, tecla, evento);
		
		// 32 é barra de espaço		
		boolean bLetrasSemEspaco = ((tecla >= 65 && tecla <= 90) || bEspecial || 
				tecla == HFSConst.TECLA_CEDILHA || isTeclaAcentuacao(tecla, evento.isShiftKeyDown()) 
				|| isTeclaCaractereEspecial(tecla, evento.isShiftKeyDown()));
		
		bLetrasSemEspaco = permitirOutraTeclas(bLetrasSemEspaco, tecla, evento);
				
		boolean bLetras = (bLetrasSemEspaco || tecla == HFSConst.TECLA_ESPACO);
		boolean bNumerosKeypad = (tecla >= 96 && tecla <= 105);
		boolean bNumeros = ((tecla >= 48 && tecla <= 57)
				|| tecla == HFSConst.TECLA_DIMINUIR || bNumerosKeypad || bEspecial);

		boolean bDecimal = false;
		if (this.decimalComVirgula)
			bDecimal = (bNumeros || tecla == HFSConst.TECLA_VIRGULA || tecla == HFSConst.TECLAKEYPAD_VIRGULA); // virgula
		else
			// ponto decimal
			bDecimal = (bNumeros || tecla == HFSConst.TECLA_PONTO || tecla == HFSConst.TECLAKEYPAD_PONTO);

		boolean bDecimalCalculadora = bDecimal;
		// boolean bDecimalCalculadora = (bDecimal || tecla ==
		// HFSConst.TECLA_SOMAR || tecla == HFSConst.TECLA_DIMINUIR ||
		// tecla == HFSConst.TECLA_MULTIPLICAR || tecla ==
		// HFSConst.TECLA_DIVIDIR);

		boolean bMoeda = bDecimal;
		// ponto decimal e barra de menos
		boolean bCPF = (bNumeros);
		// || tecla == TECLA_PONTO || tecla == TECLAKEYPAD_PONTO || tecla ==
		// TECLA_DIMINUIR);
		boolean bCNPJ = (bCPF); // || tecla == TECLA_BARRA);
		boolean bData = (bNumeros); // || tecla == TECLA_BARRA); // barra é 193

		// dois pontos é 191 com shift antes é 16
		boolean bHora = (bNumeros); // || tecla == TECLA_DOISPONTOS);

		// 32 é barra de espaço
		boolean bDataHora = (bData || bHora); // || tecla == TECLA_ESPACO);

		boolean bEmail = (bLetrasSemEspaco || (tecla == HFSConst.TECLA_ARROBA && evento.isShiftKeyDown()) || tecla == HFSConst.TECLA_PONTO);
		
		boolean bCEP = (bNumeros); // || tecla == TECLA_DIMINUIR);

		switch (formato) {
		case LETRA_NUMERO:
			if (!bLetraNumero) {
				evento.preventDefault();
			}
			break;
		case LETRA:
			if (!bLetras) {
				evento.preventDefault();
			}
			break;
		case EMAIL:
			if (!bEmail) {
				evento.preventDefault();
			}
			break;
		case NUMERO:
			if (!bNumeros) {
				evento.preventDefault();
			}
			break;
		case DECIMAL:
			if (!bDecimal) {
				evento.preventDefault();
			}
			break;
		case DECIMAL_CALCULADORA:
			if (!bDecimalCalculadora) {
				evento.preventDefault();
			}
			break;
		case MOEDA:
			if (!bMoeda) {
				evento.preventDefault();
			}
			break;
		case DATA:
			if (!bData) {
				evento.preventDefault();
			}
			break;
		case DATA_CALENDARIO:
			if (!bData) {
				evento.preventDefault();
			}
			break;
		case HORA:
			if (!bHora) {
				evento.preventDefault();
			}
			break;
		case DATAHORA:
			if (!bDataHora) {
				evento.preventDefault();
			}
			break;
		case CPF:
			if (!bCPF) {
				evento.preventDefault();
			}
			break;
		case CNPJ:
			if (!bCNPJ) {
				evento.preventDefault();
			}
			break;
		case CEP:
			if (!bCEP) {
				evento.preventDefault();
			}
			break;
		default:
			break;
		}

		if (keyDownHandler != null) {
			keyDownHandler.onKeyDown(evento);
		}

	}

	private void btnCalendarioClick(ClickEvent evento) {
		if (formato == HFSTextBox.Formato.DATA_CALENDARIO) {
			popupCalendario = new PopupPanel(true);

			DatePicker cal = new DatePicker();
			cal.setStyleName("HFSTextBox-calendario");
			cal.addValueChangeHandler(new ValueChangeHandler<Date>() {
				public void onValueChange(ValueChangeEvent<Date> event) {
					Date data = event.getValue();
					DateTimeFormat datafmt = DateTimeFormat
							.getFormat(HFSConst.MASCARA_DATA);
					String sdata = datafmt.format(data);
					setTexto(sdata);
					popupCalendario.hide();
				}
			});
			if (this.getDataHora() == null)
				cal.setValue(new Date(), true);
			else
				cal.setValue(this.getDataHora(), true);

			popupCalendario.setWidget(cal);
			popupCalendario.setStyleName("");
			int top = this.getBtnCalendario().getAbsoluteTop()
					+ this.getBtnCalendario().getOffsetHeight();
			int left = this.getCaixaTexto().getAbsoluteLeft();
			popupCalendario.setPopupPosition(left, top);
			popupCalendario.show();
		}

	}

	private void btnCalculadoraClick(ClickEvent evento) {
		if (formato == HFSTextBox.Formato.DECIMAL_CALCULADORA) {
			calculadora = new HFSCalculator(HFSCalculator.BotaoTamanho.PEQUENO);
			calculadora.setDecimalComVirgula(this.decimalComVirgula);
			popupCalculadora = calculadora.getPopupPanel();

			calculadora.addBtnClickHandler(new HFSCalculator.BtnClickHandler() {
				@Override
				public void onBtnIgualClick() {
					popupCalculadora.hide();
				}

				@Override
				public void onBotoesClick(double valor, String valorFormatado) {
					setDecimal(valor);
				}
			});

			if (this.getTexto().trim().length() == 0)
				calculadora.setValor(0);
			else
				calculadora.setValor(this.getDecimal());

			int top = this.getBtnCalculadora().getAbsoluteTop()
					+ this.getBtnCalculadora().getOffsetHeight();
			int left = this.getCaixaTexto().getAbsoluteLeft();
			popupCalculadora.setPopupPosition(left, top);
			popupCalculadora.show();
		}

	}

	public Date getDataHora() {
		if (this.formato == HFSTextBox.Formato.DATA
				|| this.formato == HFSTextBox.Formato.DATA_CALENDARIO
				|| this.formato == HFSTextBox.Formato.DATAHORA
				|| this.formato == HFSTextBox.Formato.HORA) {
			DateTimeFormat datahorafmt = DateTimeFormat
					.getFormat(HFSConst.MASCARA_DATA);
			if (this.getTexto().trim().length() > 0)
				dataHora = datahorafmt.parseStrict(this.getTexto());
			else
				dataHora = null;
		} else
			dataHora = null;

		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		DateTimeFormat datahorafmt;

		switch (this.formato) {
		case DATA:
			datahorafmt = DateTimeFormat.getFormat(HFSConst.MASCARA_DATA);
			this.setTexto(datahorafmt.format(dataHora));
			break;
		case DATA_CALENDARIO:
			datahorafmt = DateTimeFormat.getFormat(HFSConst.MASCARA_DATA);
			this.setTexto(datahorafmt.format(dataHora));
			break;
		case HORA:
			datahorafmt = DateTimeFormat.getFormat(HFSConst.MASCARA_HORA);
			this.setTexto(datahorafmt.format(dataHora));
			break;
		case DATAHORA:
			datahorafmt = DateTimeFormat.getFormat(HFSConst.MASCARA_DATAHORA);
			this.setTexto(datahorafmt.format(dataHora));
			break;
		}
		this.dataHora = dataHora;
	}

	public long getNumero() {
		if (this.formato == HFSTextBox.Formato.NUMERO
				|| this.formato == HFSTextBox.Formato.CEP) {
			Long num = new Long(this.getTexto());
			numero = num.longValue();
		} else
			numero = -1;

		return numero;
	}

	public void setNumero(long numero) {
		if (this.formato == HFSTextBox.Formato.NUMERO
				|| this.formato == HFSTextBox.Formato.CEP) {
			this.setTexto(Long.toString(numero));
		}
		this.numero = numero;
	}

	public double getDecimal() {
		if (this.formato == HFSTextBox.Formato.DECIMAL
				|| this.formato == HFSTextBox.Formato.DECIMAL_CALCULADORA) {
			Double dec = new Double(this.getTexto());
			decimal = dec.doubleValue();
		} else
			decimal = -1;

		return decimal;
	}

	public void setDecimal(double decimal) {
		if (this.formato == HFSTextBox.Formato.DECIMAL
				|| this.formato == HFSTextBox.Formato.DECIMAL_CALCULADORA) {
			if (this.decimalComVirgula)
				this.setTexto(Double.toString(decimal).replace(".", ","));
			else
				this.setTexto(Double.toString(decimal));
		}
		this.decimal = decimal;
	}

	public double getMoeda() {
		if (this.formato == HFSTextBox.Formato.MOEDA) {
			Double dec = new Double(this.getTexto());
			moeda = dec.doubleValue();
		} else
			moeda = -1;

		return moeda;
	}

	public void setMoeda(double moeda) {
		if (this.formato == HFSTextBox.Formato.MOEDA) {
			if (this.decimalComVirgula)
				this.setTexto(Double.toString(moeda).replace(".", ","));
			else
				this.setTexto(Double.toString(moeda));
		}
		this.moeda = moeda;
	}

	public String getNomeCaixaTexto() {
		return nomeCaixaTexto;
	}

	public void setNomeCaixaTexto(String nomeCaixaTexto) {
		this.nomeCaixaTexto = nomeCaixaTexto;
		if (formato == HFSTextBox.Formato.SENHA)
			this.getCaixaSenha().setName(nomeCaixaTexto);
		else
			this.getCaixaTexto().setName(nomeCaixaTexto);
	}

	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public void addKeyDownHandler(KeyDownHandler keyDownHandler) {
		this.keyDownHandler = keyDownHandler;
	}

	public boolean isPermitirAcentuacao() {
		return permitirAcentuacao;
	}

	public void setPermitirAcentuacao(boolean permitirAcentuacao) {
		this.permitirAcentuacao = permitirAcentuacao;
	}

	public boolean isPermitirCedilha() {
		return permitirCedilha;
	}

	public void setPermitirCedilha(boolean permitirCedilha) {
		this.permitirCedilha = permitirCedilha;
	}

	public boolean isPermitirCaractereEspecial() {
		return permitirCaractereEspecial;
	}

	public void setPermitirCaractereEspecial(boolean permitirCaractereEspecial) {
		this.permitirCaractereEspecial = permitirCaractereEspecial;
	}

	public AlinhamentoRotulo getAlinhamentoRotulo() {
		return alinhamentoRotulo;
	}

	public void setAlinhamentoRotulo(AlinhamentoRotulo alinhamentoRotulo) {
		this.alinhamentoRotulo = alinhamentoRotulo;
		switch (alinhamentoRotulo) {
		case ESQUERDA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			break;
		case CENTRO:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			break;
		case DIREITA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			break;
		}
	}

	public void setLargura(String largura){
		if (formato == HFSTextBox.Formato.SENHA)
			getCaixaSenha().setWidth(largura);
		else
			getCaixaTexto().setWidth(largura);

		if (posicao == PosicaoRotulo.ACIMA) {
			getVerticalPanel().setWidth(largura);
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			getHorizontalPanel().setWidth(largura);
		}
	}
	
}
