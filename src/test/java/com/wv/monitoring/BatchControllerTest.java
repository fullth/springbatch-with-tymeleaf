package com.wv.monitoring;

import com.wv.monitoring.mapper.BatchStatusMapper;
import com.wv.monitoring.service.BatchStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("배치결과전체조회")
    @Test
    void testSelectAllStatus() throws Exception {
        mockMvc.perform(
                get("/batch/status"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
