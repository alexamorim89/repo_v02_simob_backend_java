package br.com.simobapi.domain.controller.v1.vo.report;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteReportVO {
    private String nome;
    private String telefonePrincipal;
    private String telefoneSecundario;
    private String email;
    private String endereço;
}