package com.accenture.application.validations.impls;

import com.accenture.application.domain.dtos.responses.ViaCepDTO;
import com.accenture.application.services.impls.ViaCepService;
import com.accenture.application.validations.interfaces.IValidarCEP;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCEPValidator implements ConstraintValidator<IValidarCEP, String> {

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        if (cep == null || cep.isBlank()) {
            return true; // A validação @NotBlank já cuida disso
        }

        Optional<ViaCepDTO> response = viaCepService.buscarCep(cep);
        return response != null;
    }
}