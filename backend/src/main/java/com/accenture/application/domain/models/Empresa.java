package com.accenture.application.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter 14 dígitos.")
    @Column(unique = true)
    private String cnpj;

    @NotBlank(message = "O nome fantasia é obrigatório.")
    private String nomeFantasia;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;
    
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "empresa_fornecedor",
        joinColumns = @JoinColumn(name = "id_empresa"),
        inverseJoinColumns = @JoinColumn(name = "id_fornecedor")
    )
    private List<Fornecedor> fornecedores = new ArrayList<>();

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = new ArrayList<>(fornecedores);
    }
}