package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioUpdateRequestVO;

import br.com.simobapi.domain.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    UsuarioEntity salvar(UsuarioRequestVO usuarioRequestVO);

    Page<UsuarioEntity> consultar(Pageable pageable);

    Page<UsuarioEntity> consultarPor(UsuarioFilterRequestVO filterRequestVO, Pageable pageable);

    UsuarioEntity consultarPorMatricula(String matricula);

    UsuarioEntity atualizar(String matricula, UsuarioUpdateRequestVO usuarioUpdateRequestVO);

    void excluir(String matricula);

    void modificarStatus(boolean status, String matricula);
}
