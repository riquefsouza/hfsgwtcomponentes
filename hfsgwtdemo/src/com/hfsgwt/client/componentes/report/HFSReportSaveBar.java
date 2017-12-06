package com.hfsgwt.client.componentes.report;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HFSReportSaveBar extends Composite {
	private DecoratorPanel decoratorPanel;
	private VerticalPanel verticalPanel;
	private Grid grid;
	private Label labFormato;
	private ListBox cmbFormato;
	private CheckBox chkCompactado;
	private Button btnSalvar;
	private Frame frame;

	private String nomeRelatorio;

	public HFSReportSaveBar(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
		initComponents();
	}

	private void initComponents() {
		initWidget(getDecoratorPanel());
	}

	private DecoratorPanel getDecoratorPanel() {
		if (decoratorPanel == null) {
			decoratorPanel = new DecoratorPanel();
			decoratorPanel.setWidget(getVerticalPanel());
		}
		return decoratorPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getGrid());
		}
		return verticalPanel;
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 3);
			grid.setWidget(0, 0, getLabFormato());
			grid.setWidget(0, 1, getFrame());
			grid.setWidget(1, 0, getCmbFormato());
			grid.setWidget(1, 1, getChkCompactado());
			grid.setWidget(1, 2, getBtnSalvar());
		}
		return grid;
	}

	private Label getLabFormato() {
		if (labFormato == null) {
			labFormato = new Label("Formato");
			labFormato.setWordWrap(false);
		}
		return labFormato;
	}

	private ListBox getCmbFormato() {
		if (cmbFormato == null) {
			cmbFormato = new ListBox();
			cmbFormato.addItem("PDF");
			cmbFormato.addItem("HTML");
			cmbFormato.addItem("XLS");
			cmbFormato.addItem("RTF");
			cmbFormato.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					ListBox cmb = (ListBox) event.getSource();
					if (cmb.getSelectedIndex() < 2) { // PDF e HTML
						getChkCompactado().setEnabled(false);
						getChkCompactado().setValue(true);
					} else {
						getChkCompactado().setEnabled(true);
						getChkCompactado().setValue(false);
					}
				}
			});
		}
		return cmbFormato;
	}

	private CheckBox getChkCompactado() {
		if (chkCompactado == null) {
			chkCompactado = new CheckBox("Compactado");
			chkCompactado.setEnabled(false);
			chkCompactado.setValue(true);
		}
		return chkCompactado;
	}

	private String getURL() {
		String formato = cmbFormato.getItemText(cmbFormato.getSelectedIndex());
		String url = GWT.getHostPageBaseURL() + "relatorio?opcao=ARQUIVO"
				+ "&nome=" + nomeRelatorio + "&formato=" + formato
				+ "&compactado=" + chkCompactado.getValue();
		return url;
	}

	private Button getBtnSalvar() {
		if (btnSalvar == null) {
			btnSalvar = new Button("Salvar");
			btnSalvar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getFrame().setUrl(getURL());
				}
			});
		}
		return btnSalvar;
	}

	private Frame getFrame() {
		if (frame == null) {
			frame = new Frame();
			frame.setVisible(false);
		}
		return frame;
	}

}
