package com.hfsgwtdemo.client.demo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoTextBox extends Composite {
	private Grid grid;
	private HFSTextBox textBox0;
	private HFSTextBox textBox1;
	private HFSTextBox textBox2;
	private HFSTextBox textBox3;
	private HFSTextBox textBox4;
	private HFSTextBox textBox5;
	private HFSTextBox textBox6;
	private HFSTextBox textBox7;
	private HFSTextBox textBox8;
	private HFSTextBox textBox9;
	private HFSTextBox textBox10;
	private HFSTextBox textBox11;

	public DemoTextBox() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(4, 3);
			grid.setCellPadding(5);
			grid.setCellSpacing(5);
			grid.setWidget(0, 0, getTextBox0());
			grid.setWidget(0, 1, getTextBox1());
			grid.setWidget(0, 2, getTextBox2());
			grid.setWidget(1, 0, getTextBox3());
			grid.setWidget(1, 1, getTextBox4());
			grid.setWidget(1, 2, getTextBox5());
			grid.setWidget(2, 0, getTextBox6());
			grid.setWidget(2, 1, getTextBox7());
			grid.setWidget(2, 2, getTextBox8());
			grid.setWidget(3, 0, getTextBox9());
			grid.setWidget(3, 1, getTextBox10());
			grid.setWidget(3, 2, getTextBox11());
		}
		return grid;
	}

	private HFSTextBox getTextBox0() {
		if (textBox0 == null) {
			textBox0 = new HFSTextBox(PosicaoRotulo.ACIMA, "Somente Letra",
					Formato.LETRA, 10, 10, true);
			textBox0.setCaixa(HFSTextBox.Caixa.MAIUSCULA);
//			textBox0.setPermitirAcentuacao(false);
			textBox0.setPermitirCedilha(true);
//			textBox0.setPermitirCaractereEspecial(false);
		}
		return textBox0;
	}

	private HFSTextBox getTextBox1() {
		if (textBox1 == null) {
			textBox1 = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Somente Número",
					Formato.NUMERO, 10, 10, false);
		}
		return textBox1;
	}

	private HFSTextBox getTextBox2() {
		if (textBox2 == null) {
			textBox2 = new HFSTextBox(PosicaoRotulo.ACIMA, "Moeda",
					Formato.MOEDA, 10, 10, false);
		}
		return textBox2;
	}

	private HFSTextBox getTextBox3() {
		if (textBox3 == null) {
			textBox3 = new HFSTextBox(PosicaoRotulo.ESQUERDA, "Decimal",
					Formato.DECIMAL, 10, 10, true);
		}
		return textBox3;
	}

	private HFSTextBox getTextBox4() {
		if (textBox4 == null) {
			textBox4 = new HFSTextBox(PosicaoRotulo.ACIMA, "Data/Hora",
					Formato.DATAHORA, 0, 0, false);
		}
		return textBox4;
	}

	private HFSTextBox getTextBox5() {
		if (textBox5 == null) {
			textBox5 = new HFSTextBox(PosicaoRotulo.ACIMA, "CPF", Formato.CPF,
					0, 0, false);
		}
		return textBox5;
	}

	private HFSTextBox getTextBox6() {
		if (textBox6 == null) {
			textBox6 = new HFSTextBox(PosicaoRotulo.ACIMA, "CNPJ",
					Formato.CNPJ, 0, 0, false);
		}
		return textBox6;
	}

	private HFSTextBox getTextBox7() {
		if (textBox7 == null) {
			textBox7 = new HFSTextBox(PosicaoRotulo.ACIMA, "CEP", Formato.CEP,
					0, 0, false);
		}
		return textBox7;
	}

	private HFSTextBox getTextBox8() {
		if (textBox8 == null) {
			textBox8 = new HFSTextBox(PosicaoRotulo.ACIMA, "Email",
					Formato.EMAIL, 20, 20, false);
		}
		return textBox8;
	}

	private HFSTextBox getTextBox9() {
		if (textBox9 == null) {
			textBox9 = new HFSTextBox(PosicaoRotulo.ACIMA, "Hora",
					Formato.HORA, 0, 0, false);
		}
		return textBox9;
	}

	private HFSTextBox getTextBox10() {
		if (textBox10 == null) {
			textBox10 = new HFSTextBox(PosicaoRotulo.ACIMA,
					"Data com calendario", Formato.DATA_CALENDARIO, 10, 10, false);
		}
		return textBox10;
	}

	private HFSTextBox getTextBox11() {
		if (textBox11 == null) {
			textBox11 = new HFSTextBox(PosicaoRotulo.ACIMA,
					"Decimal com calculadora", Formato.DECIMAL_CALCULADORA, 10,
					10, false);
		}
		return textBox11;
	}

}
