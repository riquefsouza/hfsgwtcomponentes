package com.hfsgwtdemo.server.persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jdbc.driver.OracleCallableStatement;

import org.apache.log4j.Logger;

import com.hfsgwtdemo.server.persistencia.conexao.ConexaoOracle;
import com.hfsgwtdemo.server.persistencia.conexao.ErroConexaoException;
import com.hfsgwtdemo.server.persistencia.transacao.SingleTransactionObject;
import com.hfsgwtdemo.server.persistencia.transacao.TransactionParams;

/**
 * Classe que serve de base para o padrão Data Access Object (DAO), a qual
 * encapsula a persistência dos objetos de valor (Value Objects) no banco de
 * dados.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 */
public abstract class BaseDataAccessObject extends SingleTransactionObject {
	private static Logger log = Logger.getLogger(BaseDataAccessObject.class);

	protected Connection conexao;

	protected PreparedStatement pstmt;

	protected CallableStatement cstmt;
	
	protected OracleCallableStatement ocstmt;

	private String[] tabelas = {};

	private String[] campos = {};

	private String[] camposChave = {};

	private String[] camposNaoChave = {};

	public BaseDataAccessObject() {
		super();
	}

	protected void testaNull(PreparedStatement lpstmt, int ord, int codigo)
			throws SQLException {
		if (codigo == -1)
			lpstmt.setNull(ord, Types.INTEGER);
		else
			lpstmt.setInt(ord, codigo);
	}

	protected void nvlPreparado(int ordem, int valor) throws DAOException {
		try {
			if (valor == -1) {

				this.pstmt.setNull(ordem, java.sql.Types.INTEGER);
			} else {
				this.pstmt.setInt(ordem, valor);
			}
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}

	}

	protected PreparedStatement getSqlPreparado(String sql) throws DAOException {
		try {
			conexao = ConexaoOracle.getConexao();
			pstmt = conexao.prepareStatement(sql);

			if (PersistenciaParams.getInstancia().isMostrarSQL()) {
				log.info("[SQL Preparado]: " + sql);
			}
		} catch (ErroConexaoException e) {
			throw new DAOException(log, e.getMessage());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return pstmt;
	}

	protected PreparedStatement getSqlPreparado(String sql, TransactionParams tp)
			throws DAOException {
		if (tp != null) {
			if (tp.isEmTransacao())
				return getSqlTransacao(sql, tp);
			else
				return getSqlPreparado(sql);
		} else
			return getSqlPreparado(sql);

	}

	protected void fecharSqlPreparado() throws DAOException {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
		if (conexao != null) {
			try {
				if (!conexao.isClosed()) {
					conexao.close();
				}
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}

	protected void fecharSqlPreparado(TransactionParams tp) throws DAOException {
		if (tp != null) {
			if (tp.isEmTransacao())
				this.fimRecursoTransacao(tp);
			else
				this.fecharSqlPreparado();
		} else
			this.fecharSqlPreparado();

	}

	protected void fecharSqlPreparado(PreparedStatement lpstmt)
			throws DAOException {
		if (lpstmt != null) {
			try {
				lpstmt.close();
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
		if (conexao != null) {
			try {
				if (!conexao.isClosed()) {
					conexao.close();
				}
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}

	protected void fecharSqlPreparado(PreparedStatement lpstmt,
			TransactionParams tp) throws DAOException {
		if (tp != null) {
			if (tp.isEmTransacao())
				this.fimRecursoTransacao(tp);
			else
				this.fecharSqlPreparado(lpstmt);
		} else
			this.fecharSqlPreparado(lpstmt);

	}

	protected CallableStatement getSqlChamado(String sql) throws DAOException {
		try {
			conexao = ConexaoOracle.getConexao();
			cstmt = conexao.prepareCall(sql);

			if (PersistenciaParams.getInstancia().isMostrarSQL()) {
				log.info("[SQL Chamado]: " + sql);
			}
		} catch (ErroConexaoException e) {
			throw new DAOException(log, e.getMessage());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return cstmt;
	}

	protected void fecharSqlChamado() throws DAOException {
		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
		if (conexao != null) {
			try {
				if (!conexao.isClosed()) {
					conexao.close();
				}
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}

	protected OracleCallableStatement getOracleChamado(String sql) throws DAOException {
		try {
			conexao = ConexaoOracle.getConexao();
			ocstmt = (OracleCallableStatement) conexao.prepareCall(sql);

			if (PersistenciaParams.getInstancia().isMostrarSQL()) {
				log.info("[Oracle Chamado]: " + sql);
			}
		} catch (ErroConexaoException e) {
			throw new DAOException(log, 0, e.getMessage());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
//			try {
//				ocstmt = (OracleCallableStatement) ConexaoOracle.reconectar(ocstmt, e, sql);				
//			} catch (SQLException e1) {
//				throw new DAOException(log, e1.getErrorCode(), e1.getMessage());
//			}
		}
		return ocstmt;
	}

	protected void fecharOracleChamado() throws DAOException {
		if (ocstmt != null) {
			try {
				ocstmt.close();
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
		if (conexao != null) {
			try {
				if (!conexao.isClosed()) {
					conexao.close();
				}
			} catch (SQLException e) {
				throw new DAOException(log, e.getErrorCode(), e.getMessage());
			}
		}
	}
	
	protected String montarSqlCampos(String[] campos, String condicao,
			String operadorCondicao) {
		String ret = "";
		for (int i = 0; i < campos.length; i++) {
			if (i == 0) {
				ret += campos[i] + operadorCondicao + "?";
			} else {
				ret += " " + condicao + " " + campos[i] + operadorCondicao
						+ "?";
			}
		}
		return ret;
	}

	protected String montarSqlCampos(String[] campos, String condicao) {
		return montarSqlCampos(campos, condicao, "=");
	}

	protected String montarSqlCampos(String prefixo) {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return PersistenciaRotinas.arrayToString(prefixo, campos, ",");
	}

	protected String montarSqlInsert(String tabela, String[] campos) {
		return "insert into " + tabela + "("
				+ PersistenciaRotinas.arrayToString(campos, ",") + ") values("
				+ PersistenciaRotinas.repetirString("?", ",", campos.length) + ")";
	}

	protected String montarSqlUpdate(String tabela, String[] campos,
			String condicao) {
		String sql = "update " + tabela + " set "
				+ montarSqlCampos(campos, ",");
		if (!condicao.equals("")) {
			sql += " where " + condicao;
		}
		return sql;

	}

	protected String montarSqlUpdate(String tabela, String[] campos,
			String[] camposCondicao) {
		return montarSqlUpdate(tabela, campos, montarSqlCampos(camposCondicao,
				"and"));
	}

	protected String montarSqlUpdate(String tabela, String campo,
			String campoCondicao) {
		return montarSqlUpdate(tabela, new String[] { campo },
				new String[] { campoCondicao });
	}

	protected String montarSqlDelete(String tabela, String condicao) {
		String sql = "delete from " + tabela;
		if (!condicao.equals("")) {
			sql += " where " + condicao;
		}
		return sql;
	}

	protected String montarSqlDelete(String tabela, String[] camposCondicao) {
		return montarSqlDelete(tabela, montarSqlCampos(camposCondicao, "and"));
	}

	protected String montarSqlSelect(String[] tabelas, String[] campos,
			String condicao, String ordenacao) {
		String sql = "select " + PersistenciaRotinas.arrayToString(campos, ",") + " from "
				+ PersistenciaRotinas.arrayToString(tabelas, ",");
		if (!condicao.equals("")) {
			sql += " where " + condicao;
		}
		if (!ordenacao.equals("")) {
			sql += " order by " + ordenacao;
		}
		return sql;
	}

	protected String montarSqlSelect(String[] tabelas, String[] campos,
			String[] camposCondicao) {
		return montarSqlSelect(tabelas, campos, montarSqlCampos(camposCondicao,
				"and"), "");
	}

	protected String montarSqlSelect(String[] tabelas, String[] campos,
			String[] camposCondicao, String[] camposCondicaoDesigual) {
		return montarSqlSelect(tabelas, campos, montarSqlCampos(camposCondicao,
				"and")
				+ " and "
				+ montarSqlCampos(camposCondicaoDesigual, "and", "<>"), "");
	}

	protected String getSqlInsert() {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return montarSqlInsert(tabelas[0], campos);
	}

	protected String getSqlUpdate() {
		return montarSqlUpdate(tabelas[0], camposNaoChave, camposChave);
	}

	protected String getSqlDelete() {
		return montarSqlDelete(tabelas[0], camposChave);
	}

	protected String getSqlDelete(String condicao) {
		return montarSqlDelete(tabelas[0], condicao);
	}

	protected String getSqlSelect() {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return montarSqlSelect(tabelas, campos, camposChave);
	}

	protected String getSqlSelectTudo(String ordenacao) {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return montarSqlSelect(tabelas, campos, "", ordenacao);
	}

	protected String getSqlSelect(String condicao, String ordenacao) {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return montarSqlSelect(tabelas, campos, condicao, ordenacao);
	}

	protected String getSqlSelect(String[] camposCondicao) {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return montarSqlSelect(tabelas, campos, camposCondicao);
	}

	protected String getSqlNovoCodigo() {
		return "select max(" + this.camposChave[0] + ")+1 from "
				+ this.tabelas[0];
	}

	protected String getSqlNovoCodigo(String nomeSequence) {
		return "select " + nomeSequence + ".nextval from dual";
	}

	public String[] getTabelas() {
		return tabelas;
	}

	public void setTabelas(String[] tabelas) {
		this.tabelas = tabelas;
	}

	public String[] getCamposChave() {
		return camposChave;
	}

	public void setCamposChave(String[] camposChave) {
		this.camposChave = camposChave;
	}

	public String[] getCamposNaoChave() {
		return camposNaoChave;
	}

	public void setCamposNaoChave(String[] camposNaoChave) {
		this.camposNaoChave = camposNaoChave;
	}

	protected String montarProc(boolean retorno, String operacao,
			int qtdParams) {
		if (retorno)
			return "{call ? := " + tabelas[0] + "_PACK." + operacao + "("
					+ PersistenciaRotinas.repetirString("?", ",", qtdParams) + ")}";
		else
			return "{call " + tabelas[0] + "_PACK." + operacao + "("
					+ PersistenciaRotinas.repetirString("?", ",", qtdParams) + ")}";
	}

	protected String getProcNovo(int qtdParams) {
		return montarProc(true, "NOVO", qtdParams);
	}

	protected String getProcNovo() {
		return getProcNovo(camposNaoChave.length);
	}
	
	protected String getProcInsert(int qtdParams) {
		return montarProc(true, "INCLUIR", qtdParams);
	}

	protected String getProcInsert() {
		return getProcInsert(camposNaoChave.length);
	}

	protected String getProcUpdate(int qtdParams) {
		return montarProc(false, "ALTERAR", qtdParams);
	}
	
	protected String getProcUpdate() {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return getProcUpdate(campos.length);
	}

	protected String getProcDelete(int qtdParams) {
		return montarProc(false, "EXCLUIR", qtdParams);
	}

	protected String getProcDelete() {
		return getProcDelete(camposChave.length);
	}

	protected String getProcSalvar(int qtdParams) {
		return montarProc(true, "SALVAR", qtdParams);
	}
	
	protected String getProcSalvar() {
		campos = PersistenciaRotinas.concatArrays(campos, camposNaoChave);
		return getProcSalvar(campos.length);
	}

	protected String getProcSalvarNovo(int qtdParams) {
		return montarProc(true, "SALVAR_NOVO", qtdParams);
	}
	
	protected String getProcSalvarNovo() {
		campos = PersistenciaRotinas.concatArrays(camposChave, camposNaoChave);
		return getProcSalvarNovo(campos.length);
	}

	protected String getProcListar(String operacao, int qtdParams) {
		return "{call ? := " + tabelas[0] + "_PACK."+operacao + "("
		+ PersistenciaRotinas.repetirString("?", ",", qtdParams) + ")}";
	}
	
	protected String getProcListar() {
		return "{call ? := " + tabelas[0] + "_PACK.LISTAR()}";
	}

	protected String getProcListarCodigo() {
		return "{call ? := " + tabelas[0] + "_PACK.LISTAR_POR_CODIGO(?)}";
	}

	protected String getProcListarDescricao() {
		return "{call ? := " + tabelas[0] + "_PACK.LISTAR_POR_DESCRICAO(?)}";
	}
}