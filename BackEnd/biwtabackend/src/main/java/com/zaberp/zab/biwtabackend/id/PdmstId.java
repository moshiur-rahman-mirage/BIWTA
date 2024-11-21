package com.zaberp.zab.biwtabackend.id;



import java.io.Serializable;
import java.util.Objects;

public class PdmstId implements Serializable {
    private int zid;
    private String xstaff;


    public PdmstId() {}

    public PdmstId(int zid, String xstaff) {
        this.zid = zid;
        this.xstaff = xstaff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdmstId pdmstId = (PdmstId) o;
        return zid == pdmstId.zid && Objects.equals(xstaff, pdmstId.xstaff) && Objects.equals(pdmstId, pdmstId.xstaff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xstaff);
    }
}



