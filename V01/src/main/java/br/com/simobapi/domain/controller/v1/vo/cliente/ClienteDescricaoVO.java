package br.com.simobapi.domain.controller.v1.vo.cliente;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDescricaoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rg;

    private String cpf;

    private LocalDate dataNascimento;

    private String cnpj;

    private String inscricaoEstadual;

    private String razaoSocial;

    public ClienteDescricaoVO(String rg, String cpf, LocalDate dataNascimento){
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public ClienteDescricaoVO(String cnpj, String inscricaoEstadual, String razaoSocial){
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
    }
}