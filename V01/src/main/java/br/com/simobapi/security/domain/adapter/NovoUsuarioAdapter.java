package br.com.simobapi.security.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioPerfilResponseVO;
import br.com.simobapi.domain.entity.UsuarioEntity;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioResponseVO;
import org.springframework.stereotype.Component;

@Component
public class NovoUsuarioAdapter {

    public NovoUsuarioResponseVO toVO(UsuarioEntity entity) {
        return NovoUsuarioResponseVO
                .builder()
                .codigo(entity.getCodigo())
                .matricula(entity.getMatricula())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .perfis(entity.getPerfis().stream()
                        .map(p -> UsuarioPerfilResponseVO
                                .builder()
                                    .tipo(p.getTipo())
                                .build()).toList()
                )
                .build();
    }
}
