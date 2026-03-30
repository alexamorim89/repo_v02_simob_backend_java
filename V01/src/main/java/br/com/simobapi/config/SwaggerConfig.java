package br.com.simobapi.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@Configuration
@OpenAPIDefinition(info = @Info(title = "SIMOB REST API", version = "0.0.1", description = "API REST - Sistema Imobiliario Simples"))
public class SwaggerConfig  {}