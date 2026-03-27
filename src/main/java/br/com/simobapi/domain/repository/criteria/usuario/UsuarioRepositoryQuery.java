package br.com.simobapi.domain.repository.criteria.usuario;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioFilterRequestVO;
import br.com.simobapi.domain.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepositoryQuery {
    Page<UsuarioEntity> findByParameters(UsuarioFilterRequestVO filterRequestVO, Pageable pageable);
}