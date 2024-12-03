package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.MmprescriptionId;
import com.zaberp.zab.biwtabackend.model.Mmprescription;
import com.zaberp.zab.biwtabackend.repository.MmprescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MmprescriptionService {

    private final PrimaryKeyService primaryKeyService;
    @Autowired
    private final MmprescriptionRepository mmprescriptionRepository;

    public List<Mmprescription> findAll() {
        return mmprescriptionRepository.findAll();
    }

    public Optional<Mmprescription> findById(MmprescriptionId id) {
        return mmprescriptionRepository.findById(id);
    }

    public Mmprescription save(Mmprescription mmprescription) {
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(mmprescription.getZid(),"Inventory Transaction","RX--",6);
        mmprescription.setXcase(generatedKey);
        return mmprescriptionRepository.save(mmprescription);
    }

    public void deleteById(MmprescriptionId id) {
        mmprescriptionRepository.deleteById(id);
    }
}

