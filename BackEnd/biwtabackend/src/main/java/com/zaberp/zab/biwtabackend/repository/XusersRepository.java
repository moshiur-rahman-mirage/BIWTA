package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.XusersId;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import com.zaberp.zab.biwtabackend.model.Xusers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface XusersRepository extends JpaRepository<Xusers, XusersId>, JpaSpecificationExecutor<Xusers> {

    boolean existsByZidAndZemailAndXposition(Integer zid, String zemail, String xposition);
    boolean existsByZidAndZemail(Integer zid, String zemail);

    Xusers findByZemail(String zemail);

    @Query("SELECT e FROM Xusers e WHERE e.zid = :zid and  e.xposition LIKE %:searchText% OR e.zemail LIKE %:searchText% OR" +
            " e.xwh LIKE %:searchText%")
    List<Xusers> findBySearchTextAndZid(@Param("zid") int zid, @Param("searchText") String searchText);


}
