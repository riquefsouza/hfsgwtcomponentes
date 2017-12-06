package com.hfsgwtdemo.server.endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.client.endereco.EnderecoTrecho;
import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.server.endereco.objetosdao.TrechoDAO;
import com.hfsgwtdemo.server.endereco.objetoslista.BairroLista;
import com.hfsgwtdemo.server.endereco.objetoslista.MunicipioLista;
import com.hfsgwtdemo.server.endereco.objetoslista.PaisLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TipoLogradouroLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TipoPreposicaoLista;
import com.hfsgwtdemo.server.endereco.objetoslista.TituloLogradouroLista;
import com.hfsgwtdemo.server.endereco.objetoslista.UfLista;
import com.hfsgwtdemo.server.persistencia.DAOException;
import com.hfsgwtdemo.server.persistencia.PersistenciaParams;
import com.hfsgwtdemo.server.persistencia.conexao.ConexaoOracle;
import com.hfsgwtdemo.server.persistencia.conexao.ErroConexaoException;

public class RotinasEndereco {
	private static Logger log = Logger.getLogger(RotinasEndereco.class);
	private static RotinasEndereco instancia;

	private boolean arqPropsExiste = false;

	private RotinasEndereco() {
		super();
	}

	public static RotinasEndereco getInstancia() {
		if (instancia == null) {
			instancia = new RotinasEndereco();
		}
		return instancia;
	}

	public EnderecoPais[] listarPais() {
		return PaisLista.getInstancia().getElementos();
	}

	public ArrayList<EnderecoPais> listarListaPais() {
		return PaisLista.getInstancia().getListaElementos();
	}

	public EnderecoPais getPais(int codigo) {
		return PaisLista.getInstancia().getElemento(codigo);
	}

	public List<EnderecoPais> listarPais(int min, int max) {
		return PaisLista.getInstancia().getListaElementos().subList(min, max);
	}

	public EnderecoUF[] listarUF(int codigoPais) {
		ArrayList<EnderecoUF> lista = new ArrayList<EnderecoUF>();
		EnderecoUF[] listaOrigem = UfLista.getInstancia().getElementos();

		for (EnderecoUF itemUF : listaOrigem) {
			if (itemUF.getPais().getCodigo() == codigoPais) {
				lista.add(itemUF);
			}
		}
		return (EnderecoUF[]) lista.toArray(new EnderecoUF[lista.size()]);
	}

	public EnderecoMunicipio[] listarMunicipio(int codigoPais, int codigoUF) {
		ArrayList<EnderecoMunicipio> lista = new ArrayList<EnderecoMunicipio>();
		EnderecoMunicipio[] listaOrigem = MunicipioLista.getInstancia()
				.getElementos();

		for (EnderecoMunicipio itemMunicipio : listaOrigem) {
			if (itemMunicipio.getUF().getPais().getCodigo() == codigoPais) {
				if (itemMunicipio.getUF().getCodigo() == codigoUF) {
					lista.add(itemMunicipio);
				}
			}
		}
		return (EnderecoMunicipio[]) lista.toArray(new EnderecoMunicipio[lista
				.size()]);
	}

	public EnderecoBairro[] listarBairro(int codigoPais, int codigoUF,
			int codigoMunicipio) {
		ArrayList<EnderecoBairro> lista = new ArrayList<EnderecoBairro>();
		EnderecoBairro[] listaOrigem = BairroLista.getInstancia()
				.getElementos();

		for (EnderecoBairro itemBairro : listaOrigem) {
			if (itemBairro.getMunicipio().getUF().getPais().getCodigo() == codigoPais) {
				if (itemBairro.getMunicipio().getUF().getCodigo() == codigoUF) {
					if (itemBairro.getMunicipio().getCodigo() == codigoMunicipio) {
						lista.add(itemBairro);
					}
				}
			}
		}
		return (EnderecoBairro[]) lista
				.toArray(new EnderecoBairro[lista.size()]);
	}

	public List<EnderecoTrecho> consultarTrecho(int codigoUF,
			int codigoMunicipio, int codigoBairro, String logradouro) {
		List<EnderecoTrecho> lista = new ArrayList<EnderecoTrecho>();
		try {
			lista = TrechoDAO.getInstancia().pesquisarPorLogradouro(codigoUF,
					codigoMunicipio, codigoBairro, logradouro);
		} catch (DAOException e) {
			return null;
		}
		return lista;
	}

	private boolean carregarObjetos() {
		PaisLista.getInstancia().carregar(log);
		UfLista.getInstancia().carregar(log);
		MunicipioLista.getInstancia().carregar(log);
		BairroLista.getInstancia().carregar(log);
		TituloLogradouroLista.getInstancia().carregar(log);
		TipoLogradouroLista.getInstancia().carregar(log);
		TipoPreposicaoLista.getInstancia().carregar(log);
		return true;
	}

	private String getInfoCacheBanco() {
		return ConexaoOracle.listarInformacoesCache();
	}

	public void iniciar(String caminho) {
		String arqProps = caminho + "HFSEndereco.properties";
		if (Rotinas.arquivoExiste(arqProps)) {
			arqPropsExiste = true;
			Properties props = Rotinas.lerArqPropriedades(arqProps);
			PersistenciaParams params = PersistenciaParams.getInstancia();
			params.setNomeCache("ENDERECO_ORACLE_CACHE");
			params.setSistemaDescricao("SERVIDOR HFSEndereco");
			params.setMostrarCarregandoClasse(true);
			params.setMostrarSQL(true);

			params.setUrlBanco(props.getProperty("url"));
			params.setUsuarioBanco(props.getProperty("user"));
			params.setSenhaBanco(props.getProperty("senha"));
			
			log.info("Início das Configurações do Servidor Endereço");
			log.info("Sistema: " + params.getSistemaDescricao());
			log.info("Servidor " + Rotinas.getEnderecoIP());
			log.info("URL: " + params.getUrlBanco());
			log.info("Usuário: " + params.getUsuarioBanco());

			log.info("Fim das Configurações do Servidor Endereço");

			carregarObjetos();

			getInfoCacheBanco();

			log.info("Fachada do HFSEndereco inicializada");			
		}
	}

	public void finalizar() {
		if (arqPropsExiste) {
			try {
				ConexaoOracle.fechaOracleDataSource();
				log.info("Fachada do HFSEndereco finalizada");
			} catch (ErroConexaoException e) {
				log.error(e.getMessage());
			}
		}
	}

}
