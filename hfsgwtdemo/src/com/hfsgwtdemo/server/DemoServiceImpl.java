package com.hfsgwtdemo.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hfsgwtdemo.client.DemoService;
import com.hfsgwtdemo.client.Pessoa;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.client.endereco.EnderecoTrecho;
import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.client.siadm.SiadmUsuario;
import com.hfsgwtdemo.server.endereco.RotinasEndereco;
import com.hfsgwtdemo.server.siadm.RotinasSiadm;

@SuppressWarnings("serial")
public class DemoServiceImpl extends RemoteServiceServlet implements
		DemoService {

	@Override
	public Pessoa getPessoa(int codigo) throws IllegalArgumentException {
		return PessoaDAO.getInstancia().getPessoa(codigo);
	}

	@Override
	public List<Pessoa> listarPessoas(int min, int max)
			throws IllegalArgumentException {
		List<Pessoa> pessoas = PessoaDAO.getInstancia().listarPessoas(min, max);
		if (pessoas.size() == 0)
			throw new IllegalArgumentException("Pessoas não carregadas!");
		else
			return pessoas;
	}

	@Override
	public EnderecoPais[] listarPais() throws IllegalArgumentException {
		EnderecoPais[] paises = RotinasEndereco.getInstancia().listarPais();
		if (paises.length == 0) {
			throw new IllegalArgumentException("Países não carregados!");
		} else
			return paises;
	}

	@Override
	public EnderecoUF[] listarUF(int codigoPais)
			throws IllegalArgumentException {
		return RotinasEndereco.getInstancia().listarUF(codigoPais);
	}

	@Override
	public EnderecoMunicipio[] listarMunicipio(int codigoPais, int codigoUF)
			throws IllegalArgumentException {
		return RotinasEndereco.getInstancia().listarMunicipio(codigoPais,
				codigoUF);
	}

	@Override
	public EnderecoBairro[] listarBairro(int codigoPais, int codigoUF,
			int codigoMunicipio) throws IllegalArgumentException {
		return RotinasEndereco.getInstancia().listarBairro(codigoPais,
				codigoUF, codigoMunicipio);
	}

	@Override
	public List<EnderecoTrecho> consultarTrecho(int codigoUF,
			int codigoMunicipio, int codigoBairro, String logradouro)
			throws IllegalArgumentException {
		return RotinasEndereco.getInstancia().consultarTrecho(codigoUF,
				codigoMunicipio, codigoBairro, logradouro);
	}

	@Override
	public SiadmOrgao[] listarOrgao(int codigoPai)
			throws IllegalArgumentException {
		return RotinasSiadm.getInstancia().listarOrgao(codigoPai);
	}

	@Override
	public SiadmSetor[] listarSetor(int codigoOrgao)
			throws IllegalArgumentException {
		return RotinasSiadm.getInstancia().listarSetor(codigoOrgao);
	}

	@Override
	public SiadmUsuario[] listarUsuarioPeloOrgaoLotacao(int codigoOrgao)
			throws IllegalArgumentException {
		return RotinasSiadm.getInstancia().listarUsuarioPeloOrgaoLotacao(
				codigoOrgao);
	}

	@Override
	public SiadmUsuario[] listarUsuarioPeloOrgaoOrigem(int codigoOrgao)
			throws IllegalArgumentException {
		return RotinasSiadm.getInstancia().listarUsuarioPeloOrgaoOrigem(
				codigoOrgao);
	}

	@Override
	public SiadmUsuario[] listarUsuarioPeloSetor(int codigoSetor)
			throws IllegalArgumentException {
		return RotinasSiadm.getInstancia().listarUsuarioPeloSetor(codigoSetor);
	}

}
