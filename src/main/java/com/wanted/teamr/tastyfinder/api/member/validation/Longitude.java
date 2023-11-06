package com.wanted.teamr.tastyfinder.api.member.validation;

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
@Constraint(validatedBy = LongitudeValidator.class)
public @interface Longitude {

    String message() default "invalid longitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
