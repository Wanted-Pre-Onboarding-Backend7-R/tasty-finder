package com.wanted.teamr.tastyfinder.api.member.service;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.AUTH_MEMBER_NOT_EXISTS;
import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.MEMBER_NOT_EXISTS;
import static com.wanted.teamr.tastyfinder.api.member.domain.Role.ROLE_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.domain.Role;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberGetResponse;
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

    private final String email = "test@test.com";
    private final String password = "12345678";
    private final String latitude = "35.11";
    private final String longitude = "125.11";
    private final Boolean isRecommendEnabled = true;
    private final Role role = ROLE_ADMIN;

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
                .email(email)
                .password(passwordEncoder.encode(password))
                .latitude(latitude)
                .longitude(longitude)
                .isRecommendEnabled(isRecommendEnabled)
                .role(role)
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
                .password(password)
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

    @Test
    @DisplayName("id로 사용자를 조회할 수 있다.")
    void getMember() {
        //when
        MemberGetResponse memberGetResponse = memberService.getMember(memberId);

        //then
        assertThat(memberGetResponse).isNotNull();
        assertThat(memberGetResponse.getId()).isEqualTo(memberId);
        assertThat(memberGetResponse.getEmail()).isEqualTo(email);
        assertThat(memberGetResponse.getLatitude()).isEqualTo(latitude);
        assertThat(memberGetResponse.getLongitude()).isEqualTo(longitude);
        assertThat(memberGetResponse.getIsRecommendEnabled()).isEqualTo(isRecommendEnabled);
        assertThat(memberGetResponse.getRole()).isEqualTo(ROLE_ADMIN);
    }

    @Test
    @DisplayName("존재하지 않는 id로 사용자를 조회할 수 없다.")
    void getMember_invalidId() {
        //when then
        assertThatThrownBy(() -> memberService.getMember(99L))
                .isInstanceOf(CustomException.class)
                .hasMessage(MEMBER_NOT_EXISTS.getMessage());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(AUTH_MEMBER_NOT_EXISTS));
    }
}
