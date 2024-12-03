package com.zaberp.zab.biwtabackend.model;



import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mmprescription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MmprescriptionId.class)
public class Mmprescription {

    @Id
    @Column(nullable = false)
    private Integer zid;

    @Column(nullable = false)
    @Id
    private Integer xrow;

    @Column(nullable = false, length = 50)
    @Id
    private String xcase;

    private LocalDateTime ztime;
    private LocalDateTime zutime;

    @Column(length = 50)
    private String zauserid;

    @Column(length = 50)
    private String zuuserid;

    @Column(length = 50)
    private String xpatient;

    private LocalDate xfdate;
    private LocalDate xtdate;

    @Column(length = 100)
    private String xgenericname;

    @Column(length = 50)
    private String xstrengthgn;

    @Column(length = 50)
    private String xunit;

    @Column(length = 100)
    private String xdrugtype;

    @Column(length = 1000)
    private String xdesc;

    @Column(length = 350)
    private String xgenericdesc;

    @Column(length = 50)
    private String xinst;

    @Column(length = 100)
    private String xtemplateinst;

    @Column(length = 50)
    private String xdrug;

    @Column(length = 50)
    private String xdosage;

    @Column(length = 50)
    private String xdose;

    @Column(length = 50)
    private String xfrequency;

    @Column(length = 50)
    private String xtakingtime;

    @Column(length = 50)
    private String xduration1;

    @Column(length = 50)
    private String xdurationday;

    private Integer xqty;

    @Column(length = 1000)
    private String xrem;

    @Column(length = 50)
    private String xroute;

    @Column(length = 500)
    private String xmeddetail;

    @Column(length = 500)
    private String xmedcadvice;

    @Column(length = 50)
    private String xitem1;

    @Column(length = 50)
    private String xitem2;

    @Column(length = 50)
    private String xitem3;

    private BigDecimal xqtyord1;
    private BigDecimal xqtyord2;
    private BigDecimal xqtyord3;

    @Column(length = 50)
    private String xinst2;

    @Column(length = 50)
    private String xinst3;

    @Column(length = 50)
    private String xwh;

    @Column(length = 50)
    private String xwhcomp;

    private BigDecimal xqtyord;
}

