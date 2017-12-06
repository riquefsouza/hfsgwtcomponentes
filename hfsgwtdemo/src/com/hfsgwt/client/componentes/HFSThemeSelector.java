package com.hfsgwt.client.componentes;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class HFSThemeSelector extends Composite {
	private HorizontalPanel horizontalPanel;

	private String[] temasEstilo = { "standard", "chrome", "dark" };

	private String temaAtual;

	public interface BtnClickHandler extends EventHandler {
		void onBtnTemaClick();
	}

	private BtnClickHandler btnClickHandler;

	private static class ThemeButton extends ToggleButton {
		private static List<ThemeButton> allButtons = null;

		private String theme;

		public ThemeButton(String theme) {
			super();
			this.theme = theme;
			addStyleName("HFSThemeSelector-" + theme);

			if (allButtons == null) {
				allButtons = new ArrayList<ThemeButton>();
				setDown(true);
			}
			allButtons.add(this);
		}

		public String getTheme() {
			return theme;
		}

		@Override
		protected void onClick() {
			if (!isDown()) {
				for (ThemeButton button : allButtons) {
					if (button != this) {
						button.setDown(false);
					}
				}

				super.onClick();
			}
		}
	}

	public HFSThemeSelector() {
		this.temaAtual = "";
		initComponents();
	}

	private void initComponents() {
		initWidget(getHorizontalPanel());
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			criarBotoes(horizontalPanel, temasEstilo);
		}
		return horizontalPanel;
	}

	public void addBtnClickHandler(BtnClickHandler handler) {
		this.btnClickHandler = handler;
	}

	private void criarBotoes(HorizontalPanel painel, String[] temasEstilo) {
		for (int i = 0; i < temasEstilo.length; i++) {
			final ThemeButton button = new ThemeButton(temasEstilo[i]);
			painel.add(button);
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					temaAtual = button.getTheme();
					if (btnClickHandler != null) {
						btnClickHandler.onBtnTemaClick();
					}
				}
			});
		}
	}

	public String getTemaAtual() {
		return temaAtual;
	}

	public void setTemaAtual(String temaAtual) {
		this.temaAtual = temaAtual;
	}
}
