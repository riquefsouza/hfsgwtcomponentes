package com.hfsgwtdemo.server.siadm.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.siadm.SiadmTipoOrgao;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object TipoOrgaoDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class TipoOrgaoDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(TipoOrgaoDAO.class);
	private static TipoOrgaoDAO instancia;

	private TipoOrgaoDAO() {
		this.setTabelas(new String[] { "TB_MCOR_TIPO_ORGAO" });
		this.setCamposChave(new String[] { "CODIGO_TIPO_ORGAO" });
		this.setCamposNaoChave(new String[] { "NOME_TIPO_ORGAO" });
	}

	public static TipoOrgaoDAO getInstancia() {
		if (instancia == null) {
			instancia = new TipoOrgaoDAO();
		}
		return instancia;
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this
					.getSqlNovoCodigo("SEQ_TIPO_ORGAO"));
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

	public int incluir(SiadmTipoOrgao obj) throws DAOException {
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

	public int alterar(SiadmTipoOrgao obj) throws DAOException {
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

	private SiadmTipoOrgao novoObjeto(ResultSet res) throws DAOException {
		SiadmTipoOrgao ret = new SiadmTipoOrgao();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public SiadmTipoOrgao pesquisar(int codigo) throws DAOException {
		SiadmTipoOrgao obj = new SiadmTipoOrgao();
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

	public int atualizar(SiadmTipoOrgao obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public SiadmTipoOrgao[] consultarTudo() throws DAOException {
		ArrayList<SiadmTipoOrgao> lista = new ArrayList<SiadmTipoOrgao>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			SiadmTipoOrgao obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (SiadmTipoOrgao[]) lista.toArray(new SiadmTipoOrgao[lista.size()]);
	}

	public SiadmTipoOrgao[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<SiadmTipoOrgao> lista = new ArrayList<SiadmTipoOrgao>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			SiadmTipoOrgao obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (SiadmTipoOrgao[]) lista.toArray(new SiadmTipoOrgao[lista.size()]);
	}

}
