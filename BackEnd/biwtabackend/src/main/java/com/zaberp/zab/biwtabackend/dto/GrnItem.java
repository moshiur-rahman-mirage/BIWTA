package com.zaberp.zab.biwtabackend.dto;

import java.math.BigDecimal;

public class GrnItem {
    private String xitem;
    private BigDecimal xqty;
    private BigDecimal xrate;
    private BigDecimal xrategrn;
    private BigDecimal xqtygrn;

    public BigDecimal getXrategrn() {
        return xrategrn;
    }

    public void setXrategrn(BigDecimal xrategrn) {
        this.xrategrn = xrategrn;
    }

    public BigDecimal getXqtygrn() {
        return xqtygrn;
    }

    public void setXqtygrn(BigDecimal xqtygrn) {
        this.xqtygrn = xqtygrn;
    }

    public String getXitem() {
        return xitem;
    }

    public void setXitem(String xitem) {
        this.xitem = xitem;
    }

    public BigDecimal getXqty() {
        return xqty;
    }

    public void setXqty(BigDecimal xqty) {
        this.xqty = xqty;
    }

    public BigDecimal getXrate() {
        return xrate;
    }

    public void setXrate(BigDecimal xrate) {
        this.xrate = xrate;
    }
}

