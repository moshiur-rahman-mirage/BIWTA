package com.zaberp.zab.biwtabackend.model;



import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@IdClass(ImtorheaderId.class)
@Table(name = "imtorheader")
public class Imtorheader extends BaseEntity {

    @Id
    @Column(name = "zid", nullable = false)
    private int zid;

    @Id
    @Column(name = "xtornum", nullable = false, length = 50)
    private String xtornum;

    @Column(name = "ztime")
    private LocalDateTime ztime;

    @Column(name = "zutime")
    private LocalDateTime zutime;

    @Column(name = "zauserid", length = 50)
    private String zauserid;

    @Column(name = "zuuserid", length = 50)
    private String zuuserid;

    @Column(name = "xdate")
    private LocalDate xdate;

    @Column(name = "xdatecom")
    private LocalDate xdatecom;

    @Column(name = "xfwh", length = 50)
    private String xfwh;

    @Column(name = "xref", length = 50)
    private String xref;

    @Column(name = "xtwh", length = 50)
    private String xtwh;

    @Column(name = "xregi", length = 50)
    private String xregi;

    @Column(name = "xshift", length = 50)
    private String xshift;

    @Column(name = "xstatusrec", length = 50)
    private String xstatusrec;

    @Column(name = "xidsup", length = 8)
    private String xidsup;

    @Column(name = "xstatustor", length = 50)
    private String xstatustor;

    @Column(name = "xstatusreq", length = 50)
    private String xstatusreq;

    @Column(name = "xlong", length = 1000)
    private String xlong;

    @Column(name = "xnote", length = 5000)
    private String xnote;

    @Column(name = "xpreparer", length = 50)
    private String xpreparer;

    @Column(name = "xsignatory1", length = 50)
    private String xsignatory1;

    @Column(name = "xsignatory2", length = 50)
    private String xsignatory2;

    @Column(name = "xsignatory3", length = 50)
    private String xsignatory3;

    @Column(name = "xsignatory4", length = 50)
    private String xsignatory4;

    @Column(name = "xsignatory5", length = 50)
    private String xsignatory5;

    @Column(name = "xsignreject", length = 50)
    private String xsignreject;

    @Column(name = "xsigndate1")
    private LocalDateTime xsigndate1;

    @Column(name = "xsigndate2")
    private LocalDateTime xsigndate2;

    @Column(name = "xsigndate3")
    private LocalDateTime xsigndate3;

    @Column(name = "xsigndate4")
    private LocalDateTime xsigndate4;

    @Column(name = "xsigndate5")
    private LocalDateTime xsigndate5;

    @Column(name = "xsign1", length = 100)
    private String xsign1;

    @Column(name = "xsign2", length = 100)
    private String xsign2;

    @Column(name = "xsign3", length = 100)
    private String xsign3;

    @Column(name = "xsignplace")
    private Integer xsignplace;

    // Getters and Setters (Omitted for brevity)
}

