package com.accenture.application.services.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accenture.application.domain.dtos.inputs.EmpresaInputDTO;
import com.accenture.application.domain.dtos.responses.EmpresaDTO;
import com.accenture.application.domain.models.Empresa;

public interface IEmpresaService {
	EmpresaDTO criarEmpresa(EmpresaInputDTO empresaInputDTO);
	
	Empresa buscarEmpresaPorId(UUID id);
	
	Page<Empresa> listarEmpresas(Pageable pageable);
	
	EmpresaDTO atualizarEmpresa(UUID id, EmpresaInputDTO empresaInputDTO);
	
	void deletarEmpresa(UUID id);
}