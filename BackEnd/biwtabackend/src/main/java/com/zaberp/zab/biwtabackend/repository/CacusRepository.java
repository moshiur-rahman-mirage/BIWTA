package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacusRepository extends JpaRepository<Cacus, CacusId> {
    @Query("SELECT c FROM Cacus c WHERE c.zid = :zid AND c.xtype = :xtype")
    List<Cacus> findByZidAndXtype(@Param("zid") Integer zid, @Param("xtype") String xtype);


}
