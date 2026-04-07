package br.com.simobapi.domain.repository.criteria.agendamento;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoFilterRequestVO;
import br.com.simobapi.domain.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgendamentoRepositoryQuery {

    Page<AgendamentoEntity> findByParameters(AgendamentoFilterRequestVO filterRequestVO, Pageable pageable);
}