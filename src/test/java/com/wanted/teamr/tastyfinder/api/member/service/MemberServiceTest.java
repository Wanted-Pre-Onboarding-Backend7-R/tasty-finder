package com.wanted.teamr.tastyfinder.api.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Test
    @DisplayName("잘못된 이메일로 사용자를 생성할 수 없다.")
    void createMember_invalidEmail() {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email("invalidTest.com")
                .password(testPassword)
                .build();

        //when then
        assertThatThrownBy(() -> memberService.createMember(memberCreateRequest))
                //TODO: exception 처리 수정
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("이메일 없이 사용자를 생성할 수 없다.")
    void createMember_emptyEmail() {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .password(testPassword)
                .build();

        //when then
        assertThatThrownBy(() -> memberService.createMember(memberCreateRequest))
                //TODO: exception 처리 수정
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("비밀번호 없이 사용자를 생성할 수 없다.")
    void createMember_emptyPassword() {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(testEmail)
                .build();

        //when then
        assertThatThrownBy(() -> memberService.createMember(memberCreateRequest))
                //TODO: exception 처리 수정
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                //TODO: exception 처리 수정
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
