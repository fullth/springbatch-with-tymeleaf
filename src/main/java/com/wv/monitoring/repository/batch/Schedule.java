package com.wv.monitoring.repository.batch;

public class Schedule {

    private int idx;
    private String triggerName;
    private String jobDetail;
    private String cronExpression;

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "idx=" + idx +
                ", triggerName='" + triggerName + '\'' +
                ", jobDetail='" + jobDetail + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                '}';
    }
}
