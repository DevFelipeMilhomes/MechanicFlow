package com.devfelipemilhomes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(
        {
                ElementType.FIELD,
                ElementType.PARAMETER,
                ElementType.RECORD_COMPONENT
        }
)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfValidator.class)
public @interface ValidCpf {

    String message() default "Invalid CPF";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
