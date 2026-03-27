package br.com.simobapi.domain.repository;

import br.com.simobapi.domain.entity.UsuarioPerfilEntity;
import br.com.simobapi.security.domain.enums.TipoPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<UsuarioPerfilEntity, Long> {
    Optional<UsuarioPerfilEntity> findByTipo(TipoPerfil name);
}
