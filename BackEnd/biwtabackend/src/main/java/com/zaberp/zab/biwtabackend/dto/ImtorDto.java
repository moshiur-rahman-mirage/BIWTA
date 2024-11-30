package com.zaberp.zab.biwtabackend.dto;

import java.sql.Date;

public class ImtorDto {
    int zid;
    int xrow;
    String xtornum;
    Date xdate;
    String xfwh;
    String xfwhdesc;
    String xtwh;
    String xtwhdesc;
    String xstatustor;
    String xlong;
    String xitem;
    String xitemname;

    public ImtorDto(int zid, String xtornum, Date xdate, String xfwh, String xfwhdesc, String xstatustor, String xlong) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xdate = xdate;
        this.xfwh = xfwh;
        this.xfwhdesc = xfwhdesc;
        this.xstatustor = xstatustor;
        this.xlong = xlong;
    }

    public ImtorDto(int zid, String xtornum, Date xdate, String xfwh, String xfwhdesc, String xtwh, String xtwhdesc, String xstatustor, String xlong) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xdate = xdate;
        this.xfwh = xfwh;
        this.xfwhdesc = xfwhdesc;
        this.xtwh = xtwh;
        this.xtwhdesc = xtwhdesc;
        this.xstatustor = xstatustor;
        this.xlong = xlong;
    }


}
