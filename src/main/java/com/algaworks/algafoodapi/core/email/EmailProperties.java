package com.algaworks.algafoodapi.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	@NotNull
	private String remetente;
	
	private EmailImpl impl = EmailImpl.FAKE;
	
	private Sandbox sandbox = new Sandbox();
	
	@Getter
	@Setter
	public class Sandbox {
		private String destinatario;
	}
	
	public enum EmailImpl {
		SMTP, 
		FAKE, 
		SANDBOX;
	}
}
