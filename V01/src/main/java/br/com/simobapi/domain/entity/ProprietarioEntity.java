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
import javax.persistence.PrePersist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "proprietario")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "proprietario_tipo")
public class ProprietarioEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long codigo;

    @Column(name = "codigo_simob_prop", nullable = false, unique = true)
    protected UUID codigoSimob;

    @Size(message = "permitido apenas 50 caracteres", max = 50)
    @NotBlank(message = "nome é obrigatório")
    @Column(length = 50, nullable = false)
    protected String nome;

    @Size(message = "permitido apenas 20 caracteres", max = 20)
    @NotBlank(message = "telefone é obrigatório")
    @Column(length = 20)
    protected String telefone1;

    @Size(message = "permitido apenas 20 caracteres", max = 20)
    @Column(length = 20)
    protected String telefone2;

    @Size(message = "permitido apenas 100 caracteres", max = 100)
    @Column(length = 100)
    protected String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ImovelEntity> imoveis;

    protected ProprietarioEntity() {}

    @PrePersist
    private void prePersist(){
        codigoSimob = UUID.randomUUID();
    }

}
