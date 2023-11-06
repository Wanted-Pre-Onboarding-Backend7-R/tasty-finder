package com.wanted.teamr.tastyfinder.api.member.controller;

import com.wanted.teamr.tastyfinder.api.auth.domain.MemberAdapter;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberGetResponse;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import com.wanted.teamr.tastyfinder.api.member.service.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/members/{memberId}")
    public ResponseEntity<MemberGetResponse> getMember(@PathVariable Long memberId) {
        MemberGetResponse memberGetResponse = memberService.getMember(memberId);
        return ResponseEntity.ok(memberGetResponse);
    }

    @PatchMapping("/api/members")
    public ResponseEntity<Void> updateMember(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                             @RequestBody @Valid MemberUpdateRequest memberUpdateRequest) {
        Long memberId = memberAdapter.getMember().getId();
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
