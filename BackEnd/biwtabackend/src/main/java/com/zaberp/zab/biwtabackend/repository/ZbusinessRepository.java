package com.zaberp.zab.biwtabackend.repository;



import com.zaberp.zab.biwtabackend.model.Zbusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZbusinessRepository extends JpaRepository<Zbusiness, Integer> {
    Zbusiness findByZid(int zid);
}

