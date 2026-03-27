package br.com.simobapi.domain.controller.v1.vo.proprietario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProprietarioRequestVO implements Serializable {
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;

    @NotNull(message = "documentos é obrigatório")
    @JsonProperty(value = "documentos")
    private ProprietarioDescricaoRequestVO descricao;
}
