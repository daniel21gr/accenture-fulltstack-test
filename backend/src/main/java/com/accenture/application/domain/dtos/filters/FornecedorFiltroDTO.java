package com.accenture.application.domain.dtos.filters;

public class FornecedorFiltroDTO {
	private String nome;
    private String documento;
    
    public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
}