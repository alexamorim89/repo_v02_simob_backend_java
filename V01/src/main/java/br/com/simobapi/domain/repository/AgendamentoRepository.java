package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.entity.AgendamentoEntity;
import br.com.simobapi.domain.repository.criteria.agendamento.AgendamentoRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long>, AgendamentoRepositoryQuery {

    AgendamentoEntity findByCodigoSimob(UUID codigoAgenda);

    AgendamentoEntity findByDataAndHora(LocalDate data, String hora);

}
