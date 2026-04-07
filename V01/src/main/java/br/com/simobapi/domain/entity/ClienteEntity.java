package br.com.simobapi.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "cliente")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cliente_tipo")
public class ClienteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	protected ClienteEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long codigo;

    @Column(name = "codigo_simob_cli", nullable = false, unique = true)
    private UUID codigoSimob;

    @NotBlank(message = "nome é obrigatório")
    @Column(nullable = false, length = 50)
    protected String nome;

    @Column(length = 100)
    protected String email;

    @NotBlank(message = "telefone é obrigatório")
    @Column(nullable = false, length = 20)
    protected String telefone1;

    @Column(length = 20)
    protected String telefone2;

    @NotNull(message = "endereco é obrigatório")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cliente_codigo")
    protected List<ClienteEnderecoEntity> enderecos;

    @PrePersist
    private void prePersist(){
        codigoSimob = UUID.randomUUID();
    }
}