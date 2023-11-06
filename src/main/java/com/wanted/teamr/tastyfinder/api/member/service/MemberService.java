package com.wanted.teamr.tastyfinder.api.member.service;

import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
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

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
