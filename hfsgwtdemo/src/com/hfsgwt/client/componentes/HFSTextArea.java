package com.hfsgwt.client.componentes;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HFSTextArea extends Composite implements IHFSComposite {
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private VerticalPanel verticalPanel2;
	private HTML rotulo;
	private TextArea textArea;
	private Label labResto;
	//private Image image;

	//private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	private boolean somenteLeitura;
	private boolean habilitado;
	private boolean focado;
	private boolean mostrarRotulo;
	private boolean obrigatorio;
	private int larguraRotulo;
	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;
	private int tamanhoMaximoTexto;
//	private boolean clicouImage;
//	private int largura;
//	private int altura;
//	private int nlargura;
//	private int naltura;

	public HFSTextArea(PosicaoRotulo posicao, String rotulo, int largura,
			int altura, int tamanhoMaximoTexto, boolean obrigatorio) {
//		this.largura = largura;
//		this.altura = altura;
		this.habilitado = true;
		this.focado = true;
		this.mostrarRotulo = true;
		this.tamanhoMaximoTexto = tamanhoMaximoTexto; 
		this.obrigatorio = obrigatorio;
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;		
//		this.nlargura = largura;
//		this.naltura = altura;

		initComponents(posicao);

		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();

		if (largura == 0)
			largura = 5;		
		if (altura == 0)
			altura = 5;		
		this.getTextArea().setSize(largura + "px", altura + "px");
		//criarPopupImagem();
	}

	private void initComponents(PosicaoRotulo posicao) {
		if (posicao == PosicaoRotulo.ACIMA) {
			initWidget(getVerticalPanel());
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			initWidget(getHorizontalPanel());
		}
	}

	private VerticalPanel getVerticalPanel2() {
		if (verticalPanel2 == null) {
			verticalPanel2 = new VerticalPanel();
			verticalPanel2.add(getTextArea());
			verticalPanel2.add(getLabResto());
		}
		return verticalPanel2;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getRotulo());
			horizontalPanel.add(getVerticalPanel2());
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getTextArea());
			verticalPanel.add(getLabResto());
		}
		return verticalPanel;
	}

	private Label getLabResto(){
		if (labResto == null){
			labResto = new Label("Restam "+this.tamanhoMaximoTexto+" Caracteres.");			
		}
		return labResto;
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

	public TextArea getTextArea() {
		if (textArea == null) {
			textArea = new TextArea();
			textArea.setStyleName("HFSTextArea-desfocado");
			textArea.addKeyUpHandler(new KeyUpHandler() {
				public void onKeyUp(KeyUpEvent event) {
					labResto.setText("Restam "+(tamanhoMaximoTexto-textArea.getText().length())+
								" Caracteres.");
				}
			});
			textArea.addKeyDownHandler(new KeyDownHandler() {
				public void onKeyDown(KeyDownEvent event) {
					int tecla = event.getNativeKeyCode();
					boolean bEspecial = (tecla == KeyCodes.KEY_BACKSPACE
							|| tecla == KeyCodes.KEY_TAB 
							|| tecla == KeyCodes.KEY_DELETE || tecla == KeyCodes.KEY_SHIFT
							|| tecla == KeyCodes.KEY_UP || tecla == KeyCodes.KEY_DOWN
							|| tecla == KeyCodes.KEY_PAGEUP || tecla == KeyCodes.KEY_PAGEDOWN
							|| tecla == KeyCodes.KEY_LEFT || tecla == KeyCodes.KEY_RIGHT
							|| tecla == KeyCodes.KEY_HOME || tecla == KeyCodes.KEY_END);					
					
					if (!bEspecial) {
						if (textArea.getText().length() >= tamanhoMaximoTexto){
							event.preventDefault();	
						}
					}										
				}
			});			
			textArea.addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					textArea.setStyleName("HFSTextArea-focado");
				}
			});
			textArea.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					textArea.setStyleName("HFSTextArea-desfocado");
				}
			});
		}
		return textArea;
	}
	/*
	private Image getImage() {
		if (image == null) {
			image = new Image(img.textArea());
			image.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					//clicouImage = false;
					if (event.getNativeButton() == NativeEvent.BUTTON_LEFT) {
						clicouImage = true;
					}
				}
			});
			image.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					//clicouImage = false;
				}
			});
			image.addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					if (clicouImage) {
						// event.getClientX(); //browser
						// 
						//event.getRelativeX(target)
						//event.getScreenX()
						//if (event.getX() > largura){
							nlargura++; 
							getTextArea().setWidth(nlargura+"px");	
						//}
						if (event.getY() > altura){
							naltura++;
							getTextArea().setHeight(naltura+"px");
						}
					}
					//clicouImage = false;
				}
			});
			image.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					//clicouImage = false;
				}
			});
			image.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					if (event.getNativeButton() == NativeEvent.BUTTON_LEFT) {
						clicouImage = false;
					}
				}
			});
			// image.addClickHandler(new ClickHandler() {
			// @Override
			// public void onClick(ClickEvent event) {
			// }
			// });
		}
		return image;
	}

	private void criarPopupImagem() {
		PopupPanel popupImagem = new PopupPanel(false, false);
		popupImagem.setStyleName("");
		popupImagem.setWidget(getImage());
//		int top = getTextArea().getAbsoluteTop()
//				+ getTextArea().getOffsetHeight();
//		int left = getTextArea().getAbsoluteLeft()
//				+ getTextArea().getOffsetWidth() - getImage().getWidth();
		int top = altura;
		int left = largura;
		popupImagem.setPopupPosition(left, top);
		popupImagem.show();
	}
*/
	public void setSomenteLeitura(boolean somenteLeitura) {
		this.somenteLeitura = somenteLeitura;
		this.getTextArea().setReadOnly(somenteLeitura);
	}

	public boolean isSomenteLeitura() {
		return this.somenteLeitura;
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		this.getTextArea().setEnabled(habilitado);
	}

	public boolean isHabilitado() {
		return this.habilitado;
	}

	public void setFocado(boolean focado) {
		this.focado = focado;
		this.getTextArea().setFocus(focado);
	}

	public boolean isFocado() {
		return this.focado;
	}

	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getRotulo().setVisible(this.mostrarRotulo);
	}

	public void setTexto(String texto) {
		this.getTextArea().setText(texto);
	}

	public String getTexto() {
		return this.getTextArea().getText();
	}

	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}

	public String getNomeAreaTexto() {
		return getTextArea().getName();
	}

	public void setNomeAreaTexto(String nomeAreaTexto) {
		getTextArea().setName(nomeAreaTexto);
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

}
