package com.accenture.application.services.impls;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.accenture.application.domain.dtos.EmpresaDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.domain.models.Endereco;
import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.domain.repositories.EmpresaRepository;
import com.accenture.application.domain.repositories.EnderecoRepository;
import com.accenture.application.domain.repositories.FornecedorRepository;
import com.accenture.application.services.interfaces.IEmpresaService;

import jakarta.transaction.Transactional;

@Service
public class EmpresaService implements IEmpresaService {
	
	private final EmpresaRepository empresaRepository;
    private final EnderecoService enderecoService;
    private final FornecedorRepository fornecedorRepository;
	
	@Autowired
    public EmpresaService(EmpresaRepository empresaRepository, EnderecoService enderecoService, FornecedorRepository fornecedorRepository) {
        this.empresaRepository = empresaRepository;
        this.enderecoService = enderecoService;
        this.fornecedorRepository = fornecedorRepository;
    }

	@Override
	@Transactional
	public Empresa criarEmpresa(EmpresaDTO empresaDTO) {
		// 1. Converte o DTO de Endereço para a entidade e salva
        Endereco novoEndereco = enderecoService.criarEndereco(empresaDTO.getEndereco());

        // 2. Converte o DTO de Empresa para a entidade Empresa
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaDTO.getCnpj());
        empresa.setNomeFantasia(empresaDTO.getNomeFantasia());
        empresa.setEndereco(novoEndereco);

        // 3. Salva a nova empresa e retorna
        return empresaRepository.save(empresa);
	}

	@Override
	public Empresa buscarEmpresaPorId(UUID id) {
		return empresaRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public Page<Empresa> listarEmpresas(Pageable pageable) {
		return empresaRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Empresa atualizarEmpresa(UUID id, EmpresaDTO empresaDTO) {
		Empresa empresaExistente = buscarEmpresaPorId(id);

        // 1. Atualiza o endereço
        enderecoService.atualizarEndereco(empresaExistente.getEndereco().getId(), empresaDTO.getEndereco());

        // 2. Atualiza os dados da empresa
        empresaExistente.setCnpj(empresaDTO.getCnpj());
        empresaExistente.setNomeFantasia(empresaDTO.getNomeFantasia());

        // 3. Salva a empresa atualizada e retorna
        return empresaRepository.save(empresaExistente);
	}

	@Override
	public void deletarEmpresa(UUID id) {
		empresaRepository.deleteById(id);
	}

}
