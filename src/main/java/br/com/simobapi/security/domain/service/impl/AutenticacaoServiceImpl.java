package br.com.simobapi.security.domain.service.impl;

import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.security.config.jwt.JwtTokenUtil;
import br.com.simobapi.security.domain.adapter.NovoUsuarioAdapter;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginRequestVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.LoginResponseVO;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioRequestVO;
import br.com.simobapi.domain.entity.UsuarioEnderecoEntity;
import br.com.simobapi.domain.entity.UsuarioEntity;
import br.com.simobapi.domain.entity.UsuarioPerfilEntity;
import br.com.simobapi.security.domain.controller.v1.vo.autenticacao.NovoUsuarioResponseVO;
import br.com.simobapi.security.domain.enums.TipoPerfil;
import br.com.simobapi.domain.repository.PerfilRepository;
import br.com.simobapi.domain.repository.criteria.UsuarioRepository;
import br.com.simobapi.security.domain.service.AutenticacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    NovoUsuarioAdapter adapter;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtToken;

    @Override
    public LoginResponseVO login(LoginRequestVO requestVO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestVO.getEmail(), requestVO.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtToken.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> perfis = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return LoginResponseVO
                .builder()
                    .id(userDetails.getId())
                    .matricula(userDetails.getMatricula())
                    .nome(userDetails.getNome())
                    .email(userDetails.getEmail())
                    .perfis(perfis)
                    .token(jwt)
                    .tokenType("Bearer")
                .build();
    }

    @Override
    public NovoUsuarioResponseVO registrarUsuario(NovoUsuarioRequestVO requestVO) {
        if (usuarioRepository.existsByEmail(requestVO.getEmail())) { throw new DadosJaRegistradoException("Error: Email "); }

        if(requestVO.getPerfis() == null){ throw new RuntimeException("Error: perfis não encontrado!"); }

        Set<UsuarioPerfilEntity> setPerfis = new HashSet<>();
        requestVO.getPerfis().stream().forEach(perfil ->{
               switch (perfil){
                   case ADMINISTRADOR -> {
                       var perfilAdm = perfilRepository.findByTipo(TipoPerfil.ADMINISTRADOR).get();
                       setPerfis.add(perfilAdm);
                   }
                   case GERENTE -> {
                       var perfilGerente = perfilRepository.findByTipo(TipoPerfil.GERENTE).get();
                       setPerfis.add(perfilGerente);
                   }
                   case CORRETOR -> {
                       var perfilCorretor = perfilRepository.findByTipo(TipoPerfil.CORRETOR).get();
                       setPerfis.add(perfilCorretor);
                   }
                   case ATENDENTE -> {
                       var perfilAtendente = perfilRepository.findByTipo(TipoPerfil.ATENDENTE).get();
                       setPerfis.add(perfilAtendente);
                   }
                   default -> {}
               }
        });


        var usuario = UsuarioEntity
                                    .builder()
                                        .matricula(geraMatricula())
                                        .nome(requestVO.getNome())
                                        .email(requestVO.getEmail())
                                        .senha(encoder.encode(requestVO.getSenha()))
                                        .cpf("000.000.000-00")
                                        .rg("00.000.000-00")
                                        .creci("00000")
                                        .endereco(UsuarioEnderecoEntity
                                                .builder()
                                                    .rua("RUA")
                                                    .numero(0)
                                                    .complemento("s/c")
                                                    .cep("CEP")
                                                    .bairro("BAIRRO")
                                                    .cidade("CIDADE")
                                                    .estado("ESTADO")
                                                .build()
                                        )
                                        .perfis( setPerfis )
                                        .ativo(true)
                                    .build();

        return adapter.toVO(usuarioRepository.save(usuario));
    }

    private String geraMatricula(){ return String.valueOf(Year.now().toString() + new Random().nextInt(1000) ); }

}
