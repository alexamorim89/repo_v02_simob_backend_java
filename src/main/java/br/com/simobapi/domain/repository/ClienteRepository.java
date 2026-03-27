package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.entity.ClienteEntity;

import br.com.simobapi.domain.repository.criteria.cliente.ClienteRepositoryQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>, ClienteRepositoryQuery {

    @Query("""
     SELECT c FROM ClienteEntity c
     JOIN c.enderecos e
     WHERE  c.codigoSimob = :#{#filter.codigo}
     OR c.nome LIKE %:#{#filter.nome}%
     OR c.email = :#{#filter.email}
     OR c.cpf = :#{#filter.cpf}
     OR c.cnpj = :#{#filter.cnpj}
     """)
    Page<ClienteEntity> findByParametersOld(@Param("filter") ClienteFilterVO clienteFilterVO, Pageable pageable);

    ClienteEntity findByCodigoSimob(UUID codigoCliente);

    @Query(value =
    """
    SELECT * FROM cliente c
    WHERE c.rg =:RG OR c.cpf =:CPF
    """, nativeQuery = true)
    ClienteEntity checkPFIfExists(String RG, String CPF);

    @Query(value =
    """
    SELECT * FROM cliente c
    WHERE c.cnpj =:CNPJ OR c.inscricao_estadual =:inscricaoEstadual
    """, nativeQuery = true)
    ClienteEntity checkPJIfExists(String CNPJ, String inscricaoEstadual);
}