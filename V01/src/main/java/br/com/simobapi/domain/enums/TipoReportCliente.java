package br.com.simobapi.domain.enums;

public enum TipoReportCliente {

    FISICA("Fisica"),
    JURIDICA("Juridica"),
    PESSOA_FISICA("Pessoa Fisica"),
    PESSOA_JURIDICA("Pessoa Juridica"),
    TODOS("Todos");

    private final String flag;

    TipoReportCliente(String flag){
        this.flag = flag;
    }

    public String getDescricao(){
        return flag;
    }

}
