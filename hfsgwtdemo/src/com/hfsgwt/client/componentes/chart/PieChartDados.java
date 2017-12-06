package com.hfsgwt.client.componentes.chart;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PieChartDados implements IsSerializable {
	private String rotulo;
	private Number dado;

	public PieChartDados(){
		super();
	}
	
	public PieChartDados(String rotulo, Number dado) {
		this.rotulo = rotulo;
		this.dado = dado;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public Number getDado() {
		return dado;
	}

	public void setDado(Number dado) {
		this.dado = dado;
	}
}
