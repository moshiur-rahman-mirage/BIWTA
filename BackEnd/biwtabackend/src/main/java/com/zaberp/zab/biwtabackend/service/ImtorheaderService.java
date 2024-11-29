package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.ImtorheaderId;
import com.zaberp.zab.biwtabackend.model.Imtorheader;
import com.zaberp.zab.biwtabackend.repository.ImtorheaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImtorheaderService {

    @Autowired
    private ImtorheaderRepository repository;

    public List<Imtorheader> findAll() {
        return repository.findAll();
    }

    public Optional<Imtorheader> findById(ImtorheaderId id) {
        return repository.findById(id);
    }

    public Imtorheader save(Imtorheader imtorheader) {
        return repository.save(imtorheader);
    }

    public void deleteById(ImtorheaderId id) {
        repository.deleteById(id);
    }
}

