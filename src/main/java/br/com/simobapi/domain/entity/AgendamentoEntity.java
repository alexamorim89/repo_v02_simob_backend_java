package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import javax.validation.constraints.Email;
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
@DynamicInsert
@Entity
@Table(name = "agendamento")
public class AgendamentoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_simob_ag", nullable = false, unique = true)
    private UUID codigoSimob;

    @NotBlank(message = "nome é obrigatório")
    @Column(name = "nome_cliente", length = 50, nullable = false)
    private String nome;

    @NotBlank(message = "telefone é obrigatório")
    @Column(name = "telefone_cliente", length = 20, nullable = false)
    private String telefone;

    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Informe um email valido")
    @Column(name = "email_cliente", length = 70)
    private String email;

    @NotNull(message = "data é obrigatório")
    @Column(nullable = false)
    private LocalDate data;

    @NotBlank(message = "hora é obrigatório")
    @Column(length = 15, nullable = false)
    private String hora;

    @NotBlank(message = "informações é obrigatório")
    @Column(name = "informacoes")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "fk_visita_codigo")
    private VisitaEntity visita;

    @PrePersist
    private void prePersist(){
        this.codigoSimob = UUID.randomUUID();
    }

}
