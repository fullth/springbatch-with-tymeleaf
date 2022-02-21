package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Trigger;
import com.wv.monitoring.service.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchTriggerController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${monitoring.schedule.file-path}")
    private String scheduleFilePath;

    private final XmlParser xmlParser;

    public BatchTriggerController(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @GetMapping("/trigger")
    public ModelAndView batchTrigger() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("batch/trigger");

        List<Trigger> triggerList = xmlParser.batchTriggerParse(scheduleFilePath);
        
        int totalTriggerCount = triggerList.size();
        LOGGER.info("Trigger count >>> " + totalTriggerCount + "개");

        modelAndView.addObject("totalTriggerCount", totalTriggerCount);
        modelAndView.addObject(triggerList);

        return modelAndView;
    }

    @PostMapping("/trigger/items")
    public void addTrigger(@RequestBody Trigger trigger) throws Exception {
        xmlParser.addTrigger(trigger);
    }

    @PutMapping("/trigger/items")
    public void updateTrigger(@RequestBody Trigger trigger) throws Exception {
        xmlParser.updateTrigger(trigger);

    }

    @DeleteMapping("/trigger/items")
    public void deleteTrigger(@RequestBody Trigger trigger) throws Exception {
        xmlParser.deleteTrigger(trigger);
    }
}
