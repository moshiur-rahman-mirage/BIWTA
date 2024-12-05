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

        Integer maxXrow = mmprescriptionRepository.findMaxXrowByZidAndXtornum(mmprescription.getZid(), mmprescription.getXcase());
        if (maxXrow == null) {
            maxXrow = 0;
        }
        mmprescription.setXrow(maxXrow + 1);


        return mmprescriptionRepository.save(mmprescription);
    }

    public void deleteById(MmprescriptionId id) {
        mmprescriptionRepository.deleteById(id);
    }
}

