package com.wanted.teamr.tastyfinder.api.member.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    private final String testEmail = "test@test.com";
    private final String testPassword = "12345678";
    private final String requestUri = "/api/members";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("사용자를 생성할 수 있다.")
    void createMember() throws Exception {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .build();

        //when then
        mockMvc.perform(post(requestUri)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    @DisplayName("잘못된 이메일로 사용자를 생성할 수 없다.")
    void createMember_invalidEmail() throws Exception {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email("test.com")
                .password(testPassword)
                .build();

        //when then
        mockMvc.perform(post(requestUri)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                )
                .andDo(print())
                //TODO: exception 처리 수정
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("이메일 없이 사용자를 생성할 수 없다.")
    void createMember_emptyEmail() throws Exception {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .password(testPassword)
                .build();

        //when then
        mockMvc.perform(post(requestUri)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                )
                .andDo(print())
                //TODO: exception 처리 수정
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("비밀번호 없이 사용자를 생성할 수 없다.")
    void createMember_emptyPassword() throws Exception {
        //given
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(testEmail)
                .build();

        //when then
        mockMvc.perform(post(requestUri)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                )
                .andDo(print())
                //TODO: exception 처리 수정
                .andExpect(status().isBadRequest());
    }
}
