package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "pessoa_juridica")
@DiscriminatorValue("PJ")
public class ClientePessoaJuridicaEntity extends ClienteEntity {

    @NotBlank(message = "CNPJ é obrigarório")
	@Column(length = 20, unique = true)
    private String cnpj;
    @NotBlank(message = "inscricao Estadual é obrigatório")
    @Column(name = "inscricao_estadual", length = 20, unique = true)
    private String inscricaoEstadual;

    @Column(name = "razao_social", length = 50)
    private String razaoSocial;

    public ClientePessoaJuridicaEntity(String nome, String email, String telefone1, String telefone2, String cnpj,
                                       String inscricaoEstadual, String razaoSocial, List<ClienteEnderecoEntity> enderecos) {
        super();
        super.nome = nome;
        super.email = email;
        super.telefone1 = telefone1;
        super.telefone2 = telefone2;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
        super.enderecos = enderecos;
    }
}