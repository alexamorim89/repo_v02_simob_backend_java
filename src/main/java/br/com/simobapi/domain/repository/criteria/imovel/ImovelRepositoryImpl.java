package br.com.simobapi.domain.repository.criteria.imovel;

import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelFilterRequestVO;

import br.com.simobapi.domain.entity.ImovelEntity;
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

public class ImovelRepositoryImpl implements ImovelRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<ImovelEntity> findByParameters(ImovelFilterRequestVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ImovelEntity> criteria = builder.createQuery(ImovelEntity.class);

        Root<ImovelEntity> root = criteria.from(ImovelEntity.class);
        Join<Object, Object> endereco = root.join("endereco", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getCodigoSimob() != null){
            predicates.add( builder.equal(root.get("codigoSimob"), filterRequestVO.getCodigoSimob()));
        }

        if(!StringUtils.isBlank( filterRequestVO.getValorAluguel())){
            predicates.add( builder.equal(root.get("valor"), filterRequestVO.getValorAluguel().trim()) );
        }

        if (filterRequestVO.getTipoImovel() != null){
            predicates.add( builder.equal(root.get("tipoImovel"), filterRequestVO.getTipoImovel()));
        }

        if (filterRequestVO.getTipoVenda() != null){
            predicates.add( builder.equal(root.get("tipoVenda"), filterRequestVO.getTipoVenda()));
        }

        if (filterRequestVO.getStatus() != null){
            predicates.add( builder.equal(root.get("status"), filterRequestVO.getStatus()));
        }

        if (!StringUtils.isBlank(filterRequestVO.getRua())){
            predicates.add( builder.like( builder.lower(endereco.get("rua")), builder.lower( builder.literal("%" + filterRequestVO.getRua().trim() + "%")) ) );
        }


        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<ImovelEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}