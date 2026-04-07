package br.com.simobapi.domain.controller.v1.vo.visita;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitaResponseVO implements Serializable {

    private UUID codigoSimob;
    private String nome;
    private String telefone;
    private String email;
    private LocalDate data;
    private String hora;
    private String anotacao;
    private Boolean geradoPorAgenda;
}
