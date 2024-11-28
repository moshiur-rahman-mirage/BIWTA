package com.zaberp.zab.biwtabackend.id;



import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PogrndetailId implements Serializable {
    private int zid;
    private String xgrnnum;
    private int xrow;

    public PogrndetailId() {}

    public PogrndetailId(int zid, String xgrnnum, int xrow) {
        this.zid = zid;
        this.xgrnnum = xgrnnum;
        this.xrow = xrow;
    }

    // Getters and setters
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

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PogrndetailId that = (PogrndetailId) o;
        return Objects.equals(zid, that.zid) &&
                Objects.equals(xgrnnum, that.xgrnnum) &&
                Objects.equals(xrow, that.xrow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xgrnnum, xrow);
    }
}
