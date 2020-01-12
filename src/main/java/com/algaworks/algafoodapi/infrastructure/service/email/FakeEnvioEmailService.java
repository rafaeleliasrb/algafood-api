package com.algaworks.algafoodapi.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.algaworks.algafoodapi.core.email.EmailProperties;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

	@Autowired
	public FakeEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
			Configuration freemakerConfig) {
		super(mailSender, emailProperties, freemakerConfig);
	}

	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processarTemplate(mensagem);
		
		log.info("[Email fake] {} \n {}", mensagem.getDestinatarios(), corpo);
	}

}
