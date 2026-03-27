package br.com.simobapi.domain.enums;

public enum TipoStatus {
    NOVO("Novo"),
    SEMI_NOVO("Semi Novo"),
    ANTIGO("Antigo"),
    EM_CONSTRUCAO("Em Construção");

    TipoStatus(String descricao){
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao(){
        return descricao;
    }
}
