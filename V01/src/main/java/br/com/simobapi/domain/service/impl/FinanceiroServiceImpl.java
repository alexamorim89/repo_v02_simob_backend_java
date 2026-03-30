package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroUpdateRequestVO;
import br.com.simobapi.domain.adapter.FinanceiroAdapter;
import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.FinanceiroEntity;
import br.com.simobapi.domain.entity.ImovelEntity;
import br.com.simobapi.domain.enums.TipoNegocio;
import br.com.simobapi.domain.repository.ClienteRepository;
import br.com.simobapi.domain.repository.FinanceiroRepository;
import br.com.simobapi.domain.repository.ImovelRepository;
import br.com.simobapi.domain.service.FinanceiroService;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FinanceiroServiceImpl implements FinanceiroService {

    @Autowired
    private FinanceiroRepository financeiroRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FinanceiroAdapter adapter;

    @Override
    public FinanceiroEntity salvar(FinanceiroRequestVO financeiroRequestVO) {
        var cliente = verificaCliente(financeiroRequestVO);
        var imovel = verificaImovel(financeiroRequestVO);

        var financeiro = adapter.toEntity(financeiroRequestVO);
        imovel.setFinanceiro(financeiro);
        financeiro.setCliente(cliente);
        financeiro.setImovel(imovel);

        return financeiroRepository.save(financeiro);
    }

    @Override
    public Page<FinanceiroEntity> consultar(Pageable pageable) {
        return financeiroRepository.findAll(pageable);
    }

    @Override
    public Page<FinanceiroEntity> consultarPor(FinanceiroFilterRequestVO filterRequestVO, Pageable pageable) {
        return financeiroRepository.findByParameters(filterRequestVO, pageable);
    }

    @Override
    public FinanceiroEntity consultarPorId(UUID codigoFinanceiro) {
        var financeiro = financeiroRepository.findByCodigoSimob(codigoFinanceiro);
        if (financeiro == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoFinanceiro));}

        return financeiro;
    }

    @Override
    public FinanceiroEntity atualizar(UUID codigoFinanceiro, FinanceiroUpdateRequestVO financeiroUpdateRequestVO) {
        var financeiroEntity = consultarPorId(codigoFinanceiro);
        if (financeiroEntity == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoFinanceiro)); }

        setAtualizaFinanceiro(financeiroEntity, financeiroUpdateRequestVO);

        return financeiroRepository.save(financeiroEntity);
    }

    @Override
    public void excluir(UUID codigoFinanceiro) {
        var financeiro = consultarPorId(codigoFinanceiro);
        if (financeiro == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoFinanceiro));}

//        var imovel = imovelRepository.findByCodigoSimob(financeiro.getImovel().getCodigoSimob());
//        imovel.setCodigoSimob(null);
//        imovelRepository.save(imovel);

        financeiroRepository.delete(financeiro);
    }

    @Override
    public void modificarStatus(TipoNegocio situacao, UUID codigo) {
        var financeiro = consultarPorId(codigo);
        financeiro.setSituacao(situacao);
        financeiroRepository.save(financeiro);
    }


    private ClienteEntity verificaCliente(FinanceiroRequestVO financeiroRequestVO) {
        var cliente = clienteRepository.findByCodigoSimob(financeiroRequestVO.getCodigoCliente());
        if (cliente == null) { throw new RecursoNaoEncontradoException(String.valueOf(financeiroRequestVO.getCodigoCliente())); }
        return cliente;
    }

    private ImovelEntity verificaImovel(FinanceiroRequestVO financeiroRequestVO) {
        var imovel = imovelRepository.findByCodigoSimob(financeiroRequestVO.getCodigoImovel());
        if (imovel == null) { throw new RecursoNaoEncontradoException(String.valueOf(financeiroRequestVO.getCodigoImovel())); }
        return imovel;
    }

    private void setAtualizaFinanceiro(FinanceiroEntity financeiroEntity, FinanceiroUpdateRequestVO financeiroUpdateRequestVO) {
        financeiroEntity.setCodigo(financeiroEntity.getCodigo());
        financeiroEntity.setCodigoSimob(financeiroEntity.getCodigoSimob());
        financeiroEntity.setValor(financeiroUpdateRequestVO.getValor());
        financeiroEntity.setValorMensal(financeiroUpdateRequestVO.getValorMensal());
        financeiroEntity.setSituacao(financeiroUpdateRequestVO.getSituacao());
    }

}