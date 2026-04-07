package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteRequestVO;
import br.com.simobapi.domain.entity.ClienteEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ClienteService {
     ClienteEntity salvar(ClienteEntity clienteEntity);

     Page<ClienteEntity> consultar(Pageable pageable);

     ClienteEntity consultarPorId(UUID codigo);

     Page<ClienteEntity> consultarPor(ClienteFilterVO clienteFilterVO, Pageable pageable);

     ClienteEntity atualizar(UUID codigo, ClienteRequestVO clienteRequestVO);

     void excluir(UUID codigo);
}