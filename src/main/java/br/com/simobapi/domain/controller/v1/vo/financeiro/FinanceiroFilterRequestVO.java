package br.com.simobapi.domain.controller.v1.vo.financeiro;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceiroFilterRequestVO implements Serializable {
    private UUID financeiro;
    private UUID cliente;
    private UUID imovel;
    private String nomeCliente;
}