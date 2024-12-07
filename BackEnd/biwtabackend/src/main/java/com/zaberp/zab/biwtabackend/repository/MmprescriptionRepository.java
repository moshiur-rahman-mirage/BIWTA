package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import com.zaberp.zab.biwtabackend.model.Mmprescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MmprescriptionRepository extends JpaRepository<Mmprescription, MmprescriptionId> {
    @Query("SELECT COALESCE(MAX(pd.xrow), 0) FROM Mmprescription pd WHERE pd.zid = :zid AND pd.xcase = :xcase")
    int findMaxXrowByZidAndXtornum(@Param("zid") int zid, @Param("xcase") String xcase);
}
