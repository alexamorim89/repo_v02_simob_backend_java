package br.com.simob_api.domain.imovel.enums;

public enum SegmentoImovel {
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial");

    private String descricao;

    SegmentoImovel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
