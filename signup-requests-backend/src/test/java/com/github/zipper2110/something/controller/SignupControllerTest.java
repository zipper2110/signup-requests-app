package com.github.zipper2110.something.controller;

import com.github.zipper2110.something.service.SignupRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignupController.class)
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignupRequestService service;

    @Test
    public void save_valid_request() throws Exception {

        String validSignupRequest = "{" +
                "\"username\": \"hey\", " +
                "\"password\": \"wow_such_strong\"," +
                "\"fullName\": \"Full user name\"," +
                "\"email\": \"email@test.com\"" +
                "}";

        // TODO: mock service "create" method to return normally
//        given(service.create())

        mockMvc.perform(post("/signup")
                .content(validSignupRequest)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}