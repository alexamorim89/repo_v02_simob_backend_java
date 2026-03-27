package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaRequestVO;
import br.com.simobapi.domain.entity.VisitaEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VisitaService {
    VisitaEntity salvar(VisitaEntity visitaEntity);

    VisitaEntity consultarPorId(UUID codigoSimob);

    Page<VisitaEntity> consultarPor(VisitaFilterRequestVO filterRequestVO, Pageable pageable);

    Page<VisitaEntity> consultar(Pageable pageable);

    VisitaEntity atualizar(UUID codigoSimob, VisitaRequestVO visitaRequestVO);

    void excluir(UUID codigoSimob);
}
