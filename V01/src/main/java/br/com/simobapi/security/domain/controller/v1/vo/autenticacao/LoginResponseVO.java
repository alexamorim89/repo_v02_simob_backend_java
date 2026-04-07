package br.com.simobapi.security.domain.controller.v1.vo.autenticacao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponseVO {
    private Long id;
    private String matricula;
    private String nome;
    private String email;
    private List<String> perfis;
    private String token;

    private String tokenType;


    LoginResponseVO(Long id, String matricula, String nome, String email, List<String> perfis, String accessToken, String tokenType){
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.perfis = perfis;
        this.token = accessToken;
        this.tokenType = tokenType;
    }

}
