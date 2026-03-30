package br.com.simobapi.domain.service;

import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelFilterRequestVO;
import br.com.simobapi.domain.controller.v1.vo.imovel.ImovelUpdateRequestVO;
import br.com.simobapi.domain.entity.ImovelEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ImovelService {

	 ImovelEntity salvar(ImovelEntity imovel);

	 ImovelEntity salvar(UUID codigoProprietario, ImovelEntity imovel);

	 Page<ImovelEntity> consultar(Pageable pageable);

	 Page<ImovelEntity> consultarPor(ImovelFilterRequestVO filterRequestVO, Pageable pageable);

	 ImovelEntity consultarPorId(UUID codigoSimob);

	 ImovelEntity atualizar(UUID codigoImovel, ImovelUpdateRequestVO imovelUpdateRequestVO);

	 void excluir(UUID codigoSimob);
}
