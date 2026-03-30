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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "cliente_endereco")
public class ClienteEnderecoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "rua é obrigatório")
    @Column(length = 100, nullable = false)
    private String rua;

    @NotNull(message = "numero é obrigarório")
    @Column(length = 20, nullable = false)
    private Integer numero;

    @Column(length = 50)
    private String complemento;

    @NotBlank(message = "bairro é obrigatório")
    @Column(length = 50, nullable = false)
    private String bairro;

    @NotBlank(message = "cidade é obrigatório")
    @Column(length = 50, nullable = false)
    private String cidade;

    @NotBlank(message = "estado é obrigatório")
    @Column(length = 20, nullable = false)
    private String estado;

    @NotBlank(message = "cep é obrigatório")
    @Column(length = 15, nullable = false)
    private String cep;

    public ClienteEnderecoEntity(String rua, Integer numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }
}