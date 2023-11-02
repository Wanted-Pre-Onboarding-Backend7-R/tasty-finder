package com.wanted.teamr.tastyfinder.api.member.domain;

import static com.wanted.teamr.tastyfinder.api.member.domain.Role.ROLE_USER;

import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
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
    private boolean isRecommendEnabled;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    protected Member() {
    }

    @Builder
    private Member(String email, String password, Float latitude, Float longitude, Boolean isRecommendEnabled, Role role) {
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
}
