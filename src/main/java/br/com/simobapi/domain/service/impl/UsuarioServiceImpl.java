package br.com.simobapi.domain.service.impl;

import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.usuario.UsuarioUpdateRequestVO;

import br.com.simobapi.domain.adapter.UsuarioAdapter;

import br.com.simobapi.domain.entity.UsuarioPerfilEntity;
import br.com.simobapi.domain.entity.UsuarioEntity;

import br.com.simobapi.domain.repository.criteria.UsuarioRepository;
import br.com.simobapi.domain.service.UsuarioService;

import br.com.simobapi.domain.service.exceptions.DadosJaRegistradoException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioAdapter adapter;

    @Override
    public UsuarioEntity salvar(UsuarioRequestVO usuarioRequestVO) {
        var usuario = adapter.toEntity(usuarioRequestVO);
        verificaRN07(usuario);
        return repository.save(usuario);
    }

    @Override
    public Page<UsuarioEntity> consultar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<UsuarioEntity> consultarPor(UsuarioFilterRequestVO filterRequestVO, Pageable pageable) {
        return repository.findByParameters(filterRequestVO, pageable);
    }

    @Override
    public UsuarioEntity consultarPorMatricula(String matricula) {
        var usuario = repository.findByMatricula(matricula);
        if(usuario == null){ throw new RecursoNaoEncontradoException(matricula);}
        return usuario.get();
    }

    @Override
    public UsuarioEntity atualizar(String matricula, UsuarioUpdateRequestVO updateRequestVO) {
        var usuario = consultarPorMatricula(matricula);
        var usuarioAtualizado = atualizaUsuario(usuario, updateRequestVO);
        return repository.save(usuarioAtualizado);
    }

    @Override
    public void excluir(String matricula) {
        var usuario = consultarPorMatricula(matricula);
        repository.delete(usuario);
    }

    @Override
    public void modificarStatus(boolean status, String matricula) {
        var usuario = consultarPorMatricula(matricula);
        usuario.setAtivo(status);
        repository.save(usuario);
    }

    private UsuarioEntity atualizaUsuario(UsuarioEntity usuario, UsuarioUpdateRequestVO updateRequestVO) {
        usuario.setCodigo(usuario.getCodigo());
        usuario.setMatricula(usuario.getMatricula());
        usuario.setNome(updateRequestVO.getNome());
        usuario.setEmail(updateRequestVO.getEmail());
//        usuario.setRg(updateRequestVO.getRg());
//        usuario.setCpf(updateRequestVO.getCpf());
//        usuario.setCreci(updateRequestVO.getCreci());
        usuario.setAtivo(updateRequestVO.isAtivo());
//        usuario.setEndereco(UsuarioEnderecoEntity
//                .builder()
//                    .codigo(usuario.getEndereco().getCodigo())
//                    .rua(updateRequestVO.getEndereco().getLogradouro())
//                    .numero(updateRequestVO.getEndereco().getNumero())
//                    .complemento(updateRequestVO.getEndereco().getComplemento())
//                    .cep(updateRequestVO.getEndereco().getCep())
//                    .bairro(updateRequestVO.getEndereco().getBairro())
//                    .estado(updateRequestVO.getEndereco().getEstado())
//                    .cidade(updateRequestVO.getEndereco().getCidade())
//                .build()
//        );
        usuario.getPerfis().clear();
        usuario.getPerfis().addAll(
                updateRequestVO.getPerfis().stream().map(
                        p -> UsuarioPerfilEntity
                                .builder()
                                    .codigo(p.getTipo().getCodigo())
                                    .tipo(p.getTipo())
                                .build()
                ).toList()
        );
        return usuario;
    }

    private void verificaRN07(UsuarioEntity usuario) {
        var email = usuario.getEmail();
        var cpf = usuario.getCpf();
        var rg = usuario.getRg();
        var creci = usuario.getCreci();

        var exist = repository.findByUser(email, cpf, rg, creci);

        if(exist != null) {
            throw new DadosJaRegistradoException("Email = {"+email+"} ou RG = {"+rg+"} ou CPF = {"+cpf+"} ou CRECI = {"+creci+"}");
        }
    }


}
