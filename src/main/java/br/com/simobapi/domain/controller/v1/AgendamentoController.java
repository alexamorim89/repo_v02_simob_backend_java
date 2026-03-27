package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoRequestVO;
import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoResponseVO;
import br.com.simobapi.domain.controller.v1.vo.visita.AnotacaoRequestVO;
import br.com.simobapi.domain.adapter.AgendamentoAdapter;
import br.com.simobapi.domain.service.AgendamentoService;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@CrossOrigin
@Tag(name = "AGENDAMENTO-CONTROLLER - Este Endpoint realiza Agendamento de clientes")
@RestController
@RequestMapping(value = "/api/v1/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private AgendamentoAdapter adapter;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Salvar nova agenda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento salvo com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponseVO> salvar(@RequestBody @Valid AgendamentoRequestVO agendamentoRequestVO){
        var agendamento = service.salvar(adapter.toEntity(agendamentoRequestVO));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(agendamento.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(agendamento));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar agendamento por codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgendamentoResponseVO> consultarPorId(@PathVariable("codigo") UUID codigoSimob) {
        var agenda = service.consultarPorId(codigoSimob);
        return ResponseEntity.ok(adapter.toVO(agenda));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar todos os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamento retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AgendamentoResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        var pageEntity = service.consultar(PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consulta de Agendamento por parametros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de agendamento retornada com sucesso."),
        @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
        @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AgendamentoResponseVO> consultar(
            AgendamentoFilterRequestVO filterRequestVO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        var pageEntity = service.consultarPor(filterRequestVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageEntity), pageEntity.getPageable(), pageEntity.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Atualizar agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visita atualizada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<AgendamentoResponseVO> atualizar(
            @PathVariable("codigo") UUID codigoSimob,
            @RequestBody @Valid AgendamentoRequestVO agendamentoRequestVO){

        var agenda = service.atualizar(codigoSimob, agendamentoRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(agenda));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
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

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'ATENDENTE', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Gerar Visita Vinculado ao Agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Visita gerada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "/{codigo}/gerar-visita")
    public ResponseEntity<Void> gerarVisita(
            @PathVariable("codigo") UUID codigoSimob,
            @RequestBody @Valid AnotacaoRequestVO anotacao){

        service.gerarVisita(codigoSimob, anotacao);
        return ResponseEntity.noContent().build();
    }

}
