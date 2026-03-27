package br.com.simobapi.domain.controller.v1.vo.financeiro;

import br.com.simobapi.domain.enums.TipoNegocio;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinanceiroUpdateRequestVO implements Serializable {
    private BigDecimal valor;
    private BigDecimal valorMensal;
    private TipoNegocio situacao;
}