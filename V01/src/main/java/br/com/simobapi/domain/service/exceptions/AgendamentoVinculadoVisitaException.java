package br.com.simobapi.domain.service.exceptions;

public class AgendamentoVinculadoVisitaException extends RuntimeException {
    public AgendamentoVinculadoVisitaException(String mensagem) {
        super(mensagem);
    }
}