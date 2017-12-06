package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSImageViewer extends Composite {
	private ScrollPanel scrollPanel;
	private Image image;	
	private String largura;
	private String altura;

	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);
	private PushButton btnUploadImagem;
	private PushButton btnAdaptar;
	private PushButton btnZoomMais;
	private PushButton btnZoomMenos;
	private PushButton btnTelaCheia;
	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;

	public HFSImageViewer() {
		this.largura = "250px";
		this.altura = "250px";
		
		initComponents();		
	}

	private void initComponents() {
		initWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSize(largura, altura);
			verticalPanel.setStyleName("HFSImageViewer-borda");
			verticalPanel.add(getHorizontalPanel());
			verticalPanel.add(getScrollPanel());
		}
		return verticalPanel;
	}

	private ScrollPanel getScrollPanel() {
		if (scrollPanel == null) {
			scrollPanel = new ScrollPanel();
			scrollPanel.setSize(largura, altura);
			scrollPanel.setWidget(getImage());
		}
		return scrollPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image();
			image.setSize("10px", "10px");
		}
		return image;
	}
	
	private PushButton getBtnUploadImagem() {
		if (btnUploadImagem == null) {
			btnUploadImagem = new PushButton(new Image(img.imageViewerUploadImagem()));
			btnUploadImagem.setSize("18px", "18px");
			btnUploadImagem.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					HFSDialogFileUpload dlgUpload = new HFSDialogFileUpload();
					dlgUpload.addBtnClickHandler(new HFSDialogFileUpload.BtnClickHandler() {
						@Override
						public void onBtnUploadClick(String nomeArquivo) {							
							image.setUrl(GWT.getHostPageBaseURL()+"upload/"+nomeArquivo);
						}
					});
					dlgUpload.center();
					dlgUpload.show();
				}
			});
		}
		return btnUploadImagem;
	}
	
	private PushButton getBtnAdaptar() {
		if (btnAdaptar == null) {
			btnAdaptar = new PushButton(new Image(img.imageViewerResetar()));
			btnAdaptar.setSize("18px", "18px");
			btnAdaptar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					image.setSize(scrollPanel.getOffsetWidth()-5 + "px",
							scrollPanel.getOffsetHeight()-5 + "px");
				}
			});
		}
		return btnAdaptar;
	}

	private PushButton getBtnZoomMais() {
		if (btnZoomMais == null) {
			btnZoomMais = new PushButton(new Image(img.imageViewerZoomMais()));
			btnZoomMais.setSize("18px", "18px");
			btnZoomMais.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int nlargura = image.getOffsetWidth();
					int naltura = image.getOffsetHeight();

					nlargura += 50;
					naltura += 50;

					image.setSize(nlargura + "px", naltura + "px");
				}
			});
		}
		return btnZoomMais;
	}

	private PushButton getBtnZoomMenos() {
		if (btnZoomMenos == null) {
			btnZoomMenos = new PushButton(new Image(img.imageViewerZoomMenos()));
			btnZoomMenos.setSize("18px", "18px");
			btnZoomMenos.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					int nlargura = image.getOffsetWidth();
					int naltura = image.getOffsetHeight();

					nlargura -= 50;
					naltura -= 50;

					image.setSize(nlargura + "px", naltura + "px");
				}
			});
		}
		return btnZoomMenos;
	}

	private PushButton getBtnTelaCheia() {
		if (btnTelaCheia == null) {
			btnTelaCheia = new PushButton(new Image(img.imageViewerTelaCheia()));
			btnTelaCheia.setSize("18px", "18px");
			btnTelaCheia.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					Window.open(image.getUrl(), "Imagem", ""); 	
				}
			});
		}
		return btnTelaCheia;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setSpacing(4);
			horizontalPanel
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.add(getBtnUploadImagem());
			horizontalPanel.add(getBtnAdaptar());
			horizontalPanel.add(getBtnZoomMais());
			horizontalPanel.add(getBtnZoomMenos());
			horizontalPanel.add(getBtnTelaCheia());
		}
		return horizontalPanel;
	}
	
	public void setTamanho(String largura, String altura){
		verticalPanel.setSize(largura, altura);
		scrollPanel.setSize(largura, altura);
		image.setSize(largura, altura);
	}
	
	public void setImagem(String url){
		image.setUrl(GWT.getModuleBaseURL() + url);		
	}
}
