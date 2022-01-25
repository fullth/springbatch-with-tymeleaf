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
    public void testBatchSchedulerParse() {
        xmlParser.xmlParse("C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml");
    }

    @Test
    public void batchSchedulerParseTest() {
        List batchScheduleList = xmlParser.batchSchedulerParse("C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml");
        for(Object test : batchScheduleList) {
            System.out.println(test);
        }
    }

    @Test
    public void updateScheduleTest() throws Exception {
        xmlParser.updateSchedule("erpActivityInfoTrigger", "0 40 15 * * ?");
    }
}
