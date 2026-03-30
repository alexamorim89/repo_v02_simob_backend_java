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
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "pessoa_fisica")
@DiscriminatorValue("PF")
public class ClientePessoaFisicaEntity extends ClienteEntity {

    @NotBlank(message = "RG é obrigatório")
	@Column(length = 15, unique = true)
    private String rg;

    @NotBlank(message = "CPF é obrigatório")
    @Column(length = 16, unique = true)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "data_nascimento", columnDefinition = "DATE")
    private LocalDate dataNascimento;

    public ClientePessoaFisicaEntity(String nome, String email, String telefone1, String telefone2, String rg,
                                     String cpf, LocalDate dataNascimento, List<ClienteEnderecoEntity> enderecos) {
        super();
        super.nome = nome;
        super.email = email;
        super.telefone1 = telefone1;
        super.telefone2 = telefone2;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        super.enderecos = enderecos;
    }

}