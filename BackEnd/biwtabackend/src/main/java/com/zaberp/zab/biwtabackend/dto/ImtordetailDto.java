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

    public ImtordetailDto(int zid, String xtornum, int xrow, String xitem, String xdesc, BigDecimal xqtyord, String xstype, BigDecimal xprepqty) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xrow = xrow;
        this.xitem = xitem;
        this.xdesc = xdesc;
        this.xqtyord = xqtyord;
        this.xstype = xstype;
        this.xprepqty = xprepqty;
    }

}
