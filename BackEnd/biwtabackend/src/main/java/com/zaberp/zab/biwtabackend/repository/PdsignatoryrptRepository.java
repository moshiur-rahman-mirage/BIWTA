package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PdsignatoryrptRepository extends JpaRepository<Pdsignatoryrpt, PdsignatoryrptId> {
    Optional<Pdsignatoryrpt> findByZidAndXtypetrn(int zid,String xtypetrn);
}

