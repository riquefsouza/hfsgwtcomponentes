package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.server.endereco.objetoslista.PaisLista;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object UfDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class UfDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(UfDAO.class);
	private static UfDAO instancia;

	private UfDAO() {
		this.setTabelas(new String[] { "UF" });
		this.setCamposChave(new String[] { "ID_UF" });
		this.setCamposNaoChave(new String[] { "SIGLA_UF", "NM_UF", "ID_PAIS" });
	}

	public static UfDAO getInstancia() {
		if (instancia == null) {
			instancia = new UfDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(EnderecoUF obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(ord, obj.getSigla());
			pstmt.setString(++ord, obj.getNome());
			pstmt.setInt(++ord, obj.getPais().getCodigo());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlNovoCodigo("SEQ_UF"));
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

	public int incluir(EnderecoUF obj) throws DAOException {
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

	public int alterar(EnderecoUF obj) throws DAOException {
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

	private EnderecoUF novoObjeto(ResultSet res) throws DAOException {
		EnderecoUF ret = new EnderecoUF();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setSigla(Rotinas.testaNull(res.getString(++ord)));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setPais(PaisLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public EnderecoUF pesquisar(int codigo) throws DAOException {
		EnderecoUF obj = new EnderecoUF();
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

	public int atualizar(EnderecoUF obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public EnderecoUF[] consultarTudo() throws DAOException {
		ArrayList<EnderecoUF> lista = new ArrayList<EnderecoUF>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			EnderecoUF obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (EnderecoUF[]) lista.toArray(new EnderecoUF[lista.size()]);
	}

	public EnderecoUF[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<EnderecoUF> lista = new ArrayList<EnderecoUF>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			EnderecoUF obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (EnderecoUF[]) lista.toArray(new EnderecoUF[lista.size()]);
	}

}
