package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.PdDependent;
import com.zaberp.zab.biwtabackend.repository.CaitemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaitemService {

    private final CaitemRepository caitemRepository;

    @Autowired
    public CaitemService(CaitemRepository caitemRepository) {
        this.caitemRepository = caitemRepository;
    }

    public List<Caitem> getAllItems() {
        return caitemRepository.findAll();
    }

    public List<Caitem> findByZid(int zid) {
        return caitemRepository.findByZid(zid);
    }

    public Optional<Caitem> getItemById(CaitemId id) {
        return caitemRepository.findById(id);
    }

    public Caitem createItem(Caitem caitem) {
        return caitemRepository.save(caitem);
    }

    public Caitem updateItem(Caitem caitem) {
        return caitemRepository.save(caitem);
    }


    @Transactional
    public void deleteItem(CaitemId id) {
        try {
            caitemRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Cannot delete item due to foreign key constraints: " + ex.getMessage(), ex);
        }
    }
}
