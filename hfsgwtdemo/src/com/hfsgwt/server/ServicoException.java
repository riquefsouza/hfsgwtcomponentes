package com.hfsgwt.server;

import org.apache.log4j.Logger;

public class ServicoException extends Exception {
	private static final long serialVersionUID = 3071724337088184780L;

	public ServicoException(String msg) {
		super(msg);
	}

	public ServicoException(Logger log, String msg) {
		super(msg);
		log.error(msg);
	}
}
