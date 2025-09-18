package com.accenture.application.services.impls;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.accenture.application.domain.dtos.inputs.EmpresaInputDTO;
import com.accenture.application.domain.dtos.responses.EmpresaDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.domain.models.Endereco;
import com.accenture.application.domain.repositories.EmpresaRepository;
import com.accenture.application.services.interfaces.IEmpresaService;

import jakarta.transaction.Transactional;

@Service
public class EmpresaService implements IEmpresaService {
	
	private final EmpresaRepository empresaRepository;
    private final EnderecoService enderecoService;
	
	@Autowired
    public EmpresaService(EmpresaRepository empresaRepository, EnderecoService enderecoService) {
        this.empresaRepository = empresaRepository;
        this.enderecoService = enderecoService;
    }

	@Override
	@Transactional
	public EmpresaDTO criarEmpresa(EmpresaInputDTO empresaInputDTO) {
		// 1. Converte o DTO de Endereço para a entidade e salva
        Endereco novoEndereco = enderecoService.criarEndereco(empresaInputDTO.getEndereco());

        // 2. Converte o DTO de Empresa para a entidade Empresa
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaInputDTO.getCnpj());
        empresa.setNomeFantasia(empresaInputDTO.getNomeFantasia());
        empresa.setEndereco(novoEndereco);

        // 3. Salva a nova empresa e retorna
        return this.converterParaRespostaDTO(empresaRepository.save(empresa));
	}

	@Override
	public EmpresaDTO buscarEmpresaPorId(UUID id) {
		Empresa empresa = this.obterEmpresaPorId(id);
		return new EmpresaDTO(empresa);
	}

	@Override
	public Page<EmpresaDTO> listarEmpresas(Pageable pageable) {
		Page<Empresa> empresas = empresaRepository.findAll(pageable);
		
		return empresas.map(EmpresaDTO::new);
	}

	@Override
	@Transactional
	public EmpresaDTO atualizarEmpresa(UUID id, EmpresaInputDTO empresaInputDTO) {
		Empresa empresaExistente = this.obterEmpresaPorId(id);

        // 1. Atualiza o endereço
        enderecoService.atualizarEndereco(empresaExistente.getEndereco().getId(), empresaInputDTO.getEndereco());

        // 2. Atualiza os dados da empresa
        empresaExistente.setCnpj(empresaInputDTO.getCnpj());
        empresaExistente.setNomeFantasia(empresaInputDTO.getNomeFantasia());

        // 3. Salva a empresa atualizada e retorna
        return this.converterParaRespostaDTO(empresaRepository.save(empresaExistente));
	}

	@Override
	public void deletarEmpresa(UUID id) {
		empresaRepository.deleteById(id);
	}
	
	private Empresa obterEmpresaPorId(UUID id) {
		return empresaRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Empresa não encontrado com ID: " + id));
	}
	
	private EmpresaDTO converterParaRespostaDTO(Empresa empresa) {
		return new EmpresaDTO(empresa);
	}
}
