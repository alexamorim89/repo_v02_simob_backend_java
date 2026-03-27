package br.com.simobapi.domain.controller.v1.vo.proprietario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioFilterVO {
    private UUID codigoSimob;
    private String nome;
    private String email;
    private String cpf;
    private String cnpj;
}
