package com.wv.monitoring.service;

import com.wv.monitoring.mapper.BatchStatusMapper;
import com.wv.monitoring.repository.batch.Batch;
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

    @Override
    public List<String> readLogFile(String fileName) throws IOException {
        BufferedReader bufferedReader = null;
        if(fileName.length() == 0)
            fileName = "batch_error.log";
        try {
            String filePath = "C:\\WORLDVISION\\JAR\\" + fileName;
            bufferedReader = new BufferedReader(new FileReader(filePath), 16 * 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> logList = new ArrayList<>();
        String str = "";
        while (true) {
            try {
                if (!((str = bufferedReader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            logList.add(str);
        }

        bufferedReader.close();
        return logList;
    }

}
