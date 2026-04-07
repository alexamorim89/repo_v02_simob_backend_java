package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.AnotacaoRequestVO;
import br.com.simobapi.domain.entity.AgendamentoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AgendamentoService {

    AgendamentoEntity salvar(AgendamentoEntity agendamentoEntity);

    Page<AgendamentoEntity> consultar(Pageable pageable);

    Page<AgendamentoEntity> consultarPor(AgendamentoFilterRequestVO filterRequestVO, Pageable pageable);

    AgendamentoEntity consultarPorId(UUID codigoSimob);

    AgendamentoEntity atualizar(UUID codigoSimob, AgendamentoRequestVO agendamentoRequestVO);

    void excluir(UUID codigoSimob);

    void gerarVisita(UUID codigoSimob, AnotacaoRequestVO anotacao);
}
