package com.hfsgwtdemo.server.persistencia.conexao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.persistencia.PersistenciaParams;

/**
 * Classe de conexão com o banco de dados e que implementa o cache de conexões
 * do banco de dados Oracle.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 07/06/2010
 */
public class ConexaoOracle {
	private static Logger log = Logger.getLogger(ConexaoOracle.class);

	private static OracleDataSource ods = null;

	private static OracleConnectionCacheManager occm;

//	SDSDES = (DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = NSIORACLE)(PORT = 1521)))
//		 	 (LOAD_BALANCE = yes)(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = SDSDES)))
	
	static {
		iniciaOracleDataSource();
	}

	private ConexaoOracle() {
	};
	
	
	private static void iniciaOracleDataSource() {
		Properties cacheProps = new Properties();
		log.info("Inicializando o "
				+ PersistenciaParams.getInstancia().getNomeCache());
		try {
			cacheProps.setProperty("MinLimit", "10");
			cacheProps.setProperty("InitialLimit", "10");
			cacheProps.setProperty("MaxLimit", "20");
			//cacheProps.setProperty("ConnectionWaitTimeout", "5");
			cacheProps.setProperty("ValidateConnection", "true");
			if (PersistenciaParams.getInstancia().isHabilitarFailOver())
				System.setProperty("oracle.ons.oraclehome", PersistenciaParams
						.getInstancia().getOracleHome());

			log.info("---------------------------------------------");
			log.info("Carregando Oracle Driver JDBC.");
			log.info("---------------------------------------------");

			ods = new OracleDataSource();
			ods.setURL(PersistenciaParams.getInstancia().getUrlBanco());
			ods.setUser(PersistenciaParams.getInstancia().getUsuarioBanco());
			ods.setPassword(PersistenciaParams.getInstancia().getSenhaBanco());
			ods.setConnectionCachingEnabled(true);
			ods.setConnectionCacheProperties(cacheProps);

			if (PersistenciaParams.getInstancia().isHabilitarFailOver())
				ods.setFastConnectionFailoverEnabled(true);
			// Deve ser setado o cache de conexao com conexao rápida com
			// failover antes de nomear o cache
			ods.setConnectionCacheName(PersistenciaParams.getInstancia()
					.getNomeCache());
			occm = OracleConnectionCacheManager
					.getConnectionCacheManagerInstance();

			log.info("CONEXÃO INICIAL");
			log.info("URL do Banco: "+ods.getURL());
			log.info("Usuário do Banco: "+ods.getUser());
			if (PersistenciaParams.getInstancia().getOracleHome().length() > 0)
				log.info("ORACLE HOME: "+PersistenciaParams.getInstancia().getOracleHome());
			log.info("---------------------------------------------");

			Connection conn1 = ods.getConnection();
			DatabaseMetaData meta = conn1.getMetaData();
			log.info("A versão do driver JDBC é " + meta.getDriverVersion());

			if (ods.getFastConnectionFailoverEnabled()) {
				log.info("Conexão rápida com Failover está HABILITADO.");
			} else {
				log.info("Conexão rápida com Failover NÃO está HABILITADO.");
			}

			listarInformacoesCache();

			log.info("Oracle Driver JDBC carregado com Sucesso.");
			log.info("ValidateConnection = "
					+ cacheProps.getProperty("ValidateConnection"));

		} catch (SQLException e) {
			log.error("ERRO: Carregando Oracle Driver JDBC: " + e.getMessage());
		}		
	}
	
	public static Connection getConexao() throws ErroConexaoException {
		Connection conn2 = null;
		try {
			if (!PersistenciaParams.getInstancia().getNomeCache().equals(ods.getConnectionCacheName())){
				iniciaOracleDataSource();
			}
			
			if (ods == null) {
				throw new ErroConexaoException(log, "OracleDataSource é nulo.");
			}
			conn2 = ods.getConnection();
			// conn2.setAutoCommit(false);
			// if (conn2.getAutoCommit()) {
			// log.info("AutoCommit está habilitado.");
			// } else {
			// log.info("AutoCommit não está habilitado.");
			// }
		} catch (SQLException e) {
			try {
				conn2 = reconectar(conn2, e);
			} catch (SQLException e1) {
				log.error("Erro no método getConexao(): " + e1.getMessage());
				throw new ErroConexaoException(log, "Problema de Comunicação com o Servidor de Banco de Dados.");				
			} catch (Exception e2) {
				log.error("Erro no método getConexao(): " + e2.getMessage());
				throw new ErroConexaoException(log, "Problema de Comunicação com o Servidor de Banco de Dados.");				
			}
		}
		
		if (conn2 == null){
			log.error("Erro no método getConexao(): conexao NULA");
			throw new ErroConexaoException(log, "Problema de Comunicação com o Servidor de Banco de Dados.");				
		}
		
		return conn2;
	}

	private static Connection reconectar(Connection conn3, SQLException e)
			throws SQLException {
		log.error("+--------------------------------------+");
		log.error("| SQL Exception Reconectando ao ORACLE |");
		log.error("+--------------------------------------+");
		log.error("SQL Error Code   : " + e.getErrorCode());
		log.error("SQL Error Message: " + e.getMessage());

		// ORA-17008: Closed Connection
		// ORA-17143: Conexão Desatualizada ou Inválida no Cache de Conexão
		if (e.getErrorCode() == 17008 || e.getErrorCode() == 17143) {
			log.info("SQL exceção recuperável, tentando re-conectar.");
			log.info("OracleDataSource cache de conexão "
					+ "antes de começar uma nova conexão.");
			listarInformacoesCache();

			try {
				ods.close();
				conn3 = ods.getConnection();
				
				log.info("OracleDataSource cache de conexão "
						+ "depois de começar uma nova conexão.");
				listarInformacoesCache();
			} catch (SQLException e1) {
				log.error("ERRO: Não foi possível obter "
						+ "uma nova conexão a partir do cache.");
			}

		} else {
			log.error("ERRO: Este SQLException "
					+ "não se deveu a uma falha de nó.");
			log.error("Re-levantando esta Exceção.\n");
			throw e;

		}
		return conn3;
	}
	
	public static void fechaOracleDataSource() throws ErroConexaoException {
		try {
			if (ods != null) {
				log.info("---------------------------------------------");
				log.info("FECHANDO Oracle Data Source");
				log.info("---------------------------------------------");
				listarInformacoesCache();
				ods.close();
			}
		} catch (SQLException e) {
			throw new ErroConexaoException(log,
					"ERRO: Ao fechar o Oracle Data Source: " + e.getMessage());
		}
	}

	public static String listarInformacoesCache() {
		String info1, info2, erro;
		try {
			info1 = "OracleDataSource Pool: Ativo ("
					+ occm.getNumberOfActiveConnections(PersistenciaParams
							.getInstancia().getNomeCache()) + ")";
			info2 = " Disponível ("
					+ occm.getNumberOfAvailableConnections(PersistenciaParams
							.getInstancia().getNomeCache()) + ")";
			log.info(info1 + " " + info2);
			return info1 + " " + info2;
		} catch (SQLException e) {
			erro = "ERRO: Ao listar informações do cache de conexões: "
					+ e.getMessage();
			log.error(erro);
			return erro;

		}
	}

}