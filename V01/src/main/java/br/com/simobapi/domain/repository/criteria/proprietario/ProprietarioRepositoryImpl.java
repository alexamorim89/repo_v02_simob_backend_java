package br.com.simobapi.domain.repository.criteria.proprietario;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioFilterVO;

import br.com.simobapi.domain.entity.ProprietarioEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaFisicaEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaJuridicaEntity;

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
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ProprietarioRepositoryImpl implements ProprietarioRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<ProprietarioEntity> findByParameters(ProprietarioFilterVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ProprietarioEntity> criteria = builder.createQuery(ProprietarioEntity.class);

        Root<ProprietarioEntity> root = criteria.from(ProprietarioEntity.class);
        Root<ProprietarioPessoaFisicaEntity> proprietarioPF = builder.treat(root, ProprietarioPessoaFisicaEntity.class);
        Root<ProprietarioPessoaJuridicaEntity> proprietarioPJ = builder.treat(root, ProprietarioPessoaJuridicaEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getCodigoSimob() != null){
            predicates.add( builder.equal(root.get("codigoSimob"), filterRequestVO.getCodigoSimob()));
        }

        if (!StringUtils.isBlank(filterRequestVO.getNome())){
            predicates.add( builder.like(builder.lower( root.get("nome") ), "%" + filterRequestVO.getNome().trim().toLowerCase() + "%") );
        }

        if (!StringUtils.isBlank(filterRequestVO.getCpf())){
            predicates.add( builder.equal( proprietarioPF.get("cpf"), filterRequestVO.getCpf().trim()) );
        }

        if (!StringUtils.isBlank(filterRequestVO.getCnpj())){
            predicates.add( builder.equal( proprietarioPJ.get("cnpj"), filterRequestVO.getCnpj().trim()) );
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<ProprietarioEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}