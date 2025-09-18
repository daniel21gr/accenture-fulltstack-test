package com.accenture.application.validations.impls;

import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.models.TipoFornecedor;
import com.accenture.application.validations.interfaces.IValidarFornecedorPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class ValidarFornecedorPFValidator implements ConstraintValidator<IValidarFornecedorPF, FornecedorInputDTO> {

    @Override
    public boolean isValid(FornecedorInputDTO fornecedor, ConstraintValidatorContext context) {
        if (fornecedor.getTipoFornecedor() == TipoFornecedor.PESSOA_FISICA) {
            boolean rgValido = StringUtils.hasText(fornecedor.getRg());
            boolean dataNascimentoValida = fornecedor.getDataNascimento() != null;

            if (!rgValido || !dataNascimentoValida) {
                // Se algum campo não for válido, personaliza a mensagem de erro.
                context.disableDefaultConstraintViolation();
                if (!rgValido) {
                    context.buildConstraintViolationWithTemplate("RG é obrigatório para Pessoa Física.")
                           .addPropertyNode("rg")
                           .addConstraintViolation();
                }
                if (!dataNascimentoValida) {
                    context.buildConstraintViolationWithTemplate("Data de nascimento é obrigatória para Pessoa Física.")
                           .addPropertyNode("dataNascimento")
                           .addConstraintViolation();
                }
                return false;
            }
        }
        return true;
    }
}