package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.service.BatchStatusService;
import com.wv.monitoring.service.XmlParseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchScheduleController {

    private XmlParseService xmlParseService;

    public BatchScheduleController(XmlParseService xmlParseService) {
        this.xmlParseService = xmlParseService;
    }

    /** 배치 결과 조회 */
    @GetMapping("/schedule")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/schedule");


        List scheduleList = xmlParseService.batchSchedulerParse("");

        modelAndView.addObject("scheduleList", scheduleList);

        return modelAndView;
    }
}
