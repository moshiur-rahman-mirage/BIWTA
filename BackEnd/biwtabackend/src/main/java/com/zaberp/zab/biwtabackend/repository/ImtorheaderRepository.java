package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.dto.ImtorDto;
import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImtorheaderRepository extends JpaRepository<Imtorheader, ImtorheaderId> {


    Optional<Imtorheader> findByZidAndXtornum(int zid, String xtornum);


    void deleteByZidAndXtornum(int zid, String xtornum);



    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "WHERE p.zid = :zid " +
            "AND (:xstatus = '' OR p.xstatustor = :xstatus) " +
            "AND (:user = '' OR p.zauserid = :user)")
    Page<ImtorDto> findImtorWithZidAndStatusAndUser(@Param("zid") int zid,
                                                    @Param("xstatus") String xstatus, @Param("user") String user, Pageable pageable);


    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtorDto(" +
            "p.zid, p.xtornum, p.xdate, p.xfwh, x.xlong, p.xstatustor) " +
            "FROM Imtorheader p " +
            "JOIN Xcodes x ON p.zid = x.zid AND p.xfwh = x.xcode AND x.xtype = 'Branch' " +
            "WHERE p.zid = :zid " +
            "AND ((:action = 'Damage' AND p.xtornum LIKE 'DAM-%') " +
            "OR (:action = 'Requisition' AND p.xtornum like 'SR-%' )) " +
            "AND (p.xtornum LIKE %:searchText% OR x.xlong LIKE %:searchText%)")
    List<ImtorDto> findImtorWithZidAndSearchText(
            @Param("zid") int zid,
            @Param("action") String action,
            @Param("searchText") String searchText);

//    @Procedure(procedureName = "zabsp_confirmTO")
//    void confirmTransfer(int zid, String user,String xposition,String xtornum, Date xdatecom, String xwh,String xtwh,String xstatus, int len);

}

