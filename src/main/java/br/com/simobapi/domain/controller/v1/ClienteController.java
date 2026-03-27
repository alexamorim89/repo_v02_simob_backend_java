package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteRequestVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteResponseVO;
import br.com.simobapi.domain.adapter.ClienteAdapter;
import br.com.simobapi.domain.enums.TipoCliente;
import br.com.simobapi.domain.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation. RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@CrossOrigin
@Tag(name = "CLIENTE-CONTROLLER - Este Endpoint realiza cadastro e consulta de Clientes")
@RestController
@RequestMapping(value = "/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private ClienteAdapter adapter;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar cliente por codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseVO> consultarPorId(@PathVariable("codigo") UUID codigo) {
        var cliente = service.consultarPorId(codigo);
        return ResponseEntity.ok(adapter.toVO(cliente));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ClienteResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        var pageEntity = service.consultar(PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListResponseVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consulta de clientes por parametros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ClienteResponseVO> consultarPor(
            @Param(value = "filtro") ClienteFilterVO filterVO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        var pageEntity = service.consultarPor(filterVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListResponseVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Cadastrar novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseVO> salvar(
            @RequestBody @Valid ClienteRequestVO clienteRequestVO,
            @RequestHeader("tipo_cliente") TipoCliente tipo) {
        var clienteEntity = service.salvar(adapter.toEntity(tipo, clienteRequestVO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(clienteEntity.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(clienteEntity));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Atualizar clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<ClienteResponseVO> atualizar(
            @PathVariable("codigo") UUID codigoCliente,
            @RequestBody @Valid ClienteRequestVO clienteRequestVO){

         var cliente = service.atualizar(codigoCliente, clienteRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(cliente));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Excluir cliente sem vinculo financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluido com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> excluir(@PathVariable("codigo") UUID codigoCliente) {
        service.excluir(codigoCliente);
        return ResponseEntity.noContent().build();
    }

}