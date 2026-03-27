package br.com.simobapi.domain.controller.v1;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioResponseVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioUpdateRequestVO;

import br.com.simobapi.domain.adapter.UsuarioAdapter;
import br.com.simobapi.domain.service.UsuarioService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin
@Tag(name = "USUARIO-CONTROLLER - Este endpoint registra cadastro de usuario")
@RestController
@RequestMapping(value = "/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioAdapter adapter;


    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Consultar todos os Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioResponseVO> consultar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        var pageUsuario = service.consultar(PageRequest.of(page, size));

        return new PageImpl<>(adapter.toListVO(pageUsuario), pageUsuario.getPageable(), pageUsuario.getTotalElements() );
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Consultar usuario por parametros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UsuarioResponseVO> consultarPor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            UsuarioFilterRequestVO filterRequestVO) {
        var pageUsuario = service.consultarPor(filterRequestVO, PageRequest.of(page, size));
        return new PageImpl<>(adapter.toListVO(pageUsuario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Consultar usuario por matricula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponseVO> consultarPorId(@PathVariable("matricula") String matricula) {
        var usuario = service.consultarPorMatricula(matricula);

        return ResponseEntity.ok(adapter.toVO(usuario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Cadastrar novo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Novo usuario cadastrado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponseVO> salvar(@RequestBody @Valid UsuarioRequestVO usuarioRequestVO) {
        var usuario = service.salvar(usuarioRequestVO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(usuario.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(adapter.toVO(usuario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Atualizar Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/matricula/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioResponseVO> atualizar(
            @PathVariable("matricula") String matricula,
            @RequestBody @Valid UsuarioUpdateRequestVO usuarioUpdateRequestVO){

        var usuario = service.atualizar(matricula, usuarioUpdateRequestVO);
        return ResponseEntity.ok().body(adapter.toVO(usuario));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Remover usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario excluido com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> excluir(@PathVariable("matricula") String matricula) {
        service.excluir(matricula);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'GERENTE')")
    @Operation(summary = "Modificar Status do Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status atualizado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{matricula}/status")
    public ResponseEntity<Void> modificarStatus(
            @PathVariable("matricula") String matricula, @RequestBody @Valid boolean status){

        service.modificarStatus(status, matricula);
        return ResponseEntity.noContent().build();
    }

}