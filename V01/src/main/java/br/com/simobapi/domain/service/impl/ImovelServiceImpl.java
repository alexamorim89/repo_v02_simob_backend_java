package br.com.simobapi.domain.service.impl;

import java.util.UUID;

import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelUpdateRequestVO;

import br.com.simobapi.domain.entity.ImovelEntity;
import br.com.simobapi.domain.entity.ImovelCaracteristicaEntity;
import br.com.simobapi.domain.entity.ImovelEnderecoEntity;
import br.com.simobapi.domain.repository.ProprietarioRepository;
import br.com.simobapi.domain.adapter.ImovelAdapter;
import br.com.simobapi.domain.repository.ImovelRepository;
import br.com.simobapi.domain.service.ImovelService;
import br.com.simobapi.domain.service.exceptions.ImovelPossuiVinculoFinanceiroException;
import br.com.simobapi.domain.service.exceptions.RecursoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImovelServiceImpl implements ImovelService {

	@Autowired
	private ImovelRepository imovelRepository;

	@Autowired
	private ProprietarioRepository proprietarioRepository;

	@Autowired
	private ImovelAdapter adapter;

	@Transactional
	@Override
	public ImovelEntity salvar(ImovelEntity imovel) {
//		verificaProprietario(imovel);

		return imovelRepository.save(imovel);
	}

	@Override
	public ImovelEntity salvar(UUID codigoProprietario, ImovelEntity imovel) {
		var proprietario = proprietarioRepository.findByCodigoSimob(codigoProprietario);
		if(proprietario == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoProprietario)); }
		imovel.setProprietario(proprietario);

		return imovelRepository.save(imovel);
	}

	@Override
	public Page<ImovelEntity> consultar(Pageable pageable) {
		return imovelRepository.findAll(pageable);
	}

	@Override
	public Page<ImovelEntity> consultarPor(ImovelFilterRequestVO filterRequestVO, Pageable pageable) {
		return imovelRepository.findByParameters(filterRequestVO, pageable);
	}

	@Override
	public ImovelEntity consultarPorId(UUID codigoSimob) {
		var imovelEntity = imovelRepository.findByCodigoSimob(codigoSimob);
		if(imovelEntity == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob)); }
		return imovelEntity;
	}

	@Override
	public ImovelEntity atualizar(UUID codigoImovel, ImovelUpdateRequestVO imovelUpdateRequestVO) {
		var imovelEntity = consultarPorId(codigoImovel);

		var imovelAtualizado = atualizaImovel(imovelEntity, imovelUpdateRequestVO);
		return imovelRepository.save(imovelAtualizado);
	}

	@Override
	public void excluir(UUID codigoSimob) {
		var imovelEntity = consultarPorId(codigoSimob);
		verificaRN08(imovelEntity);

		imovelRepository.delete(imovelEntity);
	}


//	private void verificaProprietario(ImovelEntity imovel) {
//		ProprietarioEntity proprietarioNew;
//		var codigoSimob = imovel.getProprietario().getCodigoSimob();
//		if(codigoSimob == null){
//			if(imovel.getProprietario() instanceof ProprietarioPessoaFisicaEntity){
//				proprietarioNew = new ProprietarioPessoaFisicaEntity(
//						imovel.getCodigoSimob(),
//						imovel.getProprietario().getNome(),
//						imovel.getProprietario().getTelefone1(),
//						imovel.getProprietario().getTelefone2(),
//						imovel.getProprietario().getEmail(),
//						((ProprietarioPessoaFisicaEntity) imovel.getProprietario()).getRg(),
//						((ProprietarioPessoaFisicaEntity) imovel.getProprietario()).getCpf(),
//						((ProprietarioPessoaFisicaEntity) imovel.getProprietario()).getDataNascimento() );
//			} else {
//				proprietarioNew = new ProprietarioPessoaJuridicaEntity(
//						imovel.getCodigoSimob(),
//						imovel.getProprietario().getNome(),
//						imovel.getProprietario().getTelefone1(),
//						imovel.getProprietario().getTelefone2(),
//						imovel.getProprietario().getEmail(),
//						((ProprietarioPessoaJuridicaEntity) imovel.getProprietario()).getCnpj(),
//						((ProprietarioPessoaJuridicaEntity) imovel.getProprietario()).getInscricaoEstadual(),
//						((ProprietarioPessoaJuridicaEntity) imovel.getProprietario()).getRazaoSocial() );
//			}
//			var proprietarioReturn = proprietarioRepository.saveAndFlush(proprietarioNew);
//			imovel.setProprietario(proprietarioReturn);
//		} else {
//			var proprietario = proprietarioRepository.findByCodigoSimob(codigoSimob);
//			if(proprietario == null) { throw new RecursoNaoEncontradoException(String.valueOf(codigoSimob)); }
//			imovel.setProprietario(proprietario);
//		}
//	}

	private ImovelEntity atualizaImovel(ImovelEntity imovelEntity, ImovelUpdateRequestVO imovelUpdateRequestVO) {
		imovelEntity.setCodigo(imovelEntity.getCodigo());
		imovelEntity.setCodigoSimob(imovelEntity.getCodigoSimob());
		imovelEntity.setNome(imovelUpdateRequestVO.getNome());
		imovelEntity.setQuantidadeQuarto(imovelUpdateRequestVO.getQuantidadeQuarto());
		imovelEntity.setQuantidadeBanheiro(imovelUpdateRequestVO.getQuantidadeBanheiro());
		imovelEntity.setQuantidadeSuite(imovelUpdateRequestVO.getQuantidadeSuite());
		imovelEntity.setQuantidadeGaragem(imovelEntity.getQuantidadeGaragem());
		imovelEntity.setAreaTotal(imovelUpdateRequestVO.getAreaTotal());
		imovelEntity.setDescricao(imovelUpdateRequestVO.getDescricao());
		imovelEntity.setTipoImovel(imovelUpdateRequestVO.getTipoImovel());
		imovelEntity.setTipoVenda(imovelUpdateRequestVO.getTipoVenda());
		imovelEntity.setStatus(imovelUpdateRequestVO.getStatus());
		imovelEntity.setValor(imovelUpdateRequestVO.getValor());
		imovelEntity.setValorMensal(imovelUpdateRequestVO.getValorMensal());
		imovelEntity.setFotos(null);
		imovelEntity.setCaracteristca(
				new ImovelCaracteristicaEntity(
						imovelUpdateRequestVO.getDetalhes().isTemAreaServico(),
						imovelUpdateRequestVO.getDetalhes().isTemArmarioCozinha(),
						imovelUpdateRequestVO.getDetalhes().isTemArmarioQuarto(),
						imovelUpdateRequestVO.getDetalhes().isTemArmarioProjetado(),
						imovelUpdateRequestVO.getDetalhes().isTemBoxBanheiro(),
						imovelUpdateRequestVO.getDetalhes().isTemCeramica(),
						imovelUpdateRequestVO.getDetalhes().isTemCercado(),
						imovelUpdateRequestVO.getDetalhes().isTemCloset(),
						imovelUpdateRequestVO.getDetalhes().isTemCobertura(),
						imovelUpdateRequestVO.getDetalhes().isTemCondominioFechado(),
						imovelUpdateRequestVO.getDetalhes().isTemConjugada(),
						imovelUpdateRequestVO.getDetalhes().isTemCopa(),
						imovelUpdateRequestVO.getDetalhes().isTemCorredor(),
						imovelUpdateRequestVO.getDetalhes().isTemDespensa(),
						imovelUpdateRequestVO.getDetalhes().isTemCozinha(),
						imovelUpdateRequestVO.getDetalhes().isTemCozinhaAmericana(),
						imovelUpdateRequestVO.getDetalhes().isTemEscritorio(),
						imovelUpdateRequestVO.getDetalhes().isTemEsquadria(),
						imovelUpdateRequestVO.getDetalhes().isTemGradeado() ) );
		imovelEntity.setEndereco(
				new ImovelEnderecoEntity(
						imovelEntity.getEndereco().getCodigo(),
						imovelUpdateRequestVO.getEndereco().getRua(),
						imovelUpdateRequestVO.getEndereco().getNumero(),
						imovelUpdateRequestVO.getEndereco().getComplemento(),
						imovelUpdateRequestVO.getEndereco().getCep(),
						imovelUpdateRequestVO.getEndereco().getBairro(),
						imovelUpdateRequestVO.getEndereco().getCidade(),
						imovelUpdateRequestVO.getEndereco().getEstado()
				)
		);
		return imovelEntity;
	}

	private void verificaRN08(ImovelEntity imovelEntity) {
		if(imovelEntity.getFinanceiro() != null){
			throw new ImovelPossuiVinculoFinanceiroException("RN08: Imovel possui vinculo financeiro");
		}
	}
}