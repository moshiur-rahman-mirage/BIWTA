package com.zaberp.zab.biwtabackend.dto;

import java.util.Date;

public class ConfirmTrnDto {

    private int zid;
    private String zemail;
    private String user;
    private String position;
    private String tornum;
    private String wh;
    private String request;
    private String xgrnnum;
    private Date xdate;
    private String xwh;

    public String getXstatusdoc() {
        return xstatusdoc;
    }

    public void setXstatusdoc(String xstatusdoc) {
        this.xstatusdoc = xstatusdoc;
    }

    private String xstatusdoc;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTornum() {
        return tornum;
    }

    public void setTornum(String tornum) {
        this.tornum = tornum;
    }

    public String getWh() {
        return wh;
    }

    public void setWh(String wh) {
        this.wh = wh;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}

