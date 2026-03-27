package br.com.simobapi.domain.controller.v1.vo.imovel;

import br.com.simobapi.domain.enums.TipoStatus;
import br.com.simobapi.domain.enums.TipoImovel;
import br.com.simobapi.domain.enums.TipoVenda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelUpdateRequestVO implements Serializable {

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
    private List<ImovelFotoVO> fotos;
    private ImovelCaracteristicaVO detalhes;
    private ImovelEnderecoVO endereco;
}