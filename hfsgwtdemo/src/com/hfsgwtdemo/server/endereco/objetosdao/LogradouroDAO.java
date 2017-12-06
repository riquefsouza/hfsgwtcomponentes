package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.server.endereco.objetos.Logradouro;
import com.hfsgwtdemo.server.endereco.objetoslista.MunicipioLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TipoLogradouroLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TipoPreposicaoLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TituloLogradouroLista;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object LogradouroDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class LogradouroDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(LogradouroDAO.class);
	private static LogradouroDAO instancia;

	private LogradouroDAO() {
		this.setTabelas(new String[] { "LOGRADOURO" });
		this.setCamposChave(new String[] { "ID_LOGRADOURO" });
		this.setCamposNaoChave(new String[] { "NM_LOGRADOURO",
				"ID_TIPO_LOGRADOURO", "ID_TITULO", "NM_APELIDO", "ID_SITUACAO",
				"ID_PREPOSICAO", "ID_KM", "NM_FONETICO", "NM_APELIDO_FONETICO",
				"ID_MUNICIPIO" });
	}

	public static LogradouroDAO getInstancia() {
		if (instancia == null) {
			instancia = new LogradouroDAO();
		}
		return instancia;
	}

	private void atribuirCamposNaoChave(Logradouro obj, int ord)
			throws DAOException {
		try {
			pstmt.setString(ord, obj.getNome());
			pstmt.setInt(++ord, obj.getTipoLogradouro().getCodigo());
			pstmt.setInt(++ord, obj.getTituloLogradouro().getCodigo());
			pstmt.setString(++ord, obj.getApelido());
			pstmt.setString(++ord, obj.getSituacao());
			pstmt.setInt(++ord, obj.getTipoPreposicao().getCodigo());
			pstmt.setString(++ord, obj.getKM());
			pstmt.setString(++ord, obj.getFonetico());
			pstmt.setString(++ord, obj.getApelidoFonetico());
			pstmt.setInt(++ord, obj.getMunicipio().getCodigo());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
	}

	public int novoCodigo() throws DAOException {
		int obj = 1;
		try {
			this.pstmt = this.getSqlPreparado(this
					.getSqlNovoCodigo("SEQ_LOGRADOURO"));
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

	public int incluir(Logradouro obj) throws DAOException {
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

	public int alterar(Logradouro obj) throws DAOException {
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

	private Logradouro novoObjeto(ResultSet res) throws DAOException {
		Logradouro ret = new Logradouro();
		int ord = 1;
		try {
			ret.setCodigo(res.getInt(ord));
			ret.setNome(Rotinas.testaNull(res.getString(++ord)));
			ret.setTipoLogradouro(TipoLogradouroLista.getInstancia()
					.getElementoNaoNulo(res.getInt(++ord)));
			ret.setTituloLogradouro(TituloLogradouroLista.getInstancia()
					.getElementoNaoNulo(res.getInt(++ord)));
			ret.setApelido(Rotinas.testaNull(res.getString(++ord)));
			ret.setSituacao(Rotinas.testaNull(res.getString(++ord)));
			ret.setTipoPreposicao(TipoPreposicaoLista.getInstancia()
					.getElementoNaoNulo(res.getInt(++ord)));
			ret.setKM(Rotinas.testaNull(res.getString(++ord)));
			ret.setFonetico(Rotinas.testaNull(res.getString(++ord)));
			ret.setApelidoFonetico(Rotinas.testaNull(res.getString(++ord)));
			ret.setMunicipio(MunicipioLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord)));
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public Logradouro pesquisar(int codigo) throws DAOException {
		Logradouro obj = new Logradouro();
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

	public int atualizar(Logradouro obj) throws DAOException {
		if (!existe(obj.getCodigo()))
			return incluir(obj);
		else
			return alterar(obj);
	}

	public Logradouro[] consultarTudo() throws DAOException {
		ArrayList<Logradouro> lista = new ArrayList<Logradouro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelectTudo("1"));
			ResultSet res = pstmt.executeQuery();
			Logradouro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao consultar tudo!, " + e.getMessage());
		}
		return (Logradouro[]) lista.toArray(new Logradouro[lista.size()]);
	}

	public Logradouro[] pesquisarPorDescricao(String descricao)
			throws DAOException {
		ArrayList<Logradouro> lista = new ArrayList<Logradouro>();
		try {
			this.pstmt = this.getSqlPreparado(this.getSqlSelect(this
					.getCamposNaoChave()[0]
					+ " like ?", "2"));
			pstmt.setString(1, descricao + "%");
			ResultSet res = pstmt.executeQuery();
			Logradouro obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		return (Logradouro[]) lista.toArray(new Logradouro[lista.size()]);
	}

}
