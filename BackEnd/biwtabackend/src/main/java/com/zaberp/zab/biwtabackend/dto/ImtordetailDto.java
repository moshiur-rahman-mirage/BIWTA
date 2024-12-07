package com.zaberp.zab.biwtabackend.dto;

import java.math.BigDecimal;

public class ImtordetailDto {
    private int zid;
    private String xtornum;
    private int xrow;
    private String xitem;
    private String xdesc;
    private BigDecimal xqtyord;
    private BigDecimal xrate;
    private BigDecimal xlineamt;
    private BigDecimal xqtyreq;
    private BigDecimal xqtycom;
    private String xstype;
    private String xnote;
    private BigDecimal xqtypor;
    private BigDecimal xqtyalc;
    private BigDecimal xqtylead;
    private BigDecimal xprepqty;
    private int xdocrow;
    public ImtordetailDto() {

    }

    public ImtordetailDto(int zid, String xtornum, int xrow, String xitem, String xdesc, BigDecimal xqtyord, String xstype, BigDecimal xprepqty,BigDecimal xqtyalc) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xrow = xrow;
        this.xitem = xitem;
        this.xdesc = xdesc;
        this.xqtyord = xqtyord;
        this.xstype = xstype;
        this.xprepqty = xprepqty;
        this.xqtyalc=xqtyalc;
    }


    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public String getXtornum() {
        return xtornum;
    }

    public void setXtornum(String xtornum) {
        this.xtornum = xtornum;
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

    public BigDecimal getXqtyord() {
        return xqtyord;
    }

    public void setXqtyord(BigDecimal xqtyord) {
        this.xqtyord = xqtyord;
    }

    public BigDecimal getXrate() {
        return xrate;
    }

    public void setXrate(BigDecimal xrate) {
        this.xrate = xrate;
    }

    public BigDecimal getXlineamt() {
        return xlineamt;
    }

    public void setXlineamt(BigDecimal xlineamt) {
        this.xlineamt = xlineamt;
    }

    public BigDecimal getXqtyreq() {
        return xqtyreq;
    }

    public void setXqtyreq(BigDecimal xqtyreq) {
        this.xqtyreq = xqtyreq;
    }

    public BigDecimal getXqtycom() {
        return xqtycom;
    }

    public void setXqtycom(BigDecimal xqtycom) {
        this.xqtycom = xqtycom;
    }

    public String getXstype() {
        return xstype;
    }

    public void setXstype(String xstype) {
        this.xstype = xstype;
    }

    public String getXnote() {
        return xnote;
    }

    public void setXnote(String xnote) {
        this.xnote = xnote;
    }

    public BigDecimal getXqtypor() {
        return xqtypor;
    }

    public void setXqtypor(BigDecimal xqtypor) {
        this.xqtypor = xqtypor;
    }

    public BigDecimal getXqtyalc() {
        return xqtyalc;
    }

    public void setXqtyalc(BigDecimal xqtyalc) {
        this.xqtyalc = xqtyalc;
    }

    public BigDecimal getXqtylead() {
        return xqtylead;
    }

    public void setXqtylead(BigDecimal xqtylead) {
        this.xqtylead = xqtylead;
    }

    public BigDecimal getXprepqty() {
        return xprepqty;
    }

    public void setXprepqty(BigDecimal xprepqty) {
        this.xprepqty = xprepqty;
    }

    public int getXdocrow() {
        return xdocrow;
    }

    public void setXdocrow(int xdocrow) {
        this.xdocrow = xdocrow;
    }
}
