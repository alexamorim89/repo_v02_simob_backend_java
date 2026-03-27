package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "proprietario_pessoa_fisica")
@DiscriminatorValue("PF")
public class ProprietarioPessoaFisicaEntity extends ProprietarioEntity {
	private static final long serialVersionUID = 1L;

    @Size(message = "permitido apenas 15 caracteres", max = 15)
    @NotBlank(message = "rg é obrigatório")
	@Column(length = 15, nullable = false, unique = true)
    private String rg;

    @Size(message = "permitido apenas 16 caracteres", max = 16)
    @NotBlank(message = "cpf é obrigatório")
    @Column(length = 16, nullable = false, unique = true)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    public ProprietarioPessoaFisicaEntity(UUID codigoSimob, String nome, String telefone1, String telefone2,
                                          String email, String rg, String cpf, LocalDate dataNascimento) {
        super();
        super.codigoSimob = codigoSimob;
        super.nome = nome;
        super.telefone1 = telefone1;
        super.telefone2 = telefone2;
        super.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }


}