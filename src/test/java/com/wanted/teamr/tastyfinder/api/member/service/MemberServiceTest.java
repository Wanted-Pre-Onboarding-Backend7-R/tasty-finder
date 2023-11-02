package com.wanted.teamr.tastyfinder.api.member.service;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.teamr.tastyfinder.api.exception.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    @DisplayName("사용자를 생성할 수 있다.")
    void createMember() {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .build();

        //when
        Long memberId = memberService.createMember(memberCreateRequest);

        //then
        Member findedMember = findMemberById(memberId);
        assertThat(findedMember).isNotNull();
        assertThat(findedMember.getEmail()).isEqualTo(memberCreateRequest.getEmail());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));
    }
}
