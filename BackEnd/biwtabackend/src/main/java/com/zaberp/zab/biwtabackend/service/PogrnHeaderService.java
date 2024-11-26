package com.zaberp.zab.biwtabackend.service;

import com.zaberp.zab.biwtabackend.model.PogrnHeader;
import com.zaberp.zab.biwtabackend.repository.PogrnHeaderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


    @Service
    public class PogrnHeaderService {

        @Autowired
        private PogrnHeaderRepository repository;

        public Optional<PogrnHeader> getByZidAndXgrnnum(int zid, String xgrnnum) {
            return repository.findByZidAndXgrnnum(zid, xgrnnum);
        }

        public PogrnHeader updateByZidAndXgrnnum(PogrnHeader entity) {
            return repository.save(entity);
        }

        public void deleteByZidAndXgrnnum(int zid, String xgrnnum) {
            repository.deleteByZidAndXgrnnum(zid, xgrnnum);
        }
    }

