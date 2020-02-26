package com.algaworks.algafoodapi.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.algaworks.algafoodapi.core.security.CryptConfig;
import com.algaworks.algafoodapi.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private Set<Grupo> grupos = new HashSet<>();

	@Deprecated
	public Usuario() {}
	
	private Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		setSenha(senha);
	}
	
	public static Usuario criarUsuarioComSenhaSemHash(String nome, String email, String senha) {
		return new Usuario(nome, email, senha);
	}
	
	public boolean associarGrupo(Grupo grupo) {
		return getGrupos().add(grupo);
	}

	public boolean desassociarGrupo(Grupo grupo) {
		return getGrupos().remove(grupo);
	}
	
	public void verificarSenhaCorreta(String senhaAVerificar) {
		if(!CryptConfig.verificarSenhaCorreta(senhaAVerificar, this.senha)) {
			throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
		}
	}
	
	public void atualizarSenha(String novaSenhaSemHash) {
		setSenha(novaSenhaSemHash);
	}
	
	private void setSenha(String senha) {
		this.senha = CryptConfig.criptografar(senha);
	}
}
