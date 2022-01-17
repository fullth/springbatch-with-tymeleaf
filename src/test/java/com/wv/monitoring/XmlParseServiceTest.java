package com.wv.monitoring;

import com.wv.monitoring.service.XmlParseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class XmlParseServiceTest {

    @Autowired
    XmlParseService xmlParseService;

    @Test
    public void parseTest() {
        xmlParseService.xmlParse("TEST");
    }

    @Test
    public void batchSchedulerParseTest() {
        List batchScheduleList = xmlParseService.batchSchedulerParse("TEST");
        for(Object test : batchScheduleList) {
            System.out.println(test);
        }
    }
}
