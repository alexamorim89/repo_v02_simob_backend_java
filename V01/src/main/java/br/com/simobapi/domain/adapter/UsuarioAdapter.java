package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioPerfilResponseVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioEnderecoResponseVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioResponseVO;
import br.com.simobapi.domain.entity.UsuarioEnderecoEntity;
import br.com.simobapi.domain.entity.UsuarioPerfilEntity;
import br.com.simobapi.domain.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UsuarioAdapter {
    public UsuarioResponseVO toVO(UsuarioEntity usuario) {
        return UsuarioResponseVO
                .builder()
                .nome(usuario.getNome())
                .matricula(usuario.getMatricula())
                .rg(usuario.getRg())
                .cpf(usuario.getCpf())
                .creci(usuario.getCreci())
                . email(usuario.getEmail())
                .ativo(usuario.isAtivo())
                .endereco(
                        UsuarioEnderecoResponseVO
                                .builder()
                                .logradouro(usuario.getEndereco().getRua())
                                .numero(usuario.getEndereco().getNumero())
                                .complemento(usuario.getEndereco().getComplemento())
                                .cep(usuario.getEndereco().getCep())
                                .bairro(usuario.getEndereco().getBairro())
                                .cidade(usuario.getEndereco().getCidade())
                                .estado(usuario.getEndereco().getEstado())
                                .build()
                )
                .perfis(
                        usuario.getPerfis().stream().map(
                                perfil -> UsuarioPerfilResponseVO
                                        .builder()
                                        .tipo(perfil.getTipo())
                                        .build()
                        ).toList()
                )
                .build();
    }

    public List<UsuarioResponseVO> toListVO(Page<UsuarioEntity> pageUsuario) {
        return pageUsuario.stream().map(
                p -> UsuarioResponseVO
                        .builder()
                            .nome(p.getNome())
                            .matricula(p.getMatricula())
                            .rg(p.getRg())
                            .cpf(p.getCpf())
                            .creci(p.getCreci())
                            . email(p.getEmail())
                            .ativo(p.isAtivo())
                            .endereco(
                                    UsuarioEnderecoResponseVO
                                            .builder()
                                                .logradouro(p.getEndereco().getRua())
                                                .numero(p.getEndereco().getNumero())
                                                .complemento(p.getEndereco().getComplemento())
                                                .cep(p.getEndereco().getCep())
                                                .bairro(p.getEndereco().getBairro())
                                                .cidade(p.getEndereco().getCidade())
                                                .estado(p.getEndereco().getEstado())
                                            .build()
                            )
                            .perfis(
                                    p.getPerfis().stream().map(
                                            perfil -> UsuarioPerfilResponseVO
                                                    .builder()
                                                        .tipo(perfil.getTipo())
                                                    .build() ).toList()
                            )
                        .build()
        ).toList();
    }

    public UsuarioEntity toEntity(UsuarioRequestVO usuarioRequestVO){
        return UsuarioEntity
                .builder()
                    .nome(usuarioRequestVO.getNome())
                    .email(usuarioRequestVO.getEmail())
                    .rg("00.000.000-00")
                    .cpf("000.000.000.00")
                    .creci("00000")
                    .matricula(geraMatricula())
                    .senha( toEncrypt(geraSenha() ) )
                    .ativo(usuarioRequestVO.isAtivo())
                    .perfis(
                            usuarioRequestVO.getPerfis()
                                .stream().map(
                                    p -> UsuarioPerfilEntity
                                            .builder()
                                                .codigo(p.getTipo().getCodigo())
                                                .tipo(p.getTipo())
                                            .build() ).collect(Collectors.toSet())
                    )
                    .endereco(
                            UsuarioEnderecoEntity
                                    .builder()
                                        .rua("RUA")
                                        .numero(0)
                                        .complemento("S/C")
                                        .bairro("BAIRRO")
                                        .cidade("CIDADE")
                                        .estado("ESTADO")
                                        .cep("00.000-000")
                                    .build()
                    )
                .build();
    }


    private String geraMatricula(){ return String.valueOf(Year.now().toString() + new Random().nextInt(1000) ); }
    private String toEncrypt(String senha){ return new BCryptPasswordEncoder().encode(senha); }

    private String geraSenha(){
        return "simob@123";
    }

}
