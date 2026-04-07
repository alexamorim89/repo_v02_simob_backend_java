package br.com.simobapi.domain.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(length =60,  nullable = false)
    private String nome;

    @Column(length = 100, unique = true,  nullable = false)
    private String email;

    @Column(length = 16, unique = true, nullable = false)
    private String cpf;

    @Column(length = 15, unique = true, nullable = false)
    private String rg;

    @Column(length = 20, unique = true, nullable = false)
    private String creci;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private boolean ativo;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_usuario_endereco_codigo")
    private UsuarioEnderecoEntity endereco;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.DETACH,  CascadeType.MERGE})
    @JoinTable(name= "usuario_perfil",
        joinColumns = @JoinColumn(name="fk_usuario_codigo"),
        inverseJoinColumns = @JoinColumn(name = "fk_perfil_codigo")
    )
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<UsuarioPerfilEntity> perfis;
}