package com.hfsgwtdemo.server.siadm.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.siadm.objetoslista.OrgaoLista;

/**
 * Data Access Object SetorDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class SetorDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(SetorDAO.class);
	private static SetorDAO instancia;

	private SetorDAO() {
		this.setTabelas(new String[] { "TB_MCOR_SETOR" });
		this.setCamposChave(new String[] { "CODIGO_SETOR" });
		this.setCamposNaoChave(new String[] { "NOME_SETOR" , "CODIGO_ORGAO" });
	}

	public static SetorDAO getInstancia() {
		if (instancia == null) {
			instancia = new SetorDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(SiadmSetor obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(++ord, obj.getNome());
			pstmt.setInt(++ord, obj.getOrgao().getCodigo());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this
					.getSqlNovoCodigo("SEQ_SETOR"));
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

	public int incluir(SiadmSetor obj) throws DAOException {
		int ret = -1;
		try {
			obj.setCodigo(novoCodigo());
			pstmt = this.getSqlPreparado(this.getSqlInsert());
			pstmt.setInt(1, obj.getCodigo());
			atribuirCamposNaoChave(obj, 2);
			ret = pstmt.executeUpdate();
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), "Erro ao incluir!, "
					+ e.getMessage());
		}
		return ret;
	}

	public int alterar(SiadmSetor obj) throws DAOException {
		int ret = -1;
		try {
			pstmt = this.getSqlPreparado(this.getSqlUpdate());
			atribuirCamposNaoChave(obj, 1);
			pstmt.setInt(this.getCamposNaoChave().length + 1, obj.getCodigo());
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

	private SiadmSetor novoObjeto(ResultSet res) throws DAOException {
		SiadmSetor ret = new SiadmSetor();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setOrgao(OrgaoLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public SiadmSetor pesquisar(int codigo) throws DAOException {
		SiadmSetor obj = new SiadmSetor();
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

	public int atualizar(SiadmSetor obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public SiadmSetor[] consultarTudo() throws DAOException {
		ArrayList<SiadmSetor> lista = new ArrayList<SiadmSetor>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("3, 2"));
			ResultSet res = pstmt.executeQuery();
			SiadmSetor obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (SiadmSetor[]) lista.toArray(new SiadmSetor[lista.size()]);
	}

	public SiadmSetor[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<SiadmSetor> lista = new ArrayList<SiadmSetor>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			SiadmSetor obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (SiadmSetor[]) lista.toArray(new SiadmSetor[lista.size()]);
	}

}
