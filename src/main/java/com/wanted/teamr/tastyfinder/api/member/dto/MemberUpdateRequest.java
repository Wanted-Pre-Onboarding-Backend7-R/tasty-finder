package com.wanted.teamr.tastyfinder.api.member.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateRequest {

    @DecimalMax("38.61") //대한민국 최북단 북위 38.61
    @DecimalMin("33.11") //대한민국 최남단 북위 33.11
    private BigDecimal latitude; //위도

    @DecimalMax("131.87") //대한민국 최동단 동경 131.87
    @DecimalMin("124.60") //대한민국 최서단 동경 124.60
    private BigDecimal longitude; //경도

    private Boolean isRecommendEnabled; //점심 추천 기능 사용 여부
}
