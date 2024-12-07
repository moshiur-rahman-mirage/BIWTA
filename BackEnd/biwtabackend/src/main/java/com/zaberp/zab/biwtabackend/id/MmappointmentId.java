package com.zaberp.zab.biwtabackend.id;


import lombok.Data;

import java.util.Objects;

@Data
public class MmappointmentId {
    private int zid;
    private String xcase;

    public MmappointmentId(){}

    public MmappointmentId(int zid, String xcase) {
        this.zid = zid;
        this.xcase = xcase;
    }

}
