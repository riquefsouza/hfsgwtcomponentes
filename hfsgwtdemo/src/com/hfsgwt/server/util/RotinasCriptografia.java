package com.hfsgwt.server.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

public final class RotinasCriptografia {
	private static Logger log = Logger.getLogger(RotinasCriptografia.class);
	private static RotinasCriptografia instancia;

	public enum GeradorParChaves {
		DSA, RSA, DH, DiffieHellman
	}

	public enum GeradorChave {
		Blowfish, Rijndael, DESede, ARCFOUR, RC2, RC4, DES, AES, TripleDES
	}

	private Cipher ecipher;
	private Cipher dcipher;
	private boolean iniciado;

	private RotinasCriptografia() {
		super();
		this.iniciado = false;
	}

	public static RotinasCriptografia getInstancia() {
		if (instancia == null) {
			instancia = new RotinasCriptografia();
		}
		return instancia;
	}

	public void iniciar(GeradorChave geradorChave) throws ServicoException {
		try {
			SecretKey chave = KeyGenerator.getInstance(geradorChave.name())
					.generateKey();

			ecipher = Cipher.getInstance(geradorChave.name());
			dcipher = Cipher.getInstance(geradorChave.name());
			ecipher.init(Cipher.ENCRYPT_MODE, chave);
			dcipher.init(Cipher.DECRYPT_MODE, chave);
		} catch (InvalidKeyException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (NoSuchAlgorithmException ex) {
			throw new ServicoException(log, ex.getMessage());
		}
		this.iniciado = true;
	}

	public String encriptar(String str) throws ServicoException {
		if (!this.iniciado) {
			throw new ServicoException(log, "Criptografia não iniciada.");
		}
		try {
			// Codifica a String usando UTF-8
			byte[] utf8 = str.getBytes("UTF-8");

			// Encripta
			byte[] enc = ecipher.doFinal(utf8);

			// Codifica os bytes usando Base64
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (BadPaddingException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (UnsupportedEncodingException ex) {
			throw new ServicoException(log, ex.getMessage());
		}
	}

	public String decriptar(String str) throws ServicoException {
		if (!this.iniciado) {
			throw new ServicoException(log, "Criptografia não iniciada.");
		}
		try {
			// Decodifica na base64 os bytes capturados
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

			// Decripta
			byte[] utf8 = dcipher.doFinal(dec);

			// Decodifica usando UTF-8
			return new String(utf8, "UTF-8");
		} catch (BadPaddingException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (IllegalBlockSizeException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (UnsupportedEncodingException ex) {
			throw new ServicoException(log, ex.getMessage());
		} catch (IOException ex) {
			throw new ServicoException(log, ex.getMessage());
		}
	}

	public String[] listarGeradoresChave() {
		return getServicoCriptografia("KeyGenerator");
	}

	public String[] listarGeradoresParChaves() {
		return getServicoCriptografia("KeyPairGenerator");
	}

	public String[] getTodosServicosCriptografia() {
		Set<Object> result = new HashSet<Object>();
		// All all providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0];
				if (key.startsWith("Alg.Alias.")) {
					// Strip the alias
					key = key.substring(10);
				}
				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public String[] getServicoCriptografia(String serviceType) {
		Set<Object> result = new HashSet<Object>();
		// All all providers
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			// Get services provided by each provider
			Set<Object> keys = providers[i].keySet();
			for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
				String key = (String) it.next();
				key = key.split(" ")[0];
				if (key.startsWith(serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 1));
				} else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
					// This is an alias
					result.add(key.substring(serviceType.length() + 11));
				}
			}
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	public long gerarNumeroRandomSeguro(int tamanho) throws ServicoException {
		try {

			if (tamanho > Long.SIZE)
				throw new ServicoException(log,
						"Tamanho maior do que Long.SIZE");

			// Create a secure random number generator
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			// // Get 1024 random bits
			// byte[] bytes = new byte[1024 / 8];
			// sr.nextBytes(bytes);
			byte[] seed = sr.generateSeed(tamanho);
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(seed);
			return sr.nextLong();
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	/*
	 * The DSA requires three parameters to create a key pair – the prime (P),
	 * the subprime (Q), and the base (G). These three values are used to create
	 * a private key (called X) and a public key (called Y). This example
	 * creates a PrivateKey and PublicKey from a set of DSA parameters.
	 */
	public void criarChavesDSA(CriptografiaDados params)
			throws ServicoException {
		try {
			// Obtain the DSA parameters;
			// see Getting the Digital Signature Algorithm (DSA) Parameters of a
			// Key Pair

			// Create the DSA key factory
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			// Create the DSA private key
			KeySpec privateKeySpec = new DSAPrivateKeySpec(params
					.getXChavePrivadaDSA(), params.getPrimoDSA(), params
					.getSubPrimoDSA(), params.getBaseDSA());
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
			params.setChavePrivada(privateKey);
			// Create the DSA public key
			KeySpec publicKeySpec = new DSAPublicKeySpec(params
					.getYChavePublicaDSA(), params.getPrimoDSA(), params
					.getSubPrimoDSA(), params.getBaseDSA());
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			params.setChavePublica(publicKey);
		} catch (InvalidKeySpecException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public CriptografiaDados getChavesDSA() throws ServicoException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			DSAPrivateKey privateKey = (DSAPrivateKey) keypair.getPrivate();
			DSAPublicKey publicKey = (DSAPublicKey) keypair.getPublic();

			// Get p, q, g; they are the same for both private and public keys
			DSAParams dsaParams = privateKey.getParams();
			BigInteger primo = dsaParams.getP();
			BigInteger subprimo = dsaParams.getQ();
			BigInteger base = dsaParams.getG();

			// Get the private key's X
			BigInteger chavePrivada = privateKey.getX();

			// Get the public key's Y
			BigInteger chavePublica = publicKey.getY();

			params.setPrimoDSA(primo);
			params.setSubPrimoDSA(subprimo);
			params.setBaseDSA(base);
			params.setXChavePrivadaDSA(chavePrivada);
			params.setYChavePublicaDSA(chavePublica);
			return params;
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public CriptografiaDados geraParChaves(GeradorParChaves geradorParChaves,
			int tamanhoChave) throws ServicoException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(geradorParChaves.name());
			keyGen.initialize(tamanhoChave);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

			// Generate a 576-bit DH key pair
			// keyGen = KeyPairGenerator.getInstance("DH");
			// keyGen.initialize(576);
			// keypair = keyGen.genKeyPair();
			// privateKey = keypair.getPrivate();
			// publicKey = keypair.getPublic();

			// Generate a 1024-bit RSA key pair
			// keyGen = KeyPairGenerator.getInstance("RSA");
			// keyGen.initialize(1024);
			// keypair = keyGen.genKeyPair();
			// privateKey = keypair.getPrivate();
			// publicKey = keypair.getPublic();
			params.setChavePrivada(privateKey);
			params.setChavePublica(publicKey);
			return params;
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public CriptografiaDados getParChaves(GeradorParChaves geradorParChaves,
			int tamanhoChave) throws ServicoException {
		CriptografiaDados params = new CriptografiaDados();
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(geradorParChaves.name());
			keyGen.initialize(tamanhoChave);
			KeyPair keypair = keyGen.genKeyPair();
			PrivateKey privateKey = keypair.getPrivate();
			PublicKey publicKey = keypair.getPublic();

			// Get the bytes of the public and private keys
			byte[] privateKeyBytes = privateKey.getEncoded();
			byte[] publicKeyBytes = publicKey.getEncoded();

			// Get the formats of the encoded bytes
			String formatPrivate = privateKey.getFormat(); // PKCS#8
			String formatPublic = publicKey.getFormat(); // X.509

			// The bytes can be converted back to public and private key objects
			KeyFactory keyFactory = KeyFactory.getInstance(geradorParChaves
					.name());
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					privateKeyBytes);
			PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					publicKeyBytes);
			PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

			// The orginal and new keys are the same
			boolean samePrivate = privateKey.equals(privateKey2); // true
			boolean samePublic = publicKey.equals(publicKey2); // true

			params.setChavePrivada(privateKey);
			params.setChavePublica(publicKey);
			params.setBytesChavePrivada(privateKeyBytes);
			params.setBytesChavePublica(publicKeyBytes);
			params.setFormatoChavePrivada(formatPrivate);
			params.setFormatoChavePublica(formatPublic);
			params.setChavesPrivadasIguais(samePrivate);
			params.setChavesPublicasIguais(samePublic);
			return params;
		} catch (InvalidKeySpecException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}
}
