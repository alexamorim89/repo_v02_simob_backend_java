package br.com.simobapi.domain.repository.criteria.visita;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaFilterRequestVO;
import br.com.simobapi.domain.entity.VisitaEntity;

import org.apache.commons.lang3.StringUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class VisitaRepositoryImpl implements VisitaRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<VisitaEntity> findByParameters(VisitaFilterRequestVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VisitaEntity> criteria = builder.createQuery(VisitaEntity.class);
        Root<VisitaEntity> root = criteria.from(VisitaEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getCodigoSimob() != null){
                UUID.fromString(filterRequestVO.getCodigoSimob().toString());
                predicates.add( builder.equal(root.get("codigoSimob"), filterRequestVO.getCodigoSimob() ) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getNome())){
                predicates.add( builder.like( builder.lower(root.get("nome")), builder.lower( builder.literal("%" + filterRequestVO.getNome() + "%")) ) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getEmail())){
            predicates.add( builder.like( builder.lower(root.get("email")), builder.lower( builder.literal(filterRequestVO.getEmail()) ) ));
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<VisitaEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}