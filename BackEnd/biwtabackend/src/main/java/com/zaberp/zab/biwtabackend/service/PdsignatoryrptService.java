package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import com.zaberp.zab.biwtabackend.repository.PdsignatoryrptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdsignatoryrptService {

    @Autowired
    private PdsignatoryrptRepository repository;

    public List<Pdsignatoryrpt> getAllRecords() {
        return repository.findAll();
    }

    public Pdsignatoryrpt getRecordById(PdsignatoryrptId id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public Pdsignatoryrpt saveRecord(Pdsignatoryrpt record) {
        return repository.save(record);
    }

    public void deleteRecord(PdsignatoryrptId id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Record not found for deletion");
        }
        repository.deleteById(id);
    }
}

