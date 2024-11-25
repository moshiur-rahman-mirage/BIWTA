package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaitemRepository extends JpaRepository<Caitem, CaitemId> {
    // JpaRepository provides basic CRUD operations.
    // Custom query methods can be added here if needed.

    List<Caitem> findByZid(@Param("zid") int zid);
}

