package com.zaberp.zab.biwtabackend.id;


import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CacusId implements Serializable {
    private int zid;
    private String xcus;

    public CacusId(){}

    public CacusId(int zid, String xcus) {
        this.zid = zid;
        this.xcus = xcus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacusId cacusId = (CacusId) o;
        return Objects.equals(zid, cacusId.zid) && Objects.equals(xcus, cacusId.xcus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zid, xcus);
    }
}

