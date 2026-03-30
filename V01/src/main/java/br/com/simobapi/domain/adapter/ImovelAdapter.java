package br.com.simobapi.domain.adapter;

import br.com.simobapi.domain.controller.v1.vo.imovel.*;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioRequestVO;
import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioResponseVO;
import br.com.simobapi.domain.entity.ImovelEntity;
import br.com.simobapi.domain.entity.ProprietarioEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaFisicaEntity;
import br.com.simobapi.domain.entity.ProprietarioPessoaJuridicaEntity;
import br.com.simobapi.domain.entity.ImovelFotoEntity;
import br.com.simobapi.domain.entity.ImovelCaracteristicaEntity;
import br.com.simobapi.domain.entity.ImovelEnderecoEntity;

import br.com.simobapi.domain.enums.TipoProprietario;

import br.com.simobapi.domain.util.ImageUtils;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ImovelAdapter {

	@Deprecated
	public ImovelEntity toEntity(ImovelRequestP1VO imovelRequest, TipoProprietario tipo) {
		return ImovelEntity.builder()
				.nome(imovelRequest.getNome())
				.quantidadeQuarto(imovelRequest.getQuantidadeQuarto())
				.quantidadeBanheiro(imovelRequest.getQuantidadeBanheiro())
				.quantidadeSuite(imovelRequest.getQuantidadeSuite())
				.quantidadeGaragem(imovelRequest.getQuantidadeGaragem())
				.areaTotal(imovelRequest.getAreaTotal())
				.descricao(imovelRequest.getDescricao())
				.tipoImovel(imovelRequest.getTipoImovel())
				.tipoVenda(imovelRequest.getTipoVenda())
				.status(imovelRequest.getStatus())
				.valor(imovelRequest.getValor())
				.valorMensal(imovelRequest.getValorMensal())
//				.fotos( toFotosP1Entity(imovelRequest) )
				.caracteristca(
						new ImovelCaracteristicaEntity(
								imovelRequest.getDetalhes().isTemAreaServico(),
								imovelRequest.getDetalhes().isTemArmarioCozinha(),
								imovelRequest.getDetalhes().isTemArmarioQuarto(),
								imovelRequest.getDetalhes().isTemArmarioProjetado(),
								imovelRequest.getDetalhes().isTemBoxBanheiro(),
								imovelRequest.getDetalhes().isTemCeramica(),
								imovelRequest.getDetalhes().isTemCercado(),
								imovelRequest.getDetalhes().isTemCloset(),
								imovelRequest.getDetalhes().isTemCobertura(),
								imovelRequest.getDetalhes().isTemCondominioFechado(),
								imovelRequest.getDetalhes().isTemConjugada(),
								imovelRequest.getDetalhes().isTemCopa(),
								imovelRequest.getDetalhes().isTemCorredor(),
								imovelRequest.getDetalhes().isTemDespensa(),
								imovelRequest.getDetalhes().isTemCozinha(),
								imovelRequest.getDetalhes().isTemCozinhaAmericana(),
								imovelRequest.getDetalhes().isTemEscritorio(),
								imovelRequest.getDetalhes().isTemEsquadria(),
								imovelRequest.getDetalhes().isTemGradeado() ) )
				.endereco(
						new ImovelEnderecoEntity(
						null,
								imovelRequest.getEndereco().getRua(),
								imovelRequest.getEndereco().getNumero(),
								imovelRequest.getEndereco().getComplemento(),
								imovelRequest.getEndereco().getCep(),
								imovelRequest.getEndereco().getBairro(),
								imovelRequest.getEndereco().getCidade(),
								imovelRequest.getEndereco().getEstado() ) )
				.proprietario( toProprietarioEntity(imovelRequest.getProprietario(), tipo ) )
				.build();
	}

	public ImovelEntity toEntity(ImovelRequestP2VO imovelRequest) {
		return ImovelEntity.builder()
				.nome(imovelRequest.getNome())
				.quantidadeQuarto(imovelRequest.getQuantidadeQuarto())
				.quantidadeBanheiro(imovelRequest.getQuantidadeBanheiro())
				.quantidadeSuite(imovelRequest.getQuantidadeSuite())
				.quantidadeGaragem(imovelRequest.getQuantidadeGaragem())
				.areaTotal(imovelRequest.getAreaTotal())
				.descricao(imovelRequest.getDescricao())
				.tipoImovel(imovelRequest.getTipoImovel())
				.tipoVenda(imovelRequest.getTipoVenda())
				.status(imovelRequest.getStatus())
				.valor(imovelRequest.getValor())
				.valorMensal(imovelRequest.getValorMensal())
//				.fotos( toFotosP2Entity(imovelRequest) )
				.caracteristca(
						new ImovelCaracteristicaEntity(
								imovelRequest.getDetalhes().isTemAreaServico(),
								imovelRequest.getDetalhes().isTemArmarioCozinha(),
								imovelRequest.getDetalhes().isTemArmarioQuarto(),
								imovelRequest.getDetalhes().isTemArmarioProjetado(),
								imovelRequest.getDetalhes().isTemBoxBanheiro(),
								imovelRequest.getDetalhes().isTemCeramica(),
								imovelRequest.getDetalhes().isTemCercado(),
								imovelRequest.getDetalhes().isTemCloset(),
								imovelRequest.getDetalhes().isTemCobertura(),
								imovelRequest.getDetalhes().isTemCondominioFechado(),
								imovelRequest.getDetalhes().isTemConjugada(),
								imovelRequest.getDetalhes().isTemCopa(),
								imovelRequest.getDetalhes().isTemCorredor(),
								imovelRequest.getDetalhes().isTemDespensa(),
								imovelRequest.getDetalhes().isTemCozinha(),
								imovelRequest.getDetalhes().isTemCozinhaAmericana(),
								imovelRequest.getDetalhes().isTemEscritorio(),
								imovelRequest.getDetalhes().isTemEsquadria(),
								imovelRequest.getDetalhes().isTemGradeado() ) )
				.endereco(
						new ImovelEnderecoEntity(
								null,
								imovelRequest.getEndereco().getRua(),
								imovelRequest.getEndereco().getNumero(),
								imovelRequest.getEndereco().getComplemento(),
								imovelRequest.getEndereco().getCep(),
								imovelRequest.getEndereco().getBairro(),
								imovelRequest.getEndereco().getCidade(),
								imovelRequest.getEndereco().getEstado() ) )
				.build();
	}

	public ImovelResponseVO toVO(ImovelEntity imovelEntity) {
		return ImovelResponseVO.builder()
				.codigoSimob(imovelEntity.getCodigoSimob())
				.nome(imovelEntity.getNome())
				.quantidadeQuarto(imovelEntity.getQuantidadeQuarto())
				.quantidadeBanheiro(imovelEntity.getQuantidadeBanheiro())
				.quantidadeSuite(imovelEntity.getQuantidadeSuite())
				.quantidadeGaragem(imovelEntity.getQuantidadeGaragem())
				.areaTotal(imovelEntity.getAreaTotal())
				.descricao(imovelEntity.getDescricao())
				.tipoImovel(imovelEntity.getTipoImovel())
				.tipoVenda(imovelEntity.getTipoVenda())
				.status(imovelEntity.getStatus())
				.valor(imovelEntity.getValor())
				.valorMensal(imovelEntity.getValorMensal())
//				.fotos( toFotosImovelVO(imovelEntity) )
				.detalhes(
						new ImovelCaracteristicaVO(
							imovelEntity.getCaracteristca().isTemAreaServico(),
							imovelEntity.getCaracteristca().isTemArmarioCozinha(),
							imovelEntity.getCaracteristca().isTemArmarioQuarto(),
							imovelEntity.getCaracteristca().isTemArmarioProjetado(),
							imovelEntity.getCaracteristca().isTemBoxBanheiro(),
							imovelEntity.getCaracteristca().isTemCeramica(),
							imovelEntity.getCaracteristca().isTemCercado(),
							imovelEntity.getCaracteristca().isTemCloset(),
							imovelEntity.getCaracteristca().isTemCobertura(),
							imovelEntity.getCaracteristca().isTemCondominioFechado(),
							imovelEntity.getCaracteristca().isTemConjugada(),
							imovelEntity.getCaracteristca().isTemCopa(),
							imovelEntity.getCaracteristca().isTemCorredor(),
							imovelEntity.getCaracteristca().isTemDespensa(),
							imovelEntity.getCaracteristca().isTemCozinha(),
							imovelEntity.getCaracteristca().isTemCozinhaAmericana(),
							imovelEntity.getCaracteristca().isTemEscritorio(),
							imovelEntity.getCaracteristca().isTemEsquadria(),
							imovelEntity.getCaracteristca().isTemGradeado())
				)
				.endereco(
						new ImovelEnderecoVO(
							imovelEntity.getEndereco().getRua(),
							imovelEntity.getEndereco().getNumero(),
							imovelEntity.getEndereco().getComplemento(),
							imovelEntity.getEndereco().getCep(),
							imovelEntity.getEndereco().getBairro(),
							imovelEntity.getEndereco().getCidade(),
							imovelEntity.getEndereco().getEstado())
				)
				.proprietario( toProprietarioVO(imovelEntity.getProprietario()) )
				.build();
	}

	public List<ImovelResponseVO> toListVO(Page<ImovelEntity> pageImovel) {
		List <ImovelResponseVO> listResponse = new ArrayList<>();
		for (ImovelEntity i : pageImovel.stream().toList()) {
			listResponse.add(

					ImovelResponseVO
						.builder()
							.codigoSimob(i.getCodigoSimob())
							.nome(i.getNome())
							.quantidadeQuarto(i.getQuantidadeQuarto())
							.quantidadeBanheiro(i.getQuantidadeBanheiro())
							.quantidadeSuite(i.getQuantidadeSuite())
							.quantidadeGaragem(i.getQuantidadeGaragem())
							.areaTotal(i.getAreaTotal())
							.descricao(i.getDescricao())
							.tipoImovel(i.getTipoImovel())
							.tipoVenda(i.getTipoVenda())
							.status(i.getStatus())
							.valor(i.getValor())
							.valorMensal(i.getValorMensal())
//							.fotos(null)
							.detalhes(
									ImovelCaracteristicaVO
										.builder()
											.temAreaServico(i.getCaracteristca().isTemAreaServico())
											.temArmarioCozinha(i.getCaracteristca().isTemArmarioCozinha())
											.temArmarioQuarto(i.getCaracteristca().isTemArmarioQuarto())
											.temArmarioProjetado(i.getCaracteristca().isTemArmarioProjetado())
											.temBoxBanheiro(i.getCaracteristca().isTemBoxBanheiro())
											.temCeramica(i.getCaracteristca().isTemCeramica())
											.temCercado(i.getCaracteristca().isTemCercado())
											.temCloset(i.getCaracteristca().isTemCloset())
											.temCobertura(i.getCaracteristca().isTemCobertura())
											.temCondominioFechado(i.getCaracteristca().isTemCondominioFechado())
											.temConjugada(i.getCaracteristca().isTemConjugada())
											.temCopa(i.getCaracteristca().isTemCopa())
											.temCorredor(i.getCaracteristca().isTemCorredor())
											.temDespensa(i.getCaracteristca().isTemDespensa())
											.temCozinha(i.getCaracteristca().isTemCozinha())
											.temCozinhaAmericana(i.getCaracteristca().isTemCozinhaAmericana())
											.temEscritorio(i.getCaracteristca().isTemEscritorio())
											.temEsquadria(i.getCaracteristca().isTemEsquadria())
											.temGradeado(i.getCaracteristca().isTemGradeado())
										.build() )
							.endereco(
									ImovelEnderecoVO
										.builder()
											.rua(i.getEndereco().getRua())
											.numero(i.getEndereco().getNumero())
											.complemento(i.getEndereco().getComplemento())
											.cep(i.getEndereco().getCep())
											.bairro(i.getEndereco().getBairro())
											.cidade(i.getEndereco().getCidade())
											.estado(i.getEndereco().getEstado())
										.build() )
							.proprietario( toProprietarioVO(i.getProprietario()) )
						.build()
			);
		}
		return listResponse;
	}

	private List<ImovelFotoEntity> toFotosP1Entity(ImovelRequestP1VO imovelRequest) {
		if(imovelRequest.getFotosImovel() == null){
			imovelRequest.setFotosImovel(Arrays.asList(new ImovelFotoVO()));
		}

		return imovelRequest.getFotosImovel().stream()
				.map(f -> ImovelFotoEntity
								.builder()
									.nome(f.getNome())
									.foto(getImage(f)) //f.getSrc()
								.build()
				).toList();
	}

	private List<ImovelFotoEntity> toFotosP2Entity(ImovelRequestP2VO imovelRequest) {
		if(imovelRequest.getFotosImovel() == null){
			imovelRequest.setFotosImovel(Arrays.asList(new ImovelFotoVO()));
		}

		return imovelRequest.getFotosImovel().stream()
				.map(f -> ImovelFotoEntity
								.builder()
									.nome(f.getNome())
									.foto(getImage(f))
								.build()
				).toList();
	}

	@Deprecated
	private ProprietarioEntity toProprietarioEntity(ProprietarioRequestVO proprietarioVO, TipoProprietario tipo) {
		if (tipo.equals(TipoProprietario.PESSOA_FISICA)){
			return new ProprietarioPessoaFisicaEntity(
					null,
					proprietarioVO.getNome(),
					proprietarioVO.getTelefone1(),
					proprietarioVO.getTelefone2(),
					proprietarioVO.getEmail(),
					proprietarioVO.getDescricao().getRg(),
					proprietarioVO.getDescricao().getCpf(),
					proprietarioVO.getDescricao().getDataNascimento() );
		} else {
			return new ProprietarioPessoaJuridicaEntity(
					null,
					proprietarioVO.getNome(),
					proprietarioVO.getTelefone1(),
					proprietarioVO.getTelefone2(),
					proprietarioVO.getEmail(),
					proprietarioVO.getDescricao().getCnpj(),
					proprietarioVO.getDescricao().getInscricaoEstadual(),
					proprietarioVO.getDescricao().getRazaoSocial() );
		}
	}

	private List<ImovelFotoVO> toFotosImovelVO(ImovelEntity imovelEntity) {
		if(imovelEntity.getFotos() == null){
			imovelEntity.setFotos(Arrays.asList(new ImovelFotoEntity()));
		}
		return imovelEntity.getFotos().stream()
				.map(f -> ImovelFotoVO.builder()
						.nome(f.getNome())
						.src(null)
						.build() ).toList();
	}

	private ProprietarioResponseVO toProprietarioVO(ProprietarioEntity proprietarioEntity){
			return ProprietarioResponseVO
					.builder()
						.codigoSimob(proprietarioEntity.getCodigoSimob())
						.nome(proprietarioEntity.getNome())
						.telefone1(proprietarioEntity.getTelefone1())
						.telefone2(proprietarioEntity.getTelefone2())
					    .email(proprietarioEntity.getEmail())
					.build();
	}


	private byte[] getImage(ImovelFotoVO fotoVO)  {
		try {
			var image = fotoVO.getSrc().toString();
			System.out.println("PRINT: " +image);
			return ImageUtils.toByteArray(ImageIO.read(new File( image )), "jpg");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}



	//ImageUtils.toByteArray(ImageIO.read(new File(f.getSrc())), "jpg")

}