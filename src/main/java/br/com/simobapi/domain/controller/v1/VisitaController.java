package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaResponseVO;
import br.com.simobapi.domain.adapter.VisitaAdapter;
import br.com.simobapi.domain.service.VisitaService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@CrossOrigin
@Tag(name = "VISITA-CONTROLLER - Este endpoint registra visitas realizadas a imobiliaria ou stand")
@RestController
@RequestMapping(value = "/api/v1/visita")
public class VisitaController {

    @Autowired
    private VisitaService service;

    @Autowired
    private VisitaAdapter adapter;


    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Salvar nova visita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visita salva com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitaResponseVO> salvar(@RequestBody @Valid VisitaRequestVO visitaRequestVO){
        var visita = service.salvar(adapter.toEntity(visitaRequestVO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(visita.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(visita));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consulta de Visita por parametros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de visita retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<VisitaResponseVO> consultarPor(
            VisitaFilterRequestVO filterRequestVO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        var pageEntity = service.consultarPor(filterRequestVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consulta lista completa de Visita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de visita retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<VisitaResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        var pageEntity = service.consultar(PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consulta Visita por codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "V isita retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitaResponseVO> consultarPorId(@PathVariable("codigo") UUID codigoSimob){
        var visita = service.consultarPorId(codigoSimob);
        return ResponseEntity.ok(adapter.toVO(visita));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Atualizar visita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita atualizada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<VisitaResponseVO> atualizar(
            @PathVariable("codigo") UUID codigoSimob,
            @RequestBody @Valid VisitaRequestVO visitaRequestVO){

        var visita = service.atualizar(codigoSimob, visitaRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(visita));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Excluir visita sem vinculo financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Visita excluida com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> excluir(@PathVariable("codigo") UUID codigoSimob) {
        service.excluir(codigoSimob);
        return ResponseEntity.noContent().build();
    }

}
