package com.zaberp.zab.biwtabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "pdmst")
public class Pdmst {

    @Id
    @Column(name = "zid")
    private int zid;

    @Column(name = "ztime")
    private Date ztime;

    @Column(name = "zutime")
    private Date zutime;

    @Column(name = "zauserid")
    private String zauserid;

    @Column(name = "zuuserid")
    private String zuuserid;

    @Id
    @Column(name = "xstaff")
    private String xstaff;

    @Column(name = "xname")
    private String xname;

    @Column(name = "xfname")
    private String xfname;

    @Column(name = "xmname")
    private String xmname;

    @Column(name = "xmadd")
    private String xmadd;

    @Column(name = "xpadd")
    private String xpadd;

    @Column(name = "xbirthdate")
    private Date xbithdate;

    @Column(name = "xsex")
    private String xsex;

    @Column(name = "xmstat")
    private String xmstat;

    @Column(name = "xgroup")
    private String xgroup;

    @Column(name = "xvillage")
    private String xvillage;

    @Column(name = "xpost")
    private String xpost;

    @Column(name = "xlong")
    private String xlong;

    @Column(name = "xreplaceid")
    private String xreplaceid;

    @Column(name = "xtypeposition")
    private String xtypeposition;

    @Column(name = "xdeptname")
    private String xdeptname;

    @Column(name = "xsec")
    private String xsec;

    @Column(name = "xdesignation")
    private String xdesignation;

    @Column(name = "xdatejoin")
    private Date xdatejoin;

    @Column(name = "xgrade")
    private String xgrade;

    @Column(name = "xemail")
    private String xemail;

    @Column(name = "xenddate")
    private Date xenddate;

    @Column(name = "xretdate")
    private Date xretdate;

    @Column(name = "xotapplicable")
    private String xotapplicable;

    @Column(name = "xbatch")
    private String xbatch;

    @Column(name = "xsalute")
    private String xsalute;

    @Column(name = "xspouse")
    private String xspouse;

    @Column(name = "xempstatus")
    private String xempstatus;

    @Column(name = "xlocation")
    private String xlocation;

    @Column(name = "xemname")
    private String xemname;

    @Column(name = "xrelation")
    private String xrelation;

    @Column(name = "xemcmobile")
    private String xemcmobile;

    @Column(name = "xmobile")
    private String xmobile;

    @Column(name = "xhomephone")
    private String xhomephone;

    @Column(name = "xempjobstatus")
    private String xempjobstatus;

    @Column(name = "xdistrict")
    private String xdistrict;

    @Column(name = "xdatecom")
    private Date xdatecom;

    @Column(name = "xinclude")
    private String xinclude;

    @Column(name = "xbank")
    private String xbank;

    @Column(name = "xdesc")
    private String xdesc;

    @Column(name = "xacc")
    private String xacc;

    @Column(name = "xprobenddate")
    private Date xprobenddate;

    @Column(name = "xregino")
    private String xregino;

    @Column(name = "xregiexpdate")
    private Date xregiexpdate;

    @Column(name = "xrehire")
    private String xrehire;

    @Column(name = "xthana")
    private String xthana;

    @Column(name = "xnid")
    private String xnid;

    @Column(name = "xdatejoinp")
    private Date xdatejoinp;

    @Column(name = "xregtype")
    private String xregtype;

    @Column(name = "xposition")
    private String xposition;

    @Column(name = "xtin")
    private String xtin;

    @Column(name = "xempcategory")
    private String xempcategory;

    @Column(name = "xstatus")
    private String xstatus;

    @Column(name = "xsid")
    private String xsid;

    @Column(name = "xcar")
    private String xcar;

    @Lob
    @Column(name = "ximage")
    private byte[] ximage;

    @Column(name = "xbloodgroup")
    private String xbloodgroup;

    @Column(name = "xphone")
    private String xphone;

    @Column(name = "xgross")
    private Double xgross;

    @Column(name = "xleavecount")
    private Double xleavecount;

    @Column(name = "xhrc1")
    private String xhrc1;

    @Column(name = "xhrc2")
    private String xhrc2;

    @Column(name = "xhrc3")
    private String xhrc3;

    @Column(name = "xhrc4")
    private String xhrc4;

    @Column(name = "xplan")
    private String xplan;

    @Column(name = "xsplan")
    private String xsplan;

    @Column(name = "xhour")
    private Double xhour;

    @Column(name = "xrate")
    private Double xrate;

    @Lob
    @Column(name = "xnimage")
    private byte[] xnimage;

    @Column(name = "xreligion")
    private String xreligion;

    @Column(name = "xsuperiorsp")
    private String xsuperiorsp;

    @Column(name = "xemptype")
    private String xemptype;

    @Column(name = "zactive")
    private String zactive;

    @Lob
    @Column(name = "xsignature")
    private byte[] xsignature;

    @Column(name = "xfile")
    private String xfile;

    @Column(name = "xidsup")
    private String xidsup;

    @Column(name = "xempposition")
    private String xempposition;

    @Column(name = "xgrossold")
    private Double xgrossold;

    @Column(name = "xsignatory")
    private String xsignatory;

    @Column(name = "xrouting")
    private String xrouting;

    @Column(name = "xacctype")
    private String xacctype;

    @Column(name = "xempbank")
    private String xempbank;

    @Column(name = "xemailc")
    private String xemailc;

    @Column(name = "xfstname")
    private String xfstname;

    @Column(name = "xlstname")
    private String xlstname;

    @Column(name = "xbankamt")
    private Double xbankamt;

    @Column(name = "xcashamt")
    private Double xcashamt;

    @Column(name = "xempgrade")
    private String xempgrade;

    @Column(name = "xsection")
    private String xsection;

    @Column(name = "xshift")
    private String xshift;

    @Column(name = "xemptimein")
    private String xemptimein;

    @Column(name = "xemptimeout")
    private String xemptimeout;

    @Column(name = "xpfconrate")
    private Double xpfconrate;

    @Column(name = "xpfdef")
    private String xpfdef;

    @Column(name = "xitdef")
    private String xitdef;

    @Column(name = "xadminid")
    private String xadminid;

    @Column(name = "xweekday")
    private String xweekday;

    @Column(name = "xwkndnotapp")
    private String xwkndnotapp;

    @Column(name = "xnum")
    private Integer xnum;

    @Column(name = "xlastcrdate")
    private Date xlastcrdate;

    @Column(name = "xhdayapp")
    private String xhdayapp;

    @Column(name = "xtaxzone")
    private String xtaxzone;

    @Column(name = "xenddtcontract")
    private Date xenddtcontract;

    @Column(name = "xsuperior2")
    private String xsuperior2;

    @Column(name = "xsuperior3")
    private String xsuperior3;

    @Column(name = "xsuperior4")
    private String xsuperior4;

    @Column(name = "xsuperior5")
    private String xsuperior5;

    @Column(name = "xsuperior6")
    private String xsuperior6;

    @Column(name = "xstatus1")
    private String xstatus1;

    @Column(name = "xstatus2")
    private String xstatus2;

    @Column(name = "xstatus3")
    private String xstatus3;

    @Column(name = "xnote1")
    private String xnote1;

    @Column(name = "xnote2")
    private String xnote2;

    @Column(name = "xtaxamt")
    private Double xtaxamt;

    @Column(name = "xdivision")
    private String xdivision;

    @Column(name = "xarea")
    private String xarea;

    @Column(name = "xaccname")
    private String xaccname;

    @Column(name = "xbasic")
    private Double xbasic;

    @Column(name = "xmtleavebal")
    private Integer xmtleavebal;

    @Column(name = "xplanchange")
    private String xplanchange;

    @Column(name = "xempunit")
    private String xempunit;

    @Column(name = "xwh")
    private String xwh;

    @Column(name = "xnationality")
    private String xnationality;

    @Column(name = "xjobtitle")
    private String xjobtitle;

    @Column(name = "xmdlname")
    private String xmdlname;

    @Column(name = "xprofdegree")
    private String xprofdegree;

}

