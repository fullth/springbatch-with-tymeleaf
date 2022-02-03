package com.wv.monitoring.controller;

import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.repository.batch.Log;
import com.wv.monitoring.service.BatchStatusService;
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
public class BatchMonitorController {

    private BatchStatusService batchStatusService;

    public BatchMonitorController(BatchStatusService batchStatusService) {
        this.batchStatusService = batchStatusService;
    }

    /** 배치 결과 조회 */
    @GetMapping("/result")
    public ModelAndView batchResult() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("batch/result");

        Date time = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ( "yyyyMMdd");
        String formattedTime = DATE_FORMAT.format(time);

        List<Batch> batchList = batchStatusService.findAllJobInformation();
        // 성공한 배치 카운트
        int successCount = batchStatusService.selectCompleteStatusCount();
        int todaySuccessCount = batchStatusService.selectTodayCompleteStatusCount(formattedTime);

        // 전체 배치 수 카운트
        int batchCount = batchStatusService.selectBatchCount();

        // 실패한 배치 카운트
        int failCount = batchStatusService.selectFailStatusCount();
        int todayFailCount = batchStatusService.selectTodayFailStatusCount(formattedTime);

        // 당일 동작한 배치 카운트
        int todayBatch = todaySuccessCount + todayFailCount;

        modelAndView.addObject("statusList", batchList);
        modelAndView.addObject("todayBatchCount", todayBatch);
        modelAndView.addObject("successCount", successCount);
        modelAndView.addObject("todaySuccessCount", todaySuccessCount);
        modelAndView.addObject("batchCount", batchCount);
        modelAndView.addObject("failCount", failCount);
        modelAndView.addObject("todayFailCount", todayFailCount);

        return modelAndView;
    }

    /** 로그파일로드 */
    @GetMapping("/log")
    public ModelAndView batchLog() throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        List<Log> logList = batchStatusService.readLogFile("");

        modelAndView.setViewName("batch/log");
        modelAndView.addObject("logList", logList);

        return modelAndView;
    }

    /** 로그파일동적로드 */
    @GetMapping("/log/{fileName}")
    public ModelAndView batchLogByFileName(@PathVariable("fileName") String fileName) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        List<Log> logList = batchStatusService.readLogFile(fileName);

        modelAndView.setViewName("batch/log");
        modelAndView.addObject("logList", logList);

        return modelAndView;
    }
}
