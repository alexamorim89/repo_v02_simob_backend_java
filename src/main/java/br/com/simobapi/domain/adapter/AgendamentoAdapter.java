package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoRequestVO;
import br.com.simobapi.domain.controller.v1.vo.agendamento.AgendamentoResponseVO;
import br.com.simobapi.domain.controller.v1.vo.visita.VisitaVO;
import br.com.simobapi.domain.entity.AgendamentoEntity;

import br.com.simobapi.domain.entity.VisitaEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendamentoAdapter {


    public AgendamentoEntity toEntity(AgendamentoRequestVO agendamentoRequestVO) {
        return AgendamentoEntity
                .builder()
                    .nome(agendamentoRequestVO.getNome())
                    .telefone(agendamentoRequestVO.getTelefone())
                    .email(agendamentoRequestVO.getEmail())
                    .data(agendamentoRequestVO.getData())
                    .hora(agendamentoRequestVO.getHora())
                    .descricao(agendamentoRequestVO.getDescricao())
                .build();
    }

    public AgendamentoResponseVO toVO(AgendamentoEntity agendamento) {
        return AgendamentoResponseVO
                .builder()
                    .codigoSimob(agendamento.getCodigoSimob())
                    .nome(agendamento.getNome())
                    .telefone(agendamento.getTelefone())
                    .email(agendamento.getEmail())
                    .data(agendamento.getData())
                    .hora(agendamento.getHora())
                    .descricao(agendamento.getDescricao())
                    .visita(getVisita(agendamento.getVisita()))
                .build();
    }

    public List<AgendamentoResponseVO> toListVO(Page<AgendamentoEntity> pageEntity) {
        return pageEntity.stream().map(
                a -> AgendamentoResponseVO
                        .builder()
                            .codigoSimob(a.getCodigoSimob())
                            .nome(a.getNome())
                            .telefone(a.getTelefone())
                            .email(a.getEmail())
                            .data(a.getData())
                            .hora(a.getHora())
                            .descricao(a.getDescricao())
                            .visita(getVisita(a.getVisita()))
                        .build()
        ).toList();
    }

    private VisitaVO getVisita(VisitaEntity visita) {
        if(visita != null){
            return VisitaVO
                    .builder()
                    .codigoSimob(visita.getCodigoSimob())
                    .build();
        }
        return null;
    }

//    private LocalDate getData(String data) {
//        if (!StringUtils.isEmpty(data)){
//            return LocalDate.parse(data, DateTimeFormatter.ISO_DATE_TIME);
//        }
//        return null;
//    }
}
