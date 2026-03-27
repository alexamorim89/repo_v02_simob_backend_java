package br.com.simobapi.domain.repository.criteria.imovel;

import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelFilterRequestVO;
import br.com.simobapi.domain.entity.ImovelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImovelRepositoryQuery {

    Page<ImovelEntity> findByParameters(ImovelFilterRequestVO filterRequestVO, Pageable pageable);
}