package br.com.simobapi.domain.controller.v1.vo.financeiro;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelVO;
import br.com.simobapi.domain.enums.TipoNegocio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class  FinanceiroResponseVO {
    private UUID codigoSimob;
    private BigDecimal valor;
    private BigDecimal valorMensal;
    private LocalDate data;
    private LocalTime hora;
    private TipoNegocio situacao;
    private ClienteVO cliente;
    private ImovelVO imovel;
}