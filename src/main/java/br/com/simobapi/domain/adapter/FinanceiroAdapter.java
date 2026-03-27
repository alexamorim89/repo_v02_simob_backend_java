package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroRequestVO;
import br.com.simobapi.domain.controller.v1.vo.financeiro.FinanceiroResponseVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelVO;
import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClientePessoaJuridicaEntity;
import br.com.simobapi.domain.entity.FinanceiroEntity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FinanceiroAdapter {

    public FinanceiroEntity toEntity(FinanceiroRequestVO financeiroRequestVO) {
        return FinanceiroEntity
                .builder()
                    .data(LocalDate.now())
                    .hora(LocalTime.now())
                    .valor(financeiroRequestVO.getValor())
                    .valorMensal(financeiroRequestVO.getValorMensal())
                    .situacao(financeiroRequestVO.getSituacao())
                .build();
    }

    public FinanceiroResponseVO toVO(FinanceiroEntity financeiro) {
        return FinanceiroResponseVO
                .builder()
                    .codigoSimob(financeiro.getCodigoSimob())
                    .valor(financeiro.getValor())
                    .valorMensal(financeiro.getValorMensal())
                    .data(financeiro.getData())
                    .hora(financeiro.getHora())
                    .cliente(toClienteVO(financeiro.getCliente()))
                    .imovel(ImovelVO
                            .builder()
                                .codigoSimob(financeiro.getImovel().getCodigoSimob())
                                .nome(financeiro.getImovel().getNome())
                                .descricao(financeiro.getImovel().getDescricao())
                                .valor(financeiro.getImovel().getValor())
                            .build())
                    .situacao(financeiro.getSituacao())
                .build();
    }

    public List<FinanceiroResponseVO> toListVO(Page<FinanceiroEntity> pageFinanceiro) {
        List<FinanceiroResponseVO> listResponse = new ArrayList<>();
        for (FinanceiroEntity f : pageFinanceiro.toList()){
            listResponse.add(
                    FinanceiroResponseVO
                            .builder()
                                .codigoSimob(f.getCodigoSimob())
                                .valor(f.getValor())
                                .valorMensal(f.getValorMensal())
                                .data(f.getData())
                                .hora(f.getHora())
                                .cliente(toClienteVO(f.getCliente()))
                                .imovel(ImovelVO
                                        .builder()
                                            .codigoSimob(f.getImovel().getCodigoSimob())
                                            .nome(f.getImovel().getNome())
                                            .descricao(f.getImovel().getDescricao())
                                            .valor(f.getImovel().getValor())
                                        .build())
                                .situacao(f.getSituacao())
                            .build()
            );
        }
        return listResponse;
    }

    private ClienteVO toClienteVO(ClienteEntity cliente) {
        if (cliente instanceof ClientePessoaFisicaEntity){
            return ClienteVO
                    .builder()
                        .nome(cliente.getNome())
                        .documento(((ClientePessoaFisicaEntity) cliente).getCpf())
                    .build();
        } else {
            return ClienteVO
                    .builder()
                        .nome(cliente.getNome())
                        .documento( ((ClientePessoaJuridicaEntity) cliente).getCnpj())
                    .build();
        }
    }


}