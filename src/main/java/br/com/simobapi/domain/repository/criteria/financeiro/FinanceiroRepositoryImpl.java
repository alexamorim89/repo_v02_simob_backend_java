package br.com.simobapi.domain.repository.criteria.financeiro;

import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroFilterRequestVO;
import br.com.simobapi.domain.entity.FinanceiroEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class FinanceiroRepositoryImpl implements FinanceiroRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<FinanceiroEntity> findByParameters(FinanceiroFilterRequestVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<FinanceiroEntity> criteria = builder.createQuery(FinanceiroEntity.class);

        Root<FinanceiroEntity> root = criteria.from(FinanceiroEntity.class);
        Join<Object, Object> cliente = root.join("cliente", JoinType.INNER);
        Join<Object, Object> imovel = root.join("imovel", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getFinanceiro() != null){
            predicates.add( builder.equal(root.get("codigoSimob"), filterRequestVO.getFinanceiro()));
        }

        if (filterRequestVO.getCliente() != null){
            predicates.add( builder.equal(cliente.get("codigoSimob"), filterRequestVO.getCliente()) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getNomeCliente())){
            predicates.add( builder.like(builder.lower( cliente.get("nome") ), "%" + filterRequestVO.getNomeCliente().toLowerCase() +"%") );
        }

        if (filterRequestVO.getImovel() != null){
            predicates.add( builder.equal(imovel.get("codigoSimob"), filterRequestVO.getImovel()) );
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<FinanceiroEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}