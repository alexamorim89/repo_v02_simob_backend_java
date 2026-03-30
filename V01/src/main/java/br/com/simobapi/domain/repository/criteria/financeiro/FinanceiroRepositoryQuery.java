package br.com.simobapi.domain.repository.criteria.financeiro;

import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroFilterRequestVO;
import br.com.simobapi.domain.entity.FinanceiroEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinanceiroRepositoryQuery {

    Page<FinanceiroEntity> findByParameters(FinanceiroFilterRequestVO filterRequestVO, Pageable pageable);
}