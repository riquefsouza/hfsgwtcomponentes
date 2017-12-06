package com.hfsgwt.client.componentes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.hfsgwt.client.HfsgwtService;
import com.hfsgwt.client.HfsgwtServiceAsync;
import com.hfsgwt.client.componentes.util.HFSCrypt;
import com.hfsgwt.client.componentes.util.HFSUtil;

public class HFSPasswordChecker extends Composite implements IHFSRotulo {

	private final HfsgwtServiceAsync servico = GWT.create(HfsgwtService.class);

	private HorizontalPanel horizontalPanel;
	private VerticalPanel verticalPanel;
	private HTML rotulo;
	private Grid grid;

	public enum ForcaSenha {
		NENHUMA, MUITO_FRACA, FRACA, MEDIOCRE, FORTE, MUITO_FORTE
	}

	private boolean mostrarRotulo;
	private int larguraRotulo;
	private AlinhamentoRotulo alinhamentoRotulo;
	private PosicaoRotulo posicao;

	public HFSPasswordChecker(PosicaoRotulo posicao, String rotulo) {
		this.mostrarRotulo = true;
		this.posicao = posicao;
		this.alinhamentoRotulo = AlinhamentoRotulo.PADRAO;		

		initComponents(posicao);
		this.setRotulo(rotulo);
		this.larguraRotulo = this.getRotulo().getOffsetWidth();
	}

	private void initComponents(PosicaoRotulo posicao) {
		if (posicao == PosicaoRotulo.ACIMA) {
			initWidget(getVerticalPanel());
		} else if (posicao == PosicaoRotulo.ESQUERDA) {
			initWidget(getHorizontalPanel());
		}
	}

	private HorizontalPanel getHorizontalPanel() {
		if (horizontalPanel == null) {
			horizontalPanel = new HorizontalPanel();
			horizontalPanel.add(getRotulo());
			horizontalPanel.add(getGrid());
			horizontalPanel.setCellVerticalAlignment(getGrid(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			horizontalPanel.setCellHorizontalAlignment(getGrid(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return horizontalPanel;
	}

	private VerticalPanel getVerticalPanel() {
		if (verticalPanel == null) {
			verticalPanel = new VerticalPanel();
			verticalPanel.add(getRotulo());
			verticalPanel.add(getGrid());
			verticalPanel.setCellVerticalAlignment(getGrid(),
					HasVerticalAlignment.ALIGN_MIDDLE);
			verticalPanel.setCellHorizontalAlignment(getGrid(),
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		return verticalPanel;
	}

	private void setRotulo(String rotulo) {
		getRotulo().setHTML(rotulo);
	}

	private HTML getRotulo() {
		if (rotulo == null) {
			rotulo = new HTML("Resistência:");
		}
		return rotulo;
	}

	public boolean isMostrarRotulo() {
		return mostrarRotulo;
	}

	public void setMostrarRotulo(boolean mostrarRotulo) {
		this.mostrarRotulo = mostrarRotulo;
		this.getRotulo().setVisible(this.mostrarRotulo);
	}

	public int getLarguraRotulo() {
		return larguraRotulo;
	}

	public void setLarguraRotulo(int larguraRotulo) {
		this.larguraRotulo = larguraRotulo;
		this.getRotulo().setWidth(larguraRotulo + "px");
	}

	private Grid getGrid() {
		if (grid == null) {
			grid = new Grid(1, 4);
			grid.setSize("320px", "20px");
			for (int ncol = 0; ncol < 4; ncol++) {
				grid.getCellFormatter().setWidth(0, ncol, "25%");
			}
			mudaForcaSenha("", "");
		}
		return grid;
	}

	private void mudaLayout(String[] texto, String[] estilo) {
		for (int ncol = 0; ncol < 4; ncol++) {
			grid.setText(0, ncol, texto[ncol]);
			grid.getCellFormatter().setHorizontalAlignment(0, ncol,
					HasHorizontalAlignment.ALIGN_CENTER);
			grid.getCellFormatter().setStyleName(0, ncol,
					"HFSPasswordChecker-" + estilo[ncol]);
		}
	}

	private void mudaForcaSenha(String forca, String senha) {
		if (senha.trim().length() == 0) {
			mudaLayout(new String[] { "Não", "Classificada", "", "" },
					new String[] { "prata", "prata", "prata", "prata" });
		} else {
			switch (ForcaSenha.valueOf(forca)) {
			case NENHUMA:
				mudaLayout(new String[] { "Nenhuma", "", "", "" },
						new String[] { "prata", "prata", "prata", "prata" });
				break;
			case MUITO_FRACA:
				mudaLayout(new String[] { "Fraca", "", "", "" }, new String[] {
						"vermelho", "prata", "prata", "prata" });
				break;
			case FRACA:
				mudaLayout(new String[] { "Fraca", "", "", "" }, new String[] {
						"vermelho", "prata", "prata", "prata" });
				break;
			case MEDIOCRE:
				mudaLayout(new String[] { "", "Média", "", "" }, new String[] {
						"amarelo", "amarelo", "prata", "prata" });
				break;
			case FORTE:
				mudaLayout(new String[] { "", "", "Forte", "" }, new String[] {
						"verde", "verde", "verde", "prata" });
				break;
			case MUITO_FORTE:
				mudaLayout(new String[] { "", "", "", "MELHOR" }, new String[] {
						"verde", "verde", "verde", "verde" });
				break;
			}
		}
	}

	public void validaForcaSenha(final String senha) {
		if (senha.trim().length() == 0) {
			mudaLayout(new String[] { "Não", "Classificada", "", "" },
					new String[] { "prata", "prata", "prata", "prata" });
		} else {
			servico.validaForcaSenha(HFSCrypt
					.criptografar(senha, (short) 12345),
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							HFSUtil.mostrarFalha(caught, this.getClass(),
									"validaForcaSenha");
						}
						public void onSuccess(String forcaQualificada) {
							mudaForcaSenha(forcaQualificada, senha);
						}
					});
		}
	}

	public AlinhamentoRotulo getAlinhamentoRotulo() {
		return alinhamentoRotulo;
	}
	
	public void setAlinhamentoRotulo(AlinhamentoRotulo alinhamentoRotulo) {
		this.alinhamentoRotulo = alinhamentoRotulo;
		switch (alinhamentoRotulo) {
		case ESQUERDA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_LEFT);
			break;
		case CENTRO:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_CENTER);
			break;
		case DIREITA:
			if (posicao == PosicaoRotulo.ACIMA)
				verticalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			else
				horizontalPanel.setCellHorizontalAlignment(getRotulo(), HasHorizontalAlignment.ALIGN_RIGHT);
			break;
		}
	}
}
