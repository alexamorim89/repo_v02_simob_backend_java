package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.AnotacaoRequestVO;
import br.com.simobapi.domain.entity.AgendamentoEntity;
import br.com.simobapi.domain.entity.AnotacaoEntity;
import br.com.simobapi.domain.entity.VisitaEntity;
import br.com.simobapi.domain.repository.AgendamentoRepository;
import br.com.simobapi.domain.service.AgendamentoService;

import br.com.simobapi.domain.service.exceptions.AgendamentoVinculadoVisitaException;
import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    @Autowired
    private AgendamentoRepository repository;

    @Override
    public AgendamentoEntity salvar(AgendamentoEntity agendamentoEntity) {
        verificaRN03(agendamentoEntity);
        return repository.save(agendamentoEntity);
    }

    @Override
    public Page<AgendamentoEntity> consultar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<AgendamentoEntity> consultarPor(AgendamentoFilterRequestVO filterRequestVO, Pageable pageable) {
        return repository.findByParameters(filterRequestVO, pageable);
    }

    @Override
    public AgendamentoEntity consultarPorId(UUID codigoSimob) {
        var agenda = repository.findByCodigoSimob(codigoSimob);
        if (agenda == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob));}
        return agenda;
    }

    @Override
    public AgendamentoEntity atualizar(UUID codigoSimob, AgendamentoRequestVO agendamentoRequestVO) {
        var agendaEntity = consultarPorId(codigoSimob);

        var agendamentoAtualizado= atualizaAgenda(agendaEntity, agendamentoRequestVO);
        return repository.save(agendamentoAtualizado);
    }

    @Override
    public void excluir(UUID codigoSimob) {
        var agendaEntity = consultarPorId(codigoSimob);
        verificaRN10(agendaEntity);

        repository.delete(agendaEntity);
    }

    @Override
    public void gerarVisita(UUID codigoSimob, AnotacaoRequestVO anotacao) {
        var agenda = consultarPorId(codigoSimob);
        var visita  = vinculaVisita(agenda, anotacao);
        agenda.setVisita(visita);
        repository.save(agenda);
    }


    private AgendamentoEntity atualizaAgenda(AgendamentoEntity agendaEntity, AgendamentoRequestVO agendamentoRequestVO) {
        agendaEntity.setCodigo(agendaEntity.getCodigo());
        agendaEntity.setCodigoSimob(agendaEntity.getCodigoSimob());
        agendaEntity.setNome(agendamentoRequestVO.getNome());
        agendaEntity.setEmail(agendamentoRequestVO.getEmail());
        agendaEntity.setData(agendamentoRequestVO.getData());
        agendaEntity.setHora(agendamentoRequestVO.getHora());
        agendaEntity.setDescricao(agendamentoRequestVO.getDescricao());
        return agendaEntity;
    }

    private VisitaEntity vinculaVisita(AgendamentoEntity agenda, AnotacaoRequestVO anotacao) {
        return VisitaEntity
                .builder()
                    .nome(agenda.getNome())
                    .email(agenda.getEmail())
                    .telefone(agenda.getTelefone())
                    .data(agenda.getData())
                    .hora(agenda.getHora())
                    .anotacao(AnotacaoEntity
                            .builder()
                            .descricao(anotacao.getDescricao())
                            .build())
                    .gerardoPorAgenda(true)
                .build();
    }



    private void verificaRN03(AgendamentoEntity agendamentoEntity) {
        var data = agendamentoEntity.getData();
        var hora = agendamentoEntity.getHora();
        var exist = repository.findByDataAndHora(data, hora);
        if (exist != null) {
            throw new DadosJaRegistradoException("RN03: Agendamento na Data = {"+data+"} e Hora = {"+hora+"}");
        }
    }

    private void verificaRN10(AgendamentoEntity agendaEntity) {
        if (agendaEntity.getVisita() != null){
            throw new AgendamentoVinculadoVisitaException("RN10: Agendamento Vinculado a uma visita");
        }
    }

}