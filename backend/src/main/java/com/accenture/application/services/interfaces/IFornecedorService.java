package com.accenture.application.services.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accenture.application.domain.dtos.filters.FornecedorFiltroDTO;
import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.dtos.responses.FornecedorDTO;
import com.accenture.application.domain.models.Fornecedor;

public interface IFornecedorService {

	FornecedorDTO criarFornecedor(FornecedorInputDTO fornecedorInputDTO) throws Exception;

	FornecedorDTO buscarFornecedorPorId(UUID id);

	Page<FornecedorDTO> listarFornecedores(FornecedorFiltroDTO filtro, Pageable pageable);

	FornecedorDTO atualizarFornecedor(UUID id, FornecedorInputDTO fornecedorInputDTO);

	void deletarFornecedor(UUID id) throws Exception;
	
	FornecedorDTO vincularFornecedorAEmpresa(UUID fornecedorId, UUID empresaId) throws Exception;

    Fornecedor desvincularFornecedorDeEmpresa(UUID fornecedorId, UUID empresaId);

}