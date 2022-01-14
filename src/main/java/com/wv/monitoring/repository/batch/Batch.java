package com.wv.monitoring.repository.batch;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Batch {

    @NonNull
    private int jobExecutionId;

    private int jobInstanceId;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
    private String exitCode;
    private String jobName;
    private String jobKey;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int getJobExecutionId() {
        return jobExecutionId;
    }

    public int getJobInstanceId() {
        return jobInstanceId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getStartTime() throws ParseException {
        //simpleDateFormat.format(startTime);
        return startTime;
    }

    public Timestamp getEndTime() throws ParseException {
        //simpleDateFormat.format(endTime);
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public String getExitCode() {
        return exitCode;
    }

    public String getJobName() {
        return jobName;
    }

    public String getJobKey() {
        return jobKey;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
