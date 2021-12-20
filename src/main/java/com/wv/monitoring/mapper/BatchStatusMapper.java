package com.wv.monitoring.mapper;

import com.wv.monitoring.repository.batch.Batch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BatchStatusMapper {

    @Select("SELECT * FROM BATCH_JOB_EXECUTION A INNER JOIN  BATCH_JOB_INSTANCE B ON A.JOB_INSTANCE_ID = B.JOB_INSTANCE_ID")
    List<Batch> findAllJobInformation();
}
