package com.github.zipper2110.something;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateSignupRequest() throws Exception {
        String validSignupRequest = "{" +
                "\"username\": \"hey\", " +
                "\"password\": \"wow_such_strong\"," +
                "\"fullName\": \"Full user name\"," +
                "\"email\": \"email@test.com\"" +
                "}";

        mvc.perform(post("/signup")
                .contentType(APPLICATION_JSON)
                .content(validSignupRequest))
                .andExpect(status().isOk());
    }
}