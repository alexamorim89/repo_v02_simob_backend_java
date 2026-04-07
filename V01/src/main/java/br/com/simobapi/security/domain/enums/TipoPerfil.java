package br.com.simobapi.security.domain.enums;

public enum TipoPerfil {
    ADMINISTRADOR(1L, "Administrador"),
    ATENDENTE(2L,"Atendente"),
    CORRETOR(3L,"Corretor"),
    GERENTE(4L,"Gerente");

    private final String descricao;
    private final Long codigo;

    public String getDescricao(){
        return descricao;
    }
    public Long getCodigo() { return  codigo; }

    TipoPerfil(Long codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
