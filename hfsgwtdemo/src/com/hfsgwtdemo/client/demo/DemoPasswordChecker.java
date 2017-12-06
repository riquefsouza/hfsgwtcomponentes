package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.hfsgwt.client.componentes.HFSPasswordChecker;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;

public class DemoPasswordChecker extends Composite {
	private Grid grid;
	private HFSTextBox edtSenha;
	private HFSPasswordChecker passwordChecker;

	public DemoPasswordChecker() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(2, 1);
			grid.setWidget(0, 0, getEdtSenha());
			grid.setWidget(1, 0, getPasswordChecker());
		}
		return grid;
	}

	private HFSTextBox getEdtSenha() {
		if (edtSenha == null) {
			edtSenha = new HFSTextBox(PosicaoRotulo.ACIMA, "Digite a Senha:",
					Formato.SENHA, 30, 30, false);
			edtSenha.getCaixaSenha().addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					getPasswordChecker().validaForcaSenha(getEdtSenha().getTexto());
				}
			});			
		}
		return edtSenha;
	}

	private HFSPasswordChecker getPasswordChecker() {
		if (passwordChecker == null) {
			passwordChecker = new HFSPasswordChecker(
					HFSPasswordChecker.PosicaoRotulo.ACIMA, "Resistência:");
		}
		return passwordChecker;
	}
}
