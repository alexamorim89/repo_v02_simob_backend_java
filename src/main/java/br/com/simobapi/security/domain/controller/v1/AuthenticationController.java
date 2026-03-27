package br.com.simobapi.security.domain.controller.v1;

import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginRequestVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginResponseVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioRequestVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioResponseVO;
import br.com.simobapi.security.domain.service.AutenticacaoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {
    @Autowired
    private AutenticacaoService service;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoginResponseVO> login(@Valid @RequestBody LoginRequestVO requestVO){
        var responseVO =  service.login(requestVO);
        return ResponseEntity.ok(responseVO);
    }

    @PostMapping("/registrar-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NovoUsuarioResponseVO> registrarUsuario(@Valid @RequestBody NovoUsuarioRequestVO requestVO) {
       var response = service.registrarUsuario(requestVO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{codigo}").buildAndExpand(response.getCodigo()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}