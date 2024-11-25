package com.zaberp.zab.biwtabackend.id;



import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class XtrnId implements Serializable {
    private int zid;
    private String xtypetrn;
    private String xtrn;

    public XtrnId(int zid, String xtypetrn, String xtrn) {
        this.zid = zid;
        this.xtypetrn = xtypetrn;
        this.xtrn = xtrn;
    }

    public XtrnId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XtrnId xtrnId = (XtrnId) o;
        return zid == xtrnId.zid && Objects.equals(xtypetrn, xtrnId.xtypetrn) && Objects.equals(xtrn, xtrnId.xtrn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xtypetrn, xtrn);
    }

    // Getters, setters, equals, hashCode
}

