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

    String xname;
    String zauserid;


    public ImtorDto(){

    }

    public ImtorDto(int zid, String xtornum, Date xdate, String xfwh, String xfwhdesc,String xstatustor) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xdate = xdate;
        this.xfwh = xfwh;
        this.xfwhdesc = xfwhdesc;
        this.xstatustor = xstatustor;
    }


    public ImtorDto(int zid, String xtornum, Date xdate, String xfwh, String xfwhdesc, String xstatustor, String xname) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xdate = xdate;
        this.xfwh = xfwh;
        this.xfwhdesc = xfwhdesc;
        this.xstatustor = xstatustor;
        this.xname = xname;
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

// p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor, pd.xname
    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public int getXrow() {
        return xrow;
    }

    public void setXrow(int xrow) {
        this.xrow = xrow;
    }

    public String getXtornum() {
        return xtornum;
    }

    public void setXtornum(String xtornum) {
        this.xtornum = xtornum;
    }

    public Date getXdate() {
        return xdate;
    }

    public void setXdate(Date xdate) {
        this.xdate = xdate;
    }

    public String getXfwh() {
        return xfwh;
    }

    public void setXfwh(String xfwh) {
        this.xfwh = xfwh;
    }

    public String getXfwhdesc() {
        return xfwhdesc;
    }

    public void setXfwhdesc(String xfwhdesc) {
        this.xfwhdesc = xfwhdesc;
    }

    public String getXtwh() {
        return xtwh;
    }

    public void setXtwh(String xtwh) {
        this.xtwh = xtwh;
    }

    public String getXtwhdesc() {
        return xtwhdesc;
    }

    public void setXtwhdesc(String xtwhdesc) {
        this.xtwhdesc = xtwhdesc;
    }

    public String getXstatustor() {
        return xstatustor;
    }

    public void setXstatustor(String xstatustor) {
        this.xstatustor = xstatustor;
    }

    public String getXlong() {
        return xlong;
    }

    public void setXlong(String xlong) {
        this.xlong = xlong;
    }

    public String getXitem() {
        return xitem;
    }

    public void setXitem(String xitem) {
        this.xitem = xitem;
    }

    public String getXitemname() {
        return xitemname;
    }

    public void setXitemname(String xitemname) {
        this.xitemname = xitemname;
    }
}
