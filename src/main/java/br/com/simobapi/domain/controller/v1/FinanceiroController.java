package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroResponseVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroUpdateRequestVO;

import br.com.simobapi.domain.adapter.FinanceiroAdapter;
import br.com.simobapi.domain.enums.TipoNegocio;
import br.com.simobapi.domain.service.FinanceiroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@CrossOrigin
@Tag(name = "FINANCEIRO-CONTROLLER - Este endpoint registra movimentação financeira sobre os imoveis")
@RestController
@RequestMapping(value = "/api/v1/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroService service;

    @Autowired
    private FinanceiroAdapter adapter;

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar todos os movimentos financeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentos financeiros retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FinanceiroResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        var pageFinanceiro = service.consultar(PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageFinanceiro));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar movimentos financeiros por parametros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimentos financeiros retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<FinanceiroResponseVO> consultarPor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            FinanceiroFilterRequestVO filterRequestVO) {
        var pageFinanceiro = service.consultarPor(filterRequestVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageFinanceiro), pageFinanceiro.getPageable(), pageFinanceiro.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Consultar movimento financeiro por codigo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimento financeiro retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinanceiroResponseVO> consultarPorId(@PathVariable("codigo") UUID codigoFinanceiro) {
        var financeiro = service.consultarPorId(codigoFinanceiro);

        return ResponseEntity.ok(adapter.toVO(financeiro));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Cadastrar novo Movimento Financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimento financeiro cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinanceiroResponseVO> salvar(@RequestBody @Valid FinanceiroRequestVO financeiroRequestVO) {
        var financeiro = service.salvar(financeiroRequestVO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(financeiro.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(financeiro));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Atualizar Movimento financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimento financeiro atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{codigo}")
    public ResponseEntity<FinanceiroResponseVO> atualizar(
            @PathVariable("codigo") UUID codigoFinanceiro,
            @RequestBody @Valid FinanceiroUpdateRequestVO financeiroUpdateRequestVO){

        var financeiro = service.atualizar(codigoFinanceiro, financeiroUpdateRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(financeiro));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Excluir Movimento financeiro sem vinculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimento Financeiro excluido com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> excluir(@PathVariable("codigo") UUID codigoFinanceiro) {
        service.excluir(codigoFinanceiro);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE')")
    @Operation(summary = "Modificar Status do financeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{codigo}/situacao")
    public ResponseEntity<Void> modificarStatus(
            @PathVariable UUID codigo, @RequestBody @Valid TipoNegocio situacao){

        service.modificarStatus(situacao, codigo);
        return ResponseEntity.noContent().build();
    }
}