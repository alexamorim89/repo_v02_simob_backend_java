package br.com.simobapi.domain.controller.v1.vo.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioResponseVO {
    private String matricula;
    private String nome;
    private String email;
    private String rg;
    private String cpf;
    private String creci;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private boolean ativo;
    private UsuarioEnderecoResponseVO endereco;
    private List<UsuarioPerfilResponseVO> perfis;
}
