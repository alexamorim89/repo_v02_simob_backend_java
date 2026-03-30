package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
@Entity
@Table(name = "imovel_endereco")
public class ImovelEnderecoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @Column(name = "rua", length = 100, nullable = false)
    private String rua;

    @Column(name = "numero")
    private int numero;

    @Column(name = "complemento",  length = 50)
    private String complemento;

    @Column(name = "cep", length = 15)
    private String cep;

    @NotBlank
    @Column(name = "bairro", length = 20, nullable = false)
    private String bairro;

    @NotBlank
    @Column(name = "cidade", length = 40, nullable = false)
    private String cidade;

    @NotBlank
    @Column(name = "estado", length = 10, nullable = false)
    private String estado;
}
