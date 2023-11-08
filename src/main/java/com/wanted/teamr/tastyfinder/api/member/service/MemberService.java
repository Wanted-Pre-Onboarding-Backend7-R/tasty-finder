package com.wanted.teamr.tastyfinder.api.member.service;

import static com.wanted.teamr.tastyfinder.api.exception.domain.ErrorCode.*;

import com.wanted.teamr.tastyfinder.api.exception.domain.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberGetResponse;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createMember(MemberCreateRequest memberCreateRequest) {
        Member member = Member.of(memberCreateRequest, passwordEncoder);
        return memberRepository.save(member).getId();
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        Member member = findMemberById(memberId);
        member.update(memberUpdateRequest);
    }

    @Transactional(readOnly = true)
    public MemberGetResponse getMember(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberGetResponse.of(member);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_EXISTS));
    }
}
