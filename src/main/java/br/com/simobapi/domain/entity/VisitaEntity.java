package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "visita")
public class VisitaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_simob_vi", nullable = false, unique = true)
    private UUID codigoSimob;

    @NotBlank(message = "nome é obrigatório")
    @Column(name = "nome_cliente", length = 50, nullable = false)
    private String nome;

    @NotBlank(message = "telefone é obrigatório")
    @Column(name = "telefone_cliente", length = 20, nullable = false)
    private String telefone;

    @Column(name = "email_cliente", length = 70)
    private String email;

    @NotNull(message = "data é obrigatório")
    @Column(nullable = false)
    private LocalDate data;

    @NotBlank(message = "hora é obrigatório")
    @Column(nullable = false)
    private String hora;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "fk_anotacao_codigo")
    private AnotacaoEntity anotacao;

    @Column(name = "gerado_por_agenda", columnDefinition = "boolean default false")
    private Boolean gerardoPorAgenda;

    public VisitaEntity(UUID codigoSimob, String nome, String telefone, String email, LocalDate data, String hora, AnotacaoEntity anotacao) {
        this.codigoSimob = codigoSimob;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.data = data;
        this.hora = hora;
        this.anotacao = anotacao;
    }

    @PrePersist
    private void prePersist(){
        this.codigoSimob = UUID.randomUUID();
    }
}
