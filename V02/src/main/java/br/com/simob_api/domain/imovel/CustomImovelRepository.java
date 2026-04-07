package br.com.simob_api.domain.imovel;

import br.com.simob_api.api.dto.imovel.ImovelFiltroRequestDTO;
import br.com.simob_api.domain.imovel.entity.ImovelEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomImovelRepository {
    Page<ImovelEntity> findByFiltrosAvancados(ImovelFiltroRequestDTO filtroRequestDTO, Pageable pageable);
}
