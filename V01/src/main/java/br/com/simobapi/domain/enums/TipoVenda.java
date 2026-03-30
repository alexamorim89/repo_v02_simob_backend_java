package br.com.simobapi.domain.enums;

public enum TipoVenda {
	
	VENDA("Venda"),
	ALUGUEL("Aluguel");

	private String descricao;
	
	TipoVenda(String desscricao) {
		this.descricao = desscricao;
	}

	public String getDescricao() {
		return descricao;
	}	
	
}
