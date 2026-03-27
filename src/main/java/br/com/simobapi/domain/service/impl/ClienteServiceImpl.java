package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteFilterVO;
import br.com.simobapi.domain.controller.v1.vo.cliente.ClienteRequestVO;
import br.com.simobapi.domain.adapter.ClienteAdapter;
import br.com.simobapi.domain.entity.ClienteEntity;
import br.com.simobapi.domain.entity.ClientePessoaFisicaEntity;
import br.com.simobapi.domain.entity.ClientePessoaJuridicaEntity;
import br.com.simobapi.domain.repository.ClienteRepository;
import br.com.simobapi.domain.service.ClienteService;
import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteAdapter adapter;

    @Override
    public ClienteEntity salvar(ClienteEntity clienteEntity) {
        verificaRN01(clienteEntity);
        return repository.save(clienteEntity);
    }

    @Override
    public Page<ClienteEntity> consultar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ClienteEntity consultarPorId(UUID codigo) {
        var cliente = repository.findByCodigoSimob(codigo);
        if(cliente == null){ throw new RecursoNaoEncontradoException(String.valueOf(codigo)); }
        return cliente;
    }

    @Override
    public Page<ClienteEntity> consultarPor(ClienteFilterVO clienteFilterVO, Pageable pageable) {
        return repository.findByParameters(clienteFilterVO, pageable);
    }

    @Override
    public ClienteEntity atualizar(UUID codigo, ClienteRequestVO clienteRequestVO) {
        var clienteEntity = repository.findByCodigoSimob(codigo);
        if (clienteEntity == null){ throw new RecursoNaoEncontradoException(String.valueOf(codigo)); }

        var clienteAtualizado = atualizaCliente(clienteEntity, clienteRequestVO);
        return repository.save(clienteAtualizado);
    }

    @Override
    public void excluir(UUID codigo) {
        var clienteEntity =	repository.findByCodigoSimob(codigo);
        if(clienteEntity == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigo)); }

        repository.delete(clienteEntity);
    }

    private ClienteEntity atualizaCliente(ClienteEntity clienteEntity, ClienteRequestVO clienteRequestVO) {
        if (clienteEntity instanceof ClientePessoaFisicaEntity){
            clienteEntity.setCodigo(clienteEntity.getCodigo());
            clienteEntity.setCodigoSimob(clienteEntity.getCodigoSimob());
            clienteEntity.setNome(clienteRequestVO.getNome());
            clienteEntity.setEmail(clienteRequestVO.getEmail());
            clienteEntity.setTelefone1(clienteRequestVO.getTelefone1());
            clienteEntity.setTelefone2(clienteRequestVO.getTelefone2());
            ((ClientePessoaFisicaEntity) clienteEntity).setRg(clienteRequestVO.getDescricao().getRg());
            ((ClientePessoaFisicaEntity) clienteEntity).setCpf(clienteRequestVO.getDescricao().getCpf());
            ((ClientePessoaFisicaEntity) clienteEntity).setDataNascimento(clienteRequestVO.getDescricao().getDataNascimento());
            clienteEntity.getEnderecos().get(0).setCodigo(clienteEntity.getEnderecos().get(0).getCodigo());
            clienteEntity.getEnderecos().get(0).setRua(clienteRequestVO.getEnderecos().get(0).getRua());
            clienteEntity.getEnderecos().get(0).setNumero(clienteRequestVO.getEnderecos().get(0).getNumero());
            clienteEntity.getEnderecos().get(0).setComplemento(clienteRequestVO.getEnderecos().get(0).getComplemento());
            clienteEntity.getEnderecos().get(0).setBairro(clienteRequestVO.getEnderecos().get(0).getBairro());
            clienteEntity.getEnderecos().get(0).setCidade(clienteRequestVO.getEnderecos().get(0).getCidade());
            clienteEntity.getEnderecos().get(0).setEstado(clienteRequestVO.getEnderecos().get(0).getEstado());
            clienteEntity.getEnderecos().get(0).setCep(clienteRequestVO.getEnderecos().get(0).getCep());
        } else {
            clienteEntity.setCodigo(clienteEntity.getCodigo());
            clienteEntity.setCodigoSimob(clienteEntity.getCodigoSimob());
            clienteEntity.setNome(clienteRequestVO.getNome());
            clienteEntity.setEmail(clienteRequestVO.getEmail());
            clienteEntity.setTelefone1(clienteRequestVO.getTelefone1());
            clienteEntity.setTelefone2(clienteRequestVO.getTelefone2());
            ((ClientePessoaJuridicaEntity) clienteEntity).setCnpj(clienteRequestVO.getDescricao().getCnpj());
            ((ClientePessoaJuridicaEntity) clienteEntity).setInscricaoEstadual(clienteRequestVO.getDescricao().getInscricaoEstadual());
            ((ClientePessoaJuridicaEntity) clienteEntity).setRazaoSocial(clienteRequestVO.getDescricao().getRazaoSocial());
            clienteEntity.getEnderecos().get(0).setCodigo(clienteEntity.getEnderecos().get(0).getCodigo());
            clienteEntity.getEnderecos().get(0).setRua(clienteRequestVO.getEnderecos().get(0).getRua());
            clienteEntity.getEnderecos().get(0).setNumero(clienteRequestVO.getEnderecos().get(0).getNumero());
            clienteEntity.getEnderecos().get(0).setComplemento(clienteRequestVO.getEnderecos().get(0).getComplemento());
            clienteEntity.getEnderecos().get(0).setBairro(clienteRequestVO.getEnderecos().get(0).getBairro());
            clienteEntity.getEnderecos().get(0).setCidade(clienteRequestVO.getEnderecos().get(0).getCidade());
            clienteEntity.getEnderecos().get(0).setEstado(clienteRequestVO.getEnderecos().get(0).getEstado());
            clienteEntity.getEnderecos().get(0).setCep(clienteRequestVO.getEnderecos().get(0).getCep());
        }
        return clienteEntity;
    }

    private void verificaRN01(ClienteEntity clienteEntity) {
        if (clienteEntity instanceof ClientePessoaFisicaEntity){
            var rg = ((ClientePessoaFisicaEntity) clienteEntity).getRg();
            var cpf = ((ClientePessoaFisicaEntity) clienteEntity).getCpf();
            var exits = repository.checkPFIfExists(rg, cpf);
            if(exits != null){
                throw new DadosJaRegistradoException("RG = "+rg+" ou CPF = "+cpf);
            }

        } else {
            var cnpj = ((ClientePessoaJuridicaEntity) clienteEntity).getCnpj();
            var inscricaoEstadual = ((ClientePessoaJuridicaEntity) clienteEntity).getInscricaoEstadual();
            var exits = repository.checkPJIfExists(cnpj, inscricaoEstadual);
            if(exits != null){
                throw new DadosJaRegistradoException("CNPJ = "+cnpj+" ou InscricaoEstadual = "+inscricaoEstadual);
            }
        }
    }

}