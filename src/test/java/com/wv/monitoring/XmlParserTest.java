package com.wv.monitoring;

import com.wv.monitoring.service.XmlParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class XmlParserTest {

    @Autowired
    XmlParser xmlParser;

    @Test
    public void parseTest() {
        xmlParser.xmlParse("parseTest");
    }

    @Test
    public void batchSchedulerParseTest() {
        List batchScheduleList = xmlParser.batchSchedulerParse("batchSchedulerParseTest");
        for(Object test : batchScheduleList) {
            System.out.println(test);
        }
    }
}
