package com.hfsgwt.client.componentes.map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;

public class HFSMap extends Composite {

	private int largura;
	private int altura;
	private Frame frame;

	public HFSMap(int largura, int altura) {
		if (largura < 630)
			this.largura = 630;
		else
			this.largura = largura;
		if (altura < 400)
			this.altura = 400;
		else
			this.altura = altura;
		initComponents();
		// iniciarFuncoes();
	}

	private void initComponents() {
		initWidget(getFrame());
	}

	private Frame getFrame() {
		if (frame == null) {
			frame = new Frame();
			frame.setStyleName("HFSMap");
			frame.setSize(this.largura+"px", this.altura+"px");
		}
		return frame;
	}

	public void mostrarMapa(double latitude, double longitude) {
		String url = GWT.getHostPageBaseURL() + "mapa?latitude=" + latitude
		+ "&longitude=" + longitude;
		this.getFrame().setUrl(url);
	
	}

//	public void iniciarFuncoes() {
//		// setShowTrigger(this);
//		// setLatitudeLongitude();
//	}

	// chamar java script
	// public native String getFirstName()/*-{
	// return $wnd.frames[0].first_name;
	// }-*/;
	//
	// public native String setFirstName(String nome)/*-{
	// $wnd.frames[0].first_name = nome;
	// }-*/;
	//
	// public void runApp() {
	// Window.alert("I am a GWT function");
	// }
	//
	// // javascript chamando este codigo
	// public native void setShowTrigger(HFSMap x)/*-{
	// $wnd.mostrarAplicativo = function () {
	//
	// x.@com.hfsgwt.client.componentes.HFSMap::runApp()();
	//
	// };
	// }-*/;

//	public native String getEndereco()/*-{
//		return $wnd.document.getElementById("endereco").value;
//	}-*/;
//
//	public native void setEndereco(String endereco)/*-{
//		$wnd.document.getElementById("endereco").value = endereco;
//	}-*/;

}
