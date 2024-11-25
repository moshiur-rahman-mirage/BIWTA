package com.zaberp.zab.biwtabackend.repository;

import com.zaberp.zab.biwtabackend.model.Xtrn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryKeyRepository extends JpaRepository<Xtrn, Long> {

    @Procedure(procedureName = "Func_getTrn")
    String generatePrimaryKey(
            @Param("zid") int zid,
            @Param("xtypetrn") String xtypetrn,
            @Param("xtrn") String xtrn,
            @Param("LenV") int lenV
    );
}

