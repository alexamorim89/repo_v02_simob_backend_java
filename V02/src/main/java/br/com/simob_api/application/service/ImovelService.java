package br.com.simob_api.application.service;

import br.com.simob_api.api.dto.imovel.ImovelFiltroRequestDTO;
import br.com.simob_api.api.dto.imovel.ImovelResponseDTO;
import br.com.simob_api.api.mapper.ImovelMapper;
import br.com.simob_api.domain.imovel.entity.ImovelEntity;
import br.com.simob_api.domain.imovel.entity.ImovelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository repository;

    @Autowired
    private ImovelMapper imovelMapper;

    public Page<ImovelResponseDTO> consultarComFiltros(ImovelFiltroRequestDTO filtroRequestDTO, Pageable pageable) {
       Page<ImovelEntity>  imovelEntityPage = repository.findByFiltrosAvancados(filtroRequestDTO, pageable);
        return imovelEntityPage.map(imovelMapper::toDTO);
    }
}
