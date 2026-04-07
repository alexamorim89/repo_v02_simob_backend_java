package br.com.simobapi.domain.enums;

public enum TipoNegocio {
    EM_PROCESSO("Em Processo"),
    VENDIDO("Vendido"),
    ALUGADO("Alugado");

    private final String descricao;

    TipoNegocio(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}