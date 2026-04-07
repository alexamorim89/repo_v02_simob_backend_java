package br.com.simobapi.domain.repository.criteria.cliente;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClientePessoaJuridicaEntity;

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

public class ClienteRepositoryImpl implements ClienteRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<ClienteEntity> findByParameters(ClienteFilterVO filterRequestVO, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<ClienteEntity> criteria = builder.createQuery(ClienteEntity.class);
        Root<ClienteEntity> root = criteria.from(ClienteEntity.class);
        Root<ClientePessoaFisicaEntity> clienteFisica = builder.treat(root, ClientePessoaFisicaEntity.class);
        Root<ClientePessoaJuridicaEntity> clienteJuridica = builder.treat(root, ClientePessoaJuridicaEntity.class);
        Join<Object, Object> endereco = root.join("enderecos", JoinType.INNER);


        List<Predicate> predicates = new ArrayList<>();
        if (filterRequestVO.getCodigo() != null){
            predicates.add( builder.equal( root.get("codigoSimob"), filterRequestVO.getCodigo()) );
        }

        if (!StringUtils.isBlank(filterRequestVO.getNome())){
            predicates.add( builder.like( builder.lower(root.get("nome")), builder.lower( builder.literal("%" + filterRequestVO.getNome() + "%")) ) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getEmail())){
            predicates.add( builder.like( builder.lower(root.get("email")), builder.lower( builder.literal(filterRequestVO.getEmail()) ) ));
        }

        if (!StringUtils.isEmpty(filterRequestVO.getCpf())){
            predicates.add( builder.equal( clienteFisica.get("cpf"), filterRequestVO.getCpf()) );
        }

        if (!StringUtils.isEmpty(filterRequestVO.getCnpj())){
            predicates.add( builder.equal( clienteJuridica.get("cnpj"), filterRequestVO.getCnpj()) );
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<ClienteEntity> query = manager.createQuery(criteria);

        return new PageImpl<>(query.getResultList());
    }
}