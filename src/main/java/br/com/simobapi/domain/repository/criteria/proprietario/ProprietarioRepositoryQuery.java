package br.com.simobapi.domain.repository.criteria.proprietario;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioFilterVO;
import br.com.simobapi.domain.entity.ProprietarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProprietarioRepositoryQuery {

    Page<ProprietarioEntity> findByParameters(ProprietarioFilterVO filterRequestVO, Pageable pageable);
}