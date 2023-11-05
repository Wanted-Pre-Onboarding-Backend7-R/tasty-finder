package com.wanted.teamr.tastyfinder.api.member.controller;

import com.wanted.teamr.tastyfinder.api.auth.domain.MemberAdapter;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import com.wanted.teamr.tastyfinder.api.member.service.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        Long memberId = memberService.createMember(memberCreateRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @PatchMapping("/api/members")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        Long memberId = memberAdapter.getMember().getId();
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
