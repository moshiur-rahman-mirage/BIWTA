package com.zaberp.zab.biwtabackend.service;
import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.model.Zbusiness;
import com.zaberp.zab.biwtabackend.repository.ZbusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZbusinessService {

    @Autowired
    private ZbusinessRepository zbusinessRepository;

    public List<Zbusiness> getAllZbusinesses() {
        return zbusinessRepository.findAll();
    }

    public Optional<Zbusiness> getZbusinessById(Integer id) {
        return zbusinessRepository.findById(id);
    }

    public Zbusiness createZbusiness(Zbusiness zbusiness) {
        return zbusinessRepository.save(zbusiness);
    }

    public Zbusiness updateZbusiness(Integer id, Zbusiness updatedZbusiness) {
        if (zbusinessRepository.existsById(id)) {
            updatedZbusiness.setZid(id);
            return zbusinessRepository.save(updatedZbusiness);
        }
        return null;
    }

    public void deleteZbusiness(Integer id) {
        zbusinessRepository.deleteById(id);
    }

    public Zbusiness findByZid(int zid){

        return zbusinessRepository.findByZid(zid);
    }
}

