package com.zaberp.zab.biwtabackend.model;



import com.zaberp.zab.biwtabackend.id.PogrndetailId;
import com.zaberp.zab.biwtabackend.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "pogrndetail")
@IdClass(PogrndetailId.class)
public class Pogrndetail extends BaseEntity {

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
    @Column(name = "xgrnnum")
    private String xgrnnum;

    @Id
    @Column(name = "xrow")
    private int xrow;

    @Column(name = "xitem")
    private String xitem;

    @Column(name = "xqtygrn")
    private BigDecimal xqtygrn;

    @Column(name = "xunitpur")
    private String xunitpur;

    @Column(name = "xrate")
    private BigDecimal xrate;

    @Column(name = "xlong")
    private String xlong;

    @Column(name = "xlineamt")
    private BigDecimal xlineamt;

    @Column(name = "xbatch")
    private String xbatch;

    @Column(name = "xdateexp")
    private Date xdateexp;

    @Column(name = "xdisc")
    private BigDecimal xdisc;

    @Column(name = "xdiscf")
    private BigDecimal xdiscf;

    @Column(name = "xdocrow")
    private Integer xdocrow=0;

    @Column(name = "xcfpur")
    private BigDecimal xcfpur;

    @Column(name = "xqtybonus")
    private Integer xqtybonus=0;

    @Column(name = "xpornum")
    private String xpornum;

    @Column(name = "xtype")
    private String xtype;

    @Column(name = "xrategrn")
    private BigDecimal xrategrn;

    @Column(name = "xvatamt")
    private BigDecimal xvatamt;

    @Column(name = "xserial")
    private String xserial;

    @Column(name = "xbase")
    private BigDecimal xbase;

    @Column(name = "xqtycom")
    private BigDecimal xqtycom;

    @Column(name = "xstatusimgl")
    private String xstatusimgl;

    @Column(name = "xqtyinsp")
    private BigDecimal xqtyinsp;

    @Column(name = "xvalpo")
    private BigDecimal xvalpo;

    @Column(name = "xqtyprn")
    private BigDecimal xqtyprn;

    @Column(name = "xqtyrec")
    private BigDecimal xqtyrec;

    @Column(name = "xqtyclaim")
    private BigDecimal xqtyclaim;

    @Column(name = "xbinref")
    private String xbinref;

    @Column(name = "xexchbuyer")
    private BigDecimal xexchbuyer;

    @Column(name = "xttexch")
    private BigDecimal xttexch;

    @Column(name = "xbuyeramt")
    private BigDecimal xbuyeramt;

    @Column(name = "xttamount")
    private BigDecimal xttamount;

    @Column(name = "xacc")
    private String xacc;

    @Column(name = "xcdrate")
    private BigDecimal xcdrate;

    @Column(name = "xcdamt")
    private BigDecimal xcdamt;

    @Column(name = "xrdrate")
    private BigDecimal xrdrate;

    @Column(name = "xrdamt")
    private BigDecimal xrdamt;

    @Column(name = "xsupptaxrate")
    private BigDecimal xsupptaxrate;

    @Column(name = "xsupptaxamt")
    private BigDecimal xsupptaxamt;

    @Column(name = "xvatrate")
    private BigDecimal xvatrate;

    @Column(name = "xait")
    private BigDecimal xait;

    @Column(name = "xaitamt")
    private BigDecimal xaitamt;

    @Column(name = "xatrate")
    private BigDecimal xatrate;

    @Column(name = "xatamt")
    private BigDecimal xatamt;

    @Column(name = "xqtygain")
    private BigDecimal xqtygain;

    @Column(name = "xprimebuyer")
    private BigDecimal xprimebuyer;

    @Column(name = "xttbase")
    private BigDecimal xttbase;

    @Column(name = "xttbuyer")
    private BigDecimal xttbuyer;

    @Column(name = "xcpoqty")
    private BigDecimal xcpoqty;

    @Column(name = "xsub")
    private String xsub;

    @Column(name = "xfleet")
    private String xfleet;

    @Column(name = "xcode")
    private String xcode;

    @Column(name = "xlot")
    private String xlot;

    @Column(name = "xcottontype")
    private String xcottontype;

    @Column(name = "xqtybonusgrn")
    private BigDecimal xqtybonusgrn;
}

