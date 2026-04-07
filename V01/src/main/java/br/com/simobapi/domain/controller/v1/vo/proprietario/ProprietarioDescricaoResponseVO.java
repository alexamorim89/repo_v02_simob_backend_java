package br.com.simobapi.domain.controller.v1.vo.proprietario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProprietarioDescricaoResponseVO {
    private String rg;
    private String cpf;
    private LocalDate dataNascimento;
    private String cnpj;
    private String inscricaoEstadual;
    private String razaoSocial;
}