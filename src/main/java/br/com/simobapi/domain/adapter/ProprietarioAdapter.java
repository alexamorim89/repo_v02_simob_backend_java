package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioDescricaoResponseVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioResponseVO;

import br.com.simobapi.domain.entity.ProprietarioEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaFisicaEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaJuridicaEntity;
import br.com.simobapi.domain.enums.TipoProprietario;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProprietarioAdapter {

    public ProprietarioEntity toEntity(TipoProprietario tipo, ProprietarioRequestVO proprietarioRequestVO) {
        if(tipo.equals(TipoProprietario.PESSOA_FISICA)){
            return new ProprietarioPessoaFisicaEntity(
                    null,
                    proprietarioRequestVO.getNome(),
                    proprietarioRequestVO.getTelefone1(),
                    proprietarioRequestVO.getTelefone2(),
                    proprietarioRequestVO.getEmail(),
                    proprietarioRequestVO.getDescricao().getRg(),
                    proprietarioRequestVO.getDescricao().getCpf(),
                    proprietarioRequestVO.getDescricao().getDataNascimento() );
        } else {
            return new ProprietarioPessoaJuridicaEntity(
                    null,
                    proprietarioRequestVO.getNome(),
                    proprietarioRequestVO.getTelefone1(),
                    proprietarioRequestVO.getTelefone2(),
                    proprietarioRequestVO.getEmail(),
                    proprietarioRequestVO.getDescricao().getCnpj(),
                    proprietarioRequestVO.getDescricao().getInscricaoEstadual(),
                    proprietarioRequestVO.getDescricao().getRazaoSocial() );
        }
    }

    public ProprietarioResponseVO toVO(ProprietarioEntity proprietario) {
        if(proprietario instanceof ProprietarioPessoaFisicaEntity){
            return ProprietarioResponseVO
                    .builder()
                        .codigoSimob(proprietario.getCodigoSimob())
                        .nome(proprietario.getNome())
                        .telefone1(proprietario.getTelefone1())
                        .telefone2(proprietario.getTelefone2())
                        .email(proprietario.getEmail())
                        .descricao(
                                ProprietarioDescricaoResponseVO
                                .builder()
                                    .rg( ((ProprietarioPessoaFisicaEntity) proprietario).getRg() )
                                    .cpf( ((ProprietarioPessoaFisicaEntity) proprietario).getCpf() )
                                    .dataNascimento( ((ProprietarioPessoaFisicaEntity) proprietario).getDataNascimento() )
                                .build())
                    .build();
        } else {
            return ProprietarioResponseVO
                    .builder()
                    .codigoSimob(proprietario.getCodigoSimob())
                    .nome(proprietario.getNome())
                    .telefone1(proprietario.getTelefone1())
                    .telefone2(proprietario.getTelefone2())
                    .email(proprietario.getEmail())
                    .descricao(ProprietarioDescricaoResponseVO
                            .builder()
                                .cnpj( ((ProprietarioPessoaJuridicaEntity) proprietario).getCnpj() )
                                .inscricaoEstadual( ((ProprietarioPessoaJuridicaEntity) proprietario).getInscricaoEstadual() )
                                .razaoSocial( ((ProprietarioPessoaJuridicaEntity) proprietario).getRazaoSocial() )
                            .build())
                    .build();
        }

    }

    public List<ProprietarioResponseVO> toListVO(Page<ProprietarioEntity> pageProprietario) {
        List <ProprietarioResponseVO> listResponse = new ArrayList<>();
        for (ProprietarioEntity p: pageProprietario.toList()) {
            if (p instanceof ProprietarioPessoaFisicaEntity){
                listResponse.add(
                        ProprietarioResponseVO
                                .builder()
                                    .codigoSimob(p.getCodigoSimob())
                                    .nome(p.getNome())
                                    .telefone1(p.getTelefone1())
                                    .telefone2(p.getTelefone2())
                                    .email(p.getEmail())
                                    .descricao(
                                        ProprietarioDescricaoResponseVO
                                            .builder()
                                            .rg( ((ProprietarioPessoaFisicaEntity) p).getRg() )
                                            .cpf( ((ProprietarioPessoaFisicaEntity) p).getCpf() )
                                            .dataNascimento( ((ProprietarioPessoaFisicaEntity) p).getDataNascimento() )
                                        .build())
                                .build()
                );

            } else {
                listResponse.add(
                        ProprietarioResponseVO
                                .builder()
                                .codigoSimob(p.getCodigoSimob())
                                .nome(p.getNome())
                                .telefone1(p.getTelefone1())
                                .telefone2(p.getTelefone2())
                                .email(p.getEmail())
                                .descricao(
                                        ProprietarioDescricaoResponseVO
                                        .builder()
                                            .cnpj( ((ProprietarioPessoaJuridicaEntity) p).getCnpj() )
                                            .inscricaoEstadual( ((ProprietarioPessoaJuridicaEntity) p).getInscricaoEstadual() )
                                            .razaoSocial( ((ProprietarioPessoaJuridicaEntity) p).getRazaoSocial() )
                                        .build())
                                .build()
                );
            }
        }
        return listResponse;
    }

}
