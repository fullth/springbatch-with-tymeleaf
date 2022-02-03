package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Schedule;
import com.wv.monitoring.service.XmlParser;
import com.wv.monitoring.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchTriggerController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /** 배치 트리거 등록 및 수정 */
    @GetMapping("/trigger")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/trigger");

        return modelAndView;
    }
}
