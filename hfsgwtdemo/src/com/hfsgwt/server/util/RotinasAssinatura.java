package com.hfsgwt.server.util;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

public final class RotinasAssinatura {
	private static Logger log = Logger.getLogger(RotinasAssinatura.class);
	private static RotinasAssinatura instancia;

	private RotinasAssinatura() {
		super();
	}

	public static RotinasAssinatura getInstancia() {
		if (instancia == null) {
			instancia = new RotinasAssinatura();
		}
		return instancia;
	}

	// Returns the signature for the given 
	// buffer of bytes using the private key.
	public byte[] criarAssinatura(PrivateKey key, byte[] buffer)
			throws ServicoException {
		try {
			Signature sig = Signature.getInstance(key.getAlgorithm());
			sig.initSign(key);
			sig.update(buffer, 0, buffer.length);
			return sig.sign();
		} catch (SignatureException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	public String[] listarAlgoritmosAssinatura() {
		return RotinasCriptografia.getInstancia().getServicoCriptografia(
				"Signature");
	}

	public void assinarObjeto() throws ServicoException {
		// Create a public and private key
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		try {
			// Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			keyGen.initialize(1024);
			KeyPair keypair = keyGen.genKeyPair();
			privateKey = keypair.getPrivate();
			publicKey = keypair.getPublic();
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
		// Create the signed object
		SignedObject so = null;
		try {
			Serializable o = new MyClass();
			Signature sig = Signature.getInstance(privateKey.getAlgorithm());
			so = new SignedObject(o, privateKey, sig);
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (SignatureException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
		// Verify the signed object
		try {
			Signature sig = Signature.getInstance(publicKey.getAlgorithm());
			// Verify the signed object
			boolean b = so.verify(publicKey, sig);
			// Retrieve the object
			MyClass o = (MyClass) so.getObject();
		} catch (SignatureException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (IOException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}

	// Verifies the signature for the given 
	// buffer of bytes using the public key.
	public boolean verificarAssinatura(PublicKey key, byte[] buffer,
			byte[] signature) throws ServicoException {
		try {
			Signature sig = Signature.getInstance(key.getAlgorithm());
			sig.initVerify(key);
			sig.update(buffer, 0, buffer.length);
			return sig.verify(signature);
		} catch (SignatureException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (InvalidKeyException e) {
			throw new ServicoException(log, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new ServicoException(log, e.getMessage());
		}
	}
	
}

class MyClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String s = "my string";
	int i = 123;
}
