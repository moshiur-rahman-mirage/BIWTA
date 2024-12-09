package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.PdsignatoryrptId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pdsignatoryrpt;
import com.zaberp.zab.biwtabackend.repository.PdsignatoryrptRepository;
import com.zaberp.zab.biwtabackend.repository.custom.CustomPdsignatoryrptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PdsignatoryrptService {

    @Autowired
    private PdsignatoryrptRepository repository;

    @Autowired
    private CustomPdsignatoryrptRepository customRepository;

    public List<Pdsignatoryrpt> getAllRecords() {
        return repository.findAll();
    }

    public Pdsignatoryrpt getRecordById(PdsignatoryrptId id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public Pdsignatoryrpt saveRecord(Pdsignatoryrpt record) {
        Integer maxXrow = repository.findMaxXrowByZidAndXtypetrn(record.getZid(), record.getXtypetrn());
        if (maxXrow == null) {
            maxXrow = 0;
        }
        record.setXrow(maxXrow + 1);
        return repository.save(record);
    }


    public boolean updatePdsignatoryrpt(int zid, int xrow, Map<String, Object> updates, List<String> excludeColumns) {
        int rowsUpdated = customRepository.updateExcludingColumns(zid, xrow, updates, excludeColumns);
        return rowsUpdated > 0;
    }


    public List<Pdsignatoryrpt> searchByText(int zid, String searchText) {
        return repository.findBySearchTextAndZid(zid,searchText);
    }

    public void deleteRecord(PdsignatoryrptId id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Record not found for deletion");
        }
        repository.deleteById(id);
    }

    public Page<Pdsignatoryrpt> getSignatoryWithPaginationAndSorting(int zid, int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findByZid(zid,pageable);
    }
}

