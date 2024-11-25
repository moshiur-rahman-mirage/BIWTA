package com.zaberp.zab.biwtabackend.repository;



import com.zaberp.zab.biwtabackend.id.XtrnId;
import com.zaberp.zab.biwtabackend.model.Xtrn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XtrnRepository extends JpaRepository<Xtrn, XtrnId> , JpaSpecificationExecutor<Xtrn> {

    @Query("SELECT e FROM Xtrn e WHERE e.zactive='1' and e.zid = :zid and e.xtypetrn=:xtypetrn and e.xtrn LIKE %:searchText%")
    List<Xtrn> findActiveXtrnByZidAndTypetrn(@Param("zid") int zid, @Param("xtypetrn") String xtypetrn);
}
