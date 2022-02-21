package com.wv.monitoring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {

    @Value("${monitoring.log.file-path}")
    private String logFilePath;

    @Test
    public void test() {
        Assertions.assertEquals(logFilePath, "");
    }
}
