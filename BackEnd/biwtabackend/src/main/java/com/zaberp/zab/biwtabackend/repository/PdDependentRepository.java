package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.PdDependentId;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Pdmst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdDependentRepository extends JpaRepository<PdDependent, PdDependentId>, JpaSpecificationExecutor<PdDependent> {

    boolean existsByZidAndXstaffAndXrow(Integer zid, String xstaff, int xrow);
    @Query("SELECT e FROM PdDependent e WHERE e.zid = :zid and  e.xstaff LIKE %:searchText% OR e.xname LIKE %:searchText% OR" +
            " e.xnid LIKE %:searchText% OR e.xrelation LIKE %:searchText%")
    List<PdDependent> findBySearchTextAndZid(@Param("zid") String zid, @Param("searchText") String searchText);
}
