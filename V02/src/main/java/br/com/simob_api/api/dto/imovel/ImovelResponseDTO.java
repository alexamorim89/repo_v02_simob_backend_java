package br.com.simob_api.api.dto.imovel;

import br.com.simob_api.domain.imovel.enums.TipoImovel;
import br.com.simob_api.domain.imovel.enums.TipoStatus;
import br.com.simob_api.domain.imovel.enums.TipoVenda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelResponseDTO {
    private UUID codigoSimob;
    private String nome;
    private Integer quantidadeQuarto;
    private Integer quantidadeBanheiro;
    private Integer quantidadeSuite;
    private Integer quantidadeGaragem;
    private String areaTotal;
    private String descricao;
    private TipoImovel tipoImovel;
    private TipoVenda tipoVenda;
    private TipoStatus status;
    private BigDecimal valor;
    private BigDecimal valorMensal;
}
