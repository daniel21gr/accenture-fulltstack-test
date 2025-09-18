package com.accenture.application.validations.interfaces;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.accenture.application.validations.impls.ValidarCPFValidator;

@Documented
@Constraint(validatedBy = ValidarCPFValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IValidarCPF {
    String message() default "CPF inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}