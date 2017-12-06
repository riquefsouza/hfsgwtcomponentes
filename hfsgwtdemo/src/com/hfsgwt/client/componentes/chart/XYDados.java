package com.hfsgwt.client.componentes.chart;

import com.google.gwt.user.client.rpc.IsSerializable;

public class XYDados implements IsSerializable {
	private Number serieX;
	private Number serieY;

	public XYDados(){
		super();
	}
	
	public XYDados(Number serieX, Number serieY) {
		this.serieX = serieX;
		this.serieY = serieY;
	}

	public Number getSerieX() {
		return serieX;
	}

	public void setSerieX(Number serieX) {
		this.serieX = serieX;
	}

	public Number getSerieY() {
		return serieY;
	}

	public void setSerieY(Number serieY) {
		this.serieY = serieY;
	}
}
