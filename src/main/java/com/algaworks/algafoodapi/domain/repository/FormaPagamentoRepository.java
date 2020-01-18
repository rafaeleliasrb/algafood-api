package com.algaworks.algafoodapi.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafoodapi.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long>{

	@Query("select max(dataAtualizacao) from FormaPagamento order by dataAtualizacao desc")
	public Optional<OffsetDateTime> findDataAtualizacaoMaisRecente();
	
	@Query("select dataAtualizacao from FormaPagamento where id = :idFormaPagamento")
	public OffsetDateTime findDataAtualizacaoById(Long idFormaPagamento);
}
