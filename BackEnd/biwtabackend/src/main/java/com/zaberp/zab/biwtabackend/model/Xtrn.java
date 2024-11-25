package com.zaberp.zab.biwtabackend.model;


import com.zaberp.zab.biwtabackend.id.XtrnId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "xtrn", schema = "dbo")
@Data
@IdClass(XtrnId.class)
public class Xtrn {
    @Id
    private int zid;

    @Id
    private String xtypetrn;

    @Id
    private String xtrn;

    private LocalDateTime ztime;
    private LocalDateTime zutime;
    private String zauserid;
    private String zuuserid;
    private String xaction;
    private String xdesc;
    private Integer xnum;
    private Integer xinc;
    private String xwh;
    private String zactive;
    private String xacc;

    // Getters and setters
    // Constructors
}

