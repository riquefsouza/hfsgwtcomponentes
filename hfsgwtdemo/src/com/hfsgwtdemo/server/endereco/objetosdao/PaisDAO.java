package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object PaisDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class PaisDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(PaisDAO.class);
	private static PaisDAO instancia;

	private PaisDAO() {
		this.setTabelas(new String[] { "PAIS" });
		this.setCamposChave(new String[] { "ID_PAIS" });
		this.setCamposNaoChave(new String[] { "SIGLA_PAIS", "NM_PAIS",
				"DS_NACIONALIDADE" });
	}

	public static PaisDAO getInstancia() {
		if (instancia == null) {
			instancia = new PaisDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(EnderecoPais obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(ord, obj.getSigla());
			pstmt.setString(++ord, obj.getNome());
			pstmt.setString(++ord, obj.getNacionalidade());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this
					.getSqlPreparado(this.getSqlNovoCodigo("SEQ_PAIS"));
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

	public int incluir(EnderecoPais obj) throws DAOException {
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

	public int alterar(EnderecoPais obj) throws DAOException {
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

	private EnderecoPais novoObjeto(ResultSet res) throws DAOException {
		EnderecoPais ret = new EnderecoPais();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setSigla(Rotinas.testaNull(res.getString(++ord)));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setNacionalidade(Rotinas.testaNull(res.getString(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public EnderecoPais pesquisar(int codigo) throws DAOException {
		EnderecoPais obj = new EnderecoPais();
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

	public int atualizar(EnderecoPais obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public EnderecoPais[] consultarTudo() throws DAOException {
		ArrayList<EnderecoPais> lista = new ArrayList<EnderecoPais>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			EnderecoPais obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (EnderecoPais[]) lista.toArray(new EnderecoPais[lista.size()]);
	}

	public EnderecoPais[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<EnderecoPais> lista = new ArrayList<EnderecoPais>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			EnderecoPais obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (EnderecoPais[]) lista.toArray(new EnderecoPais[lista.size()]);
	}

}
