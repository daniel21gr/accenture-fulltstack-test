package com.accenture.application.domain.dtos.responses;

import com.accenture.application.domain.models.Endereco;
import com.accenture.application.validations.interfaces.IValidarCEP;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {

    @NotBlank(message = "O CEP é obrigatório.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos.")
    @IValidarCEP
    private String cep;

    @NotBlank(message = "A rua é obrigatória.")
    private String rua;

    private Integer numero;

    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O UF é obrigatório.")
    @Size(min = 2, max = 2, message = "O UF deve ter 2 caracteres.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "O UF deve conter apenas letras maiúsculas.")
    private String uf;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;
    
    // Construtor padrão (necessário para o JPA)
    public EnderecoDTO() {
    }

    // Novo construtor para criar a entidade a partir do DTO
    public EnderecoDTO(Endereco endereco) {
        this.cep = endereco.getCep();
        this.rua = endereco.getRua();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.cidade = endereco.getCidade();
        this.uf = endereco.getUf();
        this.estado = endereco.getEstado();
    }

    // Getters e Setters
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}