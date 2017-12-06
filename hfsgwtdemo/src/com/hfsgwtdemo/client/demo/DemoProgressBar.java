package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.hfsgwt.client.componentes.HFSProgressBar;

public class DemoProgressBar extends Composite {
	private AbsolutePanel verticalPanel;
	private HFSProgressBar gauge;
	private Button btnRodar;
	private Button btnParar;

	public DemoProgressBar() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getVerticalPanel());
	}

	private AbsolutePanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new AbsolutePanel();
			verticalPanel.setSize("211px", "181px");
			verticalPanel.add(getGauge(), 22, 45);
			verticalPanel.add(getBtnRodar(), 22, 108);
			verticalPanel.add(getBtnParar(), 111, 108);
		}
		return verticalPanel;
	}

	private HFSProgressBar getGauge() {
		if (gauge == null) {
			gauge = new HFSProgressBar(168, true, 1000);
			gauge.setMaxValor(10);
			gauge.addAnimacaoHandler(new HFSProgressBar.AnimacaoHandler() {
				@Override
				public void onAnimacaoCompletada() {
					Window.alert("Animação completada !");
				}
			});
		}
		return gauge;
	}

	private Button getBtnRodar() {
		if (btnRodar == null) {
			btnRodar = new Button("Rodar");
			btnRodar.setSize("81px", "28px");
			btnRodar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//getGauge().addProgresso(1);
					getGauge().rodarAnimacao();
				}
			});
		}
		return btnRodar;
	}

	private Button getBtnParar() {
		if (btnParar == null) {
			btnParar = new Button("Parar");
			btnParar.setSize("81px", "28px");
			btnParar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//getGauge().setProgresso(5);
					getGauge().pararAnimacao();
				}
			});
		}
		return btnParar;
	}
}
