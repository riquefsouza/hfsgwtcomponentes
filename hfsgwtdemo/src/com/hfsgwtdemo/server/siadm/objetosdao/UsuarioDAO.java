package com.hfsgwtdemo.server.siadm.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.siadm.SiadmUsuario;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.siadm.objetoslista.OrgaoLista;
import com.hfsgwtdemo.server.siadm.objetoslista.SetorLista;

/**
 * Data Access Object UsuarioDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class UsuarioDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(UsuarioDAO.class);
	private static UsuarioDAO instancia;

	private UsuarioDAO() {
		this.setTabelas(new String[] { "TB_MSEG_USUARIOS" });
		this.setCamposChave(new String[] { "CODIGO_USUARIO" });
		this.setCamposNaoChave(new String[] { "NOME_USUARIO",
						"CODIGO_ORGAO_LOTACAO", "CODIGO_ORGAO_ORIGEM",
						"CODIGO_SETOR" });
	}

	public static UsuarioDAO getInstancia() {
		if (instancia == null) {
			instancia = new UsuarioDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(SiadmUsuario obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(++ord, obj.getNome());
			pstmt.setInt(++ord, obj.getOrgaoLotacao().getCodigo());
			pstmt.setInt(++ord, obj.getOrgaoOrigem().getCodigo());
			pstmt.setInt(++ord, obj.getSetor().getCodigo());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int incluir(SiadmUsuario obj) throws DAOException {
		int ret = -1;
		try {
			pstmt = this.getSqlPreparado(this.getSqlInsert());
			pstmt.setString(1, obj.getCodigo());
			atribuirCamposNaoChave(obj, 2);
			ret = pstmt.executeUpdate();
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), "Erro ao incluir!, "
					+ e.getMessage());
		}
		return ret;
	}

	public int alterar(SiadmUsuario obj) throws DAOException {
		int ret = -1;
		try {
			pstmt = this.getSqlPreparado(this.getSqlUpdate());
			atribuirCamposNaoChave(obj, 1);
			pstmt.setString(this.getCamposNaoChave().length + 1, obj.getCodigo());
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

	public boolean existe(String codigo) throws DAOException {
		return (pesquisar(codigo).getCodigo().trim().length() > 0);
	}

	private SiadmUsuario novoObjeto(ResultSet res) throws DAOException {
		SiadmUsuario ret = new SiadmUsuario();
		int ord = 1;
		try {
			ret.setCodigo(res.getString(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setOrgaoLotacao(OrgaoLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
			ret.setOrgaoOrigem(OrgaoLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
			ret.setSetor(SetorLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public SiadmUsuario pesquisar(String codigo) throws DAOException {
		SiadmUsuario obj = new SiadmUsuario();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect());
			pstmt.setString(1, codigo);
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

	public int atualizar(SiadmUsuario obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public SiadmUsuario[] consultarTudo() throws DAOException {
		ArrayList<SiadmUsuario> lista = new ArrayList<SiadmUsuario>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("5, 2"));
			ResultSet res = pstmt.executeQuery();
			SiadmUsuario obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (SiadmUsuario[]) lista.toArray(new SiadmUsuario[lista.size()]);
	}

	public SiadmUsuario[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<SiadmUsuario> lista = new ArrayList<SiadmUsuario>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			SiadmUsuario obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (SiadmUsuario[]) lista.toArray(new SiadmUsuario[lista.size()]);
	}

}
