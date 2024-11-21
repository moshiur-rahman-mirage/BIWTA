package com.zaberp.zab.biwtabackend.id;



import java.io.Serializable;
import java.util.Objects;

public class PdDependentId implements Serializable {
    private int zid;
    private String xstaff;
    private int xrow;

    // Default constructor
    public PdDependentId() {}

    public PdDependentId(int zid, String xstaff, int xrow) {
        this.zid = zid;
        this.xstaff = xstaff;
        this.xrow = xrow;
    }

    // Getters, Setters, hashCode, and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdDependentId that = (PdDependentId) o;
        return zid == that.zid && xrow == that.xrow && Objects.equals(xstaff, that.xstaff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xstaff, xrow);
    }
}
