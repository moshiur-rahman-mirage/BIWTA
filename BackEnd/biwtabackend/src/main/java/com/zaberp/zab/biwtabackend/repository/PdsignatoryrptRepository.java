package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PdsignatoryrptRepository extends JpaRepository<Pdsignatoryrpt, PdsignatoryrptId> {
    Optional<Pdsignatoryrpt> findByZidAndXtypetrn(int zid,String xtypetrn);

    @Query("SELECT COALESCE(MAX(pd.xrow), 0) FROM Pdsignatoryrpt pd WHERE pd.zid = :zid AND pd.xtypetrn = :xtypetrn")
    int findMaxXrowByZidAndXtypetrn(@Param("zid") int zid, @Param("xtypetrn") String xtypetrn);

    @Query("SELECT e FROM Pdsignatoryrpt e WHERE e.zid = :zid and e.xtypetrn LIKE %:searchText% ")
    List<Pdsignatoryrpt> findBySearchTextAndZid(@Param("zid") int zid, @Param("searchText") String searchText);

    Page<Pdsignatoryrpt> findByZid(int zid, Pageable pageable);
}

