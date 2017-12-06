package com.hfsgwt.client.componentes.richtext;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;

public class HFSRichText extends Composite {

	private RichTextArea area;
	
	public HFSRichText() {
		area = new RichTextArea();
		area.setSize("100%", "14em");
		HFSRichTextToolbar toolbar = new HFSRichTextToolbar(area);
		toolbar.setWidth("100%");
		
		Grid grid = new Grid(2, 1);
		grid.setStyleName("HFSRichText");
		grid.setWidget(0, 0, toolbar);
		grid.setWidget(1, 0, area);
		initWidget(grid);
	}

	public String getTexto() {
		return area.getText();
	}

	public void setTexto(String texto) {
		area.setText(texto);
	}

	public String getHTML() {
		return area.getHTML();
	}

	public void setHTML(String html) {
		area.setHTML(html);
	}
}
