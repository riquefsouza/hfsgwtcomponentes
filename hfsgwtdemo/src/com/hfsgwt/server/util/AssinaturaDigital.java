package com.hfsgwt.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

/**
 * @author Daniel Freitas
 * 
 *         Classe contendo utilitários para criptografia. Modificado e Adptado
 *         por Henrique Figueiredo de Souza.
 */
public final class AssinaturaDigital {
	private static Logger log = Logger.getLogger(AssinaturaDigital.class);
	private static final String hexDigits = "0123456789abcdef";

	/**
	 * Realiza um digest em um array de bytes através do algoritmo especificado
	 * 
	 * @param input
	 *            - O array de bytes a ser criptografado
	 * @param algoritmo
	 *            - O algoritmo a ser utilizado
	 * @return byte[] - O resultado da criptografia
	 * @throws NoSuchAlgorithmException
	 *             - Caso o algoritmo fornecido não seja válido
	 */
	public static byte[] digest(byte[] input, String algoritmo)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.reset();
		return md.digest(input);
	}

	/**
	 * Converte o array de bytes em uma representação hexadecimal.
	 * 
	 * @param input
	 *            - O array de bytes a ser convertido.
	 * @return Uma String com a representação hexa do array
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			int j = ((int) b[i]) & 0xFF;
			buf.append(hexDigits.charAt(j / 16));
			buf.append(hexDigits.charAt(j % 16));
		}

		return buf.toString();
	}

	/**
	 * Converte uma String hexa no array de bytes correspondente.
	 * 
	 * @param hexa
	 *            - A String hexa
	 * @return O vetor de bytes
	 * @throws IllegalArgumentException
	 *             - Caso a String não seja uma representação haxadecimal válida
	 */
	public static byte[] hexStringToByteArray(String hexa)
			throws IllegalArgumentException {

		// verifica se a String possui uma quantidade par de elementos
		if (hexa.length() % 2 != 0) {
			throw new IllegalArgumentException("String hexa inválida");
		}

		byte[] b = new byte[hexa.length() / 2];

		for (int i = 0; i < hexa.length(); i += 2) {
			b[i / 2] = (byte) ((hexDigits.indexOf(hexa.charAt(i)) << 4) | (hexDigits
					.indexOf(hexa.charAt(i + 1))));
		}

		return b;
	}

	/**
	 * Responsável por remeter dados assinados digitalmente a um destinatário.
	 * Juntamente com a assinatura digital é fornecida uma chave pública.
	 */
	public static AssinaturaDigitalDados remeterDadosAssinados(String mensagem,
			String arquivoProperties, String arquivoChavePublica)
			throws ServicoException {
		AssinaturaDigitalDados da = new AssinaturaDigitalDados();
		try {
			// Gera chaves pública e privada
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			SecureRandom secRan = new SecureRandom();
			keyGen.initialize(512, secRan);
			KeyPair keyP = keyGen.generateKeyPair();
			PublicKey pubKey = keyP.getPublic();
			PrivateKey priKey = keyP.getPrivate();

			// System.out.println("Pubkey: " + pubKey);
			// System.out.println("Serial:" + pubKey.serialVersionUID);

			// Obtem algoritmo para geração da assinatura
			Signature geradorAss = Signature.getInstance("DSA");

			// Inicializar geração
			geradorAss.initSign(priKey);

			// Gerar assinatura
			geradorAss.update(mensagem.getBytes());
			byte[] assinatura = geradorAss.sign();
			String sAssinatura = AssinaturaDigital.byteArrayToHexString(assinatura);

			// Grava a mensagem num arquivo properties
			Properties p = new Properties();
			p.put("mensagem", mensagem);
			p.put("assinatura", sAssinatura);
			p.store(new FileOutputStream(arquivoProperties), null);

			// Serializa a chave pública
			ObjectOutputStream oout = new ObjectOutputStream(
					new FileOutputStream(arquivoChavePublica));
			oout.writeObject(pubKey);
			oout.close();

			da.setMensagem(mensagem);
			da.setArquivoChavePublica(arquivoChavePublica);
			da.setArquivoProperties(arquivoProperties);
			da.setAssinatura(sAssinatura);
			da.setAssinaturaEmByte(assinatura);

		} catch (InvalidKeyException e) {
			throw new ServicoException(log, "Erro chave inválida, "
					+ e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log,
					"Erro sem algoritmo de criptografia, " + e.getMessage());
		} catch (SignatureException e) {
			throw new ServicoException(log, "Erro de assinatura, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao remeter dados assinados, "
							+ e.getMessage());
		}
		return da;
	}

	/**
	 * Responsável por receber dados assinados digitalmente por um remetente.
	 * Juntamente com a assinatura digital é fornecida uma chave pública.
	 */
	public static AssinaturaDigitalDados receberDadosAssinados(
			String arquivoChavePublica, String arquivoProperties)
			throws ServicoException {
		AssinaturaDigitalDados da = new AssinaturaDigitalDados();
		try {
			// Obtêm chave pública
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
					arquivoChavePublica));
			PublicKey pubKey = (PublicKey) oin.readObject();
			oin.close();

			// Cria objeto signature
			Signature sig = Signature.getInstance("DSA");

			// Inicializa Signature com a chave pública
			sig.initVerify(pubKey);

			// Lê arquivo de properties com mensagem e assinatura
			Properties p = new Properties();
			p.load(new FileInputStream(arquivoProperties));

			// Captura mensagem
			String mensagem = (String) p.get("mensagem");
			String sAssinatura = (String) p.get("assinatura");
			// System.out.println("Mensagem: " + mensagem);
			// System.out.println("Assinatura: " + sAssinatura);

			// Captura assinatura
			byte[] assinatura = AssinaturaDigital.hexStringToByteArray((String) p
					.get("assinatura"));

			// valida o dado
			sig.update(mensagem.getBytes());

			boolean bValida = sig.verify(assinatura);

			da.setMensagem(mensagem);
			da.setArquivoChavePublica(arquivoChavePublica);
			da.setArquivoProperties(arquivoProperties);
			da.setAssinatura(sAssinatura);
			da.setAssinaturaEmByte(assinatura);
			da.setAssinaturaValida(bValida);

		} catch (InvalidKeyException e) {
			throw new ServicoException(log, "Erro chave inválida, "
					+ e.getMessage());
		} catch (FileNotFoundException e) {
			throw new ServicoException(log, "Erro arquivo não encontrado, "
					+ e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log,
					"Erro sem algoritmo de criptografia, " + e.getMessage());
		} catch (SignatureException e) {
			throw new ServicoException(log, "Erro de assinatura, "
					+ e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao receber dados assinados, "
							+ e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ServicoException(log, "Erro classe não encontrada, "
					+ e.getMessage());
		}

		return da;
	}

}
