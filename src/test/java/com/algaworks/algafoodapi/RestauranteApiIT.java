package com.algaworks.algafoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Endereco;
import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.service.CidadeService;
import com.algaworks.algafoodapi.domain.service.CozinhaService;
import com.algaworks.algafoodapi.domain.service.EstadoService;
import com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.algaworks.algafoodapi.util.DatabaseCleaner;
import com.algaworks.algafoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteApiIT {

	private static final String ASSOCIACAO_NAO_ENCONTRADA = "Associação não encontrada";
	private static final String DADOS_INVALIDOS = "Dados inválidos";
	private static final String PATH_RESTAURANTE_DEU_SOPA_JSON = "/json/correto/restaurante-deu-sopa.json";
	private static final String PATH_RESTAURANTE_DEU_SOPA_COM_TAXA_FRETE_INVALIDA_JSON = 
			"/json/incorreto/restaurante-deu-sopa-taxa-frete-invalida.json";
	private static final String PATH_RESTAURANTE_DEU_SOPA_SEM_COZINHA_JSON = 
			"/json/incorreto/restaurante-deu-sopa-sem-cozinha.json";
	private static final String PATH_RESTAURANTE_DEU_SOPA_COZINHA_INEXISTENTE_JSON = 
			"/json/incorreto/restaurante-deu-sopa-cozinha-inexistente.json";

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private EstadoService estadoService;

	private Cozinha brasileira;
	private int quantidadeDeRestaurantes;
	private String restauranteDeuSopaJson = ResourceUtils.getContentFromResource(PATH_RESTAURANTE_DEU_SOPA_JSON);
	private String restauranteDeuSopaComTaxaFreteInvalida = 
			ResourceUtils.getContentFromResource(PATH_RESTAURANTE_DEU_SOPA_COM_TAXA_FRETE_INVALIDA_JSON);
	private String restauranteDeuSopaSemCozinha = 
			ResourceUtils.getContentFromResource(PATH_RESTAURANTE_DEU_SOPA_SEM_COZINHA_JSON);
	private String restauranteDeuSopaCozinhaInexistente = 
			ResourceUtils.getContentFromResource(PATH_RESTAURANTE_DEU_SOPA_COZINHA_INEXISTENTE_JSON);
	
	@BeforeEach
	public void inicializar() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "restaurantes";
		
		databaseCleaner.clearTables();
		incluirRegistrosTesteNoBanco();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorreta_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeDeRestaurantes));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		given()
			.accept(ContentType.JSON)
			.body(restauranteDeuSopaJson)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoTaxaFreteInvalida() {
		given()
			.accept(ContentType.JSON)
			.body(restauranteDeuSopaComTaxaFreteInvalida)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoSemCozinha() {
		given()
			.accept(ContentType.JSON)
			.body(restauranteDeuSopaSemCozinha)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS));
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCozinhaInexistente() {
		given()
			.accept(ContentType.JSON)
			.body(restauranteDeuSopaCozinhaInexistente)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(ASSOCIACAO_NAO_ENCONTRADA));
	}
	
	private void incluirRegistrosTesteNoBanco() {
		Estado estado = new Estado();
		estado.setNome("Ceará");
		estado = estadoService.salvar(estado);
		
		Cidade cidade = new Cidade();
		cidade.setNome("Fortaleza");
		cidade.setEstado(estado);
		cidade = cidadeService.salvar(cidade);
		
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Joaquim Távora");
		endereco.setCep("04304-535");
		endereco.setCidade(cidade);
		endereco.setLogradouro("Rua Fagundes Filho");
		endereco.setNumero("258");
		
		brasileira = adicionaCozinhaBrasileira();
		
		Restaurante saborBrasileiro = new Restaurante();
		saborBrasileiro.setCozinha(brasileira);
		saborBrasileiro.setNome("Sabor brasileiro");
		saborBrasileiro.setTaxaFrete(BigDecimal.valueOf(15));
		saborBrasileiro.setEndereco(endereco);
		restauranteService.salvar(saborBrasileiro);
		quantidadeDeRestaurantes++;
		
		Restaurante novoRestaurante = new Restaurante();
		novoRestaurante.setCozinha(brasileira);
		novoRestaurante.setNome("Padaria Espiritual");
		novoRestaurante.setTaxaFrete(BigDecimal.valueOf(5.40));
		novoRestaurante.setEndereco(endereco);
		restauranteService.salvar(novoRestaurante);
		quantidadeDeRestaurantes++;
	}

	private Cozinha adicionaCozinhaBrasileira() {
		Cozinha brasileira = new Cozinha();
		brasileira.setNome("Brasileira");
		return cozinhaService.salvar(brasileira);
	}
}
