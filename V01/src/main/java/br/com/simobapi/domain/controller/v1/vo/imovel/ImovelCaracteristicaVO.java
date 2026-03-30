package br.com.simobapi.domain.controller.v1.vo.imovel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelCaracteristicaVO {
    private boolean temAreaServico;
    private boolean temArmarioCozinha;
    private boolean temArmarioQuarto;
    private boolean temArmarioProjetado;
    private boolean temBoxBanheiro;
    private boolean temCeramica;
    private boolean temCercado;
    private boolean temCloset;
    private boolean temCobertura;
    private boolean temCondominioFechado;
    private boolean temConjugada;
    private boolean temCopa;
    private boolean temCorredor;
    private boolean temDespensa;
    private boolean temCozinha;
    private boolean temCozinhaAmericana;
    private boolean temEscritorio;
    private boolean temEsquadria;
    private boolean temGradeado;
}
