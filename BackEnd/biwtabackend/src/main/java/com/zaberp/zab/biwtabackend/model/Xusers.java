package com.zaberp.zab.biwtabackend.model;


import com.zaberp.zab.biwtabackend.id.XusersId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@IdClass(XusersId.class)
public class Xusers {

    @Id
    @Column(name = "zid", insertable = true, updatable = false)
    private int zid;

    @Column(name="ztime")
    private LocalDateTime ztime;

    @Column(name="zutime")
    private LocalDateTime zutime;

    @Column(name="zauserid")
    private String zauserid;

    @Column(name="zuuserid")
    private String zuuserid;

    @Id
    @Column(name = "zemail")
    private String zemail;

    @Column(name = "xpassword")
    private String xpassword;

    @Column(name="xaccess")
    private String xaccess;

    @Column(name="xposition")
    private String xposition;

    @Column(name = "xoldpass")
    private String xoldpass;

    @Column(name = "zactive")
    private String zactive;

    @Column(name="xwh")
    private String xwh;


    public Integer getZid() {
        return zid;
    }
    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getZemail() {
        return zemail;
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
        this.zutime = LocalDateTime.now();
    }
    }
