package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.PogrnHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PogrnHeaderRepository extends JpaRepository<PogrnHeader, PogrnHeaderId> {

    Optional<PogrnHeader> findByZidAndXgrnnum(int zid, String xgrnnum);

    void deleteByZidAndXgrnnum(int zid, String xgrnnum);
}

