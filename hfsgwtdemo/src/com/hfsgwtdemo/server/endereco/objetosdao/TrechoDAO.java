package com.hfsgwtdemo.server.endereco.objetosdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hfsgwt.client.componentes.util.HFSUtil;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.client.endereco.EnderecoTrecho;
import com.hfsgwtdemo.server.endereco.objetos.Logradouro;
import com.hfsgwtdemo.server.endereco.objetoslista.BairroLista;
import com.hfsgwtdemo.server.persistencia.BaseDataAccessObject;
import com.hfsgwtdemo.server.persistencia.DAOException;

/**
 * Data Access Object TrechoDAO.
 * 
 * @author Henrique Figueiredo de Souza
 * @version 1.0
 * @since 30/12/2009
 * @see br.gov.pe.sds.fotopol.base.BaseDataAccessObject
 */
public class TrechoDAO extends BaseDataAccessObject {
	private static Logger log = Logger.getLogger(TrechoDAO.class);
	private static TrechoDAO instancia;

	private TrechoDAO() {
		this.setTabelas(new String[] { "TRECHO" });
		this.setCamposChave(new String[] { "ID_TRECHO" });
//		this.setCamposNaoChave(new String[] { "VL_NUMERO_INICIAL_PAR",
//				"VL_NUMERO_FINAL_PAR", "VL_NUMERO_INICIAL_IMPAR",
//				"VL_NUMERO_FINAL_IMPAR", "ID_STATUS", "DT_ALTERACAO",
//				"ID_LOGRADOURO", "ID_CEP_PAR", "ID_CEP_IMPAR", "ID_BAIRRO",
//				"ID_MUNICIPIO", "ID_SETOR_CENSITARIO", "ID_UF",
//				"ID_CIRCUNSCRICAO" });
		this.setCamposNaoChave(new String[] { "VL_NUMERO_INICIAL_IMPAR",
				"VL_NUMERO_FINAL_IMPAR", "ID_LOGRADOURO", 
				"ID_CEP_PAR", "ID_BAIRRO",
		"ID_MUNICIPIO", "ID_SETOR_CENSITARIO", "ID_UF",
		"ID_CIRCUNSCRICAO" });
	}


	
	public static TrechoDAO getInstancia() {
		if (instancia == null) {
			instancia = new TrechoDAO();
		}
		return instancia;
	}

	private EnderecoTrecho novoObjeto(ResultSet res) throws DAOException {
		EnderecoTrecho ret = new EnderecoTrecho();
		int ord = 1;
		String cep;
		EnderecoBairro bairro;
		Logradouro logradouro;
		try {
			ret.setCodigoTrecho(res.getInt(ord));
			ret.setNumeracao(res.getInt(++ord)+"-"+res.getInt(++ord));
			logradouro = LogradouroDAO.getInstancia().pesquisar(
					res.getInt(++ord));
			if (logradouro!=null){
				ret.setCodigoLogradouro(logradouro.getCodigo());
				ret.setLogradouro(logradouro.getNome());
			}
			cep = Integer.toString(res.getInt(++ord));
			ret.setCEP(HFSUtil.formataCEP(cep));
			bairro = BairroLista.getInstancia().getElementoNaoNulo(
					res.getInt(++ord));
			ret.setBairro(bairro.getNome());
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(), e.getMessage());
		}
		return ret;
	}

	public List<EnderecoTrecho> pesquisarPorLogradouro(int codigoUF, int codigoMunicipio,
			int codigoBairro, String logradouro) throws DAOException {
		ArrayList<EnderecoTrecho> lista = new ArrayList<EnderecoTrecho>();
		String sSql;
		try {			
			sSql = "SELECT "+this.montarSqlCampos("T.")+" FROM TRECHO T, LOGRADOURO L "+
			"WHERE T.ID_LOGRADOURO=L.ID_LOGRADOURO AND T.ID_UF=? AND T.ID_MUNICIPIO=? " +
			"AND UPPER(L.NM_LOGRADOURO) LIKE ?";
			
			if (codigoBairro != -1)
				sSql += " AND T.ID_BAIRRO=?";
			
			this.pstmt = this.getSqlPreparado(sSql);
			pstmt.setInt(1, codigoUF);
			pstmt.setInt(2, codigoMunicipio);
			pstmt.setString(3, "%" + logradouro + "%");			
			if (codigoBairro != -1)
				pstmt.setInt(4, codigoBairro);
			ResultSet res = pstmt.executeQuery();
			EnderecoTrecho obj = null;
			while (res.next()) {
				obj = novoObjeto(res);
				lista.add(obj);
			}
			this.fecharSqlPreparado();
		} catch (SQLException e) {
			throw new DAOException(log, e.getErrorCode(),
					"Erro ao pesquisar pela descricao!, " + e.getMessage());
		}
		//return (EnderecoTrecho[]) lista.toArray(new EnderecoTrecho[lista.size()]);
		return lista;
	}
	
}
