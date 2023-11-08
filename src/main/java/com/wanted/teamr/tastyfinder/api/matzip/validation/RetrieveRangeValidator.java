package com.wanted.teamr.tastyfinder.api.matzip.validation;

import com.wanted.teamr.tastyfinder.api.member.validation.Latitude;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class RetrieveRangeValidator implements ConstraintValidator<RetrieveRange, String> {

    public static final BigDecimal MIN_RANGE = new BigDecimal("0.0"); // 음수 방지
    public static final BigDecimal MAX_RANGE = new BigDecimal("30.00"); // 임의로 max 설정

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        BigDecimal range;

        try {
            range = new BigDecimal(value);
        } catch (NumberFormatException ex) {
            return false;
        }

        // bigdecimal 단순히 min <= x && x <= max 부등호 비교가 안됨
        // a.compareTo(b) < 0 : a가 b보다 작다, a.compareTo(b) > 0 : a가 b보다 크다
        // min <= x && x <= max와 같음
        return MIN_RANGE.compareTo(range) <= 0 && range.compareTo(MAX_RANGE) <= 0;
    }
}

