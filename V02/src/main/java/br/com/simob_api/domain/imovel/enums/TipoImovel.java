package br.com.simob_api.domain.imovel.enums;

public enum TipoImovel {
    AP_PADRAO("Apartamento Padrão"),
    AP_GARDEN("Apartamento Garden"),
    AP_MANSAO("Apartamento Mansão"),
    AP_DUPLEX("Apartamento Duplex"),
    AP_TRIPLEX("Apartamento Triplex"),
    STUDIO("Studio"),
    LOJA("Loja"),
    SALA_COMERCIAL("Sala Comercial"),
    GALPAO("Galpão"),
    TERRENO("Terreno"),
    SITIO("Sitio"),
    KITNET("Kitnet"),
    CASA("Casa"),
    CASA_DE_CONDOMINIO("Casa de Condominio"),
    CASA_DE_VILA("Casa de Vila"),
    CHACARA("Chacara"),
    COBERTURA("Cobertura");

    private String descricao;

    TipoImovel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
