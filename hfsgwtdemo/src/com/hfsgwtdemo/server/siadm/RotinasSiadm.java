package com.hfsgwtdemo.server.siadm;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.hfsgwt.server.util.Rotinas;
import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.client.siadm.SiadmUsuario;
import com.hfsgwtdemo.server.persistencia.PersistenciaParams;
import com.hfsgwtdemo.server.persistencia.conexao.ConexaoOracle;
import com.hfsgwtdemo.server.persistencia.conexao.ErroConexaoException;
import com.hfsgwtdemo.server.siadm.objetoslista.OrgaoLista;
import com.hfsgwtdemo.server.siadm.objetoslista.SetorLista;
import com.hfsgwtdemo.server.siadm.objetoslista.TipoOrgaoLista;
import com.hfsgwtdemo.server.siadm.objetoslista.UsuarioLista;

public class RotinasSiadm {
	private static Logger log = Logger.getLogger(RotinasSiadm.class);
	private static RotinasSiadm instancia;

	private boolean arqPropsExiste = false;

	private RotinasSiadm() {
		super();
	}

	public static RotinasSiadm getInstancia() {
		if (instancia == null) {
			instancia = new RotinasSiadm();
		}
		return instancia;
	}

	public SiadmOrgao[] listarOrgao(int codigoPai) {
		return OrgaoLista.getInstancia().getElementosCodigoPai(codigoPai);
	}

	public SiadmSetor[] listarSetor(int codigoOrgao) {
		return SetorLista.getInstancia().getElementosCodigoOrgao(codigoOrgao);
	}

	public SiadmUsuario[] listarUsuarioPeloOrgaoLotacao(int codigoOrgao) {
		return UsuarioLista.getInstancia().getElementosCodigoOrgaoLotacao(codigoOrgao);
	}

	public SiadmUsuario[] listarUsuarioPeloOrgaoOrigem(int codigoOrgao) {
		return UsuarioLista.getInstancia().getElementosCodigoOrgaoOrigem(codigoOrgao);
	}

	public SiadmUsuario[] listarUsuarioPeloSetor(int codigoSetor) {
		return UsuarioLista.getInstancia().getElementosCodigoSetor(codigoSetor);
	}

	private boolean carregarObjetos() {
		TipoOrgaoLista.getInstancia().carregar(log);
		OrgaoLista.getInstancia().carregar(log);
		SetorLista.getInstancia().carregar(log);
		UsuarioLista.getInstancia().carregar(log);
		return true;
	}

	private String getInfoCacheBanco() {
		return ConexaoOracle.listarInformacoesCache();
	}

	public void iniciar(String caminho) {
		String arqProps = caminho + "Siadm.properties";
		if (Rotinas.arquivoExiste(arqProps)) {
			arqPropsExiste = true;
			Properties props = Rotinas.lerArqPropriedades(arqProps);
			PersistenciaParams params = PersistenciaParams.getInstancia();
			params.setNomeCache("SIADM_ORACLE_CACHE");
			params.setSistemaDescricao("SERVIDOR Siadm");
			params.setMostrarCarregandoClasse(true);
			params.setMostrarSQL(true);

			params.setUrlBanco(props.getProperty("url"));
			params.setUsuarioBanco(props.getProperty("user"));
			params.setSenhaBanco(props.getProperty("senha"));
			
			log.info("Início das Configurações do Servidor Siadm");
			log.info("Sistema: " + params.getSistemaDescricao());
			log.info("Servidor " + Rotinas.getEnderecoIP());
			log.info("URL: " + params.getUrlBanco());
			log.info("Usuário: " + params.getUsuarioBanco());

			log.info("Fim das Configurações do Servidor Siadm");

			carregarObjetos();

			getInfoCacheBanco();

			log.info("Fachada do Siadm inicializada");			
		}
	}

	public void finalizar() {
		if (arqPropsExiste) {
			try {
				ConexaoOracle.fechaOracleDataSource();
				log.info("Fachada do Siadm finalizada");
			} catch (ErroConexaoException e) {
				log.error(e.getMessage());
			}
		}
	}

}
