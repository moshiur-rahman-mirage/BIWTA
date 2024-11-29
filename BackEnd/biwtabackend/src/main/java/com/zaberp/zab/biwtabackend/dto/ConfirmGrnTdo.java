package com.zaberp.zab.biwtabackend.dto;

import java.util.Date;

public class ConfirmGrnTdo {

    private int zid;
    private String zemail;
    private String xgrnnum;
    private Date xdate;
    private String xwh;
    private int len;

    // Getters and setters
    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public String getZemail() {
        return zemail;
    }

    public void setZemail(String zemail) {
        this.zemail = zemail;
    }

    public String getXgrnnum() {
        return xgrnnum;
    }

    public void setXgrnnum(String xgrnnum) {
        this.xgrnnum = xgrnnum;
    }

    public Date getXdate() {
        return xdate;
    }

    public void setXdate(Date xdate) {
        this.xdate = xdate;
    }

    public String getXwh() {
        return xwh;
    }

    public void setXwh(String xwh) {
        this.xwh = xwh;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}

