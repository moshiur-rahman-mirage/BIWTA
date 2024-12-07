package com.zaberp.zab.biwtabackend.model;


import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "mmappointment")
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

}
