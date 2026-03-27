package br.com.simobapi.domain.repository.criteria.visita;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaFilterRequestVO;
import br.com.simobapi.domain.entity.VisitaEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VisitaRepositoryQuery {

    Page<VisitaEntity> findByParameters(VisitaFilterRequestVO filterRequestVO, Pageable pageable);
}