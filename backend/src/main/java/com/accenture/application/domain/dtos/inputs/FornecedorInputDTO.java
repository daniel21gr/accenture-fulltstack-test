package com.accenture.application.domain.dtos.inputs;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import com.accenture.application.domain.dtos.responses.EnderecoDTO;
import com.accenture.application.domain.models.TipoFornecedor;
import com.accenture.application.validations.interfaces.IValidarFornecedorDocumento;
import com.accenture.application.validations.interfaces.IValidarFornecedorPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@IValidarFornecedorDocumento // Validação condicional de documento
@IValidarFornecedorPF // Validação de campos de Pessoa Física
public class FornecedorInputDTO {

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
}
