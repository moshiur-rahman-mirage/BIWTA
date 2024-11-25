package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.CacusId;
import com.zaberp.zab.biwtabackend.model.Cacus;
import com.zaberp.zab.biwtabackend.repository.CacusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CacusService {



    @Autowired
    private CacusRepository cacusRepository;




    public List<Cacus> getAllCacus() {
        return cacusRepository.findAll();
    }

    public Optional<Cacus> getCacusById(CacusId id) {
        return cacusRepository.findById(id);
    }

    public Cacus save(Cacus cacus) {
        System.out.println("why");
        System.out.println(cacus.getZid());
        return cacusRepository.save(cacus);
    }

    public void deleteCacus(CacusId id) {
        cacusRepository.deleteById(id);
    }

    public List<Cacus> getCacusByZidAndXtype(Integer zid, String xtype) {
        return cacusRepository.findByZidAndXtype(zid, xtype);
    }
}

