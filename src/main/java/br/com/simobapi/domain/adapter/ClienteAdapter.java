package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteRequestVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteResponseVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteDescricaoVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteEnderecoVO;

import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClientePessoaJuridicaEntity;
import br.com.simobapi.domain.entity.ClienteEnderecoEntity;
import br.com.simobapi.domain.enums.TipoCliente;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteAdapter {

    public ClienteEntity toEntity(TipoCliente tipo, ClienteRequestVO clienteRequestVO) {
        if(tipo.equals(TipoCliente.PESSOA_FISICA)){
            return new ClientePessoaFisicaEntity(
                    clienteRequestVO.getNome(),
                    clienteRequestVO.getEmail(),
                    clienteRequestVO.getTelefone1(),
                    clienteRequestVO.getTelefone2(),
                    clienteRequestVO.getDescricao().getRg(),
                    clienteRequestVO.getDescricao().getCpf(),
                    clienteRequestVO.getDescricao().getDataNascimento(),
                    toListEnderecoEntity(clienteRequestVO.getEnderecos())
            );
        } else {
            return new ClientePessoaJuridicaEntity(
                    clienteRequestVO.getNome(),
                    clienteRequestVO.getEmail(),
                    clienteRequestVO.getTelefone1(),
                    clienteRequestVO.getTelefone2(),
                    clienteRequestVO.getDescricao().getCnpj(),
                    clienteRequestVO.getDescricao().getInscricaoEstadual(),
                    clienteRequestVO.getDescricao().getRazaoSocial(),
                    toListEnderecoEntity(clienteRequestVO.getEnderecos())
            );
        }
    }

    public ClienteResponseVO toVO(ClienteEntity clienteEntity) {
        if(clienteEntity instanceof ClientePessoaFisicaEntity ){
            return ClienteResponseVO
                    .builder()
                        .codigoSimob(clienteEntity.getCodigoSimob())
                        .nome(clienteEntity.getNome())
                        .email(clienteEntity.getEmail())
                        .telefone1(clienteEntity.getTelefone1())
                        .telefone2(clienteEntity.getTelefone2())
                        .descricao(
                                ClienteDescricaoVO
                                .builder()
                                    .rg(((ClientePessoaFisicaEntity) clienteEntity).getRg())
                                    .cpf(((ClientePessoaFisicaEntity) clienteEntity).getCpf())
                                    .dataNascimento(((ClientePessoaFisicaEntity) clienteEntity).getDataNascimento())
                                .build()
                        )
                        .enderecos(
                                List.of(
                                        ClienteEnderecoVO
                                            .builder()
                                                .rua(clienteEntity.getEnderecos().get(0).getRua())
                                                .numero(clienteEntity.getEnderecos().get(0).getNumero())
                                                .complemento(clienteEntity.getEnderecos().get(0).getComplemento())
                                                .bairro(clienteEntity.getEnderecos().get(0).getBairro())
                                                .cidade(clienteEntity.getEnderecos().get(0).getCidade())
                                                .estado(clienteEntity.getEnderecos().get(0).getEstado())
                                                .cep(clienteEntity.getEnderecos().get(0).getCep())
                                            .build()
                                )
                        )
                        .tipo(TipoCliente.PESSOA_FISICA)
                    .build();
        } else {
            return ClienteResponseVO
                    .builder()
                    .codigoSimob(clienteEntity.getCodigoSimob())
                    .nome(clienteEntity.getNome())
                    .email(clienteEntity.getEmail())
                    .telefone1(clienteEntity.getTelefone1())
                    .telefone2(clienteEntity.getTelefone2())
                    .descricao(
                            ClienteDescricaoVO
                                    .builder()
                                        .cnpj(((ClientePessoaJuridicaEntity) clienteEntity).getCnpj())
                                        .inscricaoEstadual(((ClientePessoaJuridicaEntity) clienteEntity).getInscricaoEstadual())
                                        .razaoSocial(((ClientePessoaJuridicaEntity) clienteEntity).getRazaoSocial())
                                    .build()
                    )
                    .enderecos(
                            List.of(
                                    ClienteEnderecoVO
                                        .builder()
                                            .rua(clienteEntity.getEnderecos().get(0).getRua())
                                            .numero(clienteEntity.getEnderecos().get(0).getNumero())
                                            .complemento(clienteEntity.getEnderecos().get(0).getComplemento())
                                            .bairro(clienteEntity.getEnderecos().get(0).getBairro())
                                            .cidade(clienteEntity.getEnderecos().get(0).getCidade())
                                            .estado(clienteEntity.getEnderecos().get(0).getEstado())
                                            .cep(clienteEntity.getEnderecos().get(0).getCep())
                                        .build()
                            )
                    )
                    .tipo(TipoCliente.PESSOA_JURIDICA)
                    .build();
        }
    }

    public List<ClienteResponseVO> toListResponseVO(Page<ClienteEntity> pageEntity) {
        List <ClienteResponseVO> listResponse = new ArrayList<>();
        for (ClienteEntity c : pageEntity.stream().toList()) {
            if (c instanceof ClientePessoaFisicaEntity){
                listResponse.add(new ClienteResponseVO(
                        c.getCodigoSimob(),
                        c.getNome(),
                        c.getEmail(),
                        c.getTelefone1(),
                        c.getTelefone2(),
                        new ClienteDescricaoVO(
                                ((ClientePessoaFisicaEntity) c).getRg(),
                                ((ClientePessoaFisicaEntity) c).getCpf(),
                                ((ClientePessoaFisicaEntity) c).getDataNascimento()
                        ),
                        Arrays.asList(new ClienteEnderecoVO(
                                c.getEnderecos().get(0).getRua(),
                                c.getEnderecos().get(0).getNumero(),
                                c.getEnderecos().get(0).getComplemento(),
                                c.getEnderecos().get(0).getBairro(),
                                c.getEnderecos().get(0).getCidade(),
                                c.getEnderecos().get(0).getEstado(),
                                c.getEnderecos().get(0).getCep()
                        )),
                        TipoCliente.PESSOA_FISICA
                    )
                );
            } else {
                listResponse.add(new ClienteResponseVO(
                        c.getCodigoSimob(),
                        c.getNome(),
                        c.getEmail(),
                        c.getTelefone1(),
                        c.getTelefone2(),
                        new ClienteDescricaoVO(
                                ((ClientePessoaJuridicaEntity) c).getCnpj(),
                                ((ClientePessoaJuridicaEntity) c).getInscricaoEstadual(),
                                ((ClientePessoaJuridicaEntity) c).getRazaoSocial()
                        ),
                        Arrays.asList(new ClienteEnderecoVO(
                                c.getEnderecos().get(0).getRua(),
                                c.getEnderecos().get(0).getNumero(),
                                c.getEnderecos().get(0).getComplemento(),
                                c.getEnderecos().get(0).getBairro(),
                                c.getEnderecos().get(0).getCidade(),
                                c.getEnderecos().get(0).getEstado(),
                                c.getEnderecos().get(0).getCep()
                        )),
                        TipoCliente.PESSOA_JURIDICA )
                );
            }
        }

        return listResponse;
    }

    private List<ClienteEnderecoVO> toListEnderecoVO(ClienteEntity clienteEntity) {
        return Arrays.asList( new ClienteEnderecoVO(
                clienteEntity.getEnderecos().get(0).getRua(),
                clienteEntity.getEnderecos().get(0).getNumero(),
                clienteEntity.getEnderecos().get(0).getComplemento(),
                clienteEntity.getEnderecos().get(0).getBairro(),
                clienteEntity.getEnderecos().get(0).getCidade(),
                clienteEntity.getEnderecos().get(0).getEstado(),
                clienteEntity.getEnderecos().get(0).getCep()
        ) );
    }

    private List<ClienteEnderecoEntity> toListEnderecoEntity(List<ClienteEnderecoVO> enderecos) {
        return enderecos.stream().map(enderecoVO -> toEnderecoEntity(enderecoVO) )
                .collect(Collectors.toList());
    }

    private ClienteEnderecoEntity toEnderecoEntity(ClienteEnderecoVO enderecoVO) {
        return new ClienteEnderecoEntity(
                enderecoVO.getRua(),
                enderecoVO.getNumero(),
                enderecoVO.getComplemento(),
                enderecoVO.getBairro(),
                enderecoVO.getCidade(),
                enderecoVO.getEstado(),
                enderecoVO.getCep()
        );
    }

}