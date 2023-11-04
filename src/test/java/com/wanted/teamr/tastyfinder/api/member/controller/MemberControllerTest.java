package com.wanted.teamr.tastyfinder.api.member.controller;

import static com.wanted.teamr.tastyfinder.api.exception.ErrorCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberCreateRequest;
import com.wanted.teamr.tastyfinder.api.member.dto.MemberUpdateRequest;
import com.wanted.teamr.tastyfinder.api.member.repository.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
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
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Long memberId;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .email(testEmail)
                .password(passwordEncoder.encode(testPassword))
                .build();
        memberId = memberRepository.save(member).getId();
    }

    @Test
    @WithMockUser
    @DisplayName("사용자를 생성할 수 있다.")
    void createMember() throws Exception {
        //given
        String email = "test1@test.com";
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(email)
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(MEMBER_EMAIL_INVALID.name()));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(MEMBER_EMAIL_EMPTY.name()));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(MEMBER_PASSWORD_EMPTY.name()));
    }

    @Test
    @WithUserDetails(value = testEmail, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("사용자 정보를 수정할 수 있다.")
    void updateMember() throws Exception {
        //given
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                .latitude("35.1111")
                .longitude("125.1111")
                .isRecommendEnabled(true)
                .build();

        //when then
        mockMvc.perform(patch(requestUri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = testEmail, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("잘못된 위도로 사용자 정보를 수정할 수 없다.")
    void updateMember_invalidLatitude() throws Exception {
        //given
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                .latitude("38.62")
                .longitude("125.1111")
                .build();

        //when then
        mockMvc.perform(patch(requestUri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(MEMBER_LATITUDE_INVALID.name()));
    }

    @Test
    @WithUserDetails(value = testEmail, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("잘못된 경도로 사용자 정보를 수정할 수 없다.")
    void updateMember_invalidLongitude() throws Exception {
        //given
        MemberUpdateRequest memberUpdateRequest = MemberUpdateRequest.builder()
                .latitude("35.1111")
                .longitude("200.1111")
                .build();

        //when then
        mockMvc.perform(patch(requestUri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(MEMBER_LONGITUDE_INVALID.name()));
    }

    @Test
    @WithUserDetails(value = testEmail, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("잘못된 추천 사용 여부로 사용자 정보를 수정할 수 없다.")
    void updateMember_invalidIsRecommendEnabled() throws Exception {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("isRecommendEnabled", "ttt");

        //when then
        mockMvc.perform(patch(requestUri)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
