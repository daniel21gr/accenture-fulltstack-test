package com.accenture.application.services.impls;

import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.dtos.responses.FornecedorDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.domain.models.Endereco;
import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.domain.repositories.EmpresaRepository;
import com.accenture.application.domain.repositories.FornecedorRepository;
import com.accenture.application.services.interfaces.IFornecedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FornecedorService implements IFornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final EmpresaRepository empresaRepository;
    private final EnderecoService enderecoService;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository, EmpresaRepository empresaRepository, EnderecoService enderecoService) {
        this.fornecedorRepository = fornecedorRepository;
        this.empresaRepository = empresaRepository;
        this.enderecoService = enderecoService;
    }

    @Override
	@Transactional
    public FornecedorDTO criarFornecedor(FornecedorInputDTO fornecedorInputDTO) {
        // 1. Usa o EnderecoService para criar o Endereco
        Endereco novoEndereco = enderecoService.criarEndereco(fornecedorInputDTO.getEndereco());

        // 2. Converte o DTO para a entidade Fornecedor
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setDocumento(fornecedorInputDTO.getDocumento());
        fornecedor.setTipoFornecedor(fornecedorInputDTO.getTipoFornecedor());
        fornecedor.setNome(fornecedorInputDTO.getNome());
        fornecedor.setEmail(fornecedorInputDTO.getEmail());
        fornecedor.setRg(fornecedorInputDTO.getRg());
        fornecedor.setDataNascimento(fornecedorInputDTO.getDataNascimento());
        fornecedor.setEndereco(novoEndereco);

        // 3. Salva o fornecedor e retorna
        return this.converterParaRespostaDTO(fornecedorRepository.save(fornecedor));
    }

    @Override
    public Fornecedor buscarFornecedorPorId(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado com ID: " + id));
    }

    @Override
    public Page<Fornecedor> listarFornecedores(Pageable pageable) {
        return fornecedorRepository.findAll(pageable);
    }

    @Override
	@Transactional
    public FornecedorDTO atualizarFornecedor(UUID id, FornecedorInputDTO fornecedorInputDTO) {
        Fornecedor fornecedorExistente = buscarFornecedorPorId(id);

        // 1. Atualiza o endereço usando o EnderecoService
        enderecoService.atualizarEndereco(fornecedorExistente.getEndereco().getId(), fornecedorInputDTO.getEndereco());

        // 2. Atualiza os dados do fornecedor
        fornecedorExistente.setDocumento(fornecedorInputDTO.getDocumento());
        fornecedorExistente.setTipoFornecedor(fornecedorInputDTO.getTipoFornecedor());
        fornecedorExistente.setNome(fornecedorInputDTO.getNome());
        fornecedorExistente.setEmail(fornecedorInputDTO.getEmail());
        fornecedorExistente.setRg(fornecedorInputDTO.getRg());
        fornecedorExistente.setDataNascimento(fornecedorInputDTO.getDataNascimento());

        // 3. Salva o fornecedor atualizado
        return this.converterParaRespostaDTO(fornecedorRepository.save(fornecedorExistente));
    }

    @Override
    public void deletarFornecedor(UUID id) {
        fornecedorRepository.deleteById(id);
    }
    
    private FornecedorDTO converterParaRespostaDTO(Fornecedor fornecedor) {
    	return new FornecedorDTO(fornecedor);
    }
}