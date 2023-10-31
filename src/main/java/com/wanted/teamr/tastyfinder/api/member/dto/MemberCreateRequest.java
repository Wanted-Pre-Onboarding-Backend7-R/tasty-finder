package com.wanted.teamr.tastyfinder.api.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCreateRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
