package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.id.CaitemId;
import com.zaberp.zab.biwtabackend.id.PogrnHeaderId;
import com.zaberp.zab.biwtabackend.model.Caitem;
import com.zaberp.zab.biwtabackend.model.Pogrnheader;
import com.zaberp.zab.biwtabackend.repository.PogrnHeaderRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

        public List<Pogrnheader> getByZid(int zid) {
            return repository.findByZid(zid);
        }

        public Pogrnheader createGrn(Pogrnheader pogrnheader) {
            String generatedKey=primaryKeyService.getGeneratedPrimaryKey(pogrnheader.getZid(),"GRN Number","GRN-",6);
            pogrnheader.setXgrnnum(generatedKey);
            pogrnheader.setZauserid(SecurityContextHolder.getContext().getAuthentication().getName());
            pogrnheader.setZtime(LocalDateTime.now());
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
    }

