package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Schedule;
import com.wv.monitoring.service.XmlParser;
import com.wv.monitoring.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchScheduleController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${monitoring.schedule.file-path}")
    private String scheduleFilePath;

    private XmlParser xmlParser;

    public BatchScheduleController(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    /** 배치 결과 조회 */
    @GetMapping("/schedule")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/schedule");

        List<Schedule> scheduleList = xmlParser.batchSchedulerParse(scheduleFilePath);

        modelAndView.addObject("scheduleList", scheduleList);

        return modelAndView;
    }

    @PutMapping("/items")
    public StatusCode updateBatchSchedule(@RequestBody Schedule schedule) {
        LOGGER.info(schedule.toString());

        String updateTriggerName = schedule.getTriggerName();
        String updateCronExpression = schedule.getCronExpression();

        try {
            xmlParser.updateSchedule(updateTriggerName, updateCronExpression);
        } catch (Exception e) {
            LOGGER.error("스케줄 업데이트 에러 ::: ", e.getMessage());
            return StatusCode.BAD_REQUEST;
        }

        return StatusCode.OK;
    }
}
