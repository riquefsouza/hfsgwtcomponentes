package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.server.endereco.objetos.TipoLogradouro;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object TipoLogradouroDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class TipoLogradouroDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(TipoLogradouroDAO.class);
	private static TipoLogradouroDAO instancia;

	private TipoLogradouroDAO() {
		this.setTabelas(new String[] { "TIPO_LOGRADOURO" });
		this.setCamposChave(new String[] { "ID_TIPO_LOGRADOURO" });
		this.setCamposNaoChave(new String[] { "NM_TIPO_LOGRADOURO" });
	}

	public static TipoLogradouroDAO getInstancia() {
		if (instancia == null) {
			instancia = new TipoLogradouroDAO();
		}
		return instancia;
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlNovoCodigo("SEQ_TIPO_LOGRADOURO"));
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				obj = res.getInt(1);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar novo código!, " + e.getMessage());
		}
		return obj;
	}
	
	public int incluir(TipoLogradouro obj) throws DAOException {
		int ret = -1;
		try {
			obj.setCodigo(novoCodigo());
			pstmt = this.getSqlPreparado(this.getSqlInsert());
			pstmt.setInt(1, obj.getCodigo());
			pstmt.setString(2, obj.getNome());
			ret = pstmt.executeUpdate();
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), "Erro ao incluir!, "
					+ e.getMessage());
		}
		return ret;
	}

	public int alterar(TipoLogradouro obj) throws DAOException {
		int ret = -1;
		try {
			pstmt = this.getSqlPreparado(this.getSqlUpdate());
			pstmt.setString(1, obj.getNome());
			pstmt.setInt(2, obj.getCodigo());
			ret = pstmt.executeUpdate();
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), "Erro ao alterar!, "
					+ e.getMessage());
		}
		return ret;
	}

	public int excluir(int codigo) throws DAOException {
		int ret = -1;
		try {
			pstmt = this.getSqlPreparado(this.getSqlDelete());
			pstmt.setInt(1, codigo);
			ret = pstmt.executeUpdate();
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), "Erro ao excluir!, "
					+ e.getMessage());
		}
		return ret;
	}

	public boolean existe(int codigo) throws DAOException {
		return (pesquisar(codigo).getCodigo() != -1);
	}

	private TipoLogradouro novoObjeto(ResultSet res) throws DAOException {
		TipoLogradouro ret = new TipoLogradouro();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public TipoLogradouro pesquisar(int codigo) throws DAOException {
		TipoLogradouro obj = new TipoLogradouro();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect());
			pstmt.setInt(1, codigo);
			ResultSet res = pstmt.executeQuery();
			if (res.next()) {
				obj = novoObjeto(res);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar!, " + e.getMessage());
		}
		return obj;
	}

	public int atualizar(TipoLogradouro obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public TipoLogradouro[] consultarTudo() throws DAOException {
		ArrayList<TipoLogradouro> lista = new ArrayList<TipoLogradouro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			TipoLogradouro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (TipoLogradouro[]) lista.toArray(new TipoLogradouro[lista.size()]);
	}

	public TipoLogradouro[] pesquisarPorDescricao(String descricao) throws DAOException {
		ArrayList<TipoLogradouro> lista = new ArrayList<TipoLogradouro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			TipoLogradouro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (TipoLogradouro[]) lista.toArray(new TipoLogradouro[lista.size()]);
	}

}
