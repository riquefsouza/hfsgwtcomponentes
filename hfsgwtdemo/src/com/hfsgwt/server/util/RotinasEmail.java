package com.hfsgwt.server.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.hfsgwt.client.componentes.email.EmailAnexo;
import com.hfsgwt.client.componentes.email.EmailConteudo;
import com.hfsgwt.server.ServicoException;

public final class RotinasEmail {
	private static Logger log = Logger.getLogger(RotinasEmail.class);
	private static RotinasEmail instancia;

	private RotinasEmail() {
		super();
	}

	public static RotinasEmail getInstancia() {
		if (instancia == null) {
			instancia = new RotinasEmail();
		}
		return instancia;
	}

	public void enviarEmail(EmailConteudo email) throws ServicoException {
		if (email.isHtmlEmail()) {
			enviarEmailHTML(email);
		} else {
			if (email.getAnexos().size() == 0)
				enviarEmailSimples(email);
			else
				enviarEmailComAnexos(email);
		}
	}

	private void enviarEmailSimples(EmailConteudo ec) throws ServicoException {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setHostName(ec.getNomeServidor());
			email.addTo(ec.getEmailDestino(), ec.getNomeDestino());
			if (ec.getEmailCCDestino().trim().length() > 0) {
				email.addCc(ec.getEmailCCDestino(), ec.getNomeCCDestino());
			}
			email.setFrom(ec.getEmailOrigem(), ec.getNomeOrigem());
			email.setSubject(ec.getAssunto());
			email.setMsg(ec.getMensagem());
			email.send();
		} catch (EmailException e) {
			throw new ServicoException(log, "Erro ao enviar email simples, "
					+ e.getMessage());
		}
	}

	private void enviarEmailHTML(EmailConteudo ec) throws ServicoException {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName(ec.getNomeServidor());
			email.addTo(ec.getEmailDestino(), ec.getNomeDestino());
			if (ec.getEmailCCDestino().trim().length() > 0) {
				email.addCc(ec.getEmailCCDestino(), ec.getNomeCCDestino());
			}
			email.setFrom(ec.getEmailOrigem(), ec.getNomeOrigem());
			email.setSubject(ec.getAssunto());

			// URL url = new
			// URL("http://www.apache.org/images/asf_logo_wide.gif");
			// String cid = email.embed(url, "Apache logo");
			// email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid
			// + "\"></html>");

			email.setHtmlMsg(ec.getMensagem());
			// mensagem alternativa
			email.setTextMsg("Seu cliente de email não suporta mensagens HTML");
			email.send();
			// } catch (MalformedURLException e) {
			// e.printStackTrace();
		} catch (EmailException e) {
			throw new ServicoException(log, "Erro ao enviar email HTML, "
					+ e.getMessage());
		}
	}

	private void enviarEmailComAnexos(EmailConteudo ec) throws ServicoException {
		try {
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName(ec.getNomeServidor());
			email.addTo(ec.getEmailDestino(), ec.getNomeDestino());
			if (ec.getEmailCCDestino().trim().length() > 0) {
				email.addCc(ec.getEmailCCDestino(), ec.getNomeCCDestino());
			}
			email.setFrom(ec.getEmailOrigem(), ec.getNomeOrigem());
			email.setSubject(ec.getAssunto());
			email.setMsg(ec.getMensagem());

			EmailAttachment attachment;
			for (EmailAnexo anexo : ec.getAnexos()) {
				attachment = new EmailAttachment();
				// if (anexo.getUrl() != null
				// && anexo.getCaminho().trim().length() == 0)
				// attachment.setURL(anexo.getUrl());
				// else
				// attachment.setPath(anexo.getCaminho());

				if (anexo.getUrl().trim().length() > 0)
					attachment.setURL(new URL(anexo.getUrl()));

				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				attachment.setDescription(anexo.getDescricao());
				attachment.setName(anexo.getNome());
				email.attach(attachment);
			}
			email.send();
		} catch (EmailException e) {
			throw new ServicoException(log, "Erro ao enviar email com anexos, "
					+ e.getMessage());
		} catch (MalformedURLException e) {
			throw new ServicoException(log, "Erro URL do anexo mal formada, "
					+ e.getMessage());
		}
	}
}
