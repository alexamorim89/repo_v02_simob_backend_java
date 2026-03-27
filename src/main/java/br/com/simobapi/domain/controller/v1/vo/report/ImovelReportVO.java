package br.com.simobapi.domain.controller.v1.vo.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImovelReportVO {
    private String nome;
    private String tipoImovel;
    private String status;
    private String valorMensal;
    private String valor;
}