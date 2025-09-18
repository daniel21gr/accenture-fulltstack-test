package com.accenture.application.validations.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.accenture.application.validations.impls.ValidarFornecedorDocumentoValidator;

@Documented
@Constraint(validatedBy = ValidarFornecedorDocumentoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidarFornecedorDocumento {
    String message() default "Documento inválido para o tipo de fornecedor.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}