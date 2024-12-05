package com.zaberp.zab.biwtabackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class PogrnheaderXcusdto {
    private int zid;
    private String xgrnnum;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private Date xdate;
    private String xcus;
    private String xorg;
    private String xname;
    private String xwh;
    private String xlong;
    private String xstatus;
    private String xstatusdoc;
    private String zauserid;

    // Constructor matching the query projection
    public PogrnheaderXcusdto(int zid, String xgrnnum, Date xdate, String xcus, String xorg, String xwh,
                              String xlong, String xstatus, String xstatusdoc, String zauserid,String xname) {
        this.zid = zid;
        this.xgrnnum = xgrnnum;
        this.xdate = xdate;
        this.xcus = xcus;
        this.xorg = xorg;
        this.xwh = xwh;
        this.xlong = xlong;
        this.xstatus = xstatus;
        this.xstatusdoc = xstatusdoc;
        this.zauserid = zauserid;
        this.xname=xname;
    }

    public PogrnheaderXcusdto() {
    }

    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
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

    public String getXcus() {
        return xcus;
    }

    public void setXcus(String xcus) {
        this.xcus = xcus;
    }

    public String getXorg() {
        return xorg;
    }

    public void setXorg(String xorg) {
        this.xorg = xorg;
    }

    public String getXwh() {
        return xwh;
    }

    public void setXwh(String xwh) {
        this.xwh = xwh;
    }

    public String getXlong() {
        return xlong;
    }

    public String getXname() {
        return xname;
    }

    public void setXname(String xname) {
        this.xname = xname;
    }

    public void setXlong(String xlong) {
        this.xlong = xlong;
    }

    public String getXstatus() {
        return xstatus;
    }

    public void setXstatus(String xstatus) {
        this.xstatus = xstatus;
    }

    public String getXstatusdoc() {
        return xstatusdoc;
    }

    public void setXstatusdoc(String xstatusdoc) {
        this.xstatusdoc = xstatusdoc;
    }

    public String getZauserid() {
        return zauserid;
    }

    public void setZauserid(String zauserid) {
        this.zauserid = zauserid;
    }
}
