package com.algaworks.algafoodapi.core.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.algaworks.algafoodapi.core.email.EmailProperties.EmailImpl;
import com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafoodapi.infrastructure.service.email.SmtpEnvioEmailService;

@Configuration
public class EmailConfig {

	@Bean
	public EnvioEmailService envioEmailService(JavaMailSender mailSender, EmailProperties emailProperties, 
			freemarker.template.Configuration freemakerConfig) {
		
		if(EmailImpl.FAKE.equals(emailProperties.getImpl())) {
			return new FakeEnvioEmailService(mailSender, emailProperties, freemakerConfig);
		}
		else if(EmailImpl.SMTP.equals(emailProperties.getImpl())) {
			return new SmtpEnvioEmailService(mailSender, emailProperties, freemakerConfig);
		}
		else {
			return new SandboxEnvioEmailService(mailSender, emailProperties, freemakerConfig);
		}
	}
}
