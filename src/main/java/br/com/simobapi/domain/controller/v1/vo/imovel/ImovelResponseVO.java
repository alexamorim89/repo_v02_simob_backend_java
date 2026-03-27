package br.com.simobapi.domain.controller.v1.vo.imovel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.simobapi.domain.controller.v1.vo.proprietario.ProprietarioResponseVO;

import br.com.simobapi.domain.enums.TipoImovel;
import br.com.simobapi.domain.enums.TipoStatus;
import br.com.simobapi.domain.enums.TipoVenda;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImovelResponseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID codigoSimob;
	private String nome;
	private Integer quantidadeQuarto;
	private Integer quantidadeBanheiro;
	private Integer quantidadeSuite;
	private Integer quantidadeGaragem;
	private String areaTotal;
	private String descricao;
	private TipoImovel tipoImovel;
	private TipoVenda tipoVenda;
	private TipoStatus status;
	private BigDecimal valor;
	private BigDecimal valorMensal;
	private List<ImovelFotoVO> fotos;
	private ImovelCaracteristicaVO detalhes;
	private ImovelEnderecoVO endereco;
	private ProprietarioResponseVO proprietario;
}