package com.zaberp.zab.biwtabackend.zbusiness;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZbusinessRepository extends JpaRepository<Zbusiness, Integer> {
}

