package com.hfsgwt.client.componentes;

public interface IHFSRotulo {

	public enum PosicaoRotulo {
		ACIMA, ESQUERDA
	}

	public enum AlinhamentoRotulo {
		PADRAO, ESQUERDA, DIREITA, CENTRO
	}

	public int getLarguraRotulo();

	public void setLarguraRotulo(int larguraRotulo);

	public AlinhamentoRotulo getAlinhamentoRotulo();

	public void setAlinhamentoRotulo(AlinhamentoRotulo alinhamentoRotulo);

}
