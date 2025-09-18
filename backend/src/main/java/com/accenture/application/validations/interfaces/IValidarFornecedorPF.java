package com.accenture.application.validations.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.accenture.application.validations.impls.ValidarFornecedorPFValidator;

@Documented
@Constraint(validatedBy = ValidarFornecedorPFValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidarFornecedorPF {
    String message() default "RG e data de nascimento são obrigatórios para Pessoa Física.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}