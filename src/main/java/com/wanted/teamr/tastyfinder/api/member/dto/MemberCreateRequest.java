package com.wanted.teamr.tastyfinder.api.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCreateRequest {

    @NotBlank(message = "MEMBER_EMAIL_EMPTY")
    @Email(message = "MEMBER_EMAIL_INVALID",
            regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank(message = "MEMBER_PASSWORD_EMPTY")
    private String password;
}
