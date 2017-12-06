package com.hfsgwt.server.temporizacao;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RepetirTempo {

	public void TemporizadorRodarRepetidamente(int nSegundos) {
		int demora = 1000;
		int repetir = nSegundos * 1000;
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println(new Date());
			}
		}, demora, repetir);
	}

	public void TemporizadorRodarCertoTempo(int nSegundos) {
		int numeroMilisegundosNoFuturo = nSegundos * 1000;
		Date tempoParaRodar = new Date(System.currentTimeMillis()
				+ numeroMilisegundosNoFuturo);
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(new Date());
			}
		}, tempoParaRodar);
	}

}
