package br.com.simobapi.domain.enums;

public enum TipoReportImovel {
    ALUGUEL("Aluguel"),
    VENDA("Venda"),
    TODOS("Todos");


    private final String flag;

    TipoReportImovel(String flag){
        this.flag = flag;
    }

    public String getDescricao(){
        return flag;
    }

}