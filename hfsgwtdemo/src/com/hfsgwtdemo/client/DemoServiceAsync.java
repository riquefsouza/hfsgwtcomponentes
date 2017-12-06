package com.hfsgwtdemo.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.hfsgwtdemo.client.endereco.EnderecoBairro;
import com.hfsgwtdemo.client.endereco.EnderecoMunicipio;
import com.hfsgwtdemo.client.endereco.EnderecoPais;
import com.hfsgwtdemo.client.endereco.EnderecoTrecho;
import com.hfsgwtdemo.client.endereco.EnderecoUF;
import com.hfsgwtdemo.client.siadm.SiadmOrgao;
import com.hfsgwtdemo.client.siadm.SiadmSetor;
import com.hfsgwtdemo.client.siadm.SiadmUsuario;

public interface DemoServiceAsync {
	void listarPessoas(int min, int max, AsyncCallback<List<Pessoa>> callback)
			throws IllegalArgumentException;

	void getPessoa(int codigo, AsyncCallback<Pessoa> callback)
			throws IllegalArgumentException;

	void listarPais(AsyncCallback<EnderecoPais[]> callback)
			throws IllegalArgumentException;

	void listarUF(int codigoPais, AsyncCallback<EnderecoUF[]> callback)
			throws IllegalArgumentException;

	void listarMunicipio(int codigoPais, int codigoUF,
			AsyncCallback<EnderecoMunicipio[]> callback)
			throws IllegalArgumentException;

	void listarBairro(int codigoPais, int codigoUF, int codigoMunicipio,
			AsyncCallback<EnderecoBairro[]> callback)
			throws IllegalArgumentException;

	void consultarTrecho(int codigoUF, int codigoMunicipio, int codigoBairro,
			String logradouro, AsyncCallback<List<EnderecoTrecho>> callback)
			throws IllegalArgumentException;

	void listarOrgao(int codigoPai, AsyncCallback<SiadmOrgao[]> callback)
			throws IllegalArgumentException;

	void listarSetor(int codigoOrgao, AsyncCallback<SiadmSetor[]> callback)
			throws IllegalArgumentException;

	void listarUsuarioPeloOrgaoLotacao(int codigoOrgao,
			AsyncCallback<SiadmUsuario[]> callback)
			throws IllegalArgumentException;

	void listarUsuarioPeloOrgaoOrigem(int codigoOrgao,
			AsyncCallback<SiadmUsuario[]> callback)
			throws IllegalArgumentException;

	void listarUsuarioPeloSetor(int codigoSetor,
			AsyncCallback<SiadmUsuario[]> callback)
			throws IllegalArgumentException;

}
