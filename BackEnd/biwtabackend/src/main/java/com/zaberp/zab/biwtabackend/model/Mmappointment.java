package com.zaberp.zab.biwtabackend.model;

<<<<<<< HEAD

import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

=======
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.id.PogrndetailId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f

@Data
@Entity
@Table(name = "mmappointment")
<<<<<<< HEAD
@IdClass(MmappointmentId.class) // For composite primary key
public class Mmappointment {

    @Id
    @Column(name="zid")
    private int zid; // Part of composite primary key

    @Id
    @Column(name="xcase")
    private String xcase;

    private LocalDateTime ztime;
    private LocalDateTime zutime;
    private String xstatus;
    private String xstatuspharma;
    private String xdoctor;
    private String xstaff;
    private String xsex;
    private String xapptype;
    private String xdependent;
    private String xpatient;

=======
@IdClass(MmappointmentId.class)
public class Mmappointment {

    @Id
    @Column(name = "zid", nullable = false)
    private Integer zid;

    @Id
    @Column(name = "xcase", nullable = false, length = 50)
    private String xcase;

    @Column(name = "ztime")
    private LocalDateTime ztime;

    @Column(name = "zutime")
    private LocalDateTime zutime;

    @Column(name = "zauserid", length = 50)
    private String zauserid;

    @Column(name = "zuuserid", length = 50)
    private String zuuserid;

    @Column(name = "xstatus", length = 50)
    private String xstatus;

    @Column(name = "xstatuspharma", length = 50)
    private String xstatuspharma;

    @Column(name = "xdoctor", length = 50)
    private String xdoctor;

    @Column(name = "xstaff", length = 50)
    private String xstaff;

    @Column(name = "xbirthdate")
    private LocalDate xbirthdate;

    @Column(name = "xsex", length = 50)
    private String xsex;

    @Column(name = "xyearperdate")
    private Integer xyearperdate;

    @Column(name = "xrow")
    private Integer xrow;

    @Column(name = "xapptime")
    private LocalTime xapptime;

    @Column(name = "xmobile", length = 40)
    private String xmobile;

    @Column(name = "xappcompleted")
    private LocalDateTime xappcompleted;

    @Column(name = "xcompleteid", length = 50)
    private String xcompleteid;

    @Column(name = "xsendpharmadt")
    private LocalDateTime xsendpharmadt;

    @Column(name = "xsendpharmaid", length = 50)
    private String xsendpharmaid;

    @Column(name = "xage", length = 50)
    private String xage;

    @Column(name = "xposition", length = 50)
    private String xposition;

    @Column(name = "xdeptname", length = 50)
    private String xdeptname;

    @Column(name = "xnote", length = 5000)
    private String xnote;

    @Column(name = "xdesignation", length = 50)
    private String xdesignation;

    @Column(name = "xprofdegree", length = 250)
    private String xprofdegree;

    @Column(name = "xstatusmodifyid", length = 50)
    private String xstatusmodifyid;

    @Column(name = "xstatusinvestigation", length = 50)
    private String xstatusinvestigation;

    @Column(name = "xinvestbillerid", length = 50)
    private String xinvestbillerid;

    @Column(name = "xinvestbilldatetime")
    private LocalDateTime xinvestbilldatetime;

    @Column(name = "xdate")
    private LocalDateTime xdate;

    @Column(name = "xapptype", length = 200)
    private String xapptype;

    @Column(name = "xlong", length = 200)
    private String xlong;

    @Column(name = "xcode", length = 200)
    private String xcode;

    @Column(name = "xdependent", length = 200)
    private String xdependent;

    @Column(name = "xpatient", length = 500)
    private String xpatient;

    @Column(name = "xstatusint")
    private Integer xstatusint;

    @Column(name = "xchiefcomplain", length = 2000)
    private String xchiefcomplain;

    @Column(name = "xfamilyother", length = 5000)
    private String xfamilyother;

    @Column(name = "xsurgicalhistory", length = 250)
    private String xsurgicalhistory;

    @Column(name = "xfamilyhistory", length = 250)
    private String xfamilyhistory;

    @Column(name = "xother", length = 1000)
    private String xother;

    @Column(name = "xinvestigationhistroy", length = 1000)
    private String xinvestigationhistroy;

    @Column(name = "xfollowupadvice", length = 2000)
    private String xfollowupadvice;

    @Column(name = "xpatadvice", length = 50)
    private String xpatadvice;

    @Column(name = "xprovdiagnosis", length = 250)
    private String xprovdiagnosis;

    @Column(name = "xdiagnosis", length = 200)
    private String xdiagnosis;

    @Column(name = "xinvestigation", length = 50)
    private String xinvestigation;

    @Column(name = "xlasa", length = 2000)
    private String xlasa;
>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
}
