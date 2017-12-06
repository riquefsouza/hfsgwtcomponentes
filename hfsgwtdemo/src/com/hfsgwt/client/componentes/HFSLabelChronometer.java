package com.hfsgwt.client.componentes;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

public class HFSLabelChronometer extends Composite {
	
	//------------------------------------------------------------------------------
	private class HFSCronometroAnimacao extends Animation {

		private Label lab;
		private int duracao;

		public HFSCronometroAnimacao(Label lab, int duracao) {
			this.duracao = duracao;
			this.lab = lab;
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
			lab.setText(FormatSecsToHMS(segundo));
		}

		private String FormatSecsToHMS(long Secs) {
			String shora, smin, sseg;
			long Hrs, Min;

			Hrs = Secs / 3600;
			Secs = Secs % 3600;
			Min = Secs / 60;
			Secs = Secs % 60;

			if (Hrs < 10)
				shora = "0" + Hrs;
			else
				shora = Long.toString(Hrs);

			if (Min < 10)
				smin = "0" + Min;
			else
				smin = Long.toString(Min);

			if (Secs < 10)
				sseg = "0" + Secs;
			else
				sseg = Long.toString(Secs);

			return shora + ":" + smin + ":" + sseg;
		}

	}	
	//------------------------------------------------------------------------------
	
	public interface AnimacaoHandler extends EventHandler {
		void onAnimacaoCompletada();
	}
	
	private AnimacaoHandler animacaoHandler;
	private Label labCronometro;
	private HFSCronometroAnimacao animacao;
	private int duracao;

	public HFSLabelChronometer(int duracao) {
		this.duracao = duracao;
		initComponents();
		animacao = new HFSCronometroAnimacao(getLabCronometro(), duracao);
	}

	private void initComponents() {
		initWidget(getLabCronometro());
	}

	private Label getLabCronometro() {
		if (labCronometro == null) {
			labCronometro = new Label("00:00:00");
			labCronometro.setStyleName("HFSLabelChronometer");
			labCronometro.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		}
		return labCronometro;
	}

	public void rodar() {
		animacao.run(duracao * 1000);
	}

	public void parar() {
		animacao.cancel();
	}

	public void addAnimacaoHandler(AnimacaoHandler handler) {
		this.animacaoHandler = handler;
	}
}

