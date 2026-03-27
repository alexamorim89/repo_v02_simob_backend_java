package br.com.simobapi.security.domain.controller.v1.vo.autenticacao;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioPerfilResponseVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NovoUsuarioResponseVO {
    private Long codigo;
    private String matricula;
    private String nome;
    private String email;
    private List<UsuarioPerfilResponseVO> perfis;
}
