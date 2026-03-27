package br.com.simobapi.domain.repository.criteria.agendamento;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoFilterRequestVO;
import br.com.simobapi.domain.entity.AgendamentoEntity;

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
import java.util.UUID;

public class AgendamentoRepositoryImpl implements AgendamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<AgendamentoEntity> findByParameters(AgendamentoFilterRequestVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<AgendamentoEntity> criteria = builder.createQuery(AgendamentoEntity.class);
        Root<AgendamentoEntity> root = criteria.from(AgendamentoEntity.class);
        Join<Object, Object> visita = root.join("visita", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getCodigoSimob() != null){
                UUID.fromString(filterRequestVO.getCodigoSimob().toString());
                predicates.add( builder.equal(root.get("codigoSimob"), filterRequestVO.getCodigoSimob() ) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getNome())){
                predicates.add( builder.like( builder.lower(root.get("nome")), builder.lower( builder.literal("%" + filterRequestVO.getNome() + "%")) ) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getEmail())){
                predicates.add(builder.equal(root.get("email"), filterRequestVO.getEmail()));
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<AgendamentoEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}