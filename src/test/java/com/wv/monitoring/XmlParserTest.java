package com.wv.monitoring;

import com.wv.monitoring.repository.batch.Trigger;
import com.wv.monitoring.service.XmlParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class XmlParserTest {

    @Autowired
    XmlParser xmlParser;

    public static final String SCHEDULER_FILE_PATH = "C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml";

    @Test
    public void testBatchSchedulerParse() {
        xmlParser.xmlParse(SCHEDULER_FILE_PATH);
    }

    @Test
    public void batchSchedulerParseTest() {
        List batchScheduleList = xmlParser.batchSchedulerParse(SCHEDULER_FILE_PATH);
        for(Object test : batchScheduleList) {
            System.out.println(test);
        }
    }

    @Test
    public void updateScheduleTest() throws Exception {
        xmlParser.updateSchedule("erpActivityInfoTrigger", "0 40 15 * * ?");
    }

    @Test
    public void batchTriggerParseTest() throws Exception {
        xmlParser.batchTriggerParse(SCHEDULER_FILE_PATH);
    }

    @Test
    public void addTrigger() throws Exception {
        Trigger trigger = new Trigger();
        trigger.setTriggerName("test_trigger");
        xmlParser.addTrigger(trigger);
    }

    @Test
    public void deleteTrigger() throws Exception {
        Trigger trigger = new Trigger();
        trigger.setTriggerName("test_trigger");
        xmlParser.deleteTrigger(trigger);
    }

    @Test
    public void updateTrigger() throws Exception {
        Trigger trigger = new Trigger();
        trigger.setIdx(52);
        trigger.setTriggerName("updatedTrigger");
        xmlParser.updateTrigger(trigger);
    }
}
