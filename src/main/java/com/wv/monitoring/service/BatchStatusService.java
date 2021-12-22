package com.wv.monitoring.service;

import com.wv.monitoring.mapper.BatchStatusMapper;
import com.wv.monitoring.repository.batch.Batch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchStatusService {

    private BatchStatusMapper batchStatusMapper;

    public BatchStatusService(BatchStatusMapper batchStatusMapper) {
        this.batchStatusMapper = batchStatusMapper;
    }

    /** 배치결과조회 */
    public List<Batch> findAllJobInformation() { return batchStatusMapper.findAllJobInformation(); }

    /** 전체배치실패카운트 */
    public int selectFailStatusCount() { return batchStatusMapper.selectFailStatusCount(); }

    /** 당일배치실패카운트 */
    public int selectTodayFailStatusCount(String time) { return batchStatusMapper.selectTodayFailStatusCount(time); }

    /** 전체배치완료카운트 */
    public int selectCompleteStatusCount() { return batchStatusMapper.selectCompleteStatusCount(); }

    /** 당일배치완료카운트 */
    public int selectTodayCompleteStatusCount(String time) { return batchStatusMapper.selectTodayCompleteStatusCount(time); }
    
    /** 배치전체수카운트 */
    public int selectBatchCount() { return batchStatusMapper.selectBatchCount(); }
}
