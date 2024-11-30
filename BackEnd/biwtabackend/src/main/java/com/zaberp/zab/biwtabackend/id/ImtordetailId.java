package com.zaberp.zab.biwtabackend.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ImtordetailId implements Serializable {

    @Column(name = "zid")
    private int zid;

    @Column(name = "xtornum")
    private String xtornum;

    @Column(name = "xrow")
    private int xrow;

    // Default constructor
    public ImtordetailId() {}

    public ImtordetailId(int zid, String xtornum, int xrow) {
        this.zid = zid;
        this.xtornum = xtornum;
        this.xrow = xrow;
    }
}

