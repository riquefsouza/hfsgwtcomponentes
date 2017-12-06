package com.hfsgwtdemo.client.demo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.hfsgwt.client.componentes.HFSTextBox;
import com.hfsgwt.client.componentes.IHFSRotulo.PosicaoRotulo;
import com.hfsgwt.client.componentes.HFSTextBox.Formato;
import com.hfsgwt.client.componentes.chat.ChatUsuario;
import com.hfsgwt.client.componentes.chat.HFSDialogChat;

public class DemoDialogChat extends Composite {
	private Grid grid;
	private HFSTextBox edtUsuario;
	private HFSTextBox edtSenha;
	private Button btnMostrar;

	public DemoDialogChat() {
		initComponents();
	}

	private void initComponents() {
		initWidget(getGrid());
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(3, 1);
			grid.setCellSpacing(5);
			grid.setWidget(0, 0, getEdtUsuario());
			grid.setWidget(1, 0, getEdtSenha());
			grid.setWidget(2, 0, getBtnMostrar());
			grid.getCellFormatter().setHorizontalAlignment(2, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(1, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setHorizontalAlignment(0, 0,
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return grid;
	}

	private HFSTextBox getEdtUsuario() {
		if (edtUsuario == null) {
			edtUsuario = new HFSTextBox(PosicaoRotulo.ACIMA, "Usuário:",
					Formato.PADRAO, 30, 30, false);
			edtUsuario.setTexto("riquefsouza@gmail.com");
		}
		return edtUsuario;
	}

	private HFSTextBox getEdtSenha() {
		if (edtSenha == null) {
			edtSenha = new HFSTextBox(PosicaoRotulo.ACIMA, "Senha:",
					Formato.SENHA, 20, 20, false);
		}
		return edtSenha;
	}

	private Button getBtnMostrar() {
		if (btnMostrar == null) {
			btnMostrar = new Button("Mostrar");
			btnMostrar.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ChatUsuario usu = new ChatUsuario();
					usu.setUsuario(getEdtUsuario().getTexto());
					usu.setSenha(getEdtSenha().getTexto());
					HFSDialogChat dlgChat = new HFSDialogChat(389, 260, usu);
					dlgChat.center();
					dlgChat.show();
					dlgChat.conectar();
				}
			});
		}
		return btnMostrar;
	}
}
