package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;


@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "proprietario_pessoa_juridica")
@DiscriminatorValue("PJ")
public class ProprietarioPessoaJuridicaEntity extends ProprietarioEntity {
	private static final long serialVersionUID = 1L;

    @Size(message = "permitido apenas 20 caracteres", max = 20)
    @NotBlank(message = "cnpj é obrigatório")
	@Column(length = 20, unique = true)
    private String cnpj;

    @Size(message = "permitido apenas 30 caracteres", max = 30)
    @NotBlank(message = "inscricaoEstadual é obrigatório")
    @Column(name = "inscricao_estadual", length = 30, unique = true)
    private String inscricaoEstadual;

    @Size(message = "permitido apenas 80 caracteres", max = 80)
    @Column(name = "razao_social", length = 80)
    private String razaoSocial;

    public ProprietarioPessoaJuridicaEntity(UUID codigoSimob, String nome, String telefone1, String telefone2, String email,
                                            String cnpj, String inscricaoEstadual, String razaoSocial) {
        super();
        super.codigoSimob = codigoSimob;
        super.nome = nome;
        super.telefone1 = telefone1;
        super.telefone2 = telefone2;
        super.email = email;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
    }

}