package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.server.endereco.objetoslista.MunicipioLista;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object BairroDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class BairroDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(BairroDAO.class);
	private static BairroDAO instancia;

	private BairroDAO() {
		this.setTabelas(new String[] { "BAIRRO" });
		this.setCamposChave(new String[] { "ID_BAIRRO" });
		this.setCamposNaoChave(new String[] { "NM_BAIRRO", "ID_MUNICIPIO" });
	}

	public static BairroDAO getInstancia() {
		if (instancia == null) {
			instancia = new BairroDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(EnderecoBairro obj, int ord) throws DAOException {
		try {
			pstmt.setString(++ord, obj.getNome());
			pstmt.setInt(++ord, obj.getMunicipio().getCodigo());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlNovoCodigo("SEQ_BAIRRO"));
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

	public int incluir(EnderecoBairro obj) throws DAOException {
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

	public int alterar(EnderecoBairro obj) throws DAOException {
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

	private EnderecoBairro novoObjeto(ResultSet res) throws DAOException {
		EnderecoBairro ret = new EnderecoBairro();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setMunicipio(MunicipioLista.getInstancia().getElementoNaoNulo(res.getInt(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public EnderecoBairro pesquisar(int codigo) throws DAOException {
		EnderecoBairro obj = new EnderecoBairro();
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

	public int atualizar(EnderecoBairro obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public EnderecoBairro[] consultarTudo() throws DAOException {
		ArrayList<EnderecoBairro> lista = new ArrayList<EnderecoBairro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			EnderecoBairro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (EnderecoBairro[]) lista.toArray(new EnderecoBairro[lista.size()]);
	}

	public EnderecoBairro[] pesquisarPorDescricao(String descricao) throws DAOException {
		ArrayList<EnderecoBairro> lista = new ArrayList<EnderecoBairro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			EnderecoBairro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (EnderecoBairro[]) lista.toArray(new EnderecoBairro[lista.size()]);
	}

}
