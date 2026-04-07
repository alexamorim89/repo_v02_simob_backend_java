package br.com.simob_api.domain.imovel.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ImovelCaracteristicaEntity {
    @Column(name = "tem_area_servico", nullable = false)
    private boolean temAreaServico;

    @Column(name = "tem_armario_cozinha", nullable = false)
    private boolean temArmarioCozinha;

    @Column(name = "tem_armario_quarto", nullable = false)
    private boolean temArmarioQuarto;

    @Column(name = "tem_armario_projetado", nullable = false)
    private boolean temArmarioProjetado;

    @Column(name = "tem_box_banheiro", nullable = false)
    private boolean temBoxBanheiro;

    @Column(name = "tem_ceramica", nullable = false)
    private boolean temCeramica;

    @Column(name = "tem_cercado", nullable = false)
    private boolean temCercado;

    @Column(name = "tem_closet", nullable = false)
    private boolean temCloset;

    @Column(name = "tem_cobertura", nullable = false)
    private boolean temCobertura;

    @Column(name = "tem_condominio_fechado", nullable = false)
    private boolean temCondominioFechado;

    @Column(name = "tem_conjugada", nullable = false)
    private boolean temConjugada;

    @Column(name = "tem_copa", nullable = false)
    private boolean temCopa;

    @Column(name = "tem_corredor", nullable = false)
    private boolean temCorredor;

    @Column(name = "tem_despensa", nullable = false)
    private boolean temDespensa;

    @Column(name = "tem_cozinha", nullable = false)
    private boolean temCozinha;

    @Column(name = "tem_cozinha_americana", nullable = false)
    private boolean temCozinhaAmericana;

    @Column(name = "tem_escritorio", nullable = false)
    private boolean temEscritorio;

    @Column(name = "tem_esquadria", nullable = false)
    private boolean temEsquadria;

    @Column(name = "tem_gradeado", nullable = false)
    private boolean temGradeado;
}
