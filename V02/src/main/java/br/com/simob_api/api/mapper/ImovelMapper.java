package br.com.simob_api.api.mapper;

import br.com.simob_api.api.dto.imovel.ImovelResponseDTO;
import br.com.simob_api.domain.imovel.entity.ImovelEntity;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ImovelMapper {
    ImovelResponseDTO toDTO(ImovelEntity entity);
    ImovelEntity toEntity(ImovelResponseDTO dto);
}
