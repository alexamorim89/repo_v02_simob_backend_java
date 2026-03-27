package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.repository.criteria.financeiro.FinanceiroRepositoryQuery;
import br.com.simobapi.domain.entity.FinanceiroEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FinanceiroRepository extends JpaRepository<FinanceiroEntity, Long>, FinanceiroRepositoryQuery {

    @Query("""
        SELECT f FROM FinanceiroEntity f
        INNER JOIN f.cliente c 
        INNER JOIN f.imovel i
    """)
    Page<FinanceiroEntity> findAll(Pageable pageable);

    @Query(
    """
       SELECT f FROM FinanceiroEntity f
       INNER JOIN f.cliente c 
       INNER JOIN f.imovel i
       WHERE f.codigoSimob = :codigoFinanceiro
    """)
    FinanceiroEntity findByCodigoSimob(UUID codigoFinanceiro);
}