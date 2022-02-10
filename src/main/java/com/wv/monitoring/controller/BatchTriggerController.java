package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Trigger;
import com.wv.monitoring.service.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchTriggerController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final XmlParser xmlParser;

    public BatchTriggerController(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @GetMapping("/trigger")
    public ModelAndView batchTrigger() {
        ModelAndView modelAndView = new ModelAndView();

        List<Trigger> triggerList = xmlParser.batchTriggerParse("C:\\WORLDVISION\\JAR\\context-batch-scheduler.xml");
        int totalTriggerCount = triggerList.size();
        LOGGER.info("Trigger count >>> " + totalTriggerCount);

        modelAndView.setViewName("batch/trigger");

        modelAndView.addObject(totalTriggerCount);
        modelAndView.addObject(triggerList);

        return modelAndView;
    }
}
