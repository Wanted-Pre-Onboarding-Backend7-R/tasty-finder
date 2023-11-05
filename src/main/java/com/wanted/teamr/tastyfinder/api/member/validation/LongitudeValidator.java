package com.wanted.teamr.tastyfinder.api.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import org.springframework.util.StringUtils;

public class LongitudeValidator implements ConstraintValidator<Longitude, String> {

    public static final BigDecimal LONGITUDE_MAX = new BigDecimal("131.87"); //대한민국 최동단 동경 131.87
    public static final BigDecimal LONGITUDE_MIN = new BigDecimal("124.60"); //대한민국 최서단 동경 124.60

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }

        BigDecimal longitude = new BigDecimal(value);
        return longitude.compareTo(LONGITUDE_MAX) <= 0 && longitude.compareTo(LONGITUDE_MIN) >= 0;
    }
}
