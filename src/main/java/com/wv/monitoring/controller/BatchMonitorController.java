package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.service.BatchStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        Date time = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ( "yyyyMMdd");
        String formattedTime = DATE_FORMAT.format(time);

        System.out.println(formattedTime);

        List<Batch> batchList = batchStatusService.findAllJobInformation();
        int batchCount = batchStatusService.selectBatchCount();
        int successCount = batchStatusService.selectCompleteStatusCount();
        int todaySuccessCount = batchStatusService.selectTodayCompleteStatusCount(formattedTime);
        int failCount = batchStatusService.selectFailStatusCount();
        int todayFailCount = batchStatusService.selectTodayFailStatusCount(formattedTime);

        modelAndView.addObject("statusList", batchList);
        modelAndView.addObject("batchCount", batchCount);
        modelAndView.addObject("successCount", successCount);
        modelAndView.addObject("todaySuccessCount", todaySuccessCount);
        modelAndView.addObject("failCount", failCount);
        modelAndView.addObject("todayFailCount", todayFailCount);

        return modelAndView;
    }
}
