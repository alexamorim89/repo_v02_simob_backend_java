package br.com.simob_api.api.dto.imovel;

import br.com.simob_api.domain.imovel.enums.SegmentoImovel;
import br.com.simob_api.domain.imovel.enums.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public record ImovelFiltroRequestDTO (
    String municipio,
    TipoImovel tipo,
    SegmentoImovel segmento,
    String ordenacao
) {}
