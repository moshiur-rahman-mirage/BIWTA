package com.zaberp.zab.biwtabackend.id;



import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class StockId implements Serializable {
    private int zid;
    private String xitem;
    private String xwh;

    public StockId(){}


    public StockId(int zid, String xitem, String xwh) {
        this.zid = zid;
        this.xitem = xitem;
        this.xwh = xwh;
    }

}


