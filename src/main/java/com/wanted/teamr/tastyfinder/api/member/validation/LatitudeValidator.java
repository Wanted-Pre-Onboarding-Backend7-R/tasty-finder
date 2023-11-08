package com.wanted.teamr.tastyfinder.api.member.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import org.springframework.util.StringUtils;

public class LatitudeValidator implements ConstraintValidator<Latitude, String> {

    public static final BigDecimal LATITUDE_MAX = new BigDecimal("38.61"); //대한민국 최북단 북위 38.61
    public static final BigDecimal LATITUDE_MIN = new BigDecimal("33.11"); //대한민국 최남단 북위 33.11

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        BigDecimal latitude;
        try {
            latitude = new BigDecimal(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return latitude.compareTo(LATITUDE_MAX) <= 0 && latitude.compareTo(LATITUDE_MIN) >= 0;
    }
}
