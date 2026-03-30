package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.entity.ImovelEntity;
import br.com.simobapi.domain.repository.criteria.imovel.ImovelRepositoryQuery;
import br.com.simobapi.domain.enums.TipoVenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImovelRepository extends JpaRepository<ImovelEntity, Long>, ImovelRepositoryQuery {

    ImovelEntity findByCodigoSimob(UUID codigoImovel);

    List<ImovelEntity> findByTipoVenda(TipoVenda tipoVenda);
}
