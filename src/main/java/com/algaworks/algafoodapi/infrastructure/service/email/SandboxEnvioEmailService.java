package com.algaworks.algafoodapi.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafoodapi.core.email.EmailProperties;

import freemarker.template.Configuration;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

	@Autowired
	public SandboxEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
			Configuration freemakerConfig) {
		super(mailSender, emailProperties, freemakerConfig);
	}

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setTo(emailProperties.getSandbox().getDestinatario());
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar o e-mail.", e);
		}
	}
}
