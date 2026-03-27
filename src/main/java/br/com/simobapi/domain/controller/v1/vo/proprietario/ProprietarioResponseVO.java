package br.com.simobapi.domain.controller.v1.vo.proprietario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProprietarioResponseVO implements Serializable {
	private static final long serialVersionUID = 1L;

    private UUID codigoSimob;
    private String nome;
    private String telefone1;
    private String telefone2;
    private String email;

    @JsonProperty(value = "documentos")
    private ProprietarioDescricaoResponseVO descricao;
}
