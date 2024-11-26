package com.zaberp.zab.biwtabackend.id;
import java.io.Serializable;
import java.util.Objects;

public class PogrnHeaderId implements Serializable {

    private int zid;
    private String xgrnnum;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PogrnHeaderId that = (PogrnHeaderId) o;
        return zid == that.zid && Objects.equals(xgrnnum, that.xgrnnum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xgrnnum);
    }
}

