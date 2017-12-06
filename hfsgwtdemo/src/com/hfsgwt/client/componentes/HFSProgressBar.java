package com.hfsgwt.client.componentes;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.hfsgwt.client.componentes.imagens.HFSImages;

public class HFSProgressBar extends Composite {

	//------------------------------------------------------------------------------
	private class HFSProgressBarAnimacao extends Animation {

		private HFSProgressBar pbar;
		private int duracao;
		private long anterior;

		public HFSProgressBarAnimacao(HFSProgressBar pbar, int duracao) {
			this.anterior = -1;
			this.duracao = duracao;
			this.pbar = pbar;
		}

		@Override
		protected void onComplete() {
			super.onComplete();
			if (animacaoHandler != null) {
				animacaoHandler.onAnimacaoCompletada();
			}
		}

//		@Override
//		protected void onStart() {
//			super.onStart();
//		}
//
//		@Override
//		protected void onCancel() {
//			super.onCancel();
//		}

		@Override
		protected void onUpdate(double progress) {
			double tempo = progress * this.duracao;
			long segundo = (long) tempo;
			
			if (anterior != segundo){
				pbar.setProgresso(segundo);
				anterior = segundo;
				
			}
		}

	}
	//------------------------------------------------------------------------------	
	
	private static HFSImages img = (HFSImages) GWT.create(HFSImages.class);

	public interface AnimacaoHandler extends EventHandler {
		void onAnimacaoCompletada();
	}
	
	private AnimacaoHandler animacaoHandler;
	
	private AbsolutePanel absolutePanel;
	private Label labPercentual;
	private HFSProgressBarAnimacao animacao;

	private static final int OFFSET_IMAGEM = 2; // 2px
	private static final int LARGURA_IMAGEM = 6; // 6px
	private static final int TAMANHO_IMAGEM = LARGURA_IMAGEM + OFFSET_IMAGEM;

	private long minValor;
	private long maxValor;
	private long curValor;
	private boolean mostrarTexto;
	private int largura;
	private int duracao;
	private boolean primeiraVez;

	public HFSProgressBar(int largura, boolean mostrarPercentual) {
		this.minValor = 0;
		this.maxValor = 100;
		this.curValor = 0;
		this.mostrarTexto = mostrarPercentual;
		this.largura = largura;
		this.primeiraVez = true;
		initComponents();
	}

	public HFSProgressBar(int largura, boolean mostrarPercentual,
			int duracaoAnimacao) {
		this(largura, mostrarPercentual);
		this.duracao = duracaoAnimacao;
		animacao = new HFSProgressBarAnimacao(this, (int) this.getMaxValor());
	}

	private void initComponents() {
		initWidget(getAbsolutePanel());		
	}

	private void addLabPercentual(AbsolutePanel absPanel) {
		int larguraFonte = 5;

		int tamRotulo = getLabPercentual().getText().length();
		int larguraRotulo = tamRotulo * larguraFonte;
		int X = ((largura - larguraRotulo) / 2);

		absolutePanel.add(getLabPercentual(), X, 0);
	}

	private AbsolutePanel getAbsolutePanel() {
		if (absolutePanel == null) {
			absolutePanel = new AbsolutePanel();
			absolutePanel.setStyleName("HFSProgressBar-Panel");
			absolutePanel.setSize(this.largura + "px", "18px");
			if (mostrarTexto) {
				addLabPercentual(absolutePanel);
			}

		}
		return absolutePanel;
	}

	private int Trunc(double valor) {
		return (int) Math.floor(valor);
	}

	// Esta funcao resolve para X na equacao "x é y% de z".
	private long SolveForX(long Y, long Z) {
		return (long) Trunc(Z * (Y * 0.01));
	}

	// Esta funcao resolve para y na equacao "x é y% de z".
	private long SolveForY(long X, long Z) {
		if (Z == 0)
			return 0;
		else
			return (long) Trunc((X * 100.0) / Z);
	}

	private long GetPercentDone() {
		return SolveForY(curValor - minValor, maxValor - minValor);
	}

	private Label getLabPercentual() {
		if (labPercentual == null) {
			labPercentual = new Label("0%");
			labPercentual.setStyleName("HFSProgressBar-Label");
			labPercentual.setWordWrap(false);
			labPercentual
					.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			labPercentual.setSize("33px", "18px");
		}
		return labPercentual;
	}

	public long getProgresso() {
		return curValor;
	}

	public void setProgresso(long valor) {
		if (valor == 0){
			this.primeiraVez = true;
		}
		
		long TempPercent = GetPercentDone();

		if (valor < minValor)
			valor = minValor;
		else if (valor > maxValor)
			valor = maxValor;

		if (curValor != valor) {
			curValor = valor;
			if (TempPercent != GetPercentDone()) {
				atualize();
			}
		}
	}

	public long getMinValor() {
		return minValor;
	}

	public void setMinValor(long valor) {
		if (valor != minValor) {
			if (valor > maxValor) {
				return;
			}
			minValor = valor;
			if (curValor < valor) {
				curValor = valor;
			}
			atualize();
		}
	}

	public long getMaxValor() {
		return maxValor;
	}

	public void setMaxValor(long valor) {
		if (valor != maxValor) {
			if (valor < minValor) {
				return;
			}
			maxValor = valor;
			if (curValor > valor) {
				curValor = valor;
			}
			atualize();
		}
	}

	public void addProgresso(long valor) {
		setProgresso(curValor + valor);
	}

	private void atualize() {
		int tamPreencher;
		if (mostrarTexto) {
			// NumberFormat.getPercentFormat();
			getLabPercentual().setText(getPercentualConcluido() + "%");
		}

		// int W = largura + 1;
		int W = this.getOffsetWidth() + 1;
		// int H = this.getOffsetHeight() + 1;

		tamPreencher = (int) SolveForX(getPercentualConcluido(), W);
		if (tamPreencher > W) {
			tamPreencher = W;
		}
		if (tamPreencher > 0) {
			int qtdImagens = largura / TAMANHO_IMAGEM;
			int proporcao = (int) ((qtdImagens * getPercentualConcluido()) / 100) + 1;
			absolutePanel.clear();
			int posicaoImagem = 0;
			for (int i = 0; i < proporcao; i++) {
				absolutePanel.add(new Image(img.barraProgresso()),
						posicaoImagem, 0);
				posicaoImagem += TAMANHO_IMAGEM;

				if (mostrarTexto) {
					if (i == (proporcao-1)) {
						addLabPercentual(absolutePanel);
					}
				}
			}
		} else {
			absolutePanel.clear();

			if (mostrarTexto) {
				if (this.primeiraVez){
					addLabPercentual(absolutePanel);
					this.primeiraVez = false;
				}			
			}		
		}		
	}

	public long getPercentualConcluido() {
		return GetPercentDone();
	}

	public boolean isMostrarTexto() {
		return mostrarTexto;
	}

	public void setMostrarTexto(boolean mostrarTexto) {
		this.mostrarTexto = mostrarTexto;
	}

	public void rodarAnimacao() {
		this.setProgresso(0);
		animacao.run((int) (this.getMaxValor() * duracao));
	}

	public void pararAnimacao() {
		animacao.cancel();
		this.setProgresso(0);
	}

	public void addAnimacaoHandler(AnimacaoHandler handler) {
		this.animacaoHandler = handler;
	}
}

