package com.wv.monitoring.repository.batch;

public class Trigger {
    private int idx;
    private String triggerName;

    public int getIdx() {
        return idx;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
}
