package com.algaworks.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.service.CozinhaService;
import com.algaworks.algafoodapi.util.DatabaseCleaner;
import com.algaworks.algafoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaApiIT {

	private static final String PATH_COZINHA_PERNAMBUCANA_JSON = "/json/correto/cozinha-pernambucana.json";

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaService cozinhaService;
	
	private Cozinha cearense;
	private String cozinhaPernambucanaJson = ResourceUtils.getContentFromResource(PATH_COZINHA_PERNAMBUCANA_JSON);
	private int quantidadeDeCozinhas;
	
	@BeforeEach
	public void inicializar() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		incluiRegistrosTesteNoBanco();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("totalElements", equalTo(quantidadeDeCozinhas));
			//.body("", hasSize(quantidadeDeCozinhas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.accept(ContentType.JSON)
			.body(cozinhaPernambucanaJson)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	private void incluiRegistrosTesteNoBanco() {
		cearense = new Cozinha();
		cearense.setNome("Cearense");
		cozinhaService.salvar(cearense);
		quantidadeDeCozinhas++;
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Brasielira");
		cozinhaService.salvar(novaCozinha);
		quantidadeDeCozinhas++;
	}
}
