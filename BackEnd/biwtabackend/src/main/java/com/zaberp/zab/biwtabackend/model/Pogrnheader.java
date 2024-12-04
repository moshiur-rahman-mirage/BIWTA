package com.zaberp.zab.biwtabackend.model;

import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "pogrnheader")
@IdClass(PogrnHeaderId.class)
public class Pogrnheader {

    private LocalDateTime ztime;
    private LocalDateTime zutime;
    private String zauserid;
    private String zuuserid;
    @Id
    @Column(name="zid")
    private int zid;

    @Id
    @Column(name="xgrnnum")
    private String xgrnnum;
    private Date xdate;
    private String xsup;
    private String xorg;
    private String xstatusgrn;
    private String xref;
    private String xpornum;
    private String xnote;
    private String xcur;
    private BigDecimal xexch;
    private String xlcno;
    private String xinvnum;
    private String xtype;
    private BigDecimal xprimetotamt;
    private BigDecimal xbasetotamt;
    private String xwh;
    private String xstatusap;
    private String xstatusjv;
    private BigDecimal xdisc;
    private BigDecimal xdiscf;
    private String xdocnum;
    private BigDecimal xait;
    private BigDecimal xaitf;
    private BigDecimal xvatrate;
    private BigDecimal xdiscamt;
    private String xvoucher;
    private BigDecimal xadjustment;
    private Date xdategl;
    private BigDecimal xtotamt;
    private String xordernum;
    private String xcus;
    private Integer xsign;
    private Date xexpirydate;
    private String xsign1;
    private String xlctype;
    private String xregi;
    private String xstaff;
    private String xpaymentterm;
    private String xbank;
    private String xsuperiorsp;
    private String xporeqnum;
    private String xtornum;
    private String xadjnum;
    private String xpafnum;
    private String xstatus;
    private String xacc;
    private String xsub;
    private String xcurbuyer;
    private BigDecimal xexchbuyer;
    private String xttcur;
    private BigDecimal xttexch;
    private String xstatusimgl;
    private BigDecimal xprimebuyer;
    private BigDecimal xttbuyer;
    private String xstatusjvgl;
    private String xstatusapgl;
    private String xpreparer;
    private String xsignatory1;
    private Date xsigndate1;
    private String xsignatory2;
    private Date xsigndate2;
    private String xsignatory3;
    private Date xsigndate3;
    private String xsignatory4;
    private Date xsigndate4;
    private String xsignatory5;
    private Date xsigndate5;
    private String xsignatory6;
    private Date xsigndate6;
    private String xsignatory7;
    private Date xsigndate7;
    private String xsuperior2;
    private String xsuperior3;
    private String xsignreject;
    private Date xdatereject;
    private String xacccr;
    private BigDecimal xpercent;
    private String xtrn;
    private String xstatusinsp;
    private String xfwh;
    private BigDecimal xamtother;
    private BigDecimal xfreightcost;
    private BigDecimal xfreightbase;
    private String xproject;
    private String xchallandate;
    private String xstatusdoc;
    private String xstatusclaim;
    private String xstatusreq;
    private String xtypeobj;
    private String xbillno;
    private Date xbilldate;
    private String xblno;
    private String xtankno;
    private String xempunit;
    private String xlong;
    private String xstatuspa;
    private Date xcldate;
    private String xstatusaccrual;
    private String xvoucheraccrual;
    private String xassetcode;
    private String xaitrule;
    private Integer xyear;
    private Integer xper;
    private BigDecimal xvatamt;
    private BigDecimal xaitamt;
    private String xsignatory8;
    private Date xsigndate8;
    private String xsignatory9;
    private Date xsigndate9;
    private String xgrninvno;
    private BigDecimal xinvvalue;
    private String xstatusinv;
    private String xpreparer2;
    private String xsignreject2;
    private Date xdatereject2;
    private String xnote1;
    private String xboeno;
    private Date xdateboe;
    private Date xdaterebate;
    private String xpodate;

    public Pogrnheader() {

    }
}

