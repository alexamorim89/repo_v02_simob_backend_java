package br.com.simobapi.domain.controller.v1.vo.imovel;

import br.com.simobapi.domain.enums.TipoStatus;
import br.com.simobapi.domain.enums.TipoImovel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelFilterRequestVO implements Serializable {
    private UUID codigoSimob;
    private TipoImovel tipoImovel;
    private br.com.simobapi.domain.enums.TipoVenda TipoVenda;
    private TipoStatus status;
    private String rua;
    private String valorAluguel;
    private String valorMinimo;
    private String valorMaximo;
}
