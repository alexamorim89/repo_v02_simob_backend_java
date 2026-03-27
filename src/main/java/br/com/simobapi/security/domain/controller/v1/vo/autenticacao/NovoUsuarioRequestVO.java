package br.com.simobapi.security.domain.controller.v1.vo.autenticacao;

import br.com.simobapi.security.domain.enums.TipoPerfil;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class NovoUsuarioRequestVO {
    @NotBlank(message = "nome é obrigatório")
    private String nome;
//    private String matricula;
    @NotBlank(message = "email é obrigatório")
    private String email;
//    private String cpf;
//    private String creci;
    @NotBlank(message = "senha é obrigatória")
    private String senha;

//    private String rua;
//    private Integer numero;
//    private String complemento;
//    private String cep;
//    private String bairro;
//    private String cidade;
//    private String estado;
    @NotNull(message = "perfis é obrigatório")
    private List<TipoPerfil> perfis;
//    private Boolean ativo;

}
