package br.com.simobapi.domain.entity;

import br.com.simobapi.domain.enums.TipoNegocio;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.PrePersist;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "financeiro")
public class FinanceiroEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_simob_fin", nullable = false)
    private UUID codigoSimob;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "valor_mensal", nullable = false)
    private BigDecimal valorMensal;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private TipoNegocio situacao;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "fk_cliente_codigo")
    private ClienteEntity cliente;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "financeiro")
    private ImovelEntity imovel;

    @PrePersist
    private void prePersist(){
        codigoSimob = UUID.randomUUID();
    }

}