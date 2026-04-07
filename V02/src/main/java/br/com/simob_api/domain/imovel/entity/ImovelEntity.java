package br.com.simob_api.domain.imovel.entity;

import br.com.simob_api.domain.financeiro.FinanceiroEntity;
import br.com.simob_api.domain.proprietario.ProprietarioEntity;

import br.com.simob_api.domain.imovel.enums.TipoImovel;
import br.com.simob_api.domain.imovel.enums.TipoVenda;
import br.com.simob_api.domain.imovel.enums.TipoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.PrePersist;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "imovel")
public class ImovelEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_simob_imov", unique = true, nullable = false)
    private UUID codigoSimob;

    @Size(min = 10, max = 200, message = "O nome deve ter no minimo 10 caracteres e no maximo 200")
    @NotBlank(message = "nome é obrigatório")
    @Column(length = 200, nullable = false)
    private String nome;

    @NotNull(message = "quantidadeQuarto é obrigatório")
    @Column(name = "quantidade_quarto", nullable = false)
    private Integer quantidadeQuarto;

    @NotNull(message = "quantidadeBanheiro é obrigatório")
    @Column(name = "quantidade_banheiro")
    private Integer quantidadeBanheiro;

    @NotNull(message = "quantidadeSuite é obrigatório")
    @Column(name = "quantidade_suite")
    private Integer quantidadeSuite;

    @NotNull(message = "quantidadeGaragem é obrigatório")
    @Column(name = "quantidade_garagem")
    private Integer quantidadeGaragem;

    @NotBlank(message = "AreaTotal é obrigatório")
    @Column(name = "area_total", length = 30)
    private String areaTotal;

    @NotBlank(message = "descricao é obrigatório")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "tipoImovel é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_imovel", nullable = false)
    private TipoImovel tipoImovel;

    @NotNull(message = "tipoVenda é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_venda", nullable = false)
    private TipoVenda tipoVenda;

    @NotNull(message = "status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoStatus status;

    @NotNull(message = "valor é obrigatório")
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @NotNull(message = "valorMensal é obrigatório")
    @Column(name = "valor_mensal", nullable = false)
    private BigDecimal valorMensal;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_imagens_codigo")
    private List<ImovelFotoEntity> fotos;

    @Embedded
    private ImovelCaracteristicaEntity caracteristca;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco_codigo")
    private ImovelEnderecoEntity endereco;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "fk_proprietario_codigo")
    private ProprietarioEntity proprietario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH } )
    @JoinColumn(name = "fk_financeiro_codigo")
    private FinanceiroEntity financeiro;

    @PrePersist
    private void prePersist(){
        codigoSimob = UUID.randomUUID();
    }

}
