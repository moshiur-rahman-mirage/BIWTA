package com.zaberp.zab.biwtabackend.dto;

import java.util.Date;
import java.util.List;

public class GrnRequest {

    private int zid;
    private String xcus;
    private Date xdate;
    private String xstatusdoc;
    private String zauserid;
    private String xorg;
    private String xpreparer;

    private String xwh;
    private String xref;
    private String xnote;
    private List<GrnItem> items;

    public String getXstatusdoc() {
        return xstatusdoc;
    }

    public String getZauserid() {
        return zauserid;
    }

    public void setZauserid(String zauserid) {
        this.zauserid = zauserid;
    }

    public void setXstatusdoc(String xstatusdoc) {
        this.xstatusdoc = xstatusdoc;
    }

    public String getXwh() {
        return xwh;
    }

    public String getXorg() {
        return xorg;
    }

    public void setXorg(String xorg) {
        this.xorg = xorg;
    }

    public void setXwh(String xwh) {
        this.xwh = xwh;
    }

    public String getXref() {
        return xref;
    }

    public void setXref(String xref) {
        this.xref = xref;
    }

    public String getXnote() {
        return xnote;
    }

    public void setXnote(String xnote) {
        this.xnote = xnote;
    }

    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public String getXcus() {
        return xcus;
    }

    public void setXcus(String xcus) {
        this.xcus = xcus;
    }

    public Date getXdate() {
        return xdate;
    }

    public void setXdate(Date xdate) {
        this.xdate = xdate;
    }

    public List<GrnItem> getItems() {
        return items;
    }

    public void setItems(List<GrnItem> items) {
        this.items = items;
    }
}

