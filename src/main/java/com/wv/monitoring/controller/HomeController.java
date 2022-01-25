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
public class HomeController {

    private BatchStatusService batchStatusService;

    public HomeController(BatchStatusService batchStatusService) {
        this.batchStatusService = batchStatusService;
    }

    @GetMapping("/")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");

        Date time = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ( "yyyyMMdd");
        String formattedTime = DATE_FORMAT.format(time);

        int batchCount = batchStatusService.selectBatchCount();
        int successCount = batchStatusService.selectCompleteStatusCount();
        int failCount = batchStatusService.selectFailStatusCount();

        int todaySuccessCount = batchStatusService.selectTodayCompleteStatusCount(formattedTime);
        int todayFailCount = batchStatusService.selectTodayFailStatusCount(formattedTime);

        int todayBatchCount = todaySuccessCount + todayFailCount;

        modelAndView.addObject("batchCount", batchCount);
        modelAndView.addObject("todayBatchCount", todayBatchCount);
        modelAndView.addObject("successCount", successCount);
        modelAndView.addObject("failCount", failCount);

        return modelAndView;
    }

}
