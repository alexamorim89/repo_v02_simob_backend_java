package br.com.simobapi.domain.controller.v1.vo.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteRequestVO {

    private String nome;

    private String email;

    private String telefone1;

    private String telefone2;

    @NotNull(message = "documentos é obrigatório")
    @JsonProperty(value = "documentos")
    private ClienteDescricaoVO descricao;

    @NotNull(message = "endereços é obrigatório")
    @JsonProperty(value = "enderecos")
    private List<ClienteEnderecoVO> enderecos;
}
