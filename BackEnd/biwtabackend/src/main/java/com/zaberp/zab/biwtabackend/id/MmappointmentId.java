package com.zaberp.zab.biwtabackend.id;

<<<<<<< HEAD

import lombok.Data;

import java.util.Objects;

@Data
public class MmappointmentId {
    private int zid;
    private String xcase;

    public MmappointmentId(){}

    public MmappointmentId(int zid, String xcase) {
=======
import lombok.Data;

import java.io.Serializable;

@Data
public class MmappointmentId implements Serializable {
    private Integer zid;
    private String xcase;

    public MmappointmentId() {
    }

    public MmappointmentId(Integer zid, String xcase) {
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
        this.zid = zid;
        this.xcase = xcase;
    }

<<<<<<< HEAD
=======
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
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
}