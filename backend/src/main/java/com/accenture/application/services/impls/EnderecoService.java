package com.accenture.application.services.impls;

import com.accenture.application.domain.dtos.responses.EnderecoDTO;
import com.accenture.application.domain.models.Endereco;
import com.accenture.application.domain.repositories.EnderecoRepository;
import com.accenture.application.services.interfaces.IEnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class EnderecoService implements IEnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
	@Transactional
    public Endereco criarEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco(enderecoDTO);
        return enderecoRepository.save(endereco);
    }

    @Override
	@Transactional
    public Endereco atualizarEndereco(UUID id, EnderecoDTO enderecoDTO) {
        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado com ID: " + id));

        enderecoExistente.setCep(enderecoDTO.getCep());
        enderecoExistente.setRua(enderecoDTO.getRua());
        enderecoExistente.setNumero(enderecoDTO.getNumero());
        enderecoExistente.setBairro(enderecoDTO.getBairro());
        enderecoExistente.setCidade(enderecoDTO.getCidade());
        enderecoExistente.setUf(enderecoDTO.getUf());
        enderecoExistente.setEstado(enderecoDTO.getEstado());
        return enderecoRepository.save(enderecoExistente);
    }
}