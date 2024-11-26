package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PogrnHeaderRepository extends JpaRepository<Pogrnheader, PogrnHeaderId> {

    Optional<Pogrnheader> findByZidAndXgrnnum(int zid, String xgrnnum);


    void deleteByZidAndXgrnnum(int zid, String xgrnnum);

    List<Pogrnheader> findByZid(@Param("zid") int zid);
}

