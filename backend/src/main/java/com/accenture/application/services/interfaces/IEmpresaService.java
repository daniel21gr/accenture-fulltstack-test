package com.accenture.application.services.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accenture.application.domain.dtos.EmpresaDTO;
import com.accenture.application.domain.models.Empresa;

public interface IEmpresaService {
	Empresa criarEmpresa(EmpresaDTO empresaDTO);
	
	Empresa buscarEmpresaPorId(UUID id);
	
	Page<Empresa> listarEmpresas(Pageable pageable);
	
	Empresa atualizarEmpresa(UUID id, EmpresaDTO empresaDTO);
	
	void deletarEmpresa(UUID id);
}