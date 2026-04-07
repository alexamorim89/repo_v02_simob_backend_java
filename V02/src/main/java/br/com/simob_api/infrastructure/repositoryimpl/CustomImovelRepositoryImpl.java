package br.com.simob_api.infrastructure.repositoryimpl;

import br.com.simob_api.api.dto.imovel.ImovelFiltroRequestDTO;
import br.com.simob_api.domain.imovel.CustomImovelRepository;
import br.com.simob_api.domain.imovel.entity.ImovelEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomImovelRepositoryImpl implements CustomImovelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ImovelEntity> findByFiltrosAvancados(ImovelFiltroRequestDTO filtroRequestDTO, Pageable pageable) {

        TypedQuery<ImovelEntity> query = entityManager.createQuery("", ImovelEntity.class);

        // Paginação
        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<ImovelEntity> resultList = query.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);


    }
}
