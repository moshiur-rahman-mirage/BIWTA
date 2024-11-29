package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImtorheaderRepository extends JpaRepository<Imtorheader, ImtorheaderId> {

}

