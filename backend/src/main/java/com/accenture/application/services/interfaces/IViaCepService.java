package com.accenture.application.services.interfaces;

import java.util.Optional;

import com.accenture.application.domain.dtos.responses.ViaCepDTO;

public interface IViaCepService {
	public Optional<ViaCepDTO> buscarCep(String cep);
}