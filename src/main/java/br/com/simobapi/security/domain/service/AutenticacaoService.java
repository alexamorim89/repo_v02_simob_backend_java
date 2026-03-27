package br.com.simobapi.security.domain.service;

import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginRequestVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginResponseVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioRequestVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioResponseVO;

public interface AutenticacaoService {

    LoginResponseVO login(LoginRequestVO requestVO);

    NovoUsuarioResponseVO registrarUsuario(NovoUsuarioRequestVO requestVO);
}
