package com.wanted.teamr.tastyfinder.api.member.dto;

import com.wanted.teamr.tastyfinder.api.member.validation.Latitude;
import com.wanted.teamr.tastyfinder.api.member.validation.Longitude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateRequest {

    @Latitude
    private String latitude; //위도

    @Longitude
    private String longitude; //경도

    private Boolean isRecommendEnabled; //점심 추천 기능 사용 여부
}
