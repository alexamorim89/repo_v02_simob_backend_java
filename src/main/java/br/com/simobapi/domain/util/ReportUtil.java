package br.com.simobapi.domain.util;

import java.util.List;

public final class ReportUtil {

    private ReportUtil(){}

    public static final List<String> LISTA_NOMES_COLUNAS_IMOVEL = List.of("NOME", "TIPO DE IMOVEL", "STATUS", "VALOR MENSAL", "VALOR");
    public static final String NOME_RELATORIO_IMOVEL = "Relatório de Imovel - ";
    public static final int NUMERO_DE_COLUNAS_IMOVEL = 5;

    public static final List<String> LISTA_NOMES_COLUNAS_CLIENTE = List.of("NOME", "TELEFONE PRINCIPAL", "TELEFONE SECUNDARIO", "EMAIL", "ENDEREÇO");
    public static final String NOME_RELATORIO_CLIENTE = "Relatório de Cliente - ";
    public static final int NUMERO_DE_COLUNAS_CLIENTE = 5;
}