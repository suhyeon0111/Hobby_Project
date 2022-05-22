package com.hoppy.app.health.controller;

import com.hoppy.app.EnableMockMvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ExtendWith(SpringExtension.class)
@EnableMockMvc
@AutoConfigureRestDocs
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkHealth() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("정상 동작 중")))
                .andDo(document("health-check"));
    }
}