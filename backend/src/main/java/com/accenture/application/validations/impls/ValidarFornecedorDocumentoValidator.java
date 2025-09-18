package com.accenture.application.validations.impls;

import com.accenture.application.domain.dtos.inputs.FornecedorInputDTO;
import com.accenture.application.domain.models.TipoFornecedor;
import com.accenture.application.validations.interfaces.IValidarFornecedorDocumento;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class ValidarFornecedorDocumentoValidator implements ConstraintValidator<IValidarFornecedorDocumento, FornecedorInputDTO> {

    @Autowired
    private ValidarCPFValidator cpfValidator;
    @Autowired
    private ValidarCNPJValidator cnpjValidator;

    @Override
    public boolean isValid(FornecedorInputDTO fornecedor, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(fornecedor.getDocumento())) {
            return true; // A validação de @NotBlank já cuida disso
        }

        if (fornecedor.getTipoFornecedor() == TipoFornecedor.PESSOA_FISICA) {
            if (!cpfValidator.isValid(fornecedor.getDocumento(), context)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Documento inválido para Pessoa Física (CPF).")
                       .addPropertyNode("documento")
                       .addConstraintViolation();
                return false;
            }
        } else if (fornecedor.getTipoFornecedor() == TipoFornecedor.PESSOA_JURIDICA) {
            if (!cnpjValidator.isValid(fornecedor.getDocumento(), context)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Documento inválido para Pessoa Jurídica (CNPJ).")
                       .addPropertyNode("documento")
                       .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}