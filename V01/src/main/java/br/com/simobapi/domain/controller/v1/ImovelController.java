package br.com.simobapi.domain.controller.v1;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import br.com.simobapi.domain.enums.TipoProprietario;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.simobapi.domain.adapter.ImovelAdapter;
import br.com.simobapi.domain.service.ImovelService;

import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelUpdateRequestVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelRequestP1VO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelRequestP2VO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelResponseVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@CrossOrigin
@Tag(name = "IMOVEL-CONTROLLER Este endpoint registra cadastro de Imoveis")
@RestController
@RequestMapping(value = "/api/v1/imovel")
public class ImovelController {
	
	@Autowired
	private ImovelService service;

	@Autowired
	private ImovelAdapter adapter;

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Consultar todos os Imoveis")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Lista de imoveis retornado com sucesso."),
	    @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ImovelResponseVO> consultar(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		var pageImovel = service.consultar(PageRequest.of(page, size));
		return new PageImpl<>(adapter.toListVO(pageImovel), pageImovel.getPageable(), pageImovel.getTotalElements());
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Consultar Imoveis por parametros")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de imoveis retornado com sucesso."),
			@ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ImovelResponseVO> consultarPor(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			ImovelFilterRequestVO filterRequestVO) {
		var pageImovel = service.consultarPor(filterRequestVO, PageRequest.of(page, size));
		return new PageImpl<>(adapter.toListVO(pageImovel), pageImovel.getPageable(), pageImovel.getTotalElements());
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Consultar imovel por codigo")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Imovel retornado com sucesso."),
	    @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
	    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
	})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImovelResponseVO> consultarPorId(@PathVariable("codigo") UUID codigoSimob) {
		var imovel = service.consultarPorId(codigoSimob);

		return ResponseEntity.ok(adapter.toVO(imovel));
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Salvar novo imovel vinculado ao novo proprietario")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Imovel cadastrado com sucesso."),
	    @ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
	    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImovelResponseVO> salvarComNovoProprietario(
			@RequestBody @Valid ImovelRequestP1VO imovelRequest) {
		var tipo = TipoProprietario.PESSOA_FISICA;
		var imovel = service.salvar(adapter.toEntity(imovelRequest, tipo));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				  .path("/{codigo}").buildAndExpand(imovel.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(adapter.toVO(imovel));
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Salvar novo imovel vinculado ao coodigo simob do proprietario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Imovel cadastrado com sucesso."),
			@ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
	})
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/{codigoProprietario}/proprietario",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ImovelResponseVO> salvarComProprietario(
			@PathVariable UUID codigoProprietario,
			@RequestBody @Valid ImovelRequestP2VO imovelRequest) {
		var imovel = service.salvar(codigoProprietario, adapter.toEntity(imovelRequest));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{codigo}").buildAndExpand(imovel.getCodigo()).toUri();

		return ResponseEntity.created(uri).body(adapter.toVO(imovel));
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Atualizar Imovel")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Imovel atualizado com sucesso."),
			@ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso."),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção."),
	})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<ImovelResponseVO> atualizar(
			@PathVariable("codigo") UUID codigoImovel,
			@RequestBody @Valid ImovelUpdateRequestVO imovelUpdateRequestVO){

		var imovel = service.atualizar(codigoImovel, imovelUpdateRequestVO);
		return ResponseEntity.ok().body(adapter.toVO(imovel));
	}

	@PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CORRETOR', 'GERENTE', 'ATENDENTE')")
	@Operation(summary = "Excluir Imovel sem vinculo financeiro")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Imovel excluido com sucesso."),
			@ApiResponse(responseCode = "401", description = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> excluir(@PathVariable("codigo") UUID codigoImovel) {
		service.excluir(codigoImovel);
		return ResponseEntity.noContent().build();
	}
}