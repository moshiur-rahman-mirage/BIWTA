package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.dto.PogrndetailXitemdto;
import com.zaberp.zab.biwtabackend.id.PogrndetailId;
import com.zaberp.zab.biwtabackend.model.Pogrndetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PogrndetailRepository extends JpaRepository<Pogrndetail, PogrndetailId> {

    @Query("SELECT COALESCE(MAX(pd.xrow), 0) FROM Pogrndetail pd WHERE pd.zid = :zid AND pd.xgrnnum = :xgrnnum")
    int findMaxXrowByZidAndXgrnnumber(@Param("zid") int zid, @Param("xgrnnum") String xgrnnum);




    @Query("SELECT new com.zaberp.zab.biwtabackend.dto.PogrndetailXitemdto(" +
            "p.zid, p.xgrnnum, p.xrow, p.xitem, c.xdesc, p.xdateexp, p.xbatch, p.xqtygrn, p.xrategrn, p.xlineamt) " +
            "FROM Pogrndetail p join Caitem c on p.zid=c.zid and p.xitem=c.xitem " +
            "WHERE p.zid = :zid and p.xgrnnum=:xgrnnum ") // You can adjust the condition as needed
    Page<PogrndetailXitemdto> findAllByZid(int zid,String xgrnnum, Pageable pageable);


}
