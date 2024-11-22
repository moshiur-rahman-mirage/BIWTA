package com.zaberp.zab.biwtabackend.model;

import com.zaberp.zab.biwtabackend.id.XcodesId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "xcodes")
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime ztime;

    @LastModifiedDate
    @Column(name = "zutime")
    private LocalDateTime zutime;

    @CreatedBy
    @Column(name = "zauserid")
    private String zauserid;
    @LastModifiedBy
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

    @Column(name="xgtype")
    private String xgtype;

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

