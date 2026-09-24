package com.devfelipemilhomes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator
        implements ConstraintValidator<ValidCpf, String> {

    @Override
    public boolean isValid(
            String cpf,
            ConstraintValidatorContext context) {

        if (cpf == null) {
            return true;
        }

        String digits = cpf.replaceAll("\\D", "");

        if (digits.length() != 11) {
            return false;
        }

        if (digits.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(
                digits.substring(0, 9),
                10
        );

        int secondDigit = calculateDigit(
                digits.substring(0, 9) + firstDigit,
                11
        );

        return firstDigit == Character.getNumericValue(digits.charAt(9))
                && secondDigit == Character.getNumericValue(digits.charAt(10));
    }

    private int calculateDigit(
            String digits,
            int initialWeight) {

        int sum = 0;

        for (int i = 0; i < digits.length(); i++) {

            int digit = Character.getNumericValue(
                    digits.charAt(i)
            );

            int weight = initialWeight - i;

            sum += digit * weight;
        }

        int remainder = sum % 11;

        if (remainder < 2) {
            return 0;
        }

        return 11 - remainder;
    }
}
