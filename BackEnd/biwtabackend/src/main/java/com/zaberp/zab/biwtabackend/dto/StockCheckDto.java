package com.zaberp.zab.biwtabackend.dto;

import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.id.StockId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "stockcheckview")
@IdClass(StockId.class)
public class StockCheckDto {
    @Id
    private int zid;
    private String zorg;
    @Id
    private String xitem;
    private String xdesc;
    private String xunit;
    @Id
    private String xwh;
    private String xlong;
    private BigDecimal xinhand;
    private BigDecimal xavail;
    private String xminqty;
    private String xmaxqty;
    private String xgitem;


    public StockCheckDto() {
    }

    public StockCheckDto(int zid, String zorg, String xitem, String xdesc, String xunit, String xwh, String xlong, BigDecimal xinhand, BigDecimal xavail, String xminqty, String xmaxqty, String xgitem) {
        this.zid = zid;
        this.zorg = zorg;
        this.xitem = xitem;
        this.xdesc = xdesc;
        this.xunit = xunit;
        this.xwh = xwh;
        this.xlong = xlong;
        this.xinhand = xinhand;
        this.xavail = xavail;
        this.xminqty = xminqty;
        this.xmaxqty = xmaxqty;
        this.xgitem = xgitem;
    }

    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public String getZorg() {
        return zorg;
    }

    public void setZorg(String zorg) {
        this.zorg = zorg;
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

    public String getXunit() {
        return xunit;
    }

    public void setXunit(String xunit) {
        this.xunit = xunit;
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

    public void setXlong(String xlong) {
        this.xlong = xlong;
    }

    public BigDecimal getXinhand() {
        return xinhand;
    }

    public void setXinhand(BigDecimal xinhand) {
        this.xinhand = xinhand;
    }

    public BigDecimal getXavail() {
        return xavail;
    }

    public void setXavail(BigDecimal xavail) {
        this.xavail = xavail;
    }

    public String getXminqty() {
        return xminqty;
    }

    public void setXminqty(String xminqty) {
        this.xminqty = xminqty;
    }

    public String getXmaxqty() {
        return xmaxqty;
    }

    public void setXmaxqty(String xmaxqty) {
        this.xmaxqty = xmaxqty;
    }

    public String getXgitem() {
        return xgitem;
    }

    public void setXgitem(String xgitem) {
        this.xgitem = xgitem;
    }
}
