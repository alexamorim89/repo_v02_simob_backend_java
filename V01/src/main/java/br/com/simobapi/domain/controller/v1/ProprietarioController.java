package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioResponseVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioFilterVO;

import br.com.simobapi.domain.adapter.ProprietarioAdapter;
import br.com.simobapi.domain.enums.TipoProprietario;
import br.com.simobapi.domain.service.ProprietarioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@CrossOrigin
@Tag(name = "PROPRIETARIO-CONTROLLER - Este endpoint registra cadastro de Proprietarios")
@RestController
@RequestMapping(value = "/api/v1/proprietario")
public class ProprietarioController {

    @Autowired
    private ProprietarioService service;

    @Autowired
    private ProprietarioAdapter adapter;


    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Consultar todos os proprietarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proprietario de imoveis retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProprietarioResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        var pageProprietario = service.consultar(PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageProprietario), pageProprietario.getPageable(), pageProprietario.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Consultar proprietario por codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proprietario de imovel retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProprietarioResponseVO> consultarPorId(@PathVariable("codigo") UUID codigoProprietario) {
        var proprietario = service.consultarPorId(codigoProprietario);

        return ResponseEntity.ok(adapter.toVO(proprietario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Consulta proprietario por parametros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proprietario retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ProprietarioResponseVO> consultarPor(
            @Param(value = "filtro") ProprietarioFilterVO filterVO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        var pageEntity = service.consultarPor(filterVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Cadastrar proprietario de imovel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proprietario de imovel cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProprietarioResponseVO> salvar(
            @RequestHeader("tipo_proprietario") TipoProprietario tipo,
            @RequestBody @Valid ProprietarioRequestVO proprietarioRequestVO) {
        var proprietario = service.salvar(adapter.toEntity(tipo, proprietarioRequestVO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(proprietario.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(proprietario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Atualizar proprietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proprietario atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<ProprietarioResponseVO> atualizar(
            @PathVariable("codigo") UUID codigoProprietario,
            @RequestBody @Valid ProprietarioRequestVO proprietarioRequestVO){
        var proprietario = service.atualizar(codigoProprietario, proprietarioRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(proprietario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
    @Operation(summary = "Excluir proprietario sem vinculo financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "proprietario excluido com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> excluir(@PathVariable("codigo") UUID codigoProprietario) {
        service.excluir(codigoProprietario);
        return ResponseEntity.noContent().build();
    }

}
