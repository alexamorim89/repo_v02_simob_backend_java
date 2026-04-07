package br.com.simob_api.domain.proprietario;

import br.com.simob_api.domain.imovel.entity.ImovelEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.PrePersist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.UUID;
import java.util.List;

import lombok.Data;


@Data
@Entity
@Table(name = "proprietario")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "proprietario_tipo")
public class ProprietarioEntity implements Serializable {

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
