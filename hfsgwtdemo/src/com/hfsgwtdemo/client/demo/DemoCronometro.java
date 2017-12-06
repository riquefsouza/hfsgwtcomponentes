package com.hfsgwtdemo.client.demo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.componentes.HFSLabelChronometer;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class DemoCronometro extends Composite {
	private VerticalPanel verticalPanel;
	private HFSLabelChronometer labCrono;
	private Button btnRodar;
	private Button btnParar;

	public DemoCronometro() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getVerticalPanel());
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.setSpacing(10);
			verticalPanel.add(getLabCrono());
			verticalPanel.add(getBtnRodar());
			verticalPanel.add(getBtnParar());
		}
		return verticalPanel;
	}

	private HFSLabelChronometer getLabCrono() {
		if (labCrono == null) {
			labCrono = new HFSLabelChronometer(120);
		}
		return labCrono;
	}

	private Button getBtnRodar() {
		if (btnRodar == null) {
			btnRodar = new Button("Rodar");
			btnRodar.setSize("70px", "28px");
			btnRodar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					labCrono.rodar();
				}
			});
		}
		return btnRodar;
	}

	private Button getBtnParar() {
		if (btnParar == null) {
			btnParar = new Button("Parar");
			btnParar.setSize("70px", "28px");
			btnParar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					labCrono.parar();
				}
			});
		}
		return btnParar;
	}
}
