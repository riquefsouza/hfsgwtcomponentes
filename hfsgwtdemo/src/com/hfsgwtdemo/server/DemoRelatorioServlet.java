package com.hfsgwtdemo.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hfsgwt.server.ServicoException;
import com.hfsgwt.server.relatorio.RelatorioServlet;
import com.hfsgwt.server.relatorio.RotinasRelatorio;
import com.hfsgwtdemo.client.Pessoa;

public class DemoRelatorioServlet extends RelatorioServlet {
	private static final long serialVersionUID = -4805961860026379328L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		super.doGet(req, res);	

		HashMap<String, String> paramsRelatorio = new HashMap<String, String>();
		paramsRelatorio.put("nomeSistema", "HFS - GWT - Componentes");

		List<Pessoa> listaResultado = PessoaDAO.getInstancia().listarPessoas(0, 0);

		RelPessoaDS fonteDados = new RelPessoaDS(listaResultado);

		if (getOpcao() == TipoOpcao.PADRAO) {
			RotinasRelatorio.getInstancia().relatorioPDF(res,
					getDiretorioRelatorio(), paramsRelatorio, fonteDados);
		} else if (getOpcao() == TipoOpcao.ARQUIVO) {
			try {
				RotinasRelatorio.getInstancia().relatorioPorFormato(res,
						getDiretorioRelatorio(), paramsRelatorio, fonteDados,
						getFormatoArquivo(), getDiretorioArquivo(), isCompactado(), true);
			} catch (ServicoException e) {
				e.printStackTrace();
			}
		}		
	}
}
