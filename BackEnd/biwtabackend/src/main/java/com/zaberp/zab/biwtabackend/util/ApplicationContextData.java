package com.zaberp.zab.biwtabackend.util;

import org.springframework.stereotype.Component;

@Component
public class ApplicationContextData {
    private Integer zid;

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }
}
