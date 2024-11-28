package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.dto.PogrnheaderXcusdto;
import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.model.Xcodes;
import com.zaberp.zab.biwtabackend.repository.PogrnHeaderRepository;

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
    public class PogrnHeaderService {

        @Autowired
        private PogrnHeaderRepository repository;

        private final PrimaryKeyService primaryKeyService;

        public PogrnHeaderService(PrimaryKeyService primaryKeyService) {
            this.primaryKeyService = primaryKeyService;
        }

        public Optional<Pogrnheader> getByZidAndXgrnnum(int zid, String xgrnnum) {
            return repository.findByZidAndXgrnnum(zid, xgrnnum);
        }


        public Pogrnheader createGrn(Pogrnheader pogrnheader) {
            String generatedKey=primaryKeyService.getGeneratedPrimaryKey(pogrnheader.getZid(),"GRN Number","GRN-",6);
            pogrnheader.setXgrnnum(generatedKey);
            pogrnheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
            pogrnheader.setZtime(LocalDateTime.now());
            pogrnheader.setXstatus("Open");
            pogrnheader.setXstatusgrn("Open");
            return repository.save(pogrnheader);
        }

        public Pogrnheader updateByZidAndXgrnnum(Pogrnheader entity) {
            return repository.save(entity);
        }

        public void deleteByZidAndXgrnnum(int zid, String xgrnnum) {

            repository.deleteByZidAndXgrnnum(zid, xgrnnum);
        }

        @Transactional
        public void deleteGrn(PogrnHeaderId id) {
            try {
                repository.deleteById(id);
            } catch (DataIntegrityViolationException ex) {
                throw new DataIntegrityViolationException("Cannot delete item due to foreign key constraints: " + ex.getMessage(), ex);
            }
        }

        public Page<PogrnheaderXcusdto> findPogrnWithSupplier(int zid, String xstatus, int page, int size, String sortBy, boolean ascending) {
            Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            return repository.findPogrnWithSupplier(zid,xstatus,pageable);
        }

        public List<PogrnheaderXcusdto> searchByText(int zid, String searchText) {
            return repository.findGrnWithZidAndSearchText(zid,searchText);
        }
    }

