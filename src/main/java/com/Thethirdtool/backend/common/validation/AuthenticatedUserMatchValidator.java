package com.Thethirdtool.backend.common.validation;


import com.Thethirdtool.backend.common.annotation.AuthenticatedUserMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AuthenticatedUserMatchValidator implements ConstraintValidator<AuthenticatedUserMatch, AuthValidationDto> {
    @Override
    public boolean isValid(AuthValidationDto dto, ConstraintValidatorContext context) {
        if (dto == null) return false;
        return dto.getPathUserId().equals(dto.getAuthenticatedUserId());
    }
}