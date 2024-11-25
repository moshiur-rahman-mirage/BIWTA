package com.zaberp.zab.biwtabackend.id;



import java.io.Serializable;
import java.util.Objects;

public class CaitemId implements Serializable {

    private int zid; // Matches the field type in the `Caitem` entity
    private String xitem;

    // Default constructor
    public CaitemId() {
    }

    public CaitemId(int zid, String xitem) {
        this.zid = zid;
        this.xitem = xitem;
    }

    // Getters and Setters
    public int getZid() {
        return zid;
    }

    public void setZid(int zid) {
        this.zid = zid;
    }

    public String getXitem() {
        return xitem;
    }

    public void setXitem(String xitem) {
        this.xitem = xitem;
    }

    // Equals and HashCode (required for composite key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaitemId caitemId = (CaitemId) o;
        return zid == caitemId.zid && Objects.equals(xitem, caitemId.xitem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xitem);
    }
}

