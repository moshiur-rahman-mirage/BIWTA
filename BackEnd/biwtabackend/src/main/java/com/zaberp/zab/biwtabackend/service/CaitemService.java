package com.zaberp.zab.biwtabackend.service;


import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.repository.CaitemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CaitemService {

    private final CaitemRepository caitemRepository;

    private final PrimaryKeyService primaryKeyService;

    @Autowired
    public CaitemService(CaitemRepository caitemRepository,PrimaryKeyService primaryKeyService) {
        this.caitemRepository = caitemRepository;
        this.primaryKeyService=primaryKeyService;
    }

    public List<Caitem> getAllItems() {
        return caitemRepository.findAll();
    }



    public Optional<Caitem> getItemById(CaitemId id) {
        return caitemRepository.findById(id);
    }

    public Caitem createItem(Caitem caitem) {
        String generatedKey=primaryKeyService.getGeneratedPrimaryKey(caitem.getZid(),"Item Code","IC--",6);
        caitem.setXitem(generatedKey.substring(4));
        caitem.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
        caitem.setZtime(LocalDateTime.now());
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

    public Page<Caitem> getItemsWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return caitemRepository.findByZid(zid,pageable);
    }

    public List<Caitem> searchByText(int zid, String searchText) {
        return caitemRepository.findBySearchTextAndZid(zid,searchText);
    }
}
