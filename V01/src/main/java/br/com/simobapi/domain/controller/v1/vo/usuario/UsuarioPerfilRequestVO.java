package br.com.simobapi.domain.controller.v1.vo.usuario;

import br.com.simobapi.security.domain.enums.TipoPerfil;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class UsuarioPerfilRequestVO {
    private TipoPerfil tipo;
}