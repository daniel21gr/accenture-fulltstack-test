package com.accenture.application.controllers;

import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.dtos.responses.FornecedorDTO;
import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.services.impls.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Fornecedores", description = "Endpoints para gerenciamento de fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    @Operation(
        summary = "Cria um novo fornecedor",
        description = "Cria um novo fornecedor com as informações fornecidas e o salva no banco de dados."
    )
    public ResponseEntity<FornecedorDTO> criarFornecedor(@RequestBody @Valid FornecedorInputDTO fornecedorInputDTO) {
        FornecedorDTO novoFornecedor = fornecedorService.criarFornecedor(fornecedorInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Busca um fornecedor por ID",
        description = "Retorna um fornecedor específico com base no seu ID único."
    )
    public ResponseEntity<FornecedorDTO> buscarFornecedorPorId(@PathVariable UUID id) {
        FornecedorDTO fornecedorDTO = fornecedorService.buscarFornecedorPorId(id);
        return ResponseEntity.ok(fornecedorDTO);
    }

    @GetMapping
    @Operation(
        summary = "Lista todos os fornecedores de forma paginada",
        description = "Retorna uma lista de fornecedores, permitindo paginação para lidar com grandes volumes de dados."
    )
    public ResponseEntity<Page<FornecedorDTO>> listarFornecedores(@ParameterObject Pageable pageable) {
        Page<FornecedorDTO> fornecedoresDTO = fornecedorService.listarFornecedores(pageable);
        return ResponseEntity.ok(fornecedoresDTO);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza um fornecedor existente",
        description = "Atualiza todas as informações de um fornecedor com base no seu ID."
    )
    public ResponseEntity<FornecedorDTO> atualizarFornecedor(@PathVariable UUID id, @RequestBody @Valid FornecedorInputDTO fornecedorInputDTO) {
        FornecedorDTO fornecedorAtualizado = fornecedorService.atualizarFornecedor(id, fornecedorInputDTO);
        return ResponseEntity.ok(fornecedorAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deleta um fornecedor",
        description = "Deleta um fornecedor e seu endereço associado permanentemente do banco de dados."
    )
    public ResponseEntity<Void> deletarFornecedor(@PathVariable UUID id) {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }
}