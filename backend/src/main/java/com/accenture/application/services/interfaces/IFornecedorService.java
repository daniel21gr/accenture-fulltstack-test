package com.accenture.application.services.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.dtos.responses.FornecedorDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.domain.models.Fornecedor;

public interface IFornecedorService {

	FornecedorDTO criarFornecedor(FornecedorInputDTO fornecedorInputDTO);

	FornecedorDTO buscarFornecedorPorId(UUID id);

	Page<FornecedorDTO> listarFornecedores(Pageable pageable);

	FornecedorDTO atualizarFornecedor(UUID id, FornecedorInputDTO fornecedorInputDTO);

	void deletarFornecedor(UUID id);
	
	FornecedorDTO vincularFornecedorAEmpresa(UUID fornecedorId, UUID empresaId);

    Fornecedor desvincularFornecedorDeEmpresa(UUID fornecedorId, UUID empresaId);

}