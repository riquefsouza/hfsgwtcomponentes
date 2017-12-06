package com.hfsgwt.server.scanner;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;
import com.sun.jna.Native;

public final class RotinasScanner {
	private static Logger log = Logger.getLogger(RotinasScanner.class);
	private static RotinasScanner instancia;
	private EZTwainLibrary biblioteca;

	private RotinasScanner() {
		super();
	}

	public static RotinasScanner getInstancia() {
		if (instancia == null) {
			instancia = new RotinasScanner();
		}
		return instancia;
	}

	public void lerBiblioteca(String caminho) {
		biblioteca = (EZTwainLibrary) Native.loadLibrary(
				caminho + "EZTW32.dll", EZTwainLibrary.class);
	}

	public void registrar() {
		String versao = "1.00";
		String autor = "by HFS";
		String familia = "HFS";
		String produto = "HFS GWT Componentes";

		biblioteca.TWAIN_RegisterApp(1, 0, EZTwainLibrary.TWLG_POR,
				EZTwainLibrary.TWCY_BRAZIL, versao.toCharArray(), autor
						.toCharArray(), familia.toCharArray(), produto
						.toCharArray());
	}

	public boolean abrirScannerPadrao() {
		return (biblioteca.TWAIN_OpenDefaultSource() == 1);
	}

	public boolean obterNativo() {
		return (biblioteca.TWAIN_AcquireNative(0, 0) == 1);
	}

	public boolean obterImagemParaClipboard() {
		return (biblioteca.TWAIN_AcquireToClipboard(0, 0) == 1);
	}

	// 0 success
	// -1 Acquire failed OR user cancelled File Save dialog
	// -2 file open error (invalid path or name, or access denied)
	// -3 (weird) unable to lock DIB - probably an invalid handle.
	// -4 writing BMP data failed, possibly output device is full
	public boolean obterImagemParaArquivo(String arquivo)
			throws ServicoException {
		int resultado;
		resultado = biblioteca
				.TWAIN_AcquireToFilename(0, arquivo.toCharArray());

		if (resultado == 0)
			return true;
		else if (resultado == -1)
			throw new ServicoException(log,
					"Falha ao obter imagem ou o usuário cancelou o diálogo de salvar arquivo.");
		else if (resultado == -2)
			throw new ServicoException(log,
					"Falha ao abrir arquivo (caminho ou nome inválido, ou acesso negado).");
		else if (resultado == -3)
			throw new ServicoException(
					log,
					"(estranho) não foi possível travar DIB - provavelmente o manuseador é inválido.");
		else if (resultado == -4)
			throw new ServicoException(
					log,
					"Falha ao escreve arquivo bitmap, possivelmente dispositivo de saída está cheio.");
		else
			return false;
	}

	public void fecharScanner() {
		biblioteca.TWAIN_CloseSource();
	}
}
