package com.accenture.application.controllers;

import com.accenture.application.domain.dtos.EmpresaDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.services.impls.EmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Endpoints para gerenciamento de empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @Operation(
    	summary = "Cria uma nova empresa",
	    description = "Cria uma nova empresa com as informações fornecidas e a salva no banco de dados."
	)
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody @Valid EmpresaDTO empresaDTO) {
        Empresa novaEmpresa = empresaService.criarEmpresa(empresaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }

    @GetMapping("/{id}")
    @Operation(
    	summary = "Busca uma empresa por ID",
    	description = "Retorna uma empresa específica com base no seu ID único."
    )
    public ResponseEntity<Empresa> buscarEmpresaPorId(@PathVariable UUID id) {
        Empresa empresa = empresaService.buscarEmpresaPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping
    @Operation(
    	summary = "Lista todas as empresas de forma paginada",
    	description = "Retorna uma lista de empresas, permitindo paginação para lidar com grandes volumes de dados."
    )
    public ResponseEntity<Page<Empresa>> listarEmpresas(@ParameterObject Pageable pageable) {
        Page<Empresa> empresas = empresaService.listarEmpresas(pageable);
        return ResponseEntity.ok(empresas);
    }

    @PutMapping("/{id}")
    @Operation(
    	summary = "Atualiza uma empresa existente",
    	description = "Atualiza todas as informações de uma empresa com base no seu ID."
    )
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable UUID id, @RequestBody @Valid EmpresaDTO empresaDTO) {
        Empresa empresaAtualizada = empresaService.atualizarEmpresa(id, empresaDTO);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
    	summary = "Deleta uma empresa",
    	description = "Deleta uma empresa e seu endereço associado permanentemente do banco de dados."
    )
    public ResponseEntity<Void> deletarEmpresa(@PathVariable UUID id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}