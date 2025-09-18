package com.accenture.application.validations.impls;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.accenture.application.validations.interfaces.IValidarCNPJ;

@Service
public class ValidarCNPJValidator implements ConstraintValidator<IValidarCNPJ, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        // Validação básica de nulo ou vazio
        if (!StringUtils.hasText(cnpj)) {
            return true; // A anotação @NotBlank já lida com campos obrigatórios
        }

        // Remove caracteres não numéricos
        cnpj = cnpj.replaceAll("[^0-9]", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpj.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Lógica de validação dos dígitos verificadores
        try {
            int d1, d2;
            int digito1, digito2;
            int sum = 0;
            String tempCNPJ;

            int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            tempCNPJ = cnpj.substring(0, 12);
            for (int i = 0; i < 12; i++) {
                sum += Integer.parseInt(tempCNPJ.substring(i, i + 1)) * weights1[i];
            }
            d1 = 11 - (sum % 11);
            if (d1 > 9) d1 = 0;

            sum = 0;
            tempCNPJ = cnpj.substring(0, 13);
            for (int i = 0; i < 13; i++) {
                sum += Integer.parseInt(tempCNPJ.substring(i, i + 1)) * weights2[i];
            }
            d2 = 11 - (sum % 11);
            if (d2 > 9) d2 = 0;

            digito1 = Integer.parseInt(cnpj.substring(12, 13));
            digito2 = Integer.parseInt(cnpj.substring(13, 14));

            return (d1 == digito1) && (d2 == digito2);
        } catch (Exception e) {
            return false;
        }
    }
}