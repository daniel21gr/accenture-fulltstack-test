package com.accenture.application.services.impls;

import com.accenture.application.domain.dtos.filters.FornecedorFiltroDTO;
import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.dtos.responses.FornecedorDTO;
import com.accenture.application.domain.models.Empresa;
import com.accenture.application.domain.models.Endereco;
import com.accenture.application.domain.models.Fornecedor;
import com.accenture.application.domain.models.TipoFornecedor;
import com.accenture.application.domain.repositories.EmpresaRepository;
import com.accenture.application.domain.repositories.FornecedorRepository;
import com.accenture.application.domain.specifications.FornecedorSpecification;
import com.accenture.application.services.interfaces.IFornecedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.UUID;

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
        
        // 2. Remove a máscara do documento
        String documentoLimpo = this.limparDocumentoCPF(fornecedorInputDTO.getDocumento());

        // 3. Converte o DTO para a entidade Fornecedor
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setDocumento(documentoLimpo);
        fornecedor.setTipoFornecedor(fornecedorInputDTO.getTipoFornecedor());
        fornecedor.setNome(fornecedorInputDTO.getNome());
        fornecedor.setEmail(fornecedorInputDTO.getEmail());
        fornecedor.setRg(fornecedorInputDTO.getRg());
        fornecedor.setDataNascimento(fornecedorInputDTO.getDataNascimento());
        fornecedor.setEndereco(novoEndereco);

        // 4. Salva o fornecedor e retorna
        return this.converterParaRespostaDTO(fornecedorRepository.save(fornecedor));
    }

    @Override
    public FornecedorDTO buscarFornecedorPorId(UUID id) {
    	Fornecedor fornecedor = this.obterFornecedorPorId(id);
        return new FornecedorDTO(fornecedor);
    }

    @Override
    public Page<FornecedorDTO> listarFornecedores(FornecedorFiltroDTO filtro, Pageable pageable) {
    	Specification<Fornecedor> spec = FornecedorSpecification.byFilter(filtro);
    	Page<Fornecedor> fornecedores = fornecedorRepository.findAll(spec, pageable);
        return fornecedores.map(FornecedorDTO::new);
    }

    @Override
	@Transactional
    public FornecedorDTO atualizarFornecedor(UUID id, FornecedorInputDTO fornecedorInputDTO) {
        Fornecedor fornecedorExistente = this.obterFornecedorPorId(id);

        // 1. Atualiza o endereço usando o EnderecoService
        enderecoService.atualizarEndereco(fornecedorExistente.getEndereco().getId(), fornecedorInputDTO.getEndereco());
        
        // 2. Remove a máscara do documento
        String documentoLimpo = this.limparDocumentoCPF(fornecedorInputDTO.getDocumento());

        // 2. Atualiza os dados do fornecedor
        fornecedorExistente.setDocumento(documentoLimpo);
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
    
    @Override
    @Transactional
    public FornecedorDTO vincularFornecedorAEmpresa(UUID fornecedorId, UUID empresaId) {
        Fornecedor fornecedor = this.obterFornecedorPorId(fornecedorId);
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada com ID: " + empresaId));

        // Adiciona a empresa ao fornecedor, se ainda não estiver associada
        if (this.validarAssociacaoComEmpresaPR(fornecedor, empresa) &&
        		!fornecedor.getEmpresas().contains(empresa)) {
            fornecedor.getEmpresas().add(empresa);
            empresa.getFornecedores().add(fornecedor);
        }

        return this.converterParaRespostaDTO(fornecedorRepository.save(fornecedor));
    }

    @Override
    @Transactional
    public Fornecedor desvincularFornecedorDeEmpresa(UUID fornecedorId, UUID empresaId) {
        Fornecedor fornecedor = this.obterFornecedorPorId(fornecedorId);
        Empresa empresa = empresaRepository.findById(empresaId)
            .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com ID: " + empresaId));

        fornecedor.getEmpresas().remove(empresa);
        empresa.getFornecedores().remove(fornecedor);
        
        return fornecedorRepository.save(fornecedor);
    }
    
    private String limparDocumentoCPF(String documento) {
    	if (documento != null) {
    		return documento.replaceAll("[^0-9]", "");
    	}
    	return null;
    }
    
    private Fornecedor obterFornecedorPorId(UUID id) {
    	return fornecedorRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado com ID: " + id));
    }
    
    private FornecedorDTO converterParaRespostaDTO(Fornecedor fornecedor) {
    	return new FornecedorDTO(fornecedor);
    }
    
    // Método que implementa a regra de negócio do Paraná
    private boolean validarAssociacaoComEmpresaPR(Fornecedor fornecedor, Empresa empresa) {
    	if (fornecedor.getTipoFornecedor() != TipoFornecedor.PESSOA_FISICA) {
    		return true;
    	}
    	
    	if (!"PR".equalsIgnoreCase(empresa.getEndereco().getUf())) {
    		return true;
    	}
    	
    	LocalDate dataNascimentoLocal = fornecedor.getDataNascimento();
        Integer idade = Period.between(dataNascimentoLocal, LocalDate.now()).getYears();

        if (idade < 18) {
            throw new NoSuchElementException("Não é permitido cadastrar fornecedor menor de idade para empresas do Paraná.");
        }
        
        return true;
    }
}