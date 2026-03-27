package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaRequestVO;
import br.com.simobapi.domain.entity.VisitaEntity;
import br.com.simobapi.domain.repository.VisitaRepository;
import br.com.simobapi.domain.service.VisitaService;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;
import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VisitaServiceImpl implements VisitaService {

    @Autowired
    private VisitaRepository repository;

    @Override
    public VisitaEntity salvar(VisitaEntity visitaEntity) {
        verificaRN03(visitaEntity);
        return repository.save(visitaEntity);
    }

    @Override
    public VisitaEntity consultarPorId(UUID codigoSimob) {
        var visita = repository.findByCodigoSimob(codigoSimob);
        if (visita == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob)); }
        return visita;
    }

    @Override
    public Page<VisitaEntity> consultarPor(VisitaFilterRequestVO filterRequestVO, Pageable pageable) {
        return repository.findByParameters(filterRequestVO, pageable);
    }

    @Override
    public Page<VisitaEntity> consultar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public VisitaEntity atualizar(UUID codigoSimob, VisitaRequestVO visitaRequestVO) {
      var visitaEntity =  repository.findByCodigoSimob(codigoSimob);
        if (visitaEntity == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob)); }

        setAtualizaVisita(visitaEntity, visitaRequestVO);
        return repository.save(visitaEntity);
    }

    @Override
    public void excluir(UUID codigoSimob) {
        var visitaEntity =  repository.findByCodigoSimob(codigoSimob);
        if (visitaEntity == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob)); }

        repository.delete(visitaEntity);
    }

    private void setAtualizaVisita(VisitaEntity visitaEntity, VisitaRequestVO visitaRequestVO) {
        visitaEntity.setCodigo(visitaEntity.getCodigo());
        visitaEntity.setCodigoSimob(visitaEntity.getCodigoSimob());
        visitaEntity.setNome(visitaRequestVO.getNome());
        visitaEntity.setTelefone(visitaRequestVO.getTelefone());
        visitaEntity.setEmail(visitaRequestVO.getEmail());
        visitaEntity.setData(visitaRequestVO.getData());
        visitaEntity.setHora(visitaRequestVO.getHora());
        visitaEntity.getAnotacao().setDescricao(visitaRequestVO.getAnotacao());
    }

    private void verificaRN03(VisitaEntity visitaEntity) {
        var data = visitaEntity.getData();
        var hora = visitaEntity.getHora();
        var exist = repository.findByDataAndHora(data, hora);
        if (exist != null) {
            throw new DadosJaRegistradoException("RN03: Visita na Data = {"+data+"} e Hora = {"+hora+"}");
        }
    }

}
