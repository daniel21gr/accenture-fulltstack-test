package com.accenture.application.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class EmpresaDTO {

    private UUID id;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos.")
    private String cnpj;

    @NotBlank(message = "O nome fantasia é obrigatório.")
    private String nomeFantasia;

    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoDTO endereco;

    private List<FornecedorBasicoDTO> fornecedores;

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