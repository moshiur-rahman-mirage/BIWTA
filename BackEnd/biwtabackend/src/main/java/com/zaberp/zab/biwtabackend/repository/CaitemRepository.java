package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaitemRepository extends JpaRepository<Caitem, CaitemId> {

    @Query("SELECT e FROM Caitem e WHERE e.zid = :zid and e.xdesc LIKE %:searchText% OR e.xitem LIKE %:searchText% or e.xgenericdesc like %:searchText% or e.xgenericname like %:searchText% ")
    List<Caitem> findBySearchTextAndZid(@Param("zid") String zid, @Param("searchText") String searchText);


    Page<Caitem> findByZid(int zid, Pageable pageable);
}

