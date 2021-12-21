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

    public List<Batch> findAllJobInformation() {
        return batchStatusMapper.findAllJobInformation();
    }

    public int selectFailStatusCount() {
        return batchStatusMapper.selectFailStatusCount();
    }

    public int selectCompleteStatusCount() {
        return batchStatusMapper.selectCompleteStatusCount();
    }

    public int selectBatchCount() {
        return batchStatusMapper.selectBatchCount();
    }
}
