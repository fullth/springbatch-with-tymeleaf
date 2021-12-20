package com.wv.monitoring.repository.batch;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.lang.NonNull;

import java.sql.Date;

public class Batch {

    @NonNull
    private int jobExecutionId;

    private int jobInstanceId;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String status;
    private String exitCode;
    private String jobName;
    private String jobKey;

    public int getJobExecutionId() {
        return jobExecutionId;
    }

    public int getJobInstanceId() {
        return jobInstanceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
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
