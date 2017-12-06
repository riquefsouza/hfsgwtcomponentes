package com.hfsgwt.client.componentes;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class HFSCaptcha extends Composite {
	private FormPanel formPanel;
	private VerticalPanel centralPanel;
	private HorizontalPanel horizontalPanel;
	private Image image;
	private Button btnValidar;
	private HFSTextBox textBox;

	public HFSCaptcha() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getFormPanel());
	}

	private FormPanel getFormPanel() {
		if (formPanel == null) {
			formPanel = new FormPanel();
			formPanel.setMethod(FormPanel.METHOD_POST);
			formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
			formPanel.setWidget(getCentralPanel());
			formPanel.addSubmitHandler(new SubmitHandler() {
				@Override
				public void onSubmit(SubmitEvent event) {
					// if (something_is_wrong)
					// {
					// Take some action
					// event.cancel();
					// }
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

	private VerticalPanel getCentralPanel() {
		if (centralPanel == null) {
			centralPanel = new VerticalPanel();
			centralPanel.add(getHorizontalPanel());
			centralPanel.add(getImage());
			centralPanel.setCellVerticalAlignment(getImage(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			centralPanel.setCellHorizontalAlignment(getImage(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return centralPanel;
	}

	private HFSTextBox getTextBox() {
		if (textBox == null) {
			textBox = new HFSTextBox(PosicaoRotulo.ACIMA, "Texto:",
					Formato.PADRAO, 15, 15, false);
		}
		return textBox;
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.setWidth("100%");
			horizontalPanel.add(getTextBox());
			horizontalPanel.add(getBtnValidar());
			horizontalPanel.setCellVerticalAlignment(getBtnValidar(),
					HasVerticalAlignment.ALIGN_BOTTOM);
		}
		return horizontalPanel;
	}

	private Image getImage() {
		if (image == null) {
			image = new Image("jcaptcha.jpg");
		}
		return image;
	}

	private Button getBtnValidar() {
		if (btnValidar == null) {
			btnValidar = new Button("Validar");
			btnValidar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getFormPanel().setAction("captcha?texto="
							+ getTextBox().getTexto());
					getFormPanel().submit();
				}
			});
		}
		return btnValidar;
	}

}
