package com.Thethirdtool.backend.common.annotation;

import com.Thethirdtool.backend.common.validation.AuthenticatedUserMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AuthenticatedUserMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedUserMatch {
    String message() default "접근 권한이 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}