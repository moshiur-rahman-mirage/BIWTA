package com.zaberp.zab.biwtabackend.model;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "caitem")
@IdClass(CaitemId.class) // Link to the composite key class
public class Caitem extends BaseEntity {

    @Column(name = "ztime")
    private LocalDateTime ztime;

    @Column(name = "zutime")
    private LocalDateTime zutime;

    @Column(name = "zauserid")
    private String zauserid;

    @Column(name = "zuuserid")
    private String zuuserid;

    @Id
    @Column(name = "zid", nullable = false)
    private int zid;

    @Id
    @Column(name = "xitem", nullable = false)
    private String xitem;

    @Column(name = "xdesc")
    private String xdesc;

    @Column(name = "xunit")
    private String xunit;

    @Column(name = "xunitpur")
    private String xunitpur;

    @Column(name = "xcfpur")
    private BigDecimal xcfpur;

    @Column(name = "xunitsel")
    private String xunitsel;

    @Column(name = "xcfsel")
    private BigDecimal xcfsel;

    @Column(name = "xminqty")
    private BigDecimal xminqty;

    @Column(name = "xmaxqty")
    private BigDecimal xmaxqty;

    @Column(name = "xgitem")
    private String xgitem;

    @Column(name = "xcatitem")
    private String xcatitem;



    @Column(name = "xtitem")
    private String xtitem;


    @Column(name = "zactive")
    private String zactive;

    @Column(name = "xrate")
    private BigDecimal xrate;




    @Column(name = "xprodnature")
    private String xprodnature;
    @Column(name = "xgenericname")
    private String xgenericname;
    @Column(name = "xgenericdesc")
    private String xgenericdesc;
    @Column(name = "xdrugtype")
    private String xdrugtype;
    @Column(name = "xstrength")
    private  String xstrength;
    @Column(name = "xroute")
    private String xroute;
    @Column(name = "xbatmg")
    private String xbatmg;
    @Column(name = "xreordqty")
    private Double xreordqty;

}
