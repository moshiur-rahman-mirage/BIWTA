package com.zaberp.zab.biwtabackend.repository;



import com.zaberp.zab.biwtabackend.dto.ImtordetailDto;
import com.zaberp.zab.biwtabackend.id.ImtordetailId;
import com.zaberp.zab.biwtabackend.model.Imtordetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImtordetailRepository extends JpaRepository<Imtordetail, ImtordetailId> {

    @Query("SELECT COALESCE(MAX(pd.xrow), 0) FROM Imtordetail pd WHERE pd.zid = :zid AND pd.xtornum = :xtornum")
    int findMaxXrowByZidAndXtornum(@Param("zid") int zid, @Param("xtornum") String xtornum);




    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.ImtordetailDto(" +
            "p.zid, p.xtornum, p.xrow, p.xitem, c.xdesc, p.xqtyord, p.xstype, p.xprepqty,p.xqtyalc) " +
            "FROM Imtordetail p join Caitem c on p.zid=c.zid and p.xitem=c.xitem " +
            "WHERE p.zid = :zid and p.xtornum=:xtornum ") // You can adjust the condition as needed
    Page<ImtordetailDto> findAllByZid(int zid, String xtornum, Pageable pageable);


}
