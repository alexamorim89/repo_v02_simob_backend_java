package br.com.simob_api.domain.imovel.entity;

import br.com.simob_api.domain.imovel.CustomImovelRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImovelRepository extends JpaRepository<ImovelEntity, Long>, CustomImovelRepository {
}
