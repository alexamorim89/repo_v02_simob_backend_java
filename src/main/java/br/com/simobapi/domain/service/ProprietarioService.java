package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioFilterVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioUpdateVO;
import br.com.simobapi.domain.entity.ProprietarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface ProprietarioService {

    Page<ProprietarioEntity> consultar(Pageable pageable);

    ProprietarioEntity consultarPorId(UUID codigoProprietario);

    Page<ProprietarioEntity> consultarPor(ProprietarioFilterVO filterVO, Pageable pageable);

    ProprietarioEntity salvar(ProprietarioEntity proprietarioEntity);

    ProprietarioEntity atualizar(UUID codigoProprietario, ProprietarioRequestVO proprietarioRequestVO);

    void excluir(UUID codigoProprietario);
}
