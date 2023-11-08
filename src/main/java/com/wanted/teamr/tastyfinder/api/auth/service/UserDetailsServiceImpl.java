package com.wanted.teamr.tastyfinder.api.auth.service;

import static com.wanted.teamr.tastyfinder.api.exception.domain.ErrorCode.AUTH_MEMBER_NOT_EXISTS;

import com.wanted.teamr.tastyfinder.api.auth.domain.MemberAdapter;
import com.wanted.teamr.tastyfinder.api.exception.domain.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(AUTH_MEMBER_NOT_EXISTS));
        return MemberAdapter.from(member);
    }
}
