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

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario_endereco")
public class UsuarioEnderecoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(length = 100, nullable = false)
    private String rua;

    @Column
    private int numero;

    @Column(length = 50)
    private String complemento;

    @Column(length = 15, nullable = false)
    private String cep;

    @Column(length = 30, nullable = false)
    private String bairro;

    @Column(length = 40, nullable = false)
    private String cidade;

    @Column(length = 20, nullable = false)
    private String estado;
}
