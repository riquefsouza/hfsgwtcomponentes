package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class HFSScanner extends Composite {

	private int largura;
	private int altura;
	private HTML html;
	
	public HFSScanner(int largura, int altura) {
		if (largura < 300)
			this.largura = 300;
		else
			this.largura = largura;
		if (altura < 200)
			this.altura = 200;
		else
			this.altura = altura;
		initComponents();
	}
	
	private void initComponents() {
		initWidget(getHtml());
	}

	private HTML getHtml() {
		if (html == null) {
			html = new HTML(getScannerApplet(), true);
			html.setStyleName("HFSScanner");
			html.setSize(this.largura + "px", this.altura + "px");
		}
		return html;
	}
	
	private String getScannerApplet() {
		int naltura = (altura * 85) / 100;
		return "<html><body>" +
		"<applet code='com/hfsgwt/server/scanner/ScannerApplet' width='"+this.largura+"px' height='"+naltura+"px' "+ 
		"archive='scanner/lib/log4j.jar,scanner/lib/platform.jar,scanner/lib/jna.jar,scanner/lib/HFSScanner.jar,scanner/lib/commons-codec-1.4.jar'> "+
		"<param name='caminhodll' value='c:/windows/system32/' /> "+
		"Desculpe, Seu navegador não suporta Java applet! "+
		"</applet>" +
		"<font color='red' size=1><b>SOMENTE FUNCIONA NOS SISTEMAS OPERACIONAIS BASEADOS NO WINDOWS.</b></font><br> "+		
		"<font color='blue' size=1><b>Para executar o scaneamento, descompacte e execute este <a href='"+GWT.getModuleName()+"/scanner.zip'>arquivo</a> e reinicie o navegador.</b></font> "+		
		"</body></html> ";
	}
	
	
	
}
