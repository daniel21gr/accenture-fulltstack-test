package com.accenture.application.services.interfaces;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.accenture.application.domain.dtos.responses.EnderecoDTO;
import com.accenture.application.domain.models.Endereco;

public interface IEnderecoService {

	Endereco criarEndereco(EnderecoDTO enderecoDTO);

	Endereco atualizarEndereco(UUID id, EnderecoDTO enderecoDTO);

}