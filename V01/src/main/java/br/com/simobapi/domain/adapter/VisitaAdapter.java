package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.visita.VisitaRequestVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaResponseVO;
import br.com.simobapi.domain.entity.AnotacaoEntity;
import br.com.simobapi.domain.entity.VisitaEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VisitaAdapter {
    public VisitaEntity toEntity(VisitaRequestVO visitaRequestVO) {
        return new VisitaEntity(
                null,
                visitaRequestVO.getNome(),
                visitaRequestVO.getTelefone(),
                visitaRequestVO.getEmail(),
                visitaRequestVO.getData(),
                visitaRequestVO.getHora(),
                new AnotacaoEntity(visitaRequestVO.getAnotacao())
        );
    }

    public VisitaResponseVO toVO(VisitaEntity visita) {
        return VisitaResponseVO
                .builder()
                    .codigoSimob(visita.getCodigoSimob())
                    .nome(visita.getNome())
                    .telefone(visita.getTelefone())
                    .email(visita.getEmail())
                    .data(visita.getData())
                    .hora(visita.getHora())
                    .anotacao(visita.getAnotacao().getDescricao())
                    .geradoPorAgenda(visita.getGerardoPorAgenda())
                .build();
    }

    public List<VisitaResponseVO> toListVO(Page<VisitaEntity> pageEntity) {
        List <VisitaResponseVO> listResponse = new ArrayList<>();
        for (VisitaEntity p : pageEntity.toList()) {
            listResponse.add(
                    VisitaResponseVO
                            .builder()
                            .codigoSimob(p.getCodigoSimob())
                            .nome(p.getNome())
                            .telefone(p.getTelefone())
                            .email(p.getEmail())
                            .data(p.getData())
                            .hora(p.getHora())
                            .anotacao(p.getAnotacao().getDescricao())
                            .geradoPorAgenda(p.getGerardoPorAgenda())
                            .build()
            );
        }
        return listResponse;
    }
}
