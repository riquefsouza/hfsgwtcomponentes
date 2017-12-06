package com.hfsgwt.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

public final class RotinasCertificado {
	private static Logger log = Logger.getLogger(RotinasCertificado.class);
	private static RotinasCertificado instancia;

	private RotinasCertificado() {
		super();
	}

	public static RotinasCertificado getInstancia() {
		if (instancia == null) {
			instancia = new RotinasCertificado();
		}
		return instancia;
	}

	// This method adds a certificate with the specified alias to the specified
	// keystore file.
	public void addToKeyStore(File keystoreFile, char[] keystorePassword,
			String alias, Certificate cert) throws ServicoException {
		try {
			// Create an empty keystore object
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());

			// Load the keystore contents
			FileInputStream in = new FileInputStream(keystoreFile);
			keystore.load(in, keystorePassword);
			in.close();
			// Add the certificate
			keystore.setCertificateEntry(alias, cert);
			// Save the new keystore contents
			FileOutputStream out = new FileOutputStream(keystoreFile);
			keystore.store(out, keystorePassword);
			out.close();
		} catch (java.security.cert.CertificateException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			// Keystore does not exist
			throw new ServicoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public void listarAliasesKeyStore() throws ServicoException {
		try {
			// Load the keystore in the user's home directory
			File file = new File(System.getProperty("user.home")
					+ File.separatorChar + ".keystore");
			FileInputStream is = new FileInputStream(file);
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			String password = "my-keystore-password";
			keystore.load(is, password.toCharArray());

			// List the aliases
			Enumeration<String> enum1 = keystore.aliases();
			for (; enum1.hasMoreElements();) {
				String alias = enum1.nextElement();

				// Does alias refer to a private key?
				boolean b = keystore.isKeyEntry(alias);

				// Does alias refer to a trusted certificate?
				b = keystore.isCertificateEntry(alias);
			}
			is.close();
		} catch (java.security.cert.CertificateException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (FileNotFoundException e) {
			// Keystore does not exist
			throw new ServicoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public Certificate getCertificadoDoKeyStore() throws ServicoException {
		try { // Load the keystore in the user's home directory
			FileInputStream is = new FileInputStream(System
					.getProperty("user.home")
					+ File.separatorChar + ".keystore");
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, "my-keystore-password".toCharArray());

			// Get certificate
			Certificate cert = keystore.getCertificate("myalias");
			return cert;
		} catch (KeyStoreException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (java.security.cert.CertificateException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (java.io.IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public KeyPair getKeyPair(KeyStore keystore, String alias,
			char[] password) throws ServicoException {
		try {
			// Get private key
			Key key = keystore.getKey(alias, password);
			if (key instanceof PrivateKey) {
				// Get certificate of public key
				Certificate cert = keystore.getCertificate(alias);

				// Get public key
				PublicKey publicKey = cert.getPublicKey();

				// Return a key pair
				return new KeyPair(publicKey, (PrivateKey) key);
			}
		} catch (UnrecoverableKeyException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (KeyStoreException e) {
			throw new ServicoException(log, e.getMessage());
		}
		return null;
	}
}
