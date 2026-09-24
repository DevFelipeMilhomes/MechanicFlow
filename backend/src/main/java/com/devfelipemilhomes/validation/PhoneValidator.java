package com.devfelipemilhomes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context){
        if (phone == null) {
            return true;
        }

        String digits = phone.replaceAll("\\D", "");

        if (digits.length() != 10 && digits.length() != 11) {
            return false;
        }

        if (digits.length() == 11 && digits.charAt(2) != '9') {
            return false;
        }

        return true;
    }
}
