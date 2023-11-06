package com.wanted.teamr.tastyfinder.api.member.dto;

import com.wanted.teamr.tastyfinder.api.member.validation.Latitude;
import com.wanted.teamr.tastyfinder.api.member.validation.Longitude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateRequest {

    @Latitude(message = "MEMBER_LATITUDE_INVALID")
    private String latitude; //위도

    @Longitude(message = "MEMBER_LONGITUDE_INVALID")
    private String longitude; //경도

    private Boolean isRecommendEnabled; //점심 추천 기능 사용 여부
}
