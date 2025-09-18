package com.accenture.application.domain.dtos.responses;

import com.accenture.application.domain.dtos.basics.EmpresaBasicoDTO;
import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.domain.models.TipoFornecedor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FornecedorDTO {

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

    private LocalDate dataNascimento;

    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoDTO endereco;

    private List<EmpresaBasicoDTO> empresas;
    
 // Construtor principal
 	public FornecedorDTO(UUID id, String documento, TipoFornecedor tipoFornecedor, String nome, String email, String rg,
 			LocalDate dataNascimento, EnderecoDTO endereco, List<EmpresaBasicoDTO> empresas) {
 		this.id = id;
 		this.documento = documento;
 		this.tipoFornecedor = tipoFornecedor;
 		this.nome = nome;
 		this.email = email;
 		this.rg = rg;
 		this.dataNascimento = dataNascimento;
 		this.endereco = endereco;
 		this.empresas = empresas;
 	}

 	// Construtor de conveniência
 	public FornecedorDTO(Fornecedor fornecedor) {
 		this.id = fornecedor.getId();
 		this.nome = fornecedor.getNome();
 		this.documento = fornecedor.getDocumento();
 		this.tipoFornecedor = fornecedor.getTipoFornecedor();
 		this.email = fornecedor.getEmail();
 		this.rg = fornecedor.getRg();
 		this.dataNascimento = fornecedor.getDataNascimento();
 		this.endereco = new EnderecoDTO(fornecedor.getEndereco());
 		this.empresas = fornecedor.getEmpresas().stream().map(EmpresaBasicoDTO::new).collect(Collectors.toList());
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public List<EmpresaBasicoDTO> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<EmpresaBasicoDTO> empresas) {
        this.empresas = empresas;
    }
}