package br.com.simobapi.domain.controller.handler.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class DetalhesErro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private Long status;
	private Long timestamp;
	private String mensagemDesenvolvedor;
	private String mensagemErro;
	
}
