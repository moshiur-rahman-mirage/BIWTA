package com.zaberp.zab.biwtabackend.model;

import com.zaberp.zab.biwtabackend.id.PdDependentId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "pddependent")
@IdClass(PdDependentId.class)
public class PdDependent {
    @Column
    @Temporal(TemporalType.TIMESTAMP)

    private LocalDateTime ztime;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime zutime;
    @Column
    private String zauserid;
    @Column
    private String zuuserid;
    @Column
    @Id
    private int zid;
    @Column
    @Id
    private String xstaff;
    @Column
    @Id
    private int xrow;
    @Column
    private String xname;
    @Column
    private LocalDate xbirthdate;
    @Column
    private String xsex;
    @Column
    private String xnid;
    @Column
    private String xadd;
    @Column
    private String xcontact;
    @Column
    private String xemail;
    @Column
    private String xrelation;
    @Column
    private String xfile;
    @Column
    @Lob
    private byte[] ximage;

    @PrePersist
    protected void onCreate() {
        this.ztime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.zutime = LocalDateTime.now();
    }


}
