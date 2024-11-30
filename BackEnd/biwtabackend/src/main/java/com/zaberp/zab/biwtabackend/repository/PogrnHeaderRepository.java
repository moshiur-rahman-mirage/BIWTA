package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PogrnHeaderRepository extends JpaRepository<Pogrnheader, PogrnHeaderId> {

    Optional<Pogrnheader> findByZidAndXgrnnum(int zid, String xgrnnum);


    void deleteByZidAndXgrnnum(int zid, String xgrnnum);



    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto(" +
            "p.zid, p.xgrnnum, p.xdate, s.xcus, s.xorg, p.xwh, x.xlong, p.xstatus, p.xstatusgrn, p.zauserid) " +
            "FROM Pogrnheader p " +
            "JOIN Cacus s ON p.zid = s.zid AND p.xcus = s.xcus " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xwh = x.xcode and x.xtype='Branch' " +
            "WHERE p.zid=:zid and p.xstatus = :xstatus and p.zauserid=:user")
    Page<PogrnheaderXcusdto> findPogrnWithSupplier(@Param("zid") int zid,
            @Param("xstatus") String xstatus,@Param("user") String user, Pageable pageable);


    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto(" +
            "p.zid, p.xgrnnum, p.xdate, s.xcus, s.xorg, p.xwh, x.xlong, p.xstatus, p.xstatusgrn, p.zauserid) " +
            "FROM Pogrnheader p " +
            "JOIN Cacus s ON p.zid = s.zid AND p.xcus = s.xcus " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xwh = x.xcode and x.xtype='Branch' " +
            "WHERE p.zid=:zid and p.xgrnnum  like %:searchText% or s.xorg like %:searchText%")
    List<PogrnheaderXcusdto> findGrnWithZidAndSearchText(@Param("zid") int zid, @Param("searchText") String searchText);

    @Procedure(procedureName = "zabsp_PO_confirmGRN")
    void confirmGRN(int zid, String user, String xgrnnum, Date xdate, String xwh, int len);
}

