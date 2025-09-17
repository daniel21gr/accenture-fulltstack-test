package com.accenture.application.domain.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.accenture.application.domain.dtos.basics.FornecedorBasicoDTO;
import com.accenture.application.domain.models.Empresa;

public class EmpresaDTO {

    private UUID id;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos.")
    private String cnpj;

    @NotBlank(message = "O nome fantasia é obrigatório.")
    private String nomeFantasia;

    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoDTO endereco;

    private List<FornecedorBasicoDTO> fornecedores = new ArrayList<FornecedorBasicoDTO>(); 

	// Construtor principal
	public EmpresaDTO(UUID id, String cnpj, String nomeFantasia, EnderecoDTO endereco,
			List<FornecedorBasicoDTO> fornecedores) {
		this.id = id;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
		this.endereco = endereco;
		this.fornecedores = fornecedores;
	}

	// Construtor de conveniência
	public EmpresaDTO(Empresa empresa) {
		this.id = empresa.getId();
		this.cnpj = empresa.getCnpj();
		this.nomeFantasia = empresa.getNomeFantasia();
		this.endereco = new EnderecoDTO(empresa.getEndereco());

		// Mapeia a lista de entidades para a lista de DTOs simples
		this.fornecedores = empresa.getFornecedores().stream().map(FornecedorBasicoDTO::new)
				.collect(Collectors.toList());
	}

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public List<FornecedorBasicoDTO> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<FornecedorBasicoDTO> fornecedores) {
        this.fornecedores = fornecedores;
    }
}