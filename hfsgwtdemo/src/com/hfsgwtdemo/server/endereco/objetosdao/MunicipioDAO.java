package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.server.endereco.objetoslista.UfLista;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object MunicipioDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class MunicipioDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(MunicipioDAO.class);
	private static MunicipioDAO instancia;

	private MunicipioDAO() {
		this.setTabelas(new String[] { "MUNICIPIO" });
		this.setCamposChave(new String[] { "ID_MUNICIPIO" });
		this.setCamposNaoChave(new String[] { "NM_MUNICIPIO", "ID_UF",
				"FL_BASE_CARTOGRAFICA" });
	}

	public static MunicipioDAO getInstancia() {
		if (instancia == null) {
			instancia = new MunicipioDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(EnderecoMunicipio obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(ord, obj.getNome());
			pstmt.setInt(++ord, obj.getUF().getCodigo());
			pstmt.setString(++ord, obj.getBaseCartografica());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this
					.getSqlNovoCodigo("SEQ_MUNICIPIO"));
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

	public int incluir(EnderecoMunicipio obj) throws DAOException {
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

	public int alterar(EnderecoMunicipio obj) throws DAOException {
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

	private EnderecoMunicipio novoObjeto(ResultSet res) throws DAOException {
		EnderecoMunicipio ret = new EnderecoMunicipio();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setUF(UfLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
			ret.setBaseCartografica(Rotinas.testaNull(res.getString(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public EnderecoMunicipio pesquisar(int codigo) throws DAOException {
		EnderecoMunicipio obj = new EnderecoMunicipio();
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

	public int atualizar(EnderecoMunicipio obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public EnderecoMunicipio[] consultarTudo() throws DAOException {
		ArrayList<EnderecoMunicipio> lista = new ArrayList<EnderecoMunicipio>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			EnderecoMunicipio obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (EnderecoMunicipio[]) lista.toArray(new EnderecoMunicipio[lista
				.size()]);
	}

	public EnderecoMunicipio[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<EnderecoMunicipio> lista = new ArrayList<EnderecoMunicipio>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			EnderecoMunicipio obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (EnderecoMunicipio[]) lista.toArray(new EnderecoMunicipio[lista
				.size()]);
	}

}
