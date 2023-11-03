package com.wanted.teamr.tastyfinder.api.member.service;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberServiceTest {

    private final String testEmail = "test@test.com";
    private final String testPassword = "12345678";

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long memberId;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email(testEmail)
                .password(passwordEncoder.encode(testPassword))
                .build();
        memberId = memberRepository.save(member).getId();
    }

    @Test
    @DisplayName("사용자를 생성할 수 있다.")
    void createMember() {
        //given
        String email = "test1@test.com";
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(email)
                .password(testPassword)
                .build();

        //when
        memberId = memberService.createMember(memberCreateRequest);

        //then
        Member actualMember = findMemberById(memberId);
        assertThat(actualMember).isNotNull();
        assertThat(actualMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("사용자 정보를 수정할 수 있다.")
    void updateMember() {
        //given
        String latitude = "35.1111";
        String longitude = "125.1111";
        Boolean isRecommendEnabled = false;
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                .latitude(latitude)
                .longitude(longitude)
                .isRecommendEnabled(isRecommendEnabled)
                .build();

        //when
        memberService.updateMember(memberId, memberUpdateRequest);

        //then
        Member actualMember = findMemberById(memberId);
        assertThat(actualMember).isNotNull();
        assertThat(actualMember.getLatitude()).isEqualTo(latitude);
        assertThat(actualMember.getLongitude()).isEqualTo(longitude);
        assertThat(actualMember.getIsRecommendEnabled()).isEqualTo(isRecommendEnabled);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));
    }
}
