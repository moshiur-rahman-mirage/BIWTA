package com.zaberp.zab.biwtabackend.id;


import java.io.Serializable;
import java.util.Objects;

public class XusersId implements Serializable {
    private int zid;
    private String zemail;


    public XusersId() {}

    public XusersId(int zid, String zemail) {
        this.zid = zid;
        this.zemail = zemail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XusersId xusersId = (XusersId) o;
        return zid == xusersId.zid && Objects.equals(zemail, xusersId.zemail) && Objects.equals(xusersId, xusersId.zemail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, zemail);
    }
}


