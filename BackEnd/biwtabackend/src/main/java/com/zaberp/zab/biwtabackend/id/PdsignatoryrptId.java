package com.zaberp.zab.biwtabackend.id;



import java.io.Serializable;
import java.util.Objects;

public class PdsignatoryrptId implements Serializable {

    private Integer zid;
    private String xtypetrn;

    // Constructors
    public PdsignatoryrptId() {}

    public PdsignatoryrptId(Integer zid, String xtypetrn) {
        this.zid = zid;
        this.xtypetrn = xtypetrn;
    }

    // Getters, Setters, equals, and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdsignatoryrptId that = (PdsignatoryrptId) o;
        return Objects.equals(zid, that.zid) && Objects.equals(xtypetrn, that.xtypetrn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xtypetrn);
    }
}
