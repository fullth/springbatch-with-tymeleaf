package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.service.BatchStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchMonitorController {

    private BatchStatusService batchStatusService;

    public BatchMonitorController(BatchStatusService batchStatusService) {
        this.batchStatusService = batchStatusService;
    }

    @GetMapping("/result")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/result");

        List<Batch> batchList = batchStatusService.findAllJobInformation();
        modelAndView.addObject("statusList", batchList);

        return modelAndView;
    }
}
