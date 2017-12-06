package com.hfsgwtdemo.server.siadm.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.siadm.objetoslista.TipoOrgaoLista;

/**
 * Data Access Object OrgaoDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 17/05/2010
 */
public class OrgaoDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(OrgaoDAO.class);
	private static OrgaoDAO instancia;

	private OrgaoDAO() {
		this.setTabelas(new String[] { "TB_MCOR_ORGAO" });
		this.setCamposChave(new String[] { "CODIGO_ORGAO" });
		this.setCamposNaoChave(new String[] { "RAZAO_SOCIAL_ORGAO",
				"CODIGO_TIPO_ORGAO", "COD_ORGAO_PAI" });
	}

	public static OrgaoDAO getInstancia() {
		if (instancia == null) {
			instancia = new OrgaoDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(SiadmOrgao obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(++ord, obj.getNome());
			pstmt.setInt(++ord, obj.getTipo().getCodigo());
			pstmt.setInt(++ord, obj.getCodigoOrgaoPai());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this
					.getSqlNovoCodigo("SEQ_ORGAO"));
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

	public int incluir(SiadmOrgao obj) throws DAOException {
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

	public int alterar(SiadmOrgao obj) throws DAOException {
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

	private SiadmOrgao novoObjeto(ResultSet res) throws DAOException {
		SiadmOrgao ret = new SiadmOrgao();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setTipo(TipoOrgaoLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
			ret.setCodigoOrgaoPai(res.getInt(++ord));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public SiadmOrgao pesquisar(int codigo) throws DAOException {
		SiadmOrgao obj = new SiadmOrgao();
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

	public int atualizar(SiadmOrgao obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public SiadmOrgao[] consultarTudo() throws DAOException {
		ArrayList<SiadmOrgao> lista = new ArrayList<SiadmOrgao>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("4, 2"));
			ResultSet res = pstmt.executeQuery();
			SiadmOrgao obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (SiadmOrgao[]) lista.toArray(new SiadmOrgao[lista.size()]);
	}

	public SiadmOrgao[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<SiadmOrgao> lista = new ArrayList<SiadmOrgao>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			SiadmOrgao obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (SiadmOrgao[]) lista.toArray(new SiadmOrgao[lista.size()]);
	}

}
