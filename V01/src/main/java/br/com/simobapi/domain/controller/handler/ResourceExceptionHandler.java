package br.com.simobapi.domain.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import br.com.simobapi.domain.controller.handler.model.DetalhesErro;
import br.com.simobapi.domain.service.exceptions.AgendamentoVinculadoVisitaException;
import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.domain.service.exceptions.ImovelPossuiVinculoFinanceiroException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ResourceExceptionHandler {

	//	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<DetalhesErro> handlerHttpMessageNotReadableException(Exception e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(500L);
		erro.setTitulo("Erro Inesperado.");
		erro.setMensagemDesenvolvedor("Erro Inesperado, contate o administrador");
		erro.setMensagemErro(e.getMessage());
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(
		{
			DataIntegrityViolationException.class,
			ConstraintViolationException.class,
			MethodArgumentNotValidException.class,
			HttpMessageNotReadableException.class,
			IllegalArgumentException.class
		}
	)
	public ResponseEntity<DetalhesErro> handlerPayloadException(Exception e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400L);
		erro.setTitulo("Requisição Inválida");
		erro.setMensagemDesenvolvedor("Requisição: Problema no Payload de envio");
		erro.setMensagemErro(e.getMessage());
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<DetalhesErro> handlerDateTimeParseException(DateTimeParseException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400L);
		erro.setTitulo("Requisição Inválida");
		erro.setMensagemDesenvolvedor(e.getLocalizedMessage());
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(
			{
				AgendamentoVinculadoVisitaException.class,
				ImovelPossuiVinculoFinanceiroException.class
			}
	)
	public ResponseEntity<DetalhesErro> handlerRegraNegocioException(RuntimeException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400L);
		erro.setTitulo("Requisição Inválida");
		erro.setMensagemDesenvolvedor(e.getMessage());
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handlerRecursoNaoEncontradoException(RecursoNaoEncontradoException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(404L);
		erro.setTitulo("Recurso não encontrado");
		erro.setMensagemDesenvolvedor("Recurso { " +e.getMessage() + " } não encontrado");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(DadosJaRegistradoException.class)
	public ResponseEntity<DetalhesErro> handlerDadosJaRegistradoException(DadosJaRegistradoException e, HttpServletRequest request){
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(409L);
		erro.setTitulo("Conflito de Informações");
		erro.setMensagemDesenvolvedor(e.getMessage() + " Ja Cadastrado");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

}