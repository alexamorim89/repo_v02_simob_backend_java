package br.com.simobapi.domain.service.exceptions;

public class DadosJaRegistradoException extends RuntimeException {
    public DadosJaRegistradoException(String mensagem) {
        super(mensagem);
    }
}