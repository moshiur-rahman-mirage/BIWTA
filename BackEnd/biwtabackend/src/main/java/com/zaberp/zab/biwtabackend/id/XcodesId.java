package com.zaberp.zab.biwtabackend.id;


import java.io.Serializable;
import java.util.Objects;

public class XcodesId implements Serializable {
    private int zid;
    private String xtype;
    private String xcode;

    public XcodesId() {}

    public XcodesId(int zid, String xtype, String xcode) {
        this.zid = zid;
        this.xtype = xtype;
        this.xcode = xcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XcodesId xcodesId = (XcodesId) o;
        return zid == xcodesId.zid && Objects.equals(xtype, xcodesId.xtype) && Objects.equals(xcode, xcodesId.xcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xtype, xcode);
    }
}

