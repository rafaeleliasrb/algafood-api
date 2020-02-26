package com.algaworks.algafoodapi.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public boolean gerenciaRestaurante(Long idRestaurante) {
		return restauranteRepository.ehUsuarioResponsavel(idRestaurante, getUsuarioId());
	}
	
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
		return pedidoRepository.gerenciaRestauranteDoPedido(codigoPedido, getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long idUsuario) {
		return getUsuarioId() != null && idUsuario != null && getUsuarioId().equals(idUsuario);
	}
	
	public boolean podeGerenciarPedidos(String codigoPedido) {
		return temEscopoEscrita() && 
				(hasAuthority("GERENCIAR_PEDIDOS") || gerenciaRestauranteDoPedido(codigoPedido));
	}
	
	public boolean podeGerenciarInformacoesCadastraisDoRestaurante() {
		return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}
	
	public boolean podeGerenciarInformacoesFuncionaisDoRestaurante(Long idRestaurante) {
		return temEscopoEscrita() && 
				(hasAuthority("EDITAR_RESTAURANTES") || gerenciaRestaurante(idRestaurante));
	}
	
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static boolean podeConsultarUsuariosGruposPermissoes() {
		return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}
	
	public static boolean podeConsultar() {
		return temEscopoLeitura() && estaAutenticado();
	}
	
	public static Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		return jwt.getClaim("usuario_id");
	}
	
	public static boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	private static boolean estaAutenticado() {
		return getAuthentication().isAuthenticated();
	}

	private static boolean temEscopoLeitura() {
		return hasAuthority("SCOPE_READ");
	}
	
	private static boolean temEscopoEscrita() {
		return hasAuthority("SCOPE_WRITE");
	}
}
