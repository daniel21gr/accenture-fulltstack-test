package com.accenture.application.services.impls;

import com.accenture.application.domain.dtos.responses.ViaCepDTO;
import com.accenture.application.services.interfaces.IViaCepService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ViaCepService implements IViaCepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws";
    private final WebClient webClient;

    @Autowired
    public ViaCepService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(VIACEP_URL).build();
    }

    public Optional<ViaCepDTO> buscarCep(String cep) {
        try {
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            ViaCepDTO response = this.webClient.get().uri("/{cep}/json", cepLimpo).retrieve()
        		.bodyToMono(ViaCepDTO.class).block();
            
            // A API do ViaCEP retorna um campo "erro" se o CEP não for encontrado
            if (response != null && !response.isErro()) {
                return Optional.of(response);
            }
        } catch (Exception e) {
            return null; // Em caso de qualquer erro na requisição
        }
        
        return null;
    }
}