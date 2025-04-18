package com.wv.monitoring.service;

import com.wv.monitoring.mapper.BatchStatusMapper;
import com.wv.monitoring.repository.batch.Batch;
import com.wv.monitoring.repository.batch.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("BatchStatusService")
public class BatchStatusServiceImpl implements BatchStatusService {

    private final BatchStatusMapper batchStatusMapper;

    @Value("${monitoring.log.file-path}")
    private String logFilePath;

    public BatchStatusServiceImpl(BatchStatusMapper batchStatusMapper) {
        this.batchStatusMapper = batchStatusMapper;
    }

    /** 배치결과조회 */
    @Override
    public List<Batch> findAllJobInformation() { return batchStatusMapper.findAllJobInformation(); }

    /** 전체배치실패카운트 */
    @Override
    public int selectFailStatusCount() { return batchStatusMapper.selectFailStatusCount(); }

    /** 당일배치실패카운트 */
    @Override
    public int selectTodayFailStatusCount(String time) { return batchStatusMapper.selectTodayFailStatusCount(time); }

    /** 전체배치완료카운트 */
    @Override
    public int selectCompleteStatusCount() { return batchStatusMapper.selectCompleteStatusCount(); }

    /** 당일배치완료카운트 */
    @Override
    public int selectTodayCompleteStatusCount(String time) { return batchStatusMapper.selectTodayCompleteStatusCount(time); }
    
    /** 배치전체수카운트 */
    @Override
    public int selectBatchCount() { return batchStatusMapper.selectBatchCount(); }

    /** 배치로그파일 로드 */
    @Override
    public List<Log> readLogFile(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        if(fileName.length() == 0)
            // default: error level
            fileName = "batch_error.log";
        try {
            String filePath = logFilePath + fileName;
            bufferedReader = new BufferedReader(new FileReader(filePath), 16 * 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Log> logList = new ArrayList<>();
        String str = "";
        while (true) {
            try {
                if (!((str = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log log = new Log();
            log.setLog(str);
            logList.add(log);
        }

        bufferedReader.close();
        return logList;
    }
}
