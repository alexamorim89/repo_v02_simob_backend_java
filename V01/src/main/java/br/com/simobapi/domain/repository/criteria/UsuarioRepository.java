package br.com.simobapi.domain.repository.criteria;

import br.com.simobapi.domain.repository.criteria.usuario.UsuarioRepositoryQuery;
import br.com.simobapi.domain.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, UsuarioRepositoryQuery {


    @Query("""
        FROM UsuarioEntity u
        WHERE u.email =:email
        OR u.cpf =:CPF
        OR u.rg =:RG
        OR u.creci =:CRECI
        """)
    UsuarioEntity findByUser(String email, String CPF, String RG, String CRECI);

    Optional<UsuarioEntity> findByMatricula(String username);

    UsuarioEntity findByEmail(String email);

    Boolean existsByMatricula(String matricula);

    Boolean existsByEmail(String email);

}
