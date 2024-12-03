package com.zaberp.zab.biwtabackend.repository;


import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import com.zaberp.zab.biwtabackend.model.Mmprescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MmprescriptionRepository extends JpaRepository<Mmprescription, MmprescriptionId> {
}
