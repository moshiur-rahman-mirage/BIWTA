package com.zaberp.zab.biwtabackend.repository;

<<<<<<< HEAD

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
=======
import com.zaberp.zab.biwtabackend.id.MmappointmentId;
import com.zaberp.zab.biwtabackend.model.Mmappointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MmappointmentRepository extends JpaRepository<Mmappointment, MmappointmentId> {

    public List<Mmappointment> findByZid(int zid);
    public Mmappointment findByZidAndXcase(int zid,String xcase);
}

>>>>>>> 0ae2933b3b2aa0526bb66f144bcd669cbe51a58f
