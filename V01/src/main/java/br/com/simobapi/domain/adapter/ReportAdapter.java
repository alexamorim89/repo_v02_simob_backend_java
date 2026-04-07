package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.report.ClienteReportVO;
import br.com.simobapi.domain.controller.v1.vo.report.ImovelReportVO;
import br.com.simobapi.domain.entity.ImovelEntity;
import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClienteEnderecoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportAdapter {

    public List<ImovelReportVO> toListImovelVO(List<ImovelEntity> imoveis) {
       return imoveis.stream().map(
               i -> ImovelReportVO
                       .builder()
                            .nome(i.getNome())
                            .tipoImovel(i.getTipoImovel().toString())
                            .status(i.getStatus().toString())
                            .valor(i.getValor().toString())
                            .valorMensal(i.getValorMensal().toString())
                       .build()
       ).toList();
    }


    public List<ClienteReportVO> toListClienteVO(List<ClienteEntity> clientes) {
       return clientes.stream().map( c -> {
                    if(c instanceof ClientePessoaFisicaEntity){
                      return ClienteReportVO.
                              builder()
                                    .nome(c.getNome())
                                    .telefonePrincipal(c.getTelefone1())
                                    .telefoneSecundario(c.getTelefone2())
                                    .email(c.getEmail())
                                    .endereço(toEndereco(c.getEnderecos()))
                              .build();
                    } else {
                        return ClienteReportVO.
                                builder()
                                    .nome(c.getNome())
                                    .telefonePrincipal(c.getTelefone1())
                                    .telefoneSecundario(c.getTelefone2())
                                    .email(c.getEmail())
                                    .endereço(toEndereco(c.getEnderecos()))
                                .build();
                    }
                }
        ).toList();
    }

    private String toEndereco(List<ClienteEnderecoEntity> enderecos) {
        if (enderecos != null){
            var endereco =  enderecos.get(0);

            StringBuilder builder = new StringBuilder();
            builder.append(endereco.getRua() + " ")
                    .append(endereco.getNumero() +",")
                    .append(endereco.getBairro())
                    .append(" - ")
                    .append("cep " +endereco.getCep())
                    .append(" - ")
                    .append(endereco.getCidade());

            return builder.toString();
        }
        return "";
    }

}