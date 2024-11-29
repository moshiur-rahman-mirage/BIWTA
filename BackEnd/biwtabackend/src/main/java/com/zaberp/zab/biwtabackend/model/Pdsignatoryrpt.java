package com.zaberp.zab.biwtabackend.model;



import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pdsignatoryrpt")
@IdClass(PdsignatoryrptId.class) // Composite Key Class
public class Pdsignatoryrpt {

    @Id
    private Integer zid;

    @Id
    private String xtypetrn;

    private LocalDateTime ztime;
    private LocalDateTime zutime;
    private String zauserid;
    private String zuuserid;
    private String xsignatory1;
    private String xsignatory2;
    private String xsignatory3;
    private String xsignatory4;
    private String xsignatory5;
    private String xsignatory6;
    private String xsignatory7;
    private String xsignatory8;
    private String xsignatory9;
    private Integer xnofsignatory;
    private String xlong;
    private String xsubprcs;
    private Integer xrow;
    private String xyesno;


}

