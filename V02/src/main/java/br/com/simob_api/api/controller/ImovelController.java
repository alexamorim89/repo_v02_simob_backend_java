package br.com.simob_api.api.controller;

import br.com.simob_api.api.dto.imovel.ImovelFiltroRequestDTO;
import br.com.simob_api.api.dto.imovel.ImovelResponseDTO;
import br.com.simob_api.application.service.ImovelService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/imoveis")
@Tag(name = "imoveis", description = "Serviços relacionados a imoveis")
public class ImovelController {

    @Autowired
    private ImovelService service;

    @Operation(summary = "Consultar todos os Imoveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de imoveis retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ImovelResponseDTO> consultar(
            @ModelAttribute ImovelFiltroRequestDTO filtroRequestDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return service.consultarComFiltros(filtroRequestDTO, PageRequest.of(page, size));
    }

}
