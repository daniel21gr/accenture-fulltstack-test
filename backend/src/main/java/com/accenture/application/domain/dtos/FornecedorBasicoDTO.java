package com.accenture.application.domain.dtos;

import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.domain.models.TipoFornecedor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class FornecedorBasicoDTO {

    private UUID id;

    @NotBlank(message = "O documento é obrigatório.")
    private String documento;

    @NotNull(message = "O tipo do fornecedor é obrigatório.")
    private TipoFornecedor tipoFornecedor;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    private String rg;

    private Date dataNascimento;

    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoDTO endereco;
    
 // Construtor principal
 	public FornecedorBasicoDTO(UUID id, String documento, String nome, String email, String rg, Date dataNascimento, EnderecoDTO endereco) {
 		this.id = id;
 		this.nome = nome;
 		this.documento = documento;
 		this.email = email;
 		this.rg = rg;
 		this.dataNascimento = dataNascimento;
 		this.endereco = endereco;
 	}

 	// Construtor de conveniência
 	public FornecedorBasicoDTO(Fornecedor fornecedor) {
 		this.id = fornecedor.getId();
 		this.nome = fornecedor.getNome();
 		this.documento = fornecedor.getDocumento();
 		this.email = fornecedor.getEmail();
 		this.rg = fornecedor.getRg();
 		this.dataNascimento = fornecedor.getDataNascimento();
 		this.endereco = new EnderecoDTO(fornecedor.getEndereco());
 	}

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TipoFornecedor getTipoFornecedor() {
        return tipoFornecedor;
    }

    public void setTipoFornecedor(TipoFornecedor tipoFornecedor) {
        this.tipoFornecedor = tipoFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}