package com.accenture.application.validations.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.accenture.application.validations.impls.ValidarCEPValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidarCEPValidator.class)
public @interface IValidarCEP {
    String message() default "CEP inválido ou não encontrado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}