package com.wv.thymeleaf.repository.thymeleaf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Thymeleaf {
    private int seq;
    private String name;

    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public Thymeleaf(int seq, String name) {
        this.seq = seq;
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
