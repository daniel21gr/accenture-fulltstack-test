package com.accenture.application.validations.impls;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.accenture.application.validations.interfaces.IValidarCPF;

@Service
public class ValidarCPFValidator implements ConstraintValidator<IValidarCPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        // Validação básica de nulo ou vazio
        if (!StringUtils.hasText(cpf)) {
            return true; // A anotação @NotBlank já lida com campos obrigatórios
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Lógica de validação dos dígitos verificadores
        try {
            int d1, d2;
            int digito1, digito2, resto;
            int sum = 0;
            String tempCPF;
            
            tempCPF = cpf.substring(0, 9);
            for (int i = 0; i < 9; i++) {
                sum += (10 - i) * Integer.parseInt(tempCPF.substring(i, i + 1));
            }
            d1 = 11 - (sum % 11);
            if (d1 > 9) d1 = 0;

            sum = 0;
            tempCPF = cpf.substring(0, 10);
            for (int i = 0; i < 10; i++) {
                sum += (11 - i) * Integer.parseInt(tempCPF.substring(i, i + 1));
            }
            d2 = 11 - (sum % 11);
            if (d2 > 9) d2 = 0;

            digito1 = Integer.parseInt(cpf.substring(9, 10));
            digito2 = Integer.parseInt(cpf.substring(10, 11));

            return (d1 == digito1) && (d2 == digito2);
        } catch (Exception e) {
            return false;
        }
    }
}