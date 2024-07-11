package com.example.shop_sv.util.validator.animationCustom;

import com.example.shop_sv.modules.users.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {
    @Autowired
    private UserRepository userRepository;

    private String fieldName;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return switch (fieldName) {
            case "username" -> !userRepository.existsByUsername(value);
            case "email" -> !userRepository.existsByEmail(value);
            default -> throw new IllegalArgumentException("Invalid field name: " + fieldName);
        };
    }
}
