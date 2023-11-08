package com.wanted.teamr.tastyfinder.api.matzip.validation;

import com.wanted.teamr.tastyfinder.api.member.validation.LatitudeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RetrieveRangeValidator.class)
public @interface RetrieveRange {

    String message() default "invalid range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

