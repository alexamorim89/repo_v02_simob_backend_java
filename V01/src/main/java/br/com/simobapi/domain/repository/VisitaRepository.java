package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.entity.VisitaEntity;

import br.com.simobapi.domain.repository.criteria.visita.VisitaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface VisitaRepository extends JpaRepository<VisitaEntity, Long>, VisitaRepositoryQuery {

    VisitaEntity findByCodigoSimob(UUID codigo);

    VisitaEntity findByDataAndHora(LocalDate data, String hora);
}
