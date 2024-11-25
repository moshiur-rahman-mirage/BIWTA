package com.zaberp.zab.biwtabackend.model;


import com.zaberp.zab.biwtabackend.id.CacusId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cacus")
@IdClass(CacusId.class) // For composite primary key
public class Cacus {

    @Id
    @Column(name="zid")
    private int zid; // Part of composite primary key

    @Id
    @Column(name="xcus")
    private String xcus;

    private LocalDateTime ztime;
    private LocalDateTime zutime;
    private String zauserid;
    private String zuuserid;
    private String xorg;
    private String xmadd;
    private String xemail;
    private String xphone;
    private String xmobile;
    private String xfax;
    private String xbin;
    private String xlicense;
    private String xtin;
    private String xircno;
    private String xpaymenttype;
    private String xcontact;
    private String xstatuscus;
    private String xtype;
    private String zactive;
    private String xstatus;

}

