package com.zaberp.zab.biwtabackend.model;

import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "imtordetail")
@IdClass(ImtordetailId.class)
public class Imtordetail extends BaseEntity {

    @Column(name = "ztime")
    private LocalDateTime ztime;

    @Column(name = "zutime")
    private LocalDateTime zutime;

    @Column(name = "zauserid")
    private String zauserid;

    @Column(name = "zuuserid")
    private String zuuserid;

    @Id
    @Column(name = "zid")
    private int zid;

    @Id
    @Column(name = "xtornum")
    private String xtornum;

    @Id
    @Column(name = "xrow")
    private int xrow;

    @Column(name = "xitem")
    private String xitem;

    @Column(name = "xmasteritem")
    private String xmasteritem;

    @Column(name = "xqtyord")
    private BigDecimal xqtyord;

    @Column(name = "xunit")
    private String xunit;

    @Column(name = "xrate")
    private BigDecimal xrate;

    @Column(name = "xlineamt")
    private BigDecimal xlineamt;

    @Column(name = "xqtyreq")
    private BigDecimal xqtyreq;

    @Column(name = "xqtycom")
    private BigDecimal xqtycom;

    @Column(name = "xstype")
    private String xstype;

    @Column(name = "xnote")
    private String xnote;

    @Column(name = "xdocrow")
    private Integer xdocrow;

    @Column(name = "xprepqty")
    private BigDecimal xprepqty;

    @Column(name = "xdphqty")
    private BigDecimal xdphqty;

    @Column(name = "xqtypor")
    private BigDecimal xqtypor;

    @Column(name = "xqtyalc")
    private BigDecimal xqtyalc;

    @Column(name = "xbrand")
    private String xbrand;

    @Column(name = "xserial")
    private String xserial;

    @Column(name = "xqtycrn")
    private BigDecimal xqtycrn;

    @Column(name = "xsrnum")
    private String xsrnum;

    @Column(name = "xdate")
    private LocalDate xdate;

    @Column(name = "xbinref")
    private String xbinref;

    @Column(name = "xnote1")
    private String xnote1;

    @Column(name = "xvatrate")
    private BigDecimal xvatrate;

    @Column(name = "xfwh")
    private String xfwh;

    @Column(name = "xtwh")
    private String xtwh;

    @Column(name = "xacc")
    private String xacc;

    @Column(name = "xsub")
    private String xsub;

    @Column(name = "xfleet")
    private String xfleet;

    @Column(name = "xbatch")
    private String xbatch;

    @Column(name = "xcode")
    private String xcode;

    @Column(name = "xqty")
    private BigDecimal xqty;

    @Column(name = "xdateexp")
    private LocalDateTime xdateexp;

}

