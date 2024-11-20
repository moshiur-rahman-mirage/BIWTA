package com.zaberp.zab.biwtabackend.xcodes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "xcodes")
@IdClass(XcodesId.class) // For composite primary key
public class Xcodes {

    @Id
    @Column(name = "zid")
    private int zid;

    @Id
    @Column(name = "xtype")
    private String xtype;

    @Id
    @Column(name = "xcode")
    private String xcode;

    @Column(nullable = false, updatable = false)
    private LocalDateTime ztime;

    @Column(name = "zutime")
    private LocalDateTime zutime;

    @Column(name = "zauserid")
    private String zauserid;

    @Column(name = "zuuserid")
    private String zuuserid;

    @Column(name = "xlong")
    @NotBlank(message = "'Description' field is required.")
    private String xlong;

    @Column(name = "zactive")
    private String zactive;

    @Column(name="xemail")
    private String xemail;


    @Column(name="xphone")
    private String xphone;

    @Column(name="xtypeobj")
    private String xtypeobj;

    @Column(name="xmadd")
    private String xmadd;

    @Column(name = "xprofitcntr")
    private String xprofitcntr;

    public Integer getZid() {
        return zid;
    }

    public String getXtype() {
        return xtype;
    }

    public LocalDateTime getZtime() {
        return ztime;
    }

    public LocalDateTime getZutime() {
        return zutime;
    }

    @PrePersist
    protected void onCreate() {
        this.ztime = LocalDateTime.now(); // Set current timestamp during insert
    }

    @PreUpdate
    protected void onUpdate() {
        this.zutime = LocalDateTime.now(); // Update timestamp during update
    }
}

