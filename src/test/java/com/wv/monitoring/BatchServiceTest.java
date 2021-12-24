package com.wv.monitoring;

import com.wv.monitoring.service.BatchStatusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class BatchServiceTest {

    @Autowired
    BatchStatusService batchStatusService;

    @Test
    @DisplayName("배치결과리스트조회")
    public void readLogFile_isNotEmpty_True() throws IOException {
        batchStatusService.readLogFile("");
    }
}
