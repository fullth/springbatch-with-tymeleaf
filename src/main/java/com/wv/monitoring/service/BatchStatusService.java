package com.wv.monitoring.service;

import com.wv.monitoring.repository.batch.Batch;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BatchStatusService {
    /** 배치결과조회 */
    List<Batch> findAllJobInformation();

    /** 전체배치실패카운트 */
    int selectFailStatusCount();

    /** 당일배치실패카운트 */
    int selectTodayFailStatusCount(String time);

    /** 전체배치완료카운트 */
    int selectCompleteStatusCount();

    /** 당일배치완료카운트 */
    int selectTodayCompleteStatusCount(String time);

    /** 배치전체수카운트 */
    int selectBatchCount();

    List<String> readLogFile(String fileName) throws IOException;
}
