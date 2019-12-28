package com.algaworks.algafoodapi.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
