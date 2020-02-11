package com.algaworks.algafoodapi.core.springfox;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.algaworks.algafoodapi.api.v1.model.CidadeModel;
import com.algaworks.algafoodapi.api.v1.model.CozinhaModel;
import com.algaworks.algafoodapi.api.v1.model.EstadoModel;
import com.algaworks.algafoodapi.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafoodapi.api.v1.model.GrupoModel;
import com.algaworks.algafoodapi.api.v1.model.PedidoResumoModel;
import com.algaworks.algafoodapi.api.v1.model.PermissaoModel;
import com.algaworks.algafoodapi.api.v1.model.ProdutoModel;
import com.algaworks.algafoodapi.api.v1.model.RestauranteResumoModel;
import com.algaworks.algafoodapi.api.v1.model.UsuarioModel;
import com.algaworks.algafoodapi.api.v1.openapi.model.CidadesModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.EstadosModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.GruposModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.PermissoesModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.ProdutosModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.RestaurantesResumoModelOpenApi;
import com.algaworks.algafoodapi.api.v1.openapi.model.UsuariosModelOpenApi;
import com.algaworks.algafoodapi.api.v2.model.CidadeModelV2;
import com.algaworks.algafoodapi.api.v2.model.CozinhaModelV2;
import com.algaworks.algafoodapi.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.algaworks.algafoodapi.api.v2.openapi.model.CozinhasModelV2OpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	private static final String PROBLEMA_MODEL_OPEN_API = "Problema";
	private static final String UNSUPPORTED_MEDIA_TYPE_MSG_ERRO = "Media type não suportado";
	private static final String BAD_REQUEST_MSG_ERRO = "Requisição inválida (erro do cliente)";
	private static final String NOT_ACCEPTABLE_MSG_ERRO = "Recurso não possui representação aceita pelo cliente";
	private static final String INTERNAL_SERVER_ERROR_MSG_ERRO = "Erro interno do servidor";

	@Bean
	public Docket apiDocketV1() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("V1")
			.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafoodapi.api"))
				.paths(PathSelectors.ant("/v1/**"))
//					.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
			.globalResponseMessage(RequestMethod.POST, globalPostResponseMessage())
			.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessage())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessage())
			.globalResponseMessage(RequestMethod.PATCH, globalPatchResponseMessage())
//				.globalOperationParameters(Arrays.asList(
//						new ParameterBuilder()
//							.name("campos")
//							.description("Nomes de propriedades para filtra a resposta separados por vírgula")
//							.parameterType("query")
//							.modelRef(new ModelRef("String"))
//							.build()
//				))
			
			.apiInfo(apiInfoV1())
			.ignoredParameterTypes(ServletWebRequest.class, HttpServletRequest.class, URI.class, URL.class,
					File.class, InputStream.class, Resource.class, URLStreamHandler.class, Optional.class)
//				.enableUrlTemplating(true)
			
			.additionalModels(typeResolver.resolve(Problem.class))
			.tags(new Tag("Cidades", "Gerencia as cidades"), 
					new Tag("Grupos", "Gerencia os grupos"),
					new Tag("Cozinhas", "Gerencia as cozinhas"),
					new Tag("Pedidos", "Gerencia os pedidos"),
					new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
					new Tag("Restaurantes", "Gerencia os restaurantes"),
					new Tag("Estados", "Gerencia os estados"),
					new Tag("Produtos", "Gerencia os produtos"),
					new Tag("Usuarios", "Gerencia os usuários"),
					new Tag("Estatisticas", "Gerencia as estatísticas"))
			
			.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			.directModelSubstitute(Links.class, LinksModelOpenApi.class)
//				.directModelSubstitute(PedidoFilter.class, PedidoFilterOpenApi.class)
			
			.alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, CozinhaModel.class), 
					CozinhasModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModel.class), 
					PedidosResumoModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, CidadeModel.class), 
					CidadesModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, EstadoModel.class), 
					EstadosModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class), 
					FormasPagamentoModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, GrupoModel.class), 
					GruposModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, PermissaoModel.class), 
					PermissoesModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, ProdutoModel.class), 
					ProdutosModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, RestauranteResumoModel.class), 
					RestaurantesResumoModelOpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, UsuarioModel.class), 
					UsuariosModelOpenApi.class))
			;
	}
	
	@Bean
	public Docket apiDocketV2() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("V2")
			.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafoodapi.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, globalGetResponseMessage())
			.globalResponseMessage(RequestMethod.POST, globalPostResponseMessage())
			.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessage())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessage())
			.globalResponseMessage(RequestMethod.PATCH, globalPatchResponseMessage())
			
			.apiInfo(apiInfoV2())
			.ignoredParameterTypes(ServletWebRequest.class, HttpServletRequest.class, URI.class, URL.class,
					File.class, InputStream.class, Resource.class, URLStreamHandler.class, Optional.class)
			
			.additionalModels(typeResolver.resolve(Problem.class))
			.tags(new Tag("Cidades", "Gerencia as cidades"), 
					new Tag("Cozinhas", "Gerencia as cozinhas"))
			
			.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			.directModelSubstitute(Links.class, LinksModelOpenApi.class)
			
			.alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, CozinhaModelV2.class), 
					CozinhasModelV2OpenApi.class))
			.alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, CidadeModelV2.class), 
					CidadesModelV2OpenApi.class))
			;
	}
	
	private List<ResponseMessage> globalGetResponseMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(INTERNAL_SERVER_ERROR_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message(NOT_ACCEPTABLE_MSG_ERRO)
					.build()
			);
	}
	
	private List<ResponseMessage> globalPostResponseMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(BAD_REQUEST_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(INTERNAL_SERVER_ERROR_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message(NOT_ACCEPTABLE_MSG_ERRO)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message(UNSUPPORTED_MEDIA_TYPE_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build()
			);
	}
	
	private List<ResponseMessage> globalPutResponseMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(BAD_REQUEST_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(INTERNAL_SERVER_ERROR_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message(NOT_ACCEPTABLE_MSG_ERRO)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message(UNSUPPORTED_MEDIA_TYPE_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(BAD_REQUEST_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(INTERNAL_SERVER_ERROR_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build()
			);
	}
	
	private List<ResponseMessage> globalPatchResponseMessage() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message(BAD_REQUEST_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message(INTERNAL_SERVER_ERROR_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message(NOT_ACCEPTABLE_MSG_ERRO)
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message(UNSUPPORTED_MEDIA_TYPE_MSG_ERRO)
					.responseModel(new ModelRef(PROBLEMA_MODEL_OPEN_API))
					.build()
			);
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para restaurantes e clientes")
				.version("1")
				.contact(new Contact("Rafael", "http://www.github.com/rafaeleliasrb", "rafaeleliasrb@gmail.com"))
				.build();
	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para restaurantes e clientes")
				.version("2")
				.contact(new Contact("Rafael", "http://www.github.com/rafaeleliasrb", "rafaeleliasrb@gmail.com"))
				.build();
	}
		
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
