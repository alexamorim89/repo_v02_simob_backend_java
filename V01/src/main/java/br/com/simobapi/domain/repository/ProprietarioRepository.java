package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.entity.ProprietarioEntity;

import br.com.simobapi.domain.repository.criteria.proprietario.ProprietarioRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<ProprietarioEntity, Long>, ProprietarioRepositoryQuery {

    ProprietarioEntity findByCodigoSimob(UUID codigoProprietario);

    @Query(value =
    """
    SELECT * FROM proprietario p
    WHERE p.rg =:RG OR p.cpf =:CPF
    """, nativeQuery = true)
    ProprietarioEntity checkPFIfExists(String RG, String CPF);


    @Query(value =
    """
    SELECT * FROM proprietario p
    WHERE p.cnpj =:CNPJ OR p.inscricao_estadual =:inscricaoEstadual
    """, nativeQuery = true)
    ProprietarioEntity checkPJIfExists(String CNPJ, String inscricaoEstadual);
}
