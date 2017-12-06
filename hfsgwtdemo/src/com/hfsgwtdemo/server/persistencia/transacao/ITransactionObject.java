package com.hfsgwtdemo.server.persistencia.transacao;

import com.hfsgwtdemo.server.persistencia.DAOException;

public interface ITransactionObject {
	public TransactionParams iniciarTransacao(String sessionID)
			throws DAOException;

	public void confirmarTransacao(TransactionParams tp) throws DAOException;

	public void desfazerTransacao(TransactionParams tp) throws DAOException;

	public void fecharTransacao(TransactionParams tp) throws DAOException;

}
