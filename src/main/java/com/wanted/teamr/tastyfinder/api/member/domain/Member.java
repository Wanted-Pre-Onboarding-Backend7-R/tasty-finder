package com.wanted.teamr.tastyfinder.api.member.domain;

import static com.wanted.teamr.tastyfinder.api.member.domain.Role.ROLE_USER;

import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

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

    private String latitude; //위도

    private String longitude; //경도

    @Column(nullable = false)
    private Boolean isRecommendEnabled = false; //추천 사용 여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    protected Member() {
    }

    @Builder
    private Member(String email, String password, String latitude, String longitude, Boolean isRecommendEnabled) {
        this.email = email;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isRecommendEnabled = (isRecommendEnabled != null) ? isRecommendEnabled : false;
        this.role = (role != null) ? role : ROLE_USER;
    }

    public static Member of(MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        String encodePassword = passwordEncoder.encode(memberCreateRequest.getPassword());
        return Member.builder()
                .email(memberCreateRequest.getEmail())
                .password(encodePassword)
                .build();
    }

    public void update(MemberUpdateRequest memberUpdateRequest) {
        updateLatitude(memberUpdateRequest.getLatitude());
        updateLongitude(memberUpdateRequest.getLongitude());
        updateIsRecommendEnabled(memberUpdateRequest.getIsRecommendEnabled());
    }

    private void updateLatitude(String latitude) {
        if (StringUtils.hasText(latitude)) {
            this.latitude = latitude;
        }
    }

    private void updateLongitude(String longitude) {
        if (StringUtils.hasText(longitude)) {
            this.longitude = longitude;
        }
    }

    private void updateIsRecommendEnabled(Boolean isRecommendEnabled) {
        if (isRecommendEnabled != null) {
            this.isRecommendEnabled = isRecommendEnabled;
        }
    }
}
