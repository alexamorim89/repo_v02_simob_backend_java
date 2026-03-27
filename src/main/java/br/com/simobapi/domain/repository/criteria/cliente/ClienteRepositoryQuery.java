package br.com.simobapi.domain.repository.criteria.cliente;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.entity.ClienteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepositoryQuery {

    Page<ClienteEntity> findByParameters(ClienteFilterVO filterRequestVO, Pageable pageable);
}