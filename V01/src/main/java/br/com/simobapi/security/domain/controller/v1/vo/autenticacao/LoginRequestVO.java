package br.com.simobapi.security.domain.controller.v1.vo.autenticacao;

import lombok.Data;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class LoginRequestVO implements Serializable {
   @NotBlank
   private String email;

   @NotBlank
   private String senha;
}