package com.hfsgwt.client.componentes;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

public class HFSDialogFileUpload extends HFSPopupPanel {
	private VerticalPanel verticalPanel;
	private FileUpload uploadArquivo;
	private Label rotulo;
//	private FlexTable gridArquivos;
	private HorizontalPanel horizontalPanel;
//	private Button btnAdicionar;
	private Button btnUpload;
	private FormPanel formPanel;
	private Button btnFechar;

	public interface BtnClickHandler extends EventHandler {
		void onBtnUploadClick(String nomeArquivo);
	}
	
	private BtnClickHandler btnClickHandler;
	
	public HFSDialogFileUpload() {
		super("Upload de Arquivo");
		initComponents();
	}

	private void initComponents() {
		//setHTML("Upload de Arquivo");
		setGlassEnabled(true);
		setWidget(getFormPanel());
//		initWidget(getFormPanel());
	}

	private FormPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new FormPanel();
			formPanel.setWidth("300px");
			formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
			formPanel.setMethod(FormPanel.METHOD_POST);
			formPanel.setAction("arquivoupload");
			formPanel.add(getVerticalPanel());
			formPanel.addSubmitHandler(new SubmitHandler() {
				@Override
				public void onSubmit(SubmitEvent event) {
					// if (something_is_wrong)
					// {
					// Take some action
					// event.cancel();
					// }
					
					//Window.alert("formPanel onSubmit: "+event.isCanceled());
				}
			});
			formPanel.addSubmitCompleteHandler(new SubmitCompleteHandler() {
				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					Window.alert(event.getResults());
				}

			});
		}
		return formPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setWidth("100%");
			verticalPanel.setSpacing(4);
			verticalPanel.add(getTitulo());			
			verticalPanel.add(getRotulo());
			verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			verticalPanel.add(getUploadArquivo());			
			verticalPanel.setCellHorizontalAlignment(getUploadArquivo(), HasHorizontalAlignment.ALIGN_CENTER);
//			verticalPanel.add(getgridArquivos());
			verticalPanel.add(new HTML("<hr style='width: 100%; height: 2px;'>"));
			verticalPanel.add(getHorizontalPanel());
			verticalPanel.setCellHorizontalAlignment(getHorizontalPanel(),
			          HasHorizontalAlignment.ALIGN_CENTER);
//			verticalPanel.setBorderWidth(1);
		}
		return verticalPanel;
	}

	private FileUpload getUploadArquivo() {
		if (uploadArquivo == null) {
			uploadArquivo = new FileUpload();
			uploadArquivo.setWidth("95%");
			uploadArquivo.setName("upload");
			uploadArquivo.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					//Window.alert("uploadArquivo on Change");
				}
			});
		}
		return uploadArquivo;
	}

	private Label getRotulo() {
		if (rotulo == null) {
			rotulo = new Label("Selecione um arquivo:");
			rotulo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			rotulo.setWidth("95%");
		}
		return rotulo;
	}

//	private FlexTable getgridArquivos() {
//		if (gridArquivos == null) {
//			gridArquivos = new FlexTable();
//			gridArquivos.addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					gridArquivosClick(event);
//				}
//			});
//			gridArquivos.setWidth("100%");
//			gridArquivos.setBorderWidth(0);
//		}
//		return gridArquivos;
//	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setWidth("60%");
			horizontalPanel.setSpacing(1);
			horizontalPanel
					.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//			horizontalPanel.add(getBtnAdicionar());
//			horizontalPanel.setCellHorizontalAlignment(getBtnAdicionar(),
//					HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.add(getBtnUpload());
			horizontalPanel.setCellHorizontalAlignment(getBtnUpload(),
					HasHorizontalAlignment.ALIGN_CENTER);
			horizontalPanel.add(getBtnFechar());
		}
		return horizontalPanel;
	}

//	private Button getBtnAdicionar() {
//		if (btnAdicionar == null) {
//			btnAdicionar = new Button("Adicionar");
//			btnAdicionar.setWidth("100px");
//			btnAdicionar.addClickHandler(new ClickHandler() {
//				public void onClick(ClickEvent event) {
//					btnAdicionarClick();
//				}
//			});
//		}
//		return btnAdicionar;
//	}

	private Button getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new Button("Upload");
			btnUpload.setWidth("100px");
			btnUpload.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					btnUploadClick();
				}
			});
		}
		return btnUpload;
	}

	private Button getBtnFechar() {
		if (btnFechar == null) {
			btnFechar = new Button("Fechar");
			btnFechar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			btnFechar.setWidth("100px");
		}
		return btnFechar;
	}

//	private Button getBtnExcluir(int nlinha) {
//		Button btnExcluir = new Button("excluir arquivo? "+nlinha);
//		btnExcluir.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				Button botao = (Button)event.getSource();
//				if (gridArquivos.getRowCount() > 0) {
//					String texto = botao.getText(); 
//					int pos = texto.indexOf("?");					
//					if (pos!=-1){
//						int npos = Integer.parseInt(texto.substring(pos+1));
//						gridArquivos.removeRow(npos);
//					}
//				}
//			}
//		});
//		return btnExcluir;
//	}

//	public void btnAdicionarClick() {
//		String nomeArquivo = uploadArquivo.getFilename();
//		if (nomeArquivo.length() > 0) {
//			int nlinha = gridArquivos.getRowCount();
//			gridArquivos.setText(nlinha, 0, nomeArquivo);
//			gridArquivos.setWidget(nlinha, 1, getBtnExcluir(nlinha));
//		}
//	}

	public void btnUploadClick() {
//		if (gridArquivos.getRowCount() > 0) {
		String nomeArquivo = uploadArquivo.getFilename();
		if (nomeArquivo.length() > 0) {
			formPanel.submit();
			this.hide();
			
			if (btnClickHandler != null) {
				btnClickHandler.onBtnUploadClick(nomeArquivo);
			}
		} else {
			Window.alert("Nenhum arquivo para realizar o upload!");
		}
	}

	public void gridArquivosClick(ClickEvent evento) {
//		Cell cell = gridArquivos.getCellForEvent(evento);
//		if (cell != null) {
//			int nlinha = cell.getRowIndex();
//		}
	}
	
	public void addBtnClickHandler(BtnClickHandler handler) {
		this.btnClickHandler = handler;
	}
	
}
