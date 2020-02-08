package com.algaworks.algafoodapi.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Grupo {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	@Deprecated
	public Grupo() {}
	
	public Grupo(String nome) {
		this.nome = nome;
	}
	
	@ManyToMany
	@JoinTable(name = "grupo_permissao", 
			joinColumns = @JoinColumn(name = "grupo_id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id"))
	private Set<Permissao> permissoes = new HashSet<>();

	public boolean atribuirPermissao(Permissao permissao) {
		return getPermissoes().add(permissao);
	}

	public boolean desatribuirPermissao(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
}
