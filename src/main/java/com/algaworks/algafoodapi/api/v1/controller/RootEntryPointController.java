package com.algaworks.algafoodapi.api.v1.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@GetMapping
	public RootEntryPointModel root() {
		RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(linkTo(CozinhaController.class).withRel("cozinhas"));
		rootEntryPointModel.add(linkTo(PedidoController.class).withRel("pedidos"));
		rootEntryPointModel.add(linkTo(RestauranteController.class).withRel("restaurantes"));
		rootEntryPointModel.add(linkTo(GrupoController.class).withRel("grupos"));
		rootEntryPointModel.add(linkTo(UsuarioController.class).withRel("usuarios"));
		rootEntryPointModel.add(linkTo(PermissaoController.class).withRel("permissoes"));
		rootEntryPointModel.add(linkTo(FormaPagamentoController.class).withRel("formas-pagamento"));
		rootEntryPointModel.add(linkTo(EstadoController.class).withRel("estados"));
		rootEntryPointModel.add(linkTo(CidadeController.class).withRel("cidades"));
		rootEntryPointModel.add(linkTo(EstatisticasController.class).withRel("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {}
}
