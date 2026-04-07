package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroUpdateRequestVO;
import br.com.simobapi.domain.entity.FinanceiroEntity;

import br.com.simobapi.domain.enums.TipoNegocio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FinanceiroService {

    FinanceiroEntity salvar(FinanceiroRequestVO financeiroRequestVO);

    Page<FinanceiroEntity> consultar(Pageable pageable);

    Page<FinanceiroEntity> consultarPor(FinanceiroFilterRequestVO filterRequestVO, Pageable pageable);

    FinanceiroEntity consultarPorId(UUID codigoFinanceiro);

    FinanceiroEntity atualizar(UUID codigoFinanceiro, FinanceiroUpdateRequestVO financeiroUpdateRequestVO);

    void excluir(UUID codigoFinanceiro);

    void modificarStatus(TipoNegocio situacao, UUID codigo);
}