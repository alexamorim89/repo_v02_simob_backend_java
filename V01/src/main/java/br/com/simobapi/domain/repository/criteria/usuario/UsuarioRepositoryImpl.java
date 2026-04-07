package br.com.simobapi.domain.repository.criteria.usuario;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioFilterRequestVO;
import br.com.simobapi.domain.entity.UsuarioEntity;

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

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<UsuarioEntity> findByParameters(UsuarioFilterRequestVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntity> criteria = builder.createQuery(UsuarioEntity.class).distinct(true);
        Root<UsuarioEntity> root = criteria.from(UsuarioEntity.class);
        Join<Object, Object>  endereco = root.join("endereco", JoinType.INNER);
        Join<Object, Object>  perfil = root.join("perfis", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if(!StringUtils.isEmpty(filterRequestVO.getMatricula())){
            predicates.add( builder.equal(root.get("matricula"), filterRequestVO.getMatricula()));
        }

        if(!StringUtils.isEmpty(filterRequestVO.getNome())){
            predicates.add( builder.like(builder.lower( root.get("nome") ), "%" + filterRequestVO.getNome().toLowerCase() +"%") );
        }

        if (filterRequestVO.getPerfil() != null){
            predicates.add( builder.equal( perfil.get("tipo"), filterRequestVO.getPerfil() ) );
        }

        if (filterRequestVO.getStatus() != null){
            predicates.add( builder.equal(root.get("ativo"), filterRequestVO.getStatus()) );
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<UsuarioEntity> query = manager.createQuery(criteria);
        return new PageImpl<>(query.getResultList());
    }
}