package com.zaberp.zab.biwtabackend.repository;

import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MmappointmentRepository extends JpaRepository<Mmappointment, MmappointmentId> {

    public List<Mmappointment> findByZid(int zid);
    public Mmappointment findByZidAndXcase(int zid,String xcase);
}

