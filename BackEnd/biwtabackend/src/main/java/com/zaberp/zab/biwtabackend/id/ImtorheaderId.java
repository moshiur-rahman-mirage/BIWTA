package com.zaberp.zab.biwtabackend.id;

import java.io.Serializable;
import java.util.Objects;

public class ImtorheaderId implements Serializable {

    private int zid;
    private String xtornum;

    // Default constructor
    public ImtorheaderId() {
    }

    // Parameterized constructor
    public ImtorheaderId(int zid, String xtornum) {
        this.zid = zid;
        this.xtornum = xtornum;
    }

    // Getters and Setters
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

    // equals() and hashCode() (Important for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImtorheaderId that = (ImtorheaderId) o;
        return zid == that.zid && Objects.equals(xtornum, that.xtornum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xtornum);
    }
}
