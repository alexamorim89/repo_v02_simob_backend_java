package br.com.simobapi.domain.enums;

public enum TipoCliente {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    TipoCliente(String descricao){
        this.descricao = descricao;
    }

    private String descricao;

    public String getDescricao(){
        return descricao;
    }

}