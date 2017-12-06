package com.hfsgwtdemo.server.persistencia.transacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.PersistenciaParams;
import com.hfsgwtdemo.server.persistencia.conexao.ConexaoOracle;
import com.hfsgwtdemo.server.persistencia.conexao.ErroConexaoException;

public class SingleTransactionObject implements ITransactionObject {
	private static Logger log = Logger.getLogger(SingleTransactionObject.class);
	
	public SingleTransactionObject() {
		super();
	}

	public TransactionParams iniciarTransacao(String sessionID)
			throws DAOException {
		TransactionParams tp = new TransactionParams();
		tp.setTipoTransacao(TransactionParams.SINGLE_TRANSACTION);
		tp.setSessionID(sessionID);
		tp.setEmTransacao(true);

		try {
			Connection conexaoTransacao = ConexaoOracle.getConexao();
			conexaoTransacao.setAutoCommit(false);
			tp.setConexaoTransacao(conexaoTransacao);
		} catch (ErroConexaoException e) {
			throw new DAOException(log, 0, e.getMessage());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return tp;
	}

	protected PreparedStatement getSqlTransacao(String sql, TransactionParams tp)
			throws DAOException {
		try {
			tp
					.setPstmtTransacao(tp.getConexaoTransacao()
							.prepareStatement(sql));
			if (PersistenciaParams.getInstancia().isMostrarSQL()) {
				log.info("[SQL Preparado]: " + sql);
			}
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}

		return tp.getPstmtTransacao();
	}

	protected void fimRecursoTransacao(TransactionParams tp)
			throws DAOException {
		if (tp.getPstmtTransacao() != null) {
			try {
				tp.getPstmtTransacao().close();
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}

	public void confirmarTransacao(TransactionParams tp) throws DAOException {
		try {
			tp.getConexaoTransacao().commit();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public void desfazerTransacao(TransactionParams tp) throws DAOException {
		try {
			tp.getConexaoTransacao().rollback();
		} catch (SQLException e1) {
			throw new DAOException(log, e1.getErrorCode(), e1.getMessage());
		}
	}

	public void fecharTransacao(TransactionParams tp) throws DAOException {
		if (tp.getConexaoTransacao() != null) {
			try {
				if (!tp.getConexaoTransacao().isClosed()) {
					tp.getConexaoTransacao().setAutoCommit(true);
					tp.getConexaoTransacao().close();
				}
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}

}