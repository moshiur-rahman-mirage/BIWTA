package com.zaberp.zab.biwtabackend.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PogrndetailXitemdto {
    private int zid;
    private String xgrnnum;
    private int xrow;
    private String xitem;
    private String xdesc;
    private Date xdateexp;
    private String xbatch;
    private BigDecimal xqtygrn;
    private BigDecimal xrategrn;
    private BigDecimal xlineamt;


    public PogrndetailXitemdto() {
    }

    public PogrndetailXitemdto(int zid, String xgrnnum, int xrow, String xitem, String xdesc, Date xdateexp, String xbatch, BigDecimal xqtygrn, BigDecimal xrategrn, BigDecimal xlineamt) {
        this.zid = zid;
        this.xgrnnum = xgrnnum;
        this.xrow = xrow;
        this.xitem = xitem;
        this.xdesc = xdesc;
        this.xdateexp = xdateexp;
        this.xbatch = xbatch;
        this.xqtygrn = xqtygrn;
        this.xrategrn = xrategrn;
        this.xlineamt = xlineamt;
    }



    public PogrndetailXitemdto(int zid, String xgrnnum, int xrow, String xitem, String xdesc) {
        this.zid = zid;
        this.xgrnnum = xgrnnum;
        this.xrow = xrow;
        this.xitem = xitem;
        this.xdesc = xdesc;
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

    public int getXrow() {
        return xrow;
    }

    public void setXrow(int xrow) {
        this.xrow = xrow;
    }

    public String getXitem() {
        return xitem;
    }

    public void setXitem(String xitem) {
        this.xitem = xitem;
    }

    public String getXdesc() {
        return xdesc;
    }

    public void setXdesc(String xdesc) {
        this.xdesc = xdesc;
    }

    public Date getXdateexp() {
        return xdateexp;
    }

    public void setXdateexp(Date xdateexp) {
        this.xdateexp = xdateexp;
    }

    public String getXbatch() {
        return xbatch;
    }

    public void setXbatch(String xbatch) {
        this.xbatch = xbatch;
    }

    public BigDecimal getXqtygrn() {
        return xqtygrn;
    }

    public void setXqtygrn(BigDecimal xqtygrn) {
        this.xqtygrn = xqtygrn;
    }

    public BigDecimal getXrategrn() {
        return xrategrn;
    }

    public void setXrategrn(BigDecimal xrategrn) {
        this.xrategrn = xrategrn;
    }

    public BigDecimal getXlineamt() {
        return xlineamt;
    }

    public void setXlineamt(BigDecimal xlineamt) {
        this.xlineamt = xlineamt;
    }
}
