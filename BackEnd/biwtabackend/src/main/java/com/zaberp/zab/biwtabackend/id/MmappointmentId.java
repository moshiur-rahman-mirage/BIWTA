package com.zaberp.zab.biwtabackend.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class MmappointmentId implements Serializable {
    private Integer zid;
    private String xcase;

    public MmappointmentId() {
    }



    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getXcase() {
        return xcase;
    }

    public void setXcase(String xcase) {
        this.xcase = xcase;
    }
}
