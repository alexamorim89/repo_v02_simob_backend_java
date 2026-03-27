package br.com.simobapi.domain.enums;

public enum TipoProprietario {
    PESSOA_FISICA("PF"),
    PESSOA_JURIDICA("PJ");

    private String descricao;

    TipoProprietario(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
