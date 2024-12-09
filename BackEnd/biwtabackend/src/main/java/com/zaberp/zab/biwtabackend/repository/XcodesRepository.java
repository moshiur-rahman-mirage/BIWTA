package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.id.XcodesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface XcodesRepository extends JpaRepository<Xcodes, XcodesId> , JpaSpecificationExecutor<Xcodes> {

    @Query("SELECT e FROM Xcodes e WHERE e.zid = :zid and e.xtype=:xtype and e.xcode LIKE %:searchText% OR e.xlong LIKE %:searchText%")
    List<Xcodes> findBySearchTextAndZid(@Param("zid") int zid,@Param("xtype") String xtype, @Param("searchText") String searchText);

    @Query("SELECT e FROM Xcodes e WHERE e.zactive='1' and e.zid = :zid and e.xtype=:xtype and e.xcode LIKE %:searchText% OR e.xlong LIKE %:searchText%")
    List<Xcodes> findActiveXcodesByZidAndType(@Param("zid") int zid,@Param("xtype") String xtype);

    boolean existsByZidAndXtypeAndXcode(int zid, String xtype,String xcode);

    List<Xcodes> findByZidAndXtype(@Param("zid") int zid, @Param("xtype") String xtype);


}

