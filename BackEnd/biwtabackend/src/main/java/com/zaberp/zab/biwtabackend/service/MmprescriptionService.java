package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import com.zaberp.zab.biwtabackend.model.Mmprescription;
import com.zaberp.zab.biwtabackend.repository.MmprescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MmprescriptionService {

    private final MmprescriptionRepository mmprescriptionRepository;

    public List<Mmprescription> findAll() {
        return mmprescriptionRepository.findAll();
    }

    public Optional<Mmprescription> findById(MmprescriptionId id) {
        return mmprescriptionRepository.findById(id);
    }

    public Mmprescription save(Mmprescription mmprescription) {
        if (mmprescription.getXcase()=="") {
            throw new IllegalArgumentException("Validation failed: Case Number Required.");
        }
        return mmprescriptionRepository.save(mmprescription);
    }

    public void deleteById(MmprescriptionId id) {
        mmprescriptionRepository.deleteById(id);
    }
}

