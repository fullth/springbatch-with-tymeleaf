package com.wv.thymeleaf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ThymeleafControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("타임리프연동")
    @Test
    void testThymeleaf() throws Exception {
        mockMvc.perform(
                get("/Thymeleaf/test"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}