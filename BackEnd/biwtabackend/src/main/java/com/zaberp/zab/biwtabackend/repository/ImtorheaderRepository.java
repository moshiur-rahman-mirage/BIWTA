package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImtorheaderRepository extends JpaRepository<Imtorheader, ImtorheaderId> {






    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "WHERE p.zid = :zid  " +
            "AND (:xstatustor = '' OR p.xstatustor = :xstatustor) " +
            "AND (:xtrn = '' OR p.xtrn = :xtrn) " +
            "AND (:zauserid = '' OR p.zauserid = :zauserid)")

    Page<ImtorDto> findImtorWithZidAndStatusAndUser(@Param("zid") int zid,
                                                    @Param("xstatustor") String xstatustor,@Param("xtrn") String xtrn, @Param("zauserid") String zauserid, Pageable pageable);



    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "WHERE p.zid = :zid  " +
            "AND ( p.xstatustor in ('Partial Issue','Transferred','Confirmed','Checked','Approved')) " +
            "AND (:xtrn = '' OR p.xtrn = :xtrn) " +
            "")
    Page<ImtorDto> findImtorWithZidAndStatus(@Param("zid") int zid,@Param("xtrn") String xtrn, Pageable pageable);


    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xtwh, x1.xlong, p.xstatustor, p.xlong) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "LEFT JOIN Xcodes x1 ON p.zid = x1.zid AND p.xtwh = x1.xcode AND x1.xtype = 'Branch' " +
            "WHERE p.zid = :zid " +
            "AND (" +
            "  (:action IS NULL OR :action = '' OR :action = 'Damage' AND p.xtornum LIKE 'DAM-%') " +
            "  OR (:action = 'Requisition' AND p.xtornum LIKE 'SR-%')" +
            ") " +
            "AND (p.xtornum like %:searchText%  OR x.xlong like %:searchText% )")
    List<ImtorDto> findImtorWithZidAndSearchText(
            @Param("zid") int zid,
            @Param("action") String action,
            @Param("searchText") String searchText);




    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xtwh, x1.xlong, p.xstatustor, p.xlong) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "LEFT JOIN Xcodes x1 ON p.zid = x1.zid AND p.xtwh = x1.xcode AND x1.xtype = 'Branch' " +
            "WHERE p.zid = :zid " +
            "AND (p.xtornum like %:searchText%  OR x.xlong like %:searchText% )")
    List<ImtorDto> findImtorWithZidAndText(
            @Param("zid") int zid,
            @Param("searchText") String searchText);

    public Imtorheader findByZidAndXtornum(int zid, String xtornum);


//    @Procedure(procedureName = "zabsp_confirmTO")
//    void confirmTransfer(int zid, String user,String xposition,String xtornum, Date xdatecom, String xwh,String xtwh,String xstatus, int len);

}

