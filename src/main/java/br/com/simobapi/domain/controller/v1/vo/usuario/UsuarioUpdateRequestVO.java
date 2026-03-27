package br.com.simobapi.domain.controller.v1.vo.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class UsuarioUpdateRequestVO {
    private String nome;
    private String email;
//    private String rg;
//    private String cpf;
//    private String creci;
    private boolean ativo;

//    @JsonProperty(required = true)
//    private UsuarioEnderecoRequestVO endereco;

    @JsonProperty(required = true)
    private List<UsuarioPerfilRequestVO> perfis;
}