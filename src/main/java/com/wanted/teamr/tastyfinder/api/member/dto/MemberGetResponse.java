package com.wanted.teamr.tastyfinder.api.member.dto;

import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberGetResponse {

    private final Long id;
    private final String email;
    private final String latitude;
    private final String longitude;
    private final Boolean isRecommendEnabled;
    private final Role role;

    @Builder
    private MemberGetResponse(Long id, String email, String latitude, String longitude, Boolean isRecommendEnabled, Role role) {
        this.id = id;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isRecommendEnabled = isRecommendEnabled;
        this.role = role;
    }

    public static MemberGetResponse of(Member member) {
        return MemberGetResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .latitude(member.getLatitude())
                .longitude(member.getLongitude())
                .isRecommendEnabled(member.getIsRecommendEnabled())
                .role(member.getRole())
                .build();
    }
}
