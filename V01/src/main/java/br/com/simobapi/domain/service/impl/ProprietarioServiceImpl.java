package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioFilterVO;

import br.com.simobapi.domain.adapter.ProprietarioAdapter;
import br.com.simobapi.domain.entity.ProprietarioEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaFisicaEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaJuridicaEntity;

import br.com.simobapi.domain.repository.ProprietarioRepository;
import br.com.simobapi.domain.service.ProprietarioService;

import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {

    @Autowired
    private ProprietarioRepository repository;

    @Autowired
    private ProprietarioAdapter adapter;

    @Override
    public Page<ProprietarioEntity> consultar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProprietarioEntity consultarPorId(UUID codigoProprietario) {
        var proprietario =  repository.findByCodigoSimob(codigoProprietario);
        if(proprietario == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoProprietario));  }
        return proprietario;
    }

    @Override
    public Page<ProprietarioEntity> consultarPor(ProprietarioFilterVO filterVO, Pageable pageable) {
        return repository.findByParameters(filterVO, pageable);
    }

    @Override
    public ProprietarioEntity salvar(ProprietarioEntity proprietarioEntity) {
        verificaRN04(proprietarioEntity);
        return repository.save(proprietarioEntity);
    }

    @Override
    public ProprietarioEntity atualizar(UUID codigoProprietario, ProprietarioRequestVO proprietarioRequestVO) {
        var proprietarioEntity = consultarPorId(codigoProprietario);

        var proprietarioAtualizado = atualizaProprietario(proprietarioEntity, proprietarioRequestVO);
        return repository.save(proprietarioAtualizado);
    }

    @Override
    public void excluir(UUID codigoProprietario) {
        var proprietarioEntity = consultarPorId(codigoProprietario);
        repository.delete(proprietarioEntity);
    }

    private ProprietarioEntity atualizaProprietario(ProprietarioEntity proprietarioEntity, ProprietarioRequestVO proprietarioRequestVO) {
        if(proprietarioEntity instanceof ProprietarioPessoaFisicaEntity) {
            proprietarioEntity.setCodigo(proprietarioEntity.getCodigo());
            proprietarioEntity.setCodigoSimob(proprietarioEntity.getCodigoSimob());
            proprietarioEntity.setNome(proprietarioRequestVO.getNome());
            proprietarioEntity.setEmail(proprietarioRequestVO.getEmail());
            proprietarioEntity.setTelefone1(proprietarioRequestVO.getTelefone1());
            proprietarioEntity.setTelefone2(proprietarioRequestVO.getTelefone2());
            ((ProprietarioPessoaFisicaEntity) proprietarioEntity).setRg(proprietarioRequestVO.getDescricao().getRg());
            ((ProprietarioPessoaFisicaEntity) proprietarioEntity).setCpf(proprietarioRequestVO.getDescricao().getCpf() );
            ((ProprietarioPessoaFisicaEntity) proprietarioEntity).setDataNascimento( proprietarioRequestVO.getDescricao().getDataNascimento() );
        } else {
            proprietarioEntity.setCodigo(proprietarioEntity.getCodigo());
            proprietarioEntity.setCodigoSimob(proprietarioEntity.getCodigoSimob());
            proprietarioEntity.setNome(proprietarioRequestVO.getNome());
            proprietarioEntity.setEmail(proprietarioRequestVO.getEmail());
            proprietarioEntity.setTelefone1(proprietarioRequestVO.getTelefone1());
            proprietarioEntity.setTelefone2(proprietarioRequestVO.getTelefone2());
            ((ProprietarioPessoaJuridicaEntity) proprietarioEntity).setCnpj( proprietarioRequestVO.getDescricao().getCnpj());
            ((ProprietarioPessoaJuridicaEntity) proprietarioEntity).setInscricaoEstadual(proprietarioRequestVO.getDescricao().getInscricaoEstadual() );
            ((ProprietarioPessoaJuridicaEntity) proprietarioEntity).setRazaoSocial(proprietarioRequestVO.getDescricao().getRazaoSocial() );
        }
        return proprietarioEntity;
    }

    private void verificaRN04(ProprietarioEntity proprietarioEntity) {
        if(proprietarioEntity instanceof ProprietarioPessoaFisicaEntity) {
            var rg = ((ProprietarioPessoaFisicaEntity) proprietarioEntity).getRg();
            var cpf = ((ProprietarioPessoaFisicaEntity) proprietarioEntity).getCpf();
            var exits = repository.checkPFIfExists(rg, cpf);
            if(exits != null){
                throw new DadosJaRegistradoException("RG = {"+rg+"} ou CPF = {"+cpf+"}");
            }
        } else {
            var cnpj = ((ProprietarioPessoaJuridicaEntity) proprietarioEntity).getCnpj();
            var inscricaoEstadual = ((ProprietarioPessoaJuridicaEntity) proprietarioEntity).getInscricaoEstadual();
            var exits = repository.checkPJIfExists(cnpj, inscricaoEstadual);
            if(exits != null){
                throw new DadosJaRegistradoException("CNPJ = {"+cnpj+"} ou InscricaoEstadual = {"+inscricaoEstadual+"}");
            }
        }
    }

}
