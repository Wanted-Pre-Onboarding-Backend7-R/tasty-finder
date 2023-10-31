package com.wanted.teamr.tastyfinder.api.member.domain;

import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Float latitude; //위도

    private Float longitude; //경도

    @Column(nullable = false)
    private boolean isRecommendEnabled = false;

    protected Member() {
    }

    @Builder
    private Member(String email, String password, Float latitude, Float longitude, Boolean isRecommendEnabled) {
        this.email = email;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isRecommendEnabled = isRecommendEnabled != null ? isRecommendEnabled : false;
    }

    public static Member from(MemberCreateRequest memberCreateRequest) {
        return Member.builder()
                .email(memberCreateRequest.getEmail())
                .password(memberCreateRequest.getPassword())
                .build();
    }
}
