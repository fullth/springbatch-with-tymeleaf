package com.wv.monitoring.mapper;

import com.wv.monitoring.repository.batch.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BatchStatusMapper {
    @Select("SELECT * FROM BATCH_JOB_EXECUTION A INNER JOIN  BATCH_JOB_INSTANCE B ON A.JOB_INSTANCE_ID = B.JOB_INSTANCE_ID ORDER BY END_TIME DESC")
    List<Batch> findAllJobInformation();

    @Select("SELECT COUNT(STATUS) FROM BATCH_JOB_EXECUTION A INNER JOIN  BATCH_JOB_INSTANCE B ON A.JOB_INSTANCE_ID = B.JOB_INSTANCE_ID WHERE STATUS = 'FAILED'")
    int selectFailStatusCount();

    @Select("SELECT COUNT(STATUS) FROM BATCH_JOB_EXECUTION A INNER JOIN  BATCH_JOB_INSTANCE B ON A.JOB_INSTANCE_ID = B.JOB_INSTANCE_ID WHERE STATUS = 'COMPLETED'")
    int selectCompleteStatusCount();

    @Select("SELECT COUNT(DISTINCT JOB_NAME) FROM BATCH_JOB_INSTANCE")
    int selectBatchCount();
}
