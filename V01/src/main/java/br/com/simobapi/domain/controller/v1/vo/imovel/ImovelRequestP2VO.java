package br.com.simobapi.domain.controller.v1.vo.imovel;

import br.com.simobapi.domain.enums.TipoStatus;
import br.com.simobapi.domain.enums.TipoImovel;
import br.com.simobapi.domain.enums.TipoVenda;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelRequestP2VO implements Serializable {
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

    @JsonProperty(value = "fotos")
    private List<ImovelFotoVO> fotosImovel;

    @NotNull(message = "detalhes do imovel é obrigatóro")
    private ImovelCaracteristicaVO detalhes;

    @NotNull(message = "endereco do imovel é obrigatório")
    private ImovelEnderecoVO endereco;
}