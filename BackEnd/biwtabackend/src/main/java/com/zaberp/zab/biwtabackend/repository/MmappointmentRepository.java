package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.MmappointmentId;

import com.zaberp.zab.biwtabackend.model.Mmappointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmappointmentRepository extends JpaRepository<Mmappointment, MmappointmentId> {
   public Mmappointment findByZidAndXcase(int zid,String xcase);
   public List<Mmappointment> findByZid(int zid);
}
