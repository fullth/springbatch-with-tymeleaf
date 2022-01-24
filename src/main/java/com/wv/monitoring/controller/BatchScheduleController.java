package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Schedule;
import com.wv.monitoring.service.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchScheduleController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private XmlParser xmlParser;

    public BatchScheduleController(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    /** 배치 결과 조회 */
    @GetMapping("/schedule")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/schedule");

        List<Schedule> scheduleList = xmlParser.batchSchedulerParse("");

        modelAndView.addObject("scheduleList", scheduleList);

        return modelAndView;
    }

    @PutMapping("/items")
    public String updateBatchSchedule(@RequestBody Schedule schedule) {
        LOGGER.info(schedule.toString());
        return "test";
    }
}
