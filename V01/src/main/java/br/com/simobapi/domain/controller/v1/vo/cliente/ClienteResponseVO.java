package br.com.simobapi.domain.controller.v1.vo.cliente;

import br.com.simobapi.domain.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteResponseVO implements Serializable {

    private UUID codigoSimob;
    private String nome;
    private String email;
    private String telefone1;
    private String telefone2;

    @JsonProperty(value = "documentos")
    private ClienteDescricaoVO descricao;

    @JsonProperty(value = "enderecos")
    private List<ClienteEnderecoVO> enderecos;

    private TipoCliente tipo;
}
