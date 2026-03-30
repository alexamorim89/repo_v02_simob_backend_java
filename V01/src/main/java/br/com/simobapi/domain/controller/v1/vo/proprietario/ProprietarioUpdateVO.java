package br.com.simobapi.domain.controller.v1.vo.proprietario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProprietarioUpdateVO implements Serializable {
    @Size(message = "permitido apenas 50 caracteres", max = 50)
    @NotBlank(message = "nome é obrigatório")
    protected String nome;
    @Size(message = "permitido apenas 20 caracteres", max = 20)
    @NotBlank(message = "telefone é obrigatório")
    protected String telefone1;
    @Size(message = "permitido apenas 50 caracteres", max = 20)
    protected String telefone2;
    @Size(message = "permitido apenas 100 caracteres", max = 100)
    protected String email;
    @Size(message = "permitido apenas 15 caracteres", max = 15)
    private String rg;
    @Size(message = "permitido apenas 16 caracteres", max = 16)
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @Size(message = "permitido apenas 20 caracteres", max = 20)
    private String cnpj;
    @Size(message = "permitido apenas 30 caracteres", max = 30)
    private String inscricaoEstadual;
    @Size(message = "permitido apenas 80 caracteres", max = 80)
    private String razaoSocial;

    public ProprietarioUpdateVO(String nome, String telefone1, String telefone2,
                                String email, String rg, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.rg = rg;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
    public ProprietarioUpdateVO(String nome, String telefone1, String telefone2,
                                String email, String cnpj, String inscricaoEstadual, String razaoSocial) {
        this.nome = nome;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.razaoSocial = razaoSocial;
    }
}
