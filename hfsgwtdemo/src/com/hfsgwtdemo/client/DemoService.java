package com.hfsgwtdemo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.client.endereco.EnderecoTrecho;
import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.client.siadm.SiadmUsuario;

@RemoteServiceRelativePath("demoservico")
public interface DemoService extends RemoteService {

	public List<Pessoa> listarPessoas(int min, int max)
			throws IllegalArgumentException;

	public Pessoa getPessoa(int codigo) throws IllegalArgumentException;

	public EnderecoPais[] listarPais() throws IllegalArgumentException;

	public EnderecoUF[] listarUF(int codigoPais)
			throws IllegalArgumentException;

	public EnderecoMunicipio[] listarMunicipio(int codigoPais, int codigoUF)
			throws IllegalArgumentException;

	public EnderecoBairro[] listarBairro(int codigoPais, int codigoUF,
			int codigoMunicipio) throws IllegalArgumentException;

	public List<EnderecoTrecho> consultarTrecho(int codigoUF,
			int codigoMunicipio, int codigoBairro, String logradouro)
			throws IllegalArgumentException;

	public SiadmOrgao[] listarOrgao(int codigoPai)
			throws IllegalArgumentException;

	public SiadmSetor[] listarSetor(int codigoOrgao)
			throws IllegalArgumentException;

	public SiadmUsuario[] listarUsuarioPeloOrgaoLotacao(int codigoOrgao)
			throws IllegalArgumentException;

	public SiadmUsuario[] listarUsuarioPeloOrgaoOrigem(int codigoOrgao)
			throws IllegalArgumentException;

	public SiadmUsuario[] listarUsuarioPeloSetor(int codigoSetor)
			throws IllegalArgumentException;

}
